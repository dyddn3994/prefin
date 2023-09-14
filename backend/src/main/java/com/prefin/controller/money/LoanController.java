package com.prefin.controller.money;

import com.prefin.dto.money.LoanDto;
import com.prefin.service.money.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class LoanController {
    private final LoanService loanService;

    // 대출 요청
    @PostMapping("/loan/askForMoney")
    public ResponseEntity<String> askForMoney(@RequestBody LoanDto requsetDto) {
        return loanService.askForMoney(requsetDto);
    }

    // 대출 요청 취소


    // 대출 지급
    @PostMapping("/loan/giveMoney")
    public ResponseEntity<String> giveMoney(@RequestBody LoanDto requestDto) {
        return loanService.giveMoney(requestDto);
    }

    // 대출 내역 조회
    @GetMapping("/loan/history")
    public List<LoanDto> loanHistory(@RequestBody Long childId) {
        return loanService.loanHistory(childId);
    }

}
