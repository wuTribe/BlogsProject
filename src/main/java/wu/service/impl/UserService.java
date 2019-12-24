package wu.service.impl;

import wu.bean.Article;
import wu.bean.User;

public interface UserService {
    /**
     * 将用户加入数据库
     *
     * @param user 用户信息
     * @return 影响的行数
     */
    int addUser(User user);

    /**
     * 根据用户名查询用户
     *
     * @param user 用户填写的信息
     * @return 如果存在用户，返回用户的详细信息，否则返回null
     */
    User queueUserByUsername(User user);

    /**
     * 根据密码查询用户信息
     *
     * @param user 用户填写的信息
     * @return 如果存在用户，返回用户的详细信息，否则返回null
     */
    User queueUserByUid(User user);

    /**
     * 根据文章的auid查询个人信息
     *
     * @param article 封装的文章数据
     * @return 个人的详细信息
     */
    User queueUserByAuid(Article article);
}
