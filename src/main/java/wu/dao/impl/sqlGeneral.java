package wu.dao.impl;

import wu.utils.JDBCUtils;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 提供增删改查的通用操作
 */
public abstract class sqlGeneral {

    /**
     * 预编译sql语句，填充占位符
     *
     * @param conn 连接
     * @param sql  sql语句
     * @param args 占位符
     * @return 返回执行好的PreparedStatement
     * @throws Exception 异常
     */
    private PreparedStatement dispose(Connection conn, String sql, Object... args) throws Exception {
        // 预编译SQL语句
        PreparedStatement ps = conn.prepareStatement(sql);

        // 填充占位符
        for (int i = 0; i < args.length; i++) {
            ps.setObject(i + 1, args[i]);
        }

        return ps;
    }

    /**
     * 通用的增删改操作（考虑事务）
     *
     * @param conn 连接
     * @param sql  sql语句
     * @param args 占位符
     * @return 影响的行数
     */
    protected int update(Connection conn, String sql, Object... args) {
        PreparedStatement ps = null;

        try {
            // 预编译并填充占位符
            ps = this.dispose(conn, sql, args);

            // 执行语句
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(ps);
        }

        return 0;
    }

    /**
     * 通用的查询操作（考虑事务）
     *
     * @param clazz 需要查询的类
     * @param conn  连接
     * @param sql   sql语句
     * @param args  占位符
     * @param <T>   需要查询的类的类型
     * @return 返回查询记录得到的对象
     */
    protected <T> List<T> queue(Class<T> clazz, Connection conn, String sql, Object... args) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<T> list = null;
        try {
            // 预编译并填充占位符
            ps = this.dispose(conn, sql, args);

            // 执行，接受结果集
            rs = ps.executeQuery();

            // 处理数据
            // 获取元数据
            ResultSetMetaData md = rs.getMetaData();

            // 获取行的列数
            int col = md.getColumnCount();

            list = new ArrayList<>();

            // 如果结果集中有数据
            while (rs.next()) {
                // 创建对象
                T t = clazz.newInstance();

                for (int i = 0; i < col; i++) {
                    // 获取列值
                    Object colValue = rs.getObject(i + 1);
                    // 获取每个列的列名
                    String colName = md.getColumnLabel(i + 1);

                    // 给对象赋值
                    Field field = clazz.getDeclaredField(colName);
                    field.setAccessible(true);
                    field.set(t, colValue);
                }

                list.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(ps, rs);
        }

        return list;
    }

}
