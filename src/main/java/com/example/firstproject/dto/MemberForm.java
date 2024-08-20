package com.example.firstproject.dto;

import com.example.firstproject.entity.Member;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MemberForm {
    private Long id;
    private String email;
    private String password;

    // 전송받은 제목과 내용을 필드에 저장하는 생성자는 @AllArgsConstructor 로 대체되었습니다.

    public Member toEntity() {
        return new Member(id, email, password);
    }
}
