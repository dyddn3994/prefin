package com.prefin.service.money;

import com.prefin.repository.money.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoanService {
    private final LoanRepository loanRepository;
}
