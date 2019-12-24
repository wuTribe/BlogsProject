package wu.service.impl;

import wu.bean.Article;
import wu.bean.User;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    /**
     * 根据传入的用户信息，查询所有的文章详细信息
     *
     * @param user 用户信息
     * @return 文章集合，如果没有返回null
     */
    List<Article> queueArticleByAuid(User user);

    /**
     * 将封装的数据加入数据库
     *
     * @param article 封装的文章信息
     * @return 影响的行数
     */
    int addArticle(Article article);

    /**
     * 根据封装的article中的id查询文章的详细信息
     *
     * @param article 封装好的信息
     * @return 详细的文章信息
     */
    Article queueArticleByAid(Article article);

    /**
     * 根据封装的信息更改数据库的字段，硬盘中文件的内容以及文件名
     *
     * @param article 封装的文章信息
     */
    void changeServlet(Article article);

    /**
     * 查询前20条信息
     *
     * @return 前20条信息
     */
    List<Map<String, Object>> getAllArticleAndUser();
}
