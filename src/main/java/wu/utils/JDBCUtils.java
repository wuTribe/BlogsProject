package wu.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * JDBC工具类
 */
public class JDBCUtils {
    /**
     * 获取SQL连接
     *
     * @return SQL连接
     * @throws Exception 异常
     */
    public static Connection getConnection() throws Exception {
        // 获得类加载器，将加载器关联文件
        // 此处可能由于路径问题导致 is 为空
        InputStream is = JDBCUtils.class.getResourceAsStream("/SQLFile.properties");

        // 加载文件
        Properties pro = new Properties();
        pro.load(is);

        // 读取文件内容
        String username = pro.getProperty("username");
        String password = pro.getProperty("password");
        String url = pro.getProperty("url");
        String driverClass = pro.getProperty("driverClass");

        // 注册驱动
        Class.forName(driverClass);

        // 获取连接
        return DriverManager.getConnection(url, username, password);
    }

    /**
     * 关闭资源
     *
     * @param resources 资源
     */
    public static void closeResource(AutoCloseable... resources) {
        for (AutoCloseable resource : resources) {
            try {
                if (resource != null) {
                    resource.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
