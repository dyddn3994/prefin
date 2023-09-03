package com.prefin.service.money;

import com.prefin.repository.money.AllowanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AllowanceService {

    private final AllowanceRepository allowanceRepository;
}
