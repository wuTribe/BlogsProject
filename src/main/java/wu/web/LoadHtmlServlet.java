package wu.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import wu.service.impl.ArticleService;
import wu.service.impl.service.impl.ArticleServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/loadHtml/*")
public class LoadHtmlServlet extends BaseServlet {
    private ArticleService articleService = new ArticleServiceImpl();

    public void getAllArticle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 查询13条信息
        List<Map<String, Object>> all = articleService.getAllArticleAndUser();

        // 序列化
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(all);

        // 设置响应头
        response.setContentType("application/json;charset=utf8");
        // 写出
        response.getWriter().write(json);
    }
}
