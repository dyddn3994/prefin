package com.prefin.service.money;

import com.prefin.domain.user.Child;
import com.prefin.domain.user.Parents;
import com.prefin.repository.money.SavingRepository;
import com.prefin.repository.user.ChildRepository;
import com.prefin.repository.user.ParentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class SavingService {
    private final ChildRepository childRepository;

}
