package com.prefin.controller.user;

import com.prefin.domain.user.Parent;
import com.prefin.dto.user.ParentSignUpDto;
import com.prefin.service.user.ParentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ParentController {
    private final ParentService parentService;

    @PostMapping("/parent")
    public long signUp(@RequestBody ParentSignUpDto parentSignUpDto) {
        return parentService.signUp(parentSignUpDto);
    }

    @PostMapping("/parent/login")
    public Parent login(String userId, String password) {
        return parentService.login(userId, password);
    }


}
