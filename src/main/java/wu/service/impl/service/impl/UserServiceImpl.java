package wu.service.impl.service.impl;

import wu.bean.Article;
import wu.bean.User;
import wu.dao.impl.BlogsUserDAO;
import wu.dao.impl.dao.impl.BlogsUserDAOImpl;
import wu.service.impl.UserService;
import wu.utils.JDBCUtils;

import java.sql.Connection;

public class UserServiceImpl implements UserService {
    // 获取数据库连接
    private Connection conn = null;
    // 获取数据库操作对象
    private BlogsUserDAO userDAO = new BlogsUserDAOImpl();

    @Override
    public int addUser(User user) {
        try {
            conn = JDBCUtils.getConnection();

            // 调用dao操作数据库
            return userDAO.addUser(conn, user);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn);
        }

        return 0;
    }

    @Override
    public User queueUserByUsername(User user) {
        try {
            conn = JDBCUtils.getConnection();
            return userDAO.queueUserByUsername(conn, user);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn);
        }

        return null;
    }

    @Override
    public User queueUserByUid(User user) {
        User rsUser = queueUserByUsername(user);

        // 查询的对象不为空时，并且密码符合，返回查询到的对象
        if (rsUser != null && user != null && rsUser.getPassword().equals(user.getPassword())) {
            return rsUser;
        }

        return null;
    }

    @Override
    public User queueUserByAuid(Article article) {
        try {
            conn = JDBCUtils.getConnection();

            User user = new User(article.getAuid(), null, null, null, null);

            return userDAO.queueUserByUid(conn, user);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn);
        }

        return null;
    }


}
