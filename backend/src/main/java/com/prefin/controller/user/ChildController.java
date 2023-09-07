package com.prefin.controller.user;

import com.prefin.domain.user.Child;
import com.prefin.domain.user.Parent;
import com.prefin.dto.user.ChildDto;
import com.prefin.dto.user.ParentDto;
import com.prefin.service.user.ChildService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ChildController {
    private final ChildService childService;

    @PostMapping("/child")
    public long signUp(@RequestBody ChildDto childDto) {
        return childService.signUp(childDto);
    }

    @PostMapping("/child/login")
    public Child login(@RequestBody ChildDto childDto) {
        String userId = childDto.getUserId();
        String password = childDto.getPassword();

        return childService.login(userId, password);
    }

    @PutMapping("/child/{id}/account")
    public String setAccount(@PathVariable long id, @RequestBody ChildDto childDto) {
        return childService.setAccount(id, childDto.getAccount());
    }

    @PutMapping("/child/{id}/simplepass")
    public String setSimplePass(@PathVariable long id, @RequestBody ChildDto childDto) {
        return childService.setSimplePassword(id, childDto.getSimplePass());
    }

    @PutMapping("/child/{id}/savingaccount")
    public String changeSavingAccount(@PathVariable long id, @RequestBody ChildDto childDto) {
        return  childService.changeSavingAccount(id, childDto.getSavingAccount());
    }

    @PutMapping("/child/{id}/trustscore")
    public String changeTrustScore(@PathVariable long id, @RequestBody ChildDto childDto) {
        return childService.changeTrustScore(id, childDto.getTrustScore());
    }

    @PutMapping("/child/{id}/token")
    public String setToken(@PathVariable long id, @RequestBody ChildDto childDto) {
        return childService.setToken(id, childDto.getFcmToken());
    }

    @PutMapping("/child/{id}/deposit")
    public void deposit(@PathVariable long id, @RequestBody ChildDto childDto) {
        childService.deposit(id, childDto.getBalance());
    }

    @PutMapping("child/{id}/withdraw")
    public void withdraw(@PathVariable long id, @RequestBody ChildDto childDto) {
        childService.withdraw(id, childDto.getBalance());
    }
}
