package com.prefin.component;

import com.prefin.domain.user.Child;
import com.prefin.repository.money.SavingRepository;
import com.prefin.repository.user.ChildRepository;
import com.prefin.repository.user.ParentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SavingScheduler {
    private final ChildRepository childRepository;
    private final ParentRepository parentRepository;
    private final SavingRepository savingRepository;

//    // 매 1일 이자 입금
//    @Transactional
//    @Scheduled(cron = "0 * * * * ?", zone = "Asia/Seoul")  // "0 * * * * ?" 매 분, "* 0 0 * * ?" 매 자정
//    public void MonthlyInterest() {
//        // 자녀의 저축금액으로 부모의 이율을 적용해서 돈을 준다.
//        List<Child> childList = childRepository.findAll();
//
//        for (Child child : childList) {
//            child.getSavingAmount()
//        }
//        // 저축 내역에 추가한다.
//    }
}
