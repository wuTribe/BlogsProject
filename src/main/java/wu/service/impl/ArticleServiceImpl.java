package wu.service.impl;

import lombok.NoArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wu.bean.Article;
import wu.bean.User;
import wu.dao.BlogsArticleMapper;
import wu.dao.BlogsUserMapper;
import wu.service.ArticleService;
import wu.service.UserService;

import java.io.*;
import java.util.*;

@Service
@NoArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    // 获取数据库操作对象
    private BlogsArticleMapper articlesDAO;
    private BlogsUserMapper userDAO;

    // 查询用户信息
    private UserService userService;

    @Autowired
    public ArticleServiceImpl(BlogsArticleMapper articlesDAO, UserService userService, BlogsUserMapper userDAO) {
        this.articlesDAO = articlesDAO;
        this.userService = userService;
        this.userDAO = userDAO;
    }


    @Override
    public List<Article> queueArticleByAuid(User user) {
        return articlesDAO.queueArticleByAuid(user);
    }

    @Override
    public int addArticle(Article article) {
        return articlesDAO.addArticle(article);
    }

    @Override
    public Article queueArticleByAid(Article article) {
        BufferedReader br = null;
        Article rsArticle = null;
        try {
            // 查询文章信息 获取第一个
            rsArticle = articlesDAO.queueArticleByAid(article);

            // 将内容从硬盘中读入文章内容，封装到对象中，文件名 = 用户名 + 文章创建毫秒值
            // 封装用户信息
            User user = new User(rsArticle.getAuid(), null, null, null, null);
            // 查询用户信息
            User rsUser = userDAO.queueUserByUid(user);

            String fileName = rsArticle.getPath() + rsUser.getUsername() + rsArticle.getTime().getTime();

            // 将流对接到文件中
            br = new BufferedReader(new FileReader(fileName));

            // 读取文件中的内容
            String str;
            StringBuilder content = new StringBuilder();
            while ((str = br.readLine()) != null) {
                content.append(str);
            }

            rsArticle.setContent(content.toString());

        } catch (Exception e) {
            // todo 说明没有查询到信息，不做处理
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return rsArticle;
    }

    @Override
    public void changeServlet(Article article) {
        BufferedWriter bw = null;
        try {
            // 数据库的字段，硬盘中文件的内容以及文件名

            // 修改文件名
            // 1.获取这篇文章的个人信息
            User user = userService.queueUserByAuid(article);

            // 文件名，时间，标题
            // 创建时间对象
            Date date = new Date();
            // 将文件对象对接到文件中
            File file = new File(article.getPath() + user.getUsername() + article.getTime().getTime());

            // 将新文件对象对接到新文件中
            File newFile = new File(article.getPath() + user.getUsername() + date.getTime());

            FileUtils.moveFile(file, newFile);
            article.setTime(date);
            article.setFileName(article.getPath() + user.getUsername() + date.getTime());

            // 跟新数据库的内容
            if (articlesDAO.changeServlet(article) != 0) {
                // 更新硬盘中文件的内容
                bw = new BufferedWriter(new FileWriter(newFile));
                bw.write(article.getContent());
                bw.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Map<String, Object>> getAllArticleAndUser() {
        // 存放前13篇文章的标题，更改时间，作者
        ArrayList<Map<String, Object>> all = new ArrayList<>();

        // 前十三篇文章详细信息
        List<Article> allArticle = articlesDAO.getAllArticle();

        for (Article article : allArticle) {
            User user = userService.queueUserByAuid(article);

            // 前十三篇用户详细信息
            Map<String, Object> map = new HashMap<>();
            // 存放每一篇的信息
            map.put("title", article.getTitle());
            map.put("time", article.getTime());
            map.put("username", user.getUsername());
            map.put("aid", article.getAid());

            all.add(map);
        }

        return all;
    }
}
