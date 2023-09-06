package com.prefin.service.user;

import com.prefin.domain.user.Parent;
import com.prefin.dto.user.ParentSignUpDto;
import com.prefin.repository.user.ParentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
@RequiredArgsConstructor
public class ParentService {
    private final ParentRepository parentRepository;

    // 부모 회원 가입
    public Long signUp(ParentSignUpDto parent) {

        Parent newParent = Parent.builder().
                userId(parent.getUserId()).
                password(parent.getPassword()).
                name(parent.getName()).
                build();

        return parentRepository.save(newParent).getId();
    }

    // 로그인
    public Parent login(String userId, String password) {
        // id로 회원 정보 찾기
        Parent parent = parentRepository.findByUserId(userId).orElse(null);

        if (parent == null) return null;

        // 비밀번호 일치여부 확인
        if (parent.getPassword().equals(password)) {
            return parent;
        }

        return null;
    }

    // 계좌 등록 설정
    public Long setAccount(long id, String account) {
        Parent parent = parentRepository.findById(id).orElse(null);

        if (parent == null) return -1L;

        parent.updateAccount(account);

        return parentRepository.save(parent).getId();
    }

    // 간편 비밀번호 설정
    public Long setSimplePassword(long id, String simplePassword) {
        Parent parent = parentRepository.findById(id).orElse(null);

        if (parent == null) return -1L;

        parent.updateSimplePass(simplePassword);

        return parentRepository.save(parent).getId();
    }

    // 대출 이율 설정
    public Long setLoanRate(long id, BigDecimal loanRate) {
        Parent parent = parentRepository.findById(id).orElse(null);

        if (parent == null) return -1L;

        parent.updateLoanRate(loanRate);

        return parentRepository.save(parent).getId();
    }

    // 저축 이율 설정
    public Long setSavingRate(long id, BigDecimal savingRate) {
        Parent parent = parentRepository.findById(id).orElse(null);

        if (parent == null) return -1L;

        parent.updateSavingRate(savingRate);

        return parentRepository.save(parent).getId();
    }

    // FCM Token 등록
    public Long setToken(long id, String fcmToken) {
        Parent parent = parentRepository.findById(id).orElse(null);

        if (parent == null) return -1L;

        parent.updateToken(fcmToken);

        return parentRepository.save(parent).getId();
    }
}
