package wu.web;

import wu.bean.Article;
import wu.bean.ResultInfo;
import wu.bean.User;
import wu.service.impl.ArticleService;
import wu.service.impl.UserService;
import wu.service.impl.service.impl.ArticleServiceImpl;
import wu.service.impl.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    // 创建服务对象
    private ArticleService articleService = new ArticleServiceImpl();
    private UserService userService = new UserServiceImpl();

    // 写文章
    public void addArticleServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置流编码
        request.setCharacterEncoding("utf8");

        // 获取post文章的数据
        String title = request.getParameter("title");
        String content = request.getParameter("content");

        // 获取作者信息
        User user = (User) request.getSession().getAttribute("userMessage");

        // 获取当前系统时间，毫秒值
        Date time = new Date();
        // 获取文件内容保存的路径
        String path = "/userArticle/";
        // 获取文件名 用户名 + 当前系统的毫秒值
        String fileName = user.getUsername() + time.getTime();


        //封装数据
        // int aid, String path, String fileName, Date time, int auid, String title
        Article article = new Article(0, path, fileName, time, user.getUid(), title, content);

        // 将数据对象存入数据库
        int rs = articleService.addArticle(article);

        // 创建目录，如果成功，将文件写出
        File file = new File(path);
        // 如果有结果，就创建路径
        if (rs != 0 && file.exists() || file.mkdirs()) {
            // 将文件内容存入硬盘的某个位置
            file = new File(file, fileName);

            FileWriter fw = new FileWriter(file);
            fw.write(article.getContent());
            fw.flush();
            fw.close();

        } else {
            System.out.println("addArticleServlet  ---  提交失败");
        }

        // 重定向
        response.sendRedirect(request.getContextPath() + "/user/getArticleService");
    }

    // 注册用户
    public void addUserServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取用户的输入数据
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 将数据打包
        User user = new User(0, username, password, null, null);

        // 将数据传给Service层
        int rs = userService.addUser(user);

        // 接受数据，根据数据的情况返回结果
        if (rs != 0) {
            // 获得 session 存储对象
            HttpSession session = request.getSession();

            // 查询用户的详细信息
            User newUser = userService.queueUserByUsername(user);

            // 将用户的详细信息存入
            session.setAttribute("userMessage", newUser);
        }

        // 不管是否成功都重定向
        response.sendRedirect(request.getContextPath() + "/index.jsp");

    }

    // 寻找用户 Ajax
    public void findUserServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 将用户填写的数据打包
        User user = new User(0, (String) request.getParameter("username"), null, null, null);

        // 查询用户数据，接收查询结果
        User rsUser = userService.queueUserByUsername(user);

        // 封装结果集
        ResultInfo resultInfo = new ResultInfo();
        if (rsUser == null) {
            // 说明没有查到该信息，用户名可以使用
            resultInfo.setFlag(true);
        } else {
            // 说明数据库有该字段，用户名不可用
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("用户名被占用");
        }

        // 序列化结果对象，设置相应头，将结果返回给前端
        super.writeJson(resultInfo, response);
    }

    // 回显文章
    public void showArticleServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置流编码
        response.setCharacterEncoding("utf8");
        // 利用aid的唯一性，查询该文章的所有信息
        int aid = Integer.parseInt(request.getParameter("aid"));

        // 封装数据
        Article article = new Article(aid, null, null, null, aid, null, null);

        // 创建服务对象，并调用相关函数
        Article rs = articleService.queueArticleByAid(article);  // todo 这里可以改进，此处服务器存储了用户的信息，可以直接传递，不用后台再次查询数据库

        if (rs != null) {
            // 写出json
            super.writeJson(rs, response);
        }
    }

    // 得到文章信息
    public void getArticleService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获得信息
        User user = (User) request.getSession().getAttribute("userMessage");

        // 调用函数
        List<Article> articles = articleService.queueArticleByAuid(user);

        // 存入信息
        request.getSession().setAttribute("userArticle", articles);
        // 重定向
        response.sendRedirect(request.getContextPath() + "/personBlogs.jsp");
    }

    // 得到用户信息
    public void getUserService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("userMessage");
        super.writeJson(user, response);
    }

    // 登陆
    public void loginServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取用户的输入数据
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 将数据打包
        User user = new User(0, username, password, null, null);

        // 将数据传给Service层
        // 调用service层，添加数据
        User rsUser = userService.queueUserByUid(user);

        if (rsUser != null) {
            // 获得 session 存储对象
            HttpSession session = request.getSession();

            // 将用户的详细信息存入
            session.setAttribute("userMessage", rsUser);
        }

        // 不管是否成功都重定向
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }

    // 更改文章内容
    public void changeServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf8");
        // 获取文章id
        int aid = Integer.parseInt(request.getParameter("aid"));

        // 封装数据
        Article article = new Article(aid, null, null, null, 0, null, null);

        // 查询详细的文章具体信息
        Article rsArticle = articleService.queueArticleByAid(article);

        // 改变标题和文章内容，再次封装
        rsArticle.setTitle(request.getParameter("title"));
        rsArticle.setContent(request.getParameter("content"));

        articleService.changeServlet(rsArticle);

        // 直接重定向
        response.sendRedirect(request.getContextPath() + "/user/getArticleService");
    }

    // 退出
    public void exitUserServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().removeAttribute("userMessage");

        // 重定向
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }

    // 判断是否登陆
    public void isLoginServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取session中的数据，转化为json返回
        User user = (User) request.getSession().getAttribute("userMessage");

        super.writeJson(user, response);
    }
}
