package com.prefin.service.money;

import com.prefin.domain.money.SavingHistory;
import com.prefin.domain.user.Child;
import com.prefin.domain.user.Parents;
import com.prefin.dto.money.SavingHIstoryDto;
import com.prefin.repository.money.SavingRepository;
import com.prefin.repository.user.ChildRepository;
import com.prefin.repository.user.ParentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SavingService {

    private final ChildRepository childRepository;
    private final SavingRepository savingRepository;

    public List<SavingHIstoryDto> getSavingHistory(Long childId) {
        List<SavingHistory> savingHistoryList = savingRepository.findByChild(
                childRepository.findById(childId).orElse(null));

        List<SavingHIstoryDto> savingHIstoryDtos = savingHistoryList.stream()
                .map(SavingHIstoryDto::fromEntity)
                .collect(Collectors.toList());

        return savingHIstoryDtos;
    }
}
