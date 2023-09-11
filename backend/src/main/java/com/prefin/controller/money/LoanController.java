package com.prefin.controller.money;

import com.prefin.dto.money.LoanDto;
import com.prefin.service.money.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class LoanController {
    private final LoanService loanService;

    @PostMapping("/loan/askForMoney")
    public ResponseEntity<String> askForMoney(@RequestBody LoanDto requsetDto) {
        return loanService.askForMoney(requsetDto);
    }

    @PostMapping("/loan/giveMoney")
    public ResponseEntity<String> giveMoney(@RequestBody LoanDto requestDto) {
        return loanService.giveMoney(requestDto);
    }
}
