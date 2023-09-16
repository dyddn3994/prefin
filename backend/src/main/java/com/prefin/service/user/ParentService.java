package com.prefin.service.user;

import com.prefin.domain.user.Parents;
import com.prefin.dto.user.ChildDto;
import com.prefin.dto.user.ParentDto;
import com.prefin.repository.user.ParentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ParentService {
    private final ParentRepository parentRepository;

    // 부모 회원 가입
    public Long signUp(ParentDto parent) {
        // 자녀 중복 조회
        Parents originalParent = parentRepository.findByUserId(parent.getUserId()).orElse(null);

        if (originalParent != null) return -1L;

        Parents newParent = Parents.builder().
                userId(parent.getUserId()).
                password(parent.getPassword()).
                name(parent.getName()).
                balance(1000000).
                build();

        return parentRepository.save(newParent).getId();
    }

    // 로그인
    public ParentDto login(String userId, String password) {
        // id로 회원 정보 찾기
        Parents parent = parentRepository.findByUserId(userId).orElse(null);

        // 부모 존재 여부 및 비밀번호 일치여부 확인
        if (parent == null || !parent.getPassword().equals(password)) {
            return null;
        }

        return ParentDto.fromEntity(parent);
    }

    // 계좌 등록 설정
    public ResponseEntity<Boolean> setAccount(long id, String account) {
        Parents parent = parentRepository.findById(id).orElse(null);

        if (parent == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);

        parent.updateAccount(account);
        parentRepository.save(parent);

        return ResponseEntity.ok(true);
    }

    // 간편 비밀번호 설정
    public ResponseEntity<Boolean> setSimplePassword(long id, String simplePassword) {
        Parents parent = parentRepository.findById(id).orElse(null);

        if (parent == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);

        parent.updateSimplePass(simplePassword);
        parentRepository.save(parent);

        return ResponseEntity.ok(true);
    }

//    // 대출 이율 설정
//    public ResponseEntity<Boolean> setLoanRate(long id, BigDecimal loanRate) {
//        Parents parent = parentRepository.findById(id).orElse(null);
//
//        if (parent == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
//
//        parent.updateLoanRate(loanRate);
//        parentRepository.save(parent);
//
//        return ResponseEntity.ok(true);
//    }
//
//    // 저축 이율 설정
//    public ResponseEntity<Boolean> setSavingRate(long id, BigDecimal savingRate) {
//        Parents parent = parentRepository.findById(id).orElse(null);
//
//        if (parent == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
//
//        parent.updateSavingRate(savingRate);
//        parentRepository.save(parent);
//
//        return ResponseEntity.ok(true);
//    }

    // FCM Token 등록
    public ResponseEntity<Boolean> setToken(long id, String fcmToken) {
        Parents parent = parentRepository.findById(id).orElse(null);

        if (parent == null) ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        
        parent.updateToken(fcmToken);
        parentRepository.save(parent);

        return ResponseEntity.ok(true);
    }

    public List<ChildDto> myChildren(Long id) {
        Parents parent = parentRepository.findById(id).orElse(null);
        if (parent == null) {
            throw new NoSuchElementException("Parent Not Fount");
        }

        return parent.getChildList().stream()
                .map(ChildDto::fromEntity)
                .collect(Collectors.toList());

    }

    public ParentDto getParenById(Long id) {
        Parents parent = parentRepository.findById(id).orElse(null);

        if (parent == null) return null;

        return ParentDto.fromEntity(parent);
    }

    // 최대 이자금액 설정
    public ResponseEntity<Boolean> setMaxSavingAmount(long id, int maxSavingAmount) {
        Parents parent = parentRepository.findById(id).orElse(null);

        if (parent == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);

        parent.updateMaxSavingAmount(maxSavingAmount);
        parentRepository.save(parent);

        return ResponseEntity.ok(true);
    }
}
