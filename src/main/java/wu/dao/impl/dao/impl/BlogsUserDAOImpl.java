package wu.dao.impl.dao.impl;

import wu.bean.User;
import wu.dao.impl.BlogsUserDAO;
import wu.dao.impl.sqlGeneral;

import java.sql.Connection;

public class BlogsUserDAOImpl extends sqlGeneral implements BlogsUserDAO {

    @Override
    public int addUser(Connection conn, User user) {
        String sql = "insert into blogs_user (`username`, `password`) value(?, ?)";
        return super.update(conn, sql, user.getUsername(), user.getPassword());
    }

    @Override
    public User queueUserByUsername(Connection conn, User user) {
        String sql = "select * from blogs_user where username = ?";
        User rs = null;

        try {
            rs = super.queue(User.class, conn, sql, user.getUsername()).get(0);
        } catch (Exception e) {
            // todo 说明没有查询到结果，不做处理
        }

        return rs;
    }

    @Override
    public User queueUserByUid(Connection conn, User user) {
        String sql = "select * from blogs_user where uid = ?";

        User rs = null;

        try {
            rs = super.queue(User.class, conn, sql, user.getUid()).get(0);
        } catch (Exception e) {
            // todo 说明没有查询到，不做处理
            System.out.println("BlogsUserDAOImpl === queueUserByUid === 报了异常 === 结果返回null == 是否影响程序运行？");
        }

        return rs;
    }
}
