package com.example.firstproject.service;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    public List<CommentDto> comments(Long articleId) {
        // 결과 반환
        // 댓글 조회와 DTO 로 변환하는 코드는 스트림 문법으로 생략하였습니다.
        return commentRepository.findByArticleId(articleId)
                .stream()
                .map(comment -> CommentDto.createCommentDto(comment))
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentDto create(Long articleId, CommentDto dto) {
        // 1. 게시글 조회 및 예외 발생
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패! "+
                        "대상 게시글이 없습니다."));
        // 2. 댓글 엔티티 생성
        Comment comment = Comment.createComment(dto, article);

        // 3. 댓글 엔티티를 DB 에 저장
        Comment saved = commentRepository.save(comment);

        // 4. DTO 로 변환해 반환
        return CommentDto.createCommentDto(saved);
    }

    @Transactional
    public CommentDto update(Long commentId, CommentDto dto) {
        // 1. 댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패! " +
                        "대상 댓글이 없습니다."));
        // 2. 댓글 수정
        target.patch(dto);

        // 3. DB 갱신
        Comment updated = commentRepository.save(target);

        // 4. 댓글 엔티티를 DTO 로 변환 및 반환
        return CommentDto.createCommentDto(updated);
    }

    @Transactional
    public CommentDto delete(Long commentId) {
        // 1. 댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패! " +
                        "대상 댓글이 없습니다."));
        // 2. 댓글 삭제
        commentRepository.delete(target);

        // 3. 댓글 엔티티를 DTO 로 변환 및 반환
        return CommentDto.createCommentDto(target);
    }
}