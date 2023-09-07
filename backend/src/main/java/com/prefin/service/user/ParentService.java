package com.prefin.service.user;

import com.prefin.domain.user.Parents;
import com.prefin.dto.user.ParentDto;
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
    public Long signUp(ParentDto parent) {

        Parents newParent = Parents.builder().
                userId(parent.getUserId()).
                password(parent.getPassword()).
                name(parent.getName()).
                build();

        return parentRepository.save(newParent).getId();
    }

    // 로그인
    public Parents login(String userId, String password) {
        // id로 회원 정보 찾기
        Parents parent = parentRepository.findByUserId(userId).orElse(null);

        if (parent == null) return null;

        // 비밀번호 일치여부 확인
        if (parent.getPassword().equals(password)) {
            return parent;
        }

        return null;
    }

    // 계좌 등록 설정
    public String setAccount(long id, String account) {
        Parents parent = parentRepository.findById(id).orElse(null);

        if (parent == null) return "parent not exist";

        parent.updateAccount(account);
        parentRepository.save(parent);

        return "set Account: " + account;
    }

    // 간편 비밀번호 설정
    public String setSimplePassword(long id, String simplePassword) {
        Parents parent = parentRepository.findById(id).orElse(null);

        if (parent == null) return "parent not exist";

        parent.updateSimplePass(simplePassword);
        parentRepository.save(parent);

        return "set SimplePassword: " + simplePassword;
    }

    // 대출 이율 설정
    public String setLoanRate(long id, BigDecimal loanRate) {
        Parents parent = parentRepository.findById(id).orElse(null);

        if (parent == null) return "parent not exist";

        parent.updateLoanRate(loanRate);
        parentRepository.save(parent);

        return "set LoanRate: " + loanRate;
    }

    // 저축 이율 설정
    public String setSavingRate(long id, BigDecimal savingRate) {
        Parents parent = parentRepository.findById(id).orElse(null);

        if (parent == null) return "parent not exist";

        parent.updateSavingRate(savingRate);
        parentRepository.save(parent);

        return "set SavingRate: " + savingRate;
    }

    // FCM Token 등록
    public String setToken(long id, String fcmToken) {
        Parents parent = parentRepository.findById(id).orElse(null);

        if (parent == null) return "parent not exist";

        parent.updateToken(fcmToken);
        parentRepository.save(parent);

        return "set Token: " + fcmToken;
    }
}
