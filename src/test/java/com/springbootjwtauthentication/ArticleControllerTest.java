package com.springbootjwtauthentication;

import com.springbootjwtauthentication.entity.Article;
import com.springbootjwtauthentication.repository.ArticleRepository;
import com.springbootjwtauthentication.controller.ArticleController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;


import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ArticleControllerTest {

    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private ArticleController articleController;

    private MockHttpServletRequest request;
    private MockHttpSession session;

    @BeforeEach
    public void setUp() {
        request = new MockHttpServletRequest();
        session = new MockHttpSession();
        session.setAttribute("userId", 1L);
        request.setSession(session);
    }

    @Test
    public void testList() {
        List<Article> articles = Arrays.asList(
                new Article(1L, "Title 1", "Department 1", "Author 1", new Date(), 1L),
                new Article(2L, "Title 2", "Department 2", "Author 2", new Date(), 1L)
        );
        when(articleRepository.findByUserId(1L)).thenReturn(articles);

        Model model = mock(Model.class);
        String viewName = articleController.list(request, model);

        verify(articleRepository).findByUserId(1L);
        verify(model).addAttribute("articleList", articles);
        assertEquals("article", viewName);
    }

    @Test
    public void testDelete() {
        Long articleId = 1L;

        Model model = mock(Model.class);
        String viewName = articleController.delete(articleId, model);

        verify(articleRepository).deleteById(articleId);
        verify(model).addAttribute("message", "Delete successfully.");
        assertEquals("redirect:/api/article/list", viewName);
    }

    @Test
    public void testUpdate() {
        Long articleId = 1L;
        Article article = new Article(articleId, "Title", "Department", "Author", new Date(), 1L);
        Optional<Article> articleOptional = Optional.of(article);
        when(articleRepository.findById(articleId)).thenReturn(articleOptional);

        Model model = mock(Model.class);
        String viewName = articleController.update(model, article);

        verify(articleRepository).findById(articleId);
        verify(articleRepository).save(article);
        verify(model).addAttribute("message", "Update successfully.");
        assertEquals("redirect:/api/article/list", viewName);
    }

    @Test
    public void testSave() {
        // mock HttpServletRequest and HttpSession objects
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("userId")).thenReturn(1L);

        // mock Article object
        Article article = new Article();
        article.setTitle("Test Article1");
        article.setDepartment("Test Department1");
        article.setAuthor("Test Author1");

        // call the save method and verify that it saves the article to the repository
        String result = articleController.save(request, (Model) new ModelMap(), article);
        verify(articleRepository, times(1)).save(article);

        // verify that the save method returns the correct view name
        assertEquals("redirect:/api/article/list", result);
    }

    @Test
    public void testFrom() {
        // mock Article object
        Article article = new Article();
        article.setId(1L);
        article.setTitle("Test Article2");
        article.setDepartment("Test Department2");
        article.setAuthor("Test Author2");

        // mock the findById method of the ArticleRepository
        when(articleRepository.findById(1L)).thenReturn(Optional.of(article));

        // call the from method and verify that it returns the correct view name and model attribute
        ModelMap model = new ModelMap();
        String result = articleController.from(1L, (Model) model);
        assertEquals("articleSave", result);
        assertEquals(article, model.get("article"));
    }
}
