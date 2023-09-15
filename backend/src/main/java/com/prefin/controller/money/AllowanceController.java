package com.prefin.controller.money;

import com.prefin.dto.money.AllowanceDto;
import com.prefin.service.money.AllowanceService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AllowanceController {
    private final AllowanceService allowanceService;

    // 용돈 수동 이체
    @PostMapping("/allowance/set")  // 용돈 설정 및 수정
    public ResponseEntity<String> setAllowance(@RequestBody AllowanceDto requestDto) {
        return allowanceService.allowanceSetting(requestDto);
    }

    // 정기 용돈 삭제
    @DeleteMapping("/allowance/delete/{allowanceId}")
    public ResponseEntity<String> cancelAllowance(@PathVariable Long allowanceId) {
        return allowanceService.deleteAutoTransfer(allowanceId);
    }

//
//    // 정기 용돈 수정
//    @PutMapping("/allowance/update")
//    public ResponseEntity<String> updateAllowance(@RequestBody AllowanceDto requestDto) {
//        return allowanceService.allowanceUpdate(requestDto);
//    }


    @PostMapping("/allowance/transfer")  // 용돈 수동 이체
    public ResponseEntity<String> transferAllowance(@RequestBody AllowanceDto requestDto) {
        return allowanceService.allowanceTransfer(requestDto);
    }

    @GetMapping("/parentBalance/{parentId}")  // 부모 잔액 조회
    public ResponseEntity<Integer> parentBalance(@PathVariable Long parentId) {
        return allowanceService.parentBalance(parentId);
    }

    @GetMapping("/childBalance/{childId}")  // 자녀 잔액 조회
    public ResponseEntity<Integer> childBalance(@PathVariable Long childId) {
        return allowanceService.childBalance(childId);
    }

}
