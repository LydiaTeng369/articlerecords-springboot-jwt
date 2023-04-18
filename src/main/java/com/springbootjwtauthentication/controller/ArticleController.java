package com.springbootjwtauthentication.controller;
import com.springbootjwtauthentication.entity.Article;
import com.springbootjwtauthentication.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/article")
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/list")
    public String list(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        List<Article> articleList = articleRepository.findByUserId(userId);
        model.addAttribute("articleList",articleList);
        return "article";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {
        articleRepository.deleteById(id);
        model.addAttribute("message","Delete successfully.");
        return "redirect:/api/article/list";
    }

    @PutMapping("/update")
    public String update(Model model, @Validated Article article) {
        Optional<Article> articleOptional = articleRepository.findById(article.getId());
        if (articleOptional.isPresent()){
            Article articleById = articleOptional.get();
            articleById.setTitle(article.getTitle());
            articleById.setDepartment(article.getDepartment());
            articleById.setAuthor(article.getAuthor());
            articleRepository.save(articleById);
        }
        model.addAttribute("message","Update successfully.");
        return "redirect:/api/article/list";
    }

    @PostMapping("/save")
    public String save(HttpServletRequest request, Model model, @Validated  Article article) {
        Long id = article.getId();
        if (id != null) {
            this.update(model, article);
        }
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        article.setUserId(userId);
        article.setCreateTime(new Date());
        articleRepository.save(article);
        model.addAttribute("message", "Add successfully.");
        return "redirect:/api/article/list";
    }

    @GetMapping("/from")
    public String from(Long id,Model model) {
        Article article = new Article();
        if (id!=null){
            Optional<Article> articleOptional = articleRepository.findById(id);
            if (articleOptional.isPresent()){
                article = articleOptional.get();

            }
        }
        model.addAttribute("article",article);
        return "articleSave";
    }
}
