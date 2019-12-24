package wu.dao.impl.dao.impl;

import wu.bean.Article;
import wu.bean.User;
import wu.dao.impl.BlogsArticleDAO;
import wu.dao.impl.BlogsUserDAO;
import wu.dao.impl.sqlGeneral;
import wu.utils.JDBCUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.util.List;

public class BlogsArticleDAOImpl extends sqlGeneral implements BlogsArticleDAO {
    @Override
    public List<Article> queueArticleByAuid(Connection conn, User user) {
        String sql = "select * from blogs_article where auid = ? order by `time` desc";
        return super.queue(Article.class, conn, sql, user.getUid());
    }

    @Override
    public int addArticle(Connection conn, Article article) {
//        Article article = new Article(0, path, fileName, time, user.getUid(), head);
        String sql = "insert into blogs_article (path, fileName, `time`, auid, title) value(?, ?, ?, ?, ?)";
        return super.update(conn, sql, article.getPath(), article.getFileName(), article.getTime(), article.getAuid(), article.getTitle());
    }

    @Override
    public Article queueArticleByAid(Connection conn, Article article) {
        String sql = "select * from blogs_article where aid = ?";

        BufferedReader br = null;
        Article rsArticle = null;
        try {
            // 查询文章信息
            rsArticle = super.queue(Article.class, conn, sql, article.getAid()).get(0);

            // 将内容从硬盘中读入文章内容，封装到对象中，文件名 = 用户名 + 文章创建毫秒值
            BlogsUserDAO userDAO = new BlogsUserDAOImpl();

            // 封装用户信息
            User user = new User(rsArticle.getAuid(), null, null, null, null);
            // 查询用户信息
            User rsUser = userDAO.queueUserByUid(conn, user);

            String fileName = rsArticle.getPath() + rsUser.getUsername() + rsArticle.getTime().getTime();

            // 将流对接到文件中
            br = new BufferedReader(new FileReader(fileName));

            // 读取文件中的内容
            String str;
            StringBuilder content = new StringBuilder();
            while ((str = br.readLine()) != null) {
                content.append(str);
            }

            rsArticle.setContent(content.toString());

        } catch (Exception e) {
            // todo 说明没有查询到信息，不做处理
        } finally {
            JDBCUtils.closeResource(br);
        }

        return rsArticle;
    }

    @Override
    public int changeServlet(Connection conn, Article article) {
        String sql = "update `blogs_article` set fileName = ?, `time` = ?, title = ? where aid = ?";
        return super.update(conn, sql, article.getFileName(), article.getTime(), article.getTitle(), article.getAid());
    }

    @Override
    public List<Article> getAllArticle(Connection conn) {
        String sql = "select * from `blogs_article` order by `time` desc limit 13";
        return super.queue(Article.class, conn, sql);
    }

}
