package com.prefin.controller.user;

import com.prefin.domain.user.Child;
import com.prefin.dto.user.ChildDto;
import com.prefin.dto.user.ParentDto;
import com.prefin.service.user.ChildService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ChildController {
    private final ChildService childService;

    @PostMapping("/child")
    public long signUp(@RequestBody ChildDto childDto) {
        return childService.signUp(childDto);
    }

    @GetMapping("/child/{id}")
    public ChildDto getChildById(@PathVariable Long id) { return childService.getChildById(id);}

    @PostMapping("/child/login")
    public ChildDto login(@RequestBody ChildDto childDto) {
        String userId = childDto.getUserId();
        String password = childDto.getPassword();

        return childService.login(userId, password);
    }

    @PutMapping("/child/{id}/account")
    public ResponseEntity<Boolean> setAccount(@PathVariable long id, @RequestBody ChildDto childDto) {
        return childService.setAccount(id, childDto.getAccount());
    }

    @PutMapping("/child/{id}/simplepass")
    public ResponseEntity<Boolean> setSimplePass(@PathVariable long id, @RequestBody ChildDto childDto) {
        return childService.setSimplePassword(id, childDto.getSimplePass());
    }

    @PutMapping("/child/{id}/trustscore")
    public ResponseEntity<Boolean> changeTrustScore(@PathVariable long id, @RequestBody ChildDto childDto) {
        return childService.changeTrustScore(id, childDto.getTrustScore());
    }

    @PutMapping("/child/{id}/token")
    public ResponseEntity<Boolean> setToken(@PathVariable long id, @RequestBody ChildDto childDto) {
        return childService.setToken(id, childDto.getFcmToken());
    }

    @PutMapping("/child/{id}/deposit")
    public ResponseEntity<Boolean> deposit(@PathVariable long id, @RequestBody ChildDto childDto) {
        return childService.deposit(id, childDto.getBalance());
    }

    @PutMapping("child/{id}/withdraw")
    public ResponseEntity<Boolean> withdraw(@PathVariable long id, @RequestBody ChildDto childDto) {
        return childService.withdraw(id, childDto.getBalance());
    }
}
