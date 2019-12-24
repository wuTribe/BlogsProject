package wu.test;

import wu.bean.Article;
import wu.bean.User;
import wu.dao.impl.dao.impl.BlogsArticleDAOImpl;
import wu.dao.impl.dao.impl.BlogsUserDAOImpl;
import wu.utils.JDBCUtils;

import java.io.*;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Test {
    private Connection connection = null;

    /**
     * 测试插入文章信息是否成功
     */
    @org.junit.Test
    public void testAddArticle() {
        testAddArticle3("wu", 1, (int) (Math.random() * 10));
        testAddArticle3("li", 2, (int) (Math.random() * 10));
        testAddArticle3("lin", 3, (int) (Math.random() * 10));
        testAddArticle3("wang", 4, (int) (Math.random() * 10))
        ;
        testAddArticle3("wu", 1, (int) (Math.random() * 10));
        testAddArticle3("lin", 3, (int) (Math.random() * 10));
        testAddArticle3("lin", 3, (int) (Math.random() * 10));

        testAddArticle3("wang", 4, (int) (Math.random() * 10));
        testAddArticle3("wu", 1, (int) (Math.random() * 10));

        testAddArticle3("wang", 4, (int) (Math.random() * 10));
        testAddArticle3("wu", 1, (int) (Math.random() * 10));
        testAddArticle3("lin", 3, (int) (Math.random() * 10));
        testAddArticle3("lin", 3, (int) (Math.random() * 10));
        testAddArticle3("li", 2, (int) (Math.random() * 10));

        testAddArticle3("wang", 4, (int) (Math.random() * 10));
        testAddArticle3("wu", 1, (int) (Math.random() * 10));
        testAddArticle3("lin", 3, (int) (Math.random() * 10));
    }

    private void testAddArticle2(String username, int aid) {
        // 获取post文章的数据
        String title = "测试";

        // 获取作者信息
        // 获取当前系统时间，毫秒值
        Date time = new Date();
        // 获取文件内容保存的路径
        String path = "/userArticle/";
        // 获取文件名 用户名 + 当前系统的毫秒值
        String fileName = username + time.getTime();

        //封装数据
        // int aid, String path, String fileName, Date time, int auid, String title
        Article article = new Article(0, path, fileName, time, aid, title, null);

        BlogsArticleDAOImpl b = new BlogsArticleDAOImpl();

        try {
            connection = JDBCUtils.getConnection();
            b.addArticle(connection, article);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection);
        }
    }

    private void testAddArticle3(String username, int aid, int num) {
        for (int i = 0; i < num; i++) {
            testAddArticle2(username, aid);
        }
    }

    /**
     * 测试文件读取是否成功
     */
    @org.junit.Test
    public void readArticle() {
//         获取文件的路径
//        文件路径：String path = "/userArticle/";
//        String fileName = user.getUsername() + time.getTime();
//        文件名：wu1574768534612

//        文件路径
        String path = "/userArticle/";
//        文件名
        String filename = "wu1574768025214";

        BufferedReader br = null;
        try {
            // 将流对接到文件中
            br = new BufferedReader(new FileReader(path + filename));

            // 查看br是否为空
            System.out.println("br =======  " + br);

            // 将文件中的内容读取到内存中
            StringBuffer sb = new StringBuffer();
            String str = null;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }

            // 将内容直接输出
            System.out.println(sb);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 测试文件的写出路径
     */
    @org.junit.Test
    public void writeArticle() {
        // 获取文件所需参数
        String path = "/userArticle/";
        Date time = new Date();

        // 获取文件名 用户名 + 当前系统的毫秒值
        String fileName = path + "wu" + time.getTime();

        // 创建文件路径
        File file = new File(path);
        boolean flag = file.mkdir();
        System.out.println(flag);

        // 文本内容
        String content = "我是Java";

        // 写出内容
        try (FileWriter fw = new FileWriter(fileName)) {
            fw.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试Time类
     */
    @org.junit.Test
    public void testTime() {
//        2019-11-26 20:21:35
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            System.out.println(simpleDateFormat.parse("2019-11-26 20:21:35").getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试数据库的连接是否正常
     */
    @org.junit.Test
    public void testJDBCUtils() {
        try {
            connection = JDBCUtils.getConnection();
            System.out.println(connection);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection);
        }
    }

    @org.junit.Test
    public void testBlogsArticleDAOImpl() {
        try {
            connection = JDBCUtils.getConnection();

            BlogsArticleDAOImpl b = new BlogsArticleDAOImpl();
            User user = new User(1, null, null, null, null);

            List<Article> articles = b.queueArticleByAuid(connection, user);

            for (Article article : articles) {
                System.out.println(article);
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection);
        }
    }

    @org.junit.Test
    public void testBlogsUserImpl2() {
        try {
            connection = JDBCUtils.getConnection();

            User user = new User(0, "ee", "root", null, null);

            BlogsUserDAOImpl b = new BlogsUserDAOImpl();
            User rs = b.queueUserByUsername(connection, user);

            System.out.println(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection);
        }
    }

    @org.junit.Test
    public void testBlogsUserImpl1() {
        try {
            connection = JDBCUtils.getConnection();

            // 封装一个User
            User user = new User(0, "oo", "root", null, null);

            BlogsUserDAOImpl b = new BlogsUserDAOImpl();
            int i = b.addUser(connection, user);

            System.out.println("testBlogsUserImpl ---> " + i);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection);
        }
    }

//    /**
//     * 测试通用的增删改操作是否正常
//     */

//    @org.junit.Test
//    public void testSqlGeneral() {
//        Connection connection = null;
//        sqlGeneral gen = new sqlGeneral();
//        try {
//            connection = JDBCUtils.getConnection();
//
////             String sql = "insert INTO blogs_article (path, auid) value(?, ?);";
////             String sql = "INSERT INTO blogs_user (username, `password`) VALUE(?, ?);";
////             String sql = "INSERT INTO blogs_user (username, `password`) VALUE(?, ?);";
////            String sql = "select * from blogs_user";
//            String sql = "select * from blogs_article";
//
////            int i = gen.update(connection, sql, "zhang", "root");
////            System.out.println("testSqlGeneral  -->  " + i);
//            List<Article> rs = gen.queue(Article.class, connection, sql);
//
//            for (Article r : rs) {
//                System.out.println(r);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            JDBCUtils.closeResource(connection);
//        }
//    }
}
