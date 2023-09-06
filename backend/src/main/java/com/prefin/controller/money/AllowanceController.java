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
@RequestMapping("/api")
public class AllowanceController {
    private final AllowanceService allowanceService;

    // 용돈 수동 이체
    @PostMapping("/allowance/transfer")  // 필요 정보 : 출금계좌번호, 입금은행코드, 입금계좌번호,
    // 이체금액, 입금계좌통장메모, 출금계좌통장메모, 용돈지급일 인데 이체금액과 용돈지급일만 정하고 나머지는 fix 해둘 수 없나?
    public ResponseEntity<String> transferAllowance(@RequestBody AllowanceDto requestDto) {
        System.out.println("===============  testing  ==============");
        return allowanceService.allowanceTransfer(requestDto);
    }

}
