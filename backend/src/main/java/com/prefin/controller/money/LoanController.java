package com.prefin.controller.money;

import com.prefin.dto.money.LoanDto;
import com.prefin.service.money.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class LoanController {
    private final LoanService loanService;

    // 대출 요청
    @PostMapping("/loan/askForMoney")
    public ResponseEntity<String> askForMoney(@RequestBody LoanDto requsetDto) throws IOException {
        return loanService.askForMoney(requsetDto);
    }

    // 대출 요청 수정
    @PutMapping("/loan/update")
    public ResponseEntity<String> updateLoanRequest(@RequestBody LoanDto requestDto) {
        return loanService.updateLoanRequest(requestDto);
    }

    // 대출 요청 삭제
    @DeleteMapping("/loan/cancel/{loanId}")
    public ResponseEntity<String> cancelLoanRequest(@PathVariable Long loanId){
        return loanService.deleteRequest(loanId);
    }


    // 대출 지급
    @PostMapping("/loan/giveMoney")
    public ResponseEntity<String> giveMoney(@RequestBody LoanDto requestDto) throws IOException {
        return loanService.giveMoney(requestDto);
    }

    // 대출 내역 조회
    @GetMapping("/loan/history/{childId}")
    public List<LoanDto> loanHistory(@PathVariable Long childId) {
        return loanService.loanHistory(childId);
    }

}
