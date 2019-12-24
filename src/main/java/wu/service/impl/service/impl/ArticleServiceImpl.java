package wu.service.impl.service.impl;

import wu.bean.Article;
import wu.bean.User;
import wu.dao.impl.BlogsArticleDAO;
import wu.dao.impl.dao.impl.BlogsArticleDAOImpl;
import wu.service.impl.ArticleService;
import wu.service.impl.UserService;
import wu.utils.JDBCUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.util.*;

public class ArticleServiceImpl implements ArticleService {
    // 获取数据库连接
    private Connection conn = null;
    // 获取数据库操作对象
    private BlogsArticleDAO articlesDAO = new BlogsArticleDAOImpl();

    @Override
    public List<Article> queueArticleByAuid(User user) {
        try {
            conn = JDBCUtils.getConnection();
            return articlesDAO.queueArticleByAuid(conn, user);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn);
        }

        return null;
    }

    @Override
    public int addArticle(Article article) {
        try {
            conn = JDBCUtils.getConnection();
            return articlesDAO.addArticle(conn, article);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn);
        }
        return 0;
    }

    @Override
    public Article queueArticleByAid(Article article) {
        try {
            conn = JDBCUtils.getConnection();

            return articlesDAO.queueArticleByAid(conn, article);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn);
        }

        return null;
    }

    @Override
    public void changeServlet(Article article) {
        BufferedWriter bw = null;
        try {
            conn = JDBCUtils.getConnection();

            // 数据库的字段，硬盘中文件的内容以及文件名

            // 修改文件名
            // 1.获取这篇文章的个人信息
            User user = new UserServiceImpl().queueUserByAuid(article);

            // 文件名，时间，标题
            // 创建时间对象
            Date date = new Date();
            // 将文件对象对接到文件中
            File file = new File(article.getPath() + user.getUsername() + article.getTime().getTime());

            // 将新文件对象对接到新文件中
            File newFile = new File(article.getPath() + user.getUsername() + date.getTime());

            // 如果文件重命名成功，更改article中时间的保存点和文件名
            // 否则不做处理
            if (file.renameTo(newFile)) {
                article.setTime(date);
                article.setFileName(article.getPath() + user.getUsername() + date.getTime());

                // 跟新数据库的内容
                if (articlesDAO.changeServlet(conn, article) != 0) {
                    // 更新硬盘中文件的内容
                    bw = new BufferedWriter(new FileWriter(newFile));
                    bw.write(article.getContent());
                    bw.flush();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, bw);
        }
    }

    @Override
    public List<Map<String, Object>> getAllArticleAndUser() {
        try {
            // 存放前13篇文章的标题，更改时间，作者
            ArrayList<Map<String, Object>> all = new ArrayList<>();

            conn = JDBCUtils.getConnection();
            // 前十三篇文章详细信息
            List<Article> allArticle = articlesDAO.getAllArticle(conn);

            // 查询用户信息
            UserService userService = new UserServiceImpl();
            for (Article article : allArticle) {
                User user = userService.queueUserByAuid(article);

                // 前十三篇用户详细信息
                Map<String, Object> map = new HashMap<>();
                // 存放每一篇的信息
                map.put("title", article.getTitle());
                map.put("time", article.getTime());
                map.put("username", user.getUsername());
                map.put("aid", article.getAid());

                all.add(map);
            }

            return all;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn);
        }

        return null;
    }


}
