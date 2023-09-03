package com.prefin.controller.money;

import com.prefin.service.money.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoanController {
    private final LoanService loanService;
}
