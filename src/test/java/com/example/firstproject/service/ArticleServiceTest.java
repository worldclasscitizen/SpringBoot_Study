package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArticleServiceTest {

    @Autowired
    ArticleService articleService;

    @Test
    void index() {
        // 1. 예상 데이터
        Article a = new Article(1L, "가가가가", "1111");
        Article b = new Article(2L, "나나나나", "2222");
        Article c = new Article(3L, "다다다다", "3333");
        List<Article> expected = new ArrayList<Article>(Arrays.asList(a, b, c));
        // 2. 실제 데이터
        List<Article> articles = articleService.index();
        // 3. 비교 및 검증
        assertEquals(expected.toString(), articles.toString());
    }

    @Test
    void show_success() {
        Long id = 1L;
        Article expected = new Article(1L, "가가가가", "1111");

        Article article = articleService.show(id);

        assertEquals(expected.toString(), article.toString());
    }

    @Test
    void show_fail() {
        Long id = -1L;
        Article expected = null;

        Article article = articleService.show(id);

        assertEquals(expected, article);
    }

    @Transactional
    @Test
    void create_success() {
        String title = "DB에 담겨 있는 제목";
        String content = "DB에 담겨 있는 내용";
        ArticleForm form = new ArticleForm(null, title, content);
        Article expected = new Article(4L, "DB에 담겨 있는 제목", "DB에 담겨 있는 내용");

        Article article = articleService.create(form);

        assertEquals(expected.toString(), article.toString());
    }

    @Transactional
    @Test
    void create_fail() {
        Long id = 4L;
        String title = "라라라라";
        String content = "4444";
        ArticleForm form = new ArticleForm(id, title, content);
        Article expected = null;

        Article article = articleService.create(form);

        assertEquals(expected, article);
    }

    @Test
    @Transactional
    void update_all() {
        Long id = 3L;
        String title = "파파파파";
        String content = "1234";
        ArticleForm form = new ArticleForm(id, title, content);
        Article expected = new Article(id, title, content);

        Article article = articleService.update(id, form);

        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void update_idTitle() {
        Long id = 3L;
        String title = "파파파파";
        String content = null;
        ArticleForm form = new ArticleForm(id, title, content);
        Article expected = new Article(3L, "파파파파", "3333");

        Article article = articleService.update(id, form);

        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void update_fail() {
        Long id = 5L;
        String title = "5번 게시물은";
        String content = "없습니다.";
        ArticleForm form = new ArticleForm(id, title, content);
        Article expected = null;

        Article article = articleService.update(id, form);

        assertEquals(expected, article);
    }

    @Test
    @Transactional
    void delete_success() {
        Long id = 2L;
        Article expected = new Article(id, "나나나나", "2222");

        Article article = articleService.delete(id);

        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void delete_fail() {
        Long id = -1L;
        Article expected = null;

        Article article = articleService.delete(id);

        assertEquals(expected, article);
    }
}