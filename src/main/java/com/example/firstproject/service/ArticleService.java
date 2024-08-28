package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm form) {
        Article article = form.toEntity();
        if(article.getId() != null) {
            return null;
        }
        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleForm form) {
        //  1. 수정용 엔티티 생성하기
        Article article = form.toEntity();
        log.info("id : {}, article : {}", id, article.toString());
        log.info("form : " + form.toString());

        // 2. DB 에 대상 엔티티가 있는지 조회하기
        Article target = articleRepository.findById(id).orElse(null);

        // 3. 대상 엔티티가 없거나, 수정하려는 id 가 잘못됐을 경우 처리하기
        if(target == null || id != article.getId()) {
            log.info("잘못된 요청! id : {}, article : {}", id, article.toString());
            return null;
        }
        // 4. 대상 엔티티가 있으면 수정 내용으로 업데이트 후 상태 코드 200 보내기
        target.patch(article);
        Article updated = articleRepository.save(target);
        return updated;
    }

    public Article delete(Long id) {
        Article target = articleRepository.findById(id).orElse(null);
        if(target == null) {
            return null;
        }
        articleRepository.delete(target);
        return target;
    }

    @Transactional
    public List<Article> createArticles(List<ArticleForm> forms) {
        // 1. DTO 묶음을 엔티티 묶음으로 변환하기
        List<Article> articleList = forms.stream()
                .map(form -> form.toEntity())
                .collect(Collectors.toList());
        // 2. 엔티티 묶음을 DB 에 저장하기
        articleList.stream()
                .forEach(article -> articleRepository.save(article));
        // 3. 강제로 에러를 발생시키기
        articleRepository.findById(-1L)
                .orElseThrow(() -> new IllegalArgumentException("결제 실패!"));
        // 4. 결과 값 반환하기
        return articleList;
    }
}
