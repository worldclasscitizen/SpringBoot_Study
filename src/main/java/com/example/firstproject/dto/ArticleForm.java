package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class ArticleForm {
    private Long id;
    private String title; // 제목을 받을 필드
    private String content; // 내용을 받을 필드

    // 전송받은 제목과 내용을 필드에 저장하는 생성자는 @AllArgsConstructor 로 대체되었습니다.

    public Article toEntity() {
        return new Article(id, title, content);
    }
}