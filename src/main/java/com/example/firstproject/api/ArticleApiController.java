package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
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
    private ArticleRepository articleRepository;
    // GET
    @GetMapping("/api/articles")
    public List<Article> index() {
        return articleRepository.findAll();
    }

    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    // POST
    @PostMapping("/api/articles")
    public Article create(@RequestBody ArticleForm form) {
        Article article = form.toEntity();
        return articleRepository.save(article);
    }
    // PATCH
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm form) {
        // 1. 수정용 엔티티 생성하기
        Article article = form.toEntity();
        log.info("id : {}, article : {}", id, article.toString());
        log.info("form : " + form.toString());

        // 2. DB 에 대상 엔티티가 있는지 조회하기
        Article target = articleRepository.findById(id).orElse(null);

        // 3. 대상 엔티티가 없거나, 수정하려는 id 가 잘못됐을 경우 처리하기
        if(target == null || id != article.getId()) {
            log.info("잘못된 요청! id : {}, article : {}", id, article.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        // 4. 대상 엔티티가 있으면 수정 내용으로 업데이트 후 상태 코드 200 보내기
        target.patch(article);
        Article updated = articleRepository.save(target);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }
    // DELETE
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id) {
        // 1. 대상 엔티티 조회
        Article target = articleRepository.findById(id).orElse(null);
        // 2. 대상 엔티티가 없을 때
        if(target == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        // 3. 대상 엔티티가 있을 때
        articleRepository.delete(target);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
