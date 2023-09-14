package com.prefin.controller.user;

import com.prefin.domain.user.Child;
import com.prefin.domain.user.Parents;
import com.prefin.dto.user.ChildDto;
import com.prefin.dto.user.ParentDto;
import com.prefin.service.user.ParentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ParentController {
    private final ParentService parentService;

    @PostMapping("/parent")
    public long signUp(@RequestBody ParentDto parentDto) {
        return parentService.signUp(parentDto);
    }

    @GetMapping("/parent/{id}")
    public ParentDto getParentById(@PathVariable Long id) { return parentService.getParenById(id);}

    @PostMapping("/parent/login")
    public ParentDto login(@RequestBody ParentDto parentDto) {
        String userId = parentDto.getUserId();
        String password = parentDto.getPassword();

        return parentService.login(userId, password);
    }

    @PutMapping("/parent/{id}/account")
    public ResponseEntity<Boolean> setAccount(@PathVariable long id, @RequestBody ParentDto parentDto) {
        return parentService.setAccount(id, parentDto.getAccount());
    }

    @PutMapping("/parent/{id}/simplepass")
    public ResponseEntity<Boolean> setSimplePass(@PathVariable long id, @RequestBody ParentDto parentDto) {
        return parentService.setSimplePassword(id, parentDto.getSimplePass());
    }

    @PutMapping("/parent/{id}/loan")
    public ResponseEntity<Boolean> setLoanRate(@PathVariable long id, @RequestBody ParentDto parentDto) {
        return  parentService.setLoanRate(id, parentDto.getLoanRate());
    }

    @PutMapping("/parent/{id}/saving")
    public ResponseEntity<Boolean> setSavingRate(@PathVariable long id, @RequestBody ParentDto parentDto) {
        return parentService.setSavingRate(id, parentDto.getSavingRate());
    }

    @PutMapping("/parent/{id}/token")
    public ResponseEntity<Boolean> setToken(@PathVariable long id, @RequestBody ParentDto parentDto) {
        return parentService.setToken(id, parentDto.getFcmToken());
    }

    @GetMapping("/parent/{id}/getChildren")
    public List<ChildDto> myChildren(@PathVariable Long id) {
        return parentService.myChildren(id);
    }

}
