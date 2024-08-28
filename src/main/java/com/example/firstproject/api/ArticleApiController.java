package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ArticleApiController {
    @Autowired
    private ArticleService articleService;
    // GET
    @GetMapping("/api/articles")
    public List<Article> index() {
        return articleService.index();
    }

    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id) {
        // 찾고자 하는 게시물이 없을 경우에 대한 예외 처리가 필요함
        return articleService.show(id);
    }

    // POST
    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm form) {
        Article created = articleService.create(form);
        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
    // PATCH
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm form) {
        Article updated = articleService.update(id, form);
        return (updated != null) ?
                ResponseEntity.status(HttpStatus.OK).body(updated) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    // DELETE
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id) {
        Article deleted = articleService.delete(id);
        return (deleted != null) ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/api/transaction-test")
    public ResponseEntity<List<Article>> transactionTest(@RequestBody List<ArticleForm> forms) {
        List<Article> createdList = articleService.createArticles(forms);
        return (createdList != null) ?
                ResponseEntity.status(HttpStatus.OK).body(createdList) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
