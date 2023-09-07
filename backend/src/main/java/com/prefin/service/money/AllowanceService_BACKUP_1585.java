package com.prefin.service.money;

import com.prefin.domain.money.Allowance;
import com.prefin.domain.user.Child;
import com.prefin.domain.user.Parents;
import com.prefin.dto.money.AllowanceDto;
import com.prefin.repository.money.AllowanceRepository;
import com.prefin.repository.money.LoanRepository;
import com.prefin.repository.user.ChildRepository;
import com.prefin.repository.user.ParentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
<<<<<<< HEAD
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
=======
>>>>>>> backend

@Service
@RequiredArgsConstructor
public class AllowanceService {

    private final AllowanceRepository allowanceRepository;
    private final ParentRepository parentRepository;
    private final ChildRepository childRepository;
    private final LoanRepository loanRepository;


    // 용돈을 얼마 줄지, 언제 줄지 설정하는 로직
    @Transactional
    public ResponseEntity<String> allowanceSetting(AllowanceDto allowanceDto) {
        // 부모와 자식의 정보를 가져온다
        Parents parent = null;
        Child child = null;

        if (allowanceDto.getParentId() != null) {
            parent = parentRepository.findById(allowanceDto.getParentId())
                    .orElseThrow(() -> new EntityNotFoundException("Parent Not Found"));
        }
        if (allowanceDto.getChildId() != null) {
            child = childRepository.findById(allowanceDto.getChildId())
                    .orElseThrow(() -> new EntityNotFoundException("Child Not Found"));
        }

        // 용돈 테이블에 새로운 용돈을 추가
        // 위에서 가져온 부모, 자식의 정보와 Dto에서 받은 용돈 금액, 용돈 지급일을 입력한다.
        Allowance newAllowance = Allowance.builder()
                .parent(parent)
                .child(child)
                .allowanceAmount(allowanceDto.getAllowanceAmount())
                .payday(allowanceDto.getPayday())
                .build();

        allowanceRepository.save(newAllowance);

        return ResponseEntity.ok("Allowance Set");
    }

    @Transactional  // 용돈 이체 로직
    public ResponseEntity<String> allowanceTransfer(AllowanceDto allowanceDto) {  // AllowanceDto 로 받고
        // 받은 Dto 에서 부모 id로 해당 용돈 정보를 찾는다.
        Allowance newAllowance = allowanceRepository.findByParentId(allowanceDto.getParentId());

        // 부모 계좌에서 용돈과 잔액 비교 후 용돈 <= 잔액 이라면 돈을 차감하고 아니라면 cause error
        Parents newParent = newAllowance.getParent();
        Child newChild = newAllowance.getChild();

        int balance = newParent.getBalance();
        int allowance = newAllowance.getAllowanceAmount();

        if (balance < allowance) {
            System.out.println("잔액 부족");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잔액이 부족합니다.");
        }


        // 아래의 식을 계산하면 빌린 돈이 0일 땐 myDebt가 0이고 아니라면 한달치 이자가 나온다.
        BigDecimal loanInterst = newParent.getLoanRate();

        BigDecimal bigDebt = new BigDecimal();


        BigDecimal totalDebt = loanInterst.multiply(bigDebt);
        int myDebt = totalDebt.intValueExact();
        // 만약 빌린 돈이 있다면 용돈에서 해당 원금과 한달 이자를 더한 금액을 용돈에서 뺀다.
        if (myDebt != 0) {
            allowance = allowance - myDebt;
        }

        // 부모 계좌 잔액 차감
        newParent.transfer(allowance);

        // 자식 계좌 잔액 증가시키기
        newChild.addMoney(allowance);

        // 대출 내역 제거?
        // 빌린 날짜로 구분?
        // 체크하는 로직이 하나 더 필요, 만약 빌린 날짜가 용돈 지급일의 일보다 낮다면

        System.out.println("===========================================");
        System.out.println("아이에게 용돈지급 완료");
        System.out.println(newChild.getBalance());
        System.out.println("===========================================");
        return ResponseEntity.status(HttpStatus.OK).body("용돈 정상 지급");


    }
}
