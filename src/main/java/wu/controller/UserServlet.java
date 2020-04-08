package wu.controller;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wu.bean.Article;
import wu.bean.ResultInfo;
import wu.bean.User;
import wu.service.ArticleService;
import wu.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileWriter;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/static/user")
@NoArgsConstructor
public class UserServlet {
    // 创建服务对象
    private ArticleService articleService;
    private UserService userService;

    @Autowired
    public UserServlet(ArticleService articleService, UserService userService) {
        this.articleService = articleService;
        this.userService = userService;
    }

    // 写文章
    @RequestMapping("/addArticleServlet")
    public String addArticleServlet(HttpServletRequest request) throws Exception {
        // 获取作者信息
        User user = (User) request.getSession().getAttribute("userMessage");

        // 获取当前系统时间，毫秒值
        Date time = new Date();
        // 获取文件内容保存的路径
        String path = "/userArticle/";
        // 获取文件名 用户名 + 当前系统的毫秒值
        String fileName = user.getUsername() + time.getTime();
        // 获取post文章的数据
        String title = request.getParameter("title");
        String content = request.getParameter("content");

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
        }

        // 重定向
        return "redirect:/static/user/getArticleService";
    }

    // 注册用户
    @RequestMapping("/addUserServlet")
    public String addUserServlet(User user, HttpServletRequest request) {
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
        return "redirect:/index.jsp";
    }

    // 寻找用户 Ajax
    @RequestMapping("/findUserServlet")
    @ResponseBody
    public ResultInfo findUserServlet(User user) {
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
        return resultInfo;
    }

    // 回显文章
    @RequestMapping("/showArticleServlet")
    @ResponseBody
    public Article showArticleServlet(Article article) {
        // 创建服务对象，并调用相关函数
        return articleService.queueArticleByAid(article);  // todo 这里可以改进，此处服务器存储了用户的信息，可以直接传递，不用后台再次查询数据库
    }

    // 得到文章信息
    @RequestMapping("/getArticleService")
    public String getArticleService(HttpServletRequest request) {
        // 获得信息
        User user = (User) request.getSession().getAttribute("userMessage");

        // 调用函数
        List<Article> articles = articleService.queueArticleByAuid(user);

        // 存入信息
        request.getSession().setAttribute("userArticle", articles);

        // 重定向
        return "redirect:/static/personBlogs.jsp";
    }

    // 得到用户信息
    @RequestMapping("/getUserService")
    @ResponseBody
    public User getUserService(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("userMessage");
    }

    // 登陆
    @RequestMapping("/loginServlet")
    public String loginServlet(User user, HttpServletRequest request) {
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
        return "redirect:/index.jsp";
    }

    // 更改文章内容
    @RequestMapping("/changeServlet")
    public String changeServlet(Article article) {
        // 查询详细的文章具体信息
        Article rsArticle = articleService.queueArticleByAid(article);

        // 改变标题和文章内容，再次封装
        rsArticle.setTitle(article.getTitle());
        rsArticle.setContent(article.getContent());

        articleService.changeServlet(rsArticle);

        // 直接重定向
        return "redirect:/static/user/getArticleService";
    }

    // 退出
    @RequestMapping("/exitUserServlet")
    public String exitUserServlet(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute("userMessage");

        // 重定向
        return "redirect:/index.jsp";
    }

    // 判断是否登陆
    @RequestMapping("/isLoginServlet")
    @ResponseBody
    public User isLoginServlet(HttpServletRequest request) {
        // 获取session中的数据，转化为json返回
        return (User) request.getSession().getAttribute("userMessage");
    }
}
