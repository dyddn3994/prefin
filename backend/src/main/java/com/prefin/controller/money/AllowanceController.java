package com.prefin.controller.money;

import com.prefin.service.money.AllowanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AllowanceController {
    private final AllowanceService allowanceService;
}
