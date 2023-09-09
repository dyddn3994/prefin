package com.prefin.controller.money;

import com.prefin.dto.money.AllowanceDto;
import com.prefin.service.money.AllowanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AllowanceController {
    private final AllowanceService allowanceService;

    // 용돈 수동 이체
    @PostMapping("/allowance/set")
    public ResponseEntity<String> setAllowance(@RequestBody AllowanceDto requestDto) {
        return allowanceService.allowanceSetting(requestDto);
    }

    @PostMapping("/allowance/transfer")
    public ResponseEntity<String> transferAllowance(@RequestBody AllowanceDto requestDto) {
        return allowanceService.allowanceTransfer(requestDto);
    }


}
