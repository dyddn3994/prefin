package com.prefin.service.money;

import com.prefin.repository.money.SavingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SavingService {
    private final SavingRepository savingRepository;
}
