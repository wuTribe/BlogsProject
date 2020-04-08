package wu.controller;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wu.service.ArticleService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/static/loadHtml")
@NoArgsConstructor
public class LoadHtmlServlet {
    private ArticleService articleService;

    @Autowired
    public LoadHtmlServlet(ArticleService articleService) {
        this.articleService = articleService;
    }

    @RequestMapping("/getAllArticle")
    @ResponseBody
    public List<Map<String, Object>> getAllArticle(HttpServletRequest request, HttpServletResponse response) {
        // 查询13条信息
        return articleService.getAllArticleAndUser();
    }
}
