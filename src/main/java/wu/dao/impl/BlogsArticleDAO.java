package wu.dao.impl;

import wu.bean.Article;
import wu.bean.User;

import java.sql.Connection;
import java.util.List;

public interface BlogsArticleDAO {
    /**
     * 根据用户的uid查询该用户的所有博客文章
     *
     * @param user 用户信息
     * @return 如果有，则返回这个用户文章的list集合，否则返回null
     */
    List<Article> queueArticleByAuid(Connection conn, User user);

    /**
     * 将文章加入数据库
     *
     * @param article 封装好的文章信息
     * @return 影响的行数
     */
    int addArticle(Connection conn, Article article);

    /**
     * 根据文章的aid 查询文章
     *
     * @param conn    连接
     * @param article 封装好的文章aid
     * @return 详细的文章数据
     */
    Article queueArticleByAid(Connection conn, Article article);

    /**
     * 更改指定aid文章信息，文件名，时间，标题
     *
     * @param conn    连接
     * @param article 文章信息
     * @return 影响的行数
     */
    int changeServlet(Connection conn, Article article);

    /**
     * 返回二十条信息
     *
     * @param conn 连接
     * @return 二十条信息
     */
    List<Article> getAllArticle(Connection conn);
}
