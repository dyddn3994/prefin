package com.prefin.controller.user;

import com.prefin.service.user.ParentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ParentController {
    private final ParentService parentService;
}
