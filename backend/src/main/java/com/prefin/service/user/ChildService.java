package com.prefin.service.user;

import com.prefin.domain.user.Child;
import com.prefin.dto.user.ChildDto;
import com.prefin.repository.user.ChildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChildService {
    private final ChildRepository childRepository;

    // 자녀 회원 가입
    public Long signUp(ChildDto child) {

        Child newChild = Child.builder().
                userId(child.getUserId()).
                password(child.getPassword()).
                name(child.getName()).
                isQuizSolved(false).
                quizId(0L).
                build();

        return childRepository.save(newChild).getId();
    }

    // 계좌 등록

    // 간편 비밀번호 등록

    // 저축 금액 변경

    // 신뢰 점수 변경

    // 부모 설정

    // 마스코트 설정

    // 퀴즈 설정
}
