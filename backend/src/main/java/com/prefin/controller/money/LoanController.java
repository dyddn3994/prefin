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

    @PostMapping("/borrow")
    public ResponseEntity<String> borrowMoney(@RequestBody LoanDto requsetDto) {
        return loanService.borrowMoney(requsetDto);
    }
}
