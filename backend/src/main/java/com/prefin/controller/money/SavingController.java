package com.prefin.controller.money;

import com.prefin.domain.money.SavingHistory;
import com.prefin.domain.user.Child;
import com.prefin.repository.money.SavingRepository;
import com.prefin.repository.user.ChildRepository;
import com.prefin.service.money.SavingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SavingController {
    private final SavingService savingService;
    private final SavingRepository savingRepository;
    private final ChildRepository childRepository;

    @GetMapping("/savinghistory/{id}")
    public List<SavingHistory> getSavingHistoryByChildId(@PathVariable long id) {
        Optional<Child> child = childRepository.findById(id);

        return savingRepository.findByChild(child.get());
    }
}
