package com.prefin.service.user;

import com.prefin.domain.money.AccountHistory;
import com.prefin.domain.money.SavingHistory;
import com.prefin.domain.user.Child;
import com.prefin.domain.user.Parents;
import com.prefin.dto.bank.AccountInfoDto;
import com.prefin.dto.money.AccountHistoryDto;
import com.prefin.dto.user.ChildDto;
import com.prefin.repository.money.AccountHistoryRepository;
import com.prefin.repository.money.SavingRepository;
import com.prefin.repository.user.ChildRepository;
import com.prefin.repository.user.ParentRepository;
import com.prefin.service.firebase.FirebaseCloudMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class ChildService {
    private final ChildRepository childRepository;
    private final ParentRepository parentRepository;
    private final SavingRepository savingRepository;
    private final AccountHistoryRepository accountHistoryRepository;
    private final FirebaseCloudMessageService firebaseCloudMessageService;

    // 자녀 회원 가입
    public Long signUp(ChildDto child) {
        // 자녀 중복 조회
        Child originalChild = childRepository.findByUserId(child.getUserId()).orElse(null);

        if (originalChild != null) return -1L;

        // 자녀 저장
        Child newChild = Child.builder().
                userId(child.getUserId()).
                password(child.getPassword()).
                name(child.getName()).
                parent(parentRepository.findById(child.getParentId()).orElse(null)).
                isQuizSolved(false).
                trustScore(100).
                quizId(1L).
                build();

        Long childId = childRepository.save(newChild).getId();

        return childId;
    }

    // 로그인
    public ChildDto login(String userId, String password) {
        // id로 회원 정보 찾기
        Child child = childRepository.findByUserId(userId).orElse(null);

        if (child == null) return null;

        // 비밀번호 일치여부 확인
        if (child.getPassword().equals(password)) {
            return ChildDto.fromEntity(child);
        }

        return null;
    }

    // 계좌 등록
    public ResponseEntity<Boolean> setAccount(long id, String account) {
        Child child = childRepository.findById(id).orElse(null);

        if (child == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);

        child.updateAccount(account);
        childRepository.save(child);

        if (accountHistoryRepository.findByChild(child) == null) {
            setAccountHistory(id, account);
        }

        return ResponseEntity.ok().body(true);
    }

    private void setAccountHistory(long id, String account) {
        // 자녀 거래내역 저장
        String url = "https://shbhack.shinhan.com/v1/search/transaction";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 데이터 본문 생성
        Map<String, Object> dataHeader = new HashMap<>();
        dataHeader.put("apikey", "2023_Shinhan_SSAFY_Hackathon");

        Map<String, Object> dataBody = new HashMap<>();
        dataBody.put("계좌번호", account);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("dataHeader", dataHeader);
        requestBody.put("dataBody", dataBody);

        // HTTP 요청 엔터티 생성
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        // RestTemplate을 사용하여 HTTP POST 요청 보내기
        ResponseEntity<AccountInfoDto> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, AccountInfoDto.class);

        // 응답 처리
        AccountInfoDto accountInfoDto = responseEntity.getBody();

        List<AccountInfoDto.TransactionHistory> transactionHistoryList = accountInfoDto.getDataBody().getHistories();

        // 자녀와 거래내역 연결
        for (AccountInfoDto.TransactionHistory transactionHistory : transactionHistoryList) {
            AccountHistory accountHistory = AccountHistory.builder()
                    .child(childRepository.findById(id).get())
                    .transactionDate(transactionHistory.getTransactionDate())
                    .transactionTime(transactionHistory.getTransactionTime())
                    .briefs(transactionHistory.getBriefs())
                    .deposit(transactionHistory.getDeposit())
                    .withdraw(transactionHistory.getWithdraw())
                    .build();

            accountHistoryRepository.save(accountHistory);
        }
    }


    // 간편 비밀번호 등록
    public ResponseEntity<Boolean> setSimplePassword(long id, String simplePassword) {
        Child child = childRepository.findById(id).orElse(null);

        if (child == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);

        child.updateSimplePass(simplePassword);
        childRepository.save(child);

        return ResponseEntity.ok().body(true);
    }

    // 저축 금액 변경
    public ResponseEntity<Boolean> changeSavingAmount(long id, int savingAmount) {
        Child child = childRepository.findById(id).orElse(null);

        if (child == null) ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);

        child.updateSavingAmount(savingAmount);
        childRepository.save(child);

        return ResponseEntity.ok().body(true);
    }

    // 신뢰 점수 변경
    public ResponseEntity<Boolean> changeTrustScore(long id, int score) {
        Child child = childRepository.findById(id).orElse(null);

        if (child == null) ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);

        child.updateTrustScore(score);

        childRepository.save(child);

        return ResponseEntity.ok().body(true);
    }

    // 부모 설정
    public ResponseEntity<Boolean> setParent(long childId, long parentId) {
        Child child = childRepository.findById(childId).orElse(null);
        Parents parent = parentRepository.findById(parentId).orElse(null);

        if (child == null) ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        if (parent == null) ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);

        child.updateParent(parent);
        childRepository.save(child);

        return ResponseEntity.ok().body(true);
    }


    // 퀴즈풀이를 true로 변경
    public ResponseEntity<Boolean> solveQuiz(long id) {
        Child child = childRepository.findById(id).orElse(null);

        if (child == null) ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        child.quizSolved();
        childRepository.save(child);

        return ResponseEntity.ok().body(true);
    }

    // 다음날이 지나면 퀴즈 번호를 증가시키고 퀴즈 풀이를 false로 변경
    public ResponseEntity<Boolean> increaseQuizId(long id) {
        Child child = childRepository.findById(id).orElse(null);

        if (child == null) ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);

        child.increaseQuizId();
        childRepository.save(child);

        return ResponseEntity.ok().body(true);
    }

    // FCM Token 등록
    public ResponseEntity<Boolean> setToken(long id, String fcmToken) {
        Child child = childRepository.findById(id).orElse(null);

        if (child == null) ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);

        child.updateToken(fcmToken);
        childRepository.save(child);
        return ResponseEntity.ok().body(true);
    }

    // 저축하거나 출금할 때 가능여부를 체크하는 로직이 필요함.
    // 저축 하기
    public ResponseEntity<Boolean> deposit(long id, int balance) throws IOException {
        Child child = childRepository.findById(id).orElse(null);

        if (child == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);

        if (child.getBalance() - balance < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }

        child.getParent().updateBalance(balance);
        child.updateBalance(-balance);
        child.updateSavingAmount(balance);

        SavingHistory savingHistory = SavingHistory.builder().
                savingAmount(balance).
                savingDate(LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli()).
                parent(child.getParent()).
                child(child).
                savingType("DEPOSIT").
                build();

        savingRepository.save(savingHistory);
        childRepository.save(child);

        // FCM
        String token = child.getParent().getFcmToken();
        String title = "저축";
        String body = balance + "원 저축되었습니다.";
        firebaseCloudMessageService.sendMessageTo(token, title, body);

        return ResponseEntity.ok(true);
    }

    // 출금 하기
    public ResponseEntity<Boolean> withdraw(long id, int balance) throws IOException {
        Child child = childRepository.findById(id).orElse(null);

        if (child == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);

        if (child.getParent().getBalance() - balance < 0) ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);

        child.getParent().updateBalance(-balance);
        child.updateBalance(balance);
        child.updateSavingAmount(-balance);

        SavingHistory savingHistory = SavingHistory.builder().
                savingAmount(balance).
                savingDate(LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli()).
                parent(child.getParent()).
                child(child).
                savingType("WITHDRAW").
                build();

        savingRepository.save(savingHistory);
        childRepository.save(child);

        // FCM
        String token = child.getParent().getFcmToken();
        String title = "출금";
        String body = balance + "원 출금되었습니다.";
        firebaseCloudMessageService.sendMessageTo(token, title, body);

        return ResponseEntity.ok(true);
    }

    public ChildDto getChildById(Long id) {
        Child child = childRepository.findById(id).orElse(null);

        if (child == null) return null;

        return ChildDto.fromEntity(child);
    }

    public List<AccountHistoryDto> getAccountHistory(long id) {
        Child child = childRepository.findById(id).orElse(null);

        if (child == null) return null;
        List<AccountHistory> accountHistories = accountHistoryRepository.findByChild(child);

        List<AccountHistoryDto> accountHistoryDtos = new ArrayList<>();

        for (AccountHistory accountHistory : accountHistories) {
            accountHistoryDtos.add(AccountHistoryDto.builder()
                    .id(accountHistory.getId())
                    .childId(accountHistory.getChild().getId())
                    .transactionDate(accountHistory.getTransactionDate())
                    .transactionTime(accountHistory.getTransactionTime())
                    .briefs(accountHistory.getBriefs())
                    .deposit(accountHistory.getDeposit())
                    .withdraw(accountHistory.getWithdraw())
                    .build());
        }

        return accountHistoryDtos;
    }
}
