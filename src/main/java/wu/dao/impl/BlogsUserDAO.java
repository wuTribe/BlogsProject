package wu.dao.impl;

import wu.bean.Article;
import wu.bean.User;

import java.sql.Connection;

public interface BlogsUserDAO {
    /**
     * 向数据库中增加用户（注册）
     *
     * @param user 用户的信息
     * @return 影响的行数
     */
    int addUser(Connection conn, User user);

    /**
     * 根据用户名查询用户信息
     *
     * @param user 用户填写的信息
     * @return 如果查询成功，返回该用户的所有详细信息，否则返回null
     */
    User queueUserByUsername(Connection conn, User user);


    /**
     * 根据id查询用户详细信息
     *
     * @param user 封装的用户信息
     * @return 详细的用户信息
     */
    User queueUserByUid(Connection conn, User user);
}
