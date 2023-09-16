package com.prefin.controller.money;

import com.prefin.dto.money.SavingHistoryDto;
import com.prefin.service.money.SavingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SavingController {
    private final SavingService savingService;

    @GetMapping("/savinghistory/{childId}")
    public List<SavingHistoryDto> getSavingHistoryByChildId(@PathVariable Long childId) {
        return savingService.getSavingHistory(childId);
    }
}
