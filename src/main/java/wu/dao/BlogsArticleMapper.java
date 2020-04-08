package wu.dao;

import org.apache.ibatis.annotations.Param;
import wu.bean.Article;
import wu.bean.User;

import java.util.List;


public interface BlogsArticleMapper {
    /**
     * 根据用户的uid查询该用户的所有博客文章
     *
     * @param user 用户信息
     * @return 如果有，则返回这个用户文章的list集合，否则返回null
     */
    List<Article> queueArticleByAuid(@Param("user") User user);

    /**
     * 将文章加入数据库
     *
     * @param article 封装好的文章信息
     * @return 影响的行数
     */
    int addArticle(@Param("article") Article article);

    /**
     * 根据文章的aid 查询文章
     *
     * @param article 封装好的文章aid
     * @return 详细的文章数据
     */
    Article queueArticleByAid(@Param("article") Article article);

    /**
     * 更改指定aid文章信息，文件名，时间，标题
     *
     * @param article 文章信息
     * @return 影响的行数
     */
    int changeServlet(@Param("article") Article article);

    /**
     * 返回二十条信息
     *
     * @return 二十条信息
     */
    List<Article> getAllArticle();
}
