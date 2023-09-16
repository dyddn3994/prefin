package com.prefin.service.money;

import com.prefin.domain.money.SavingHistory;
import com.prefin.dto.money.SavingHistoryDto;
import com.prefin.repository.money.SavingRepository;
import com.prefin.repository.user.ChildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SavingService {

    private final ChildRepository childRepository;
    private final SavingRepository savingRepository;

    public List<SavingHistoryDto> getSavingHistory(Long childId) {
        List<SavingHistory> savingHistoryList = savingRepository.findByChild(
                childRepository.findById(childId).orElse(null));

        List<SavingHistoryDto> savingHistoryDtos = savingHistoryList.stream()
                .map(SavingHistoryDto::fromEntity)
                .collect(Collectors.toList());

        return savingHistoryDtos;
    }
}
