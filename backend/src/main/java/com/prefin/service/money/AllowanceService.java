package com.prefin.service.money;

import com.prefin.domain.money.Allowance;
import com.prefin.domain.user.Child;
import com.prefin.domain.user.Parent;
import com.prefin.dto.money.AllowanceDto;
import com.prefin.repository.money.AllowanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class AllowanceService {

    private final AllowanceRepository allowanceRepository;

    @Transactional  // 용돈 수동 이체 로직
    public ResponseEntity<String> allowanceTransfer(AllowanceDto requestDto) {  // AllowanceDto 로 받고
        // 받은 Dto 에서 id로 해당 용돈 정보를 찾는다.
        Allowance newAllowance = allowanceRepository.findById(requestDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("NO SUCH ALLOWANCE"));

        // 부모 계좌에서 용돈과 잔액 비교 후 용돈 <= 잔액 이라면 돈을 차감하고 아니라면 cause error
        Parent newParent = newAllowance.getParent();

        int balance = newParent.getBalance();
        int allowance = newAllowance.getAllowanceAmount();

        if (balance < allowance) {
            System.out.println("잔액 부족");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잔액이 부족합니다.");
        } else {
            newParent.transfer(allowance);
            // 자식 계좌 잔액 증가시키기
            Child newChild = newAllowance.getChild();
            newChild.getAllowance(allowance);
            System.out.println("아이에게 용돈지급 완료");
            return ResponseEntity.status(HttpStatus.OK).body("용돈 정상 지급");
        }
    }
}
