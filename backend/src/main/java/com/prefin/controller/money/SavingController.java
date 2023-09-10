package com.prefin.controller.money;

import com.prefin.service.money.SavingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SavingController {
    private final SavingService savingService;
}
