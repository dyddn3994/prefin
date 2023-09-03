package com.prefin.controller.user;

import com.prefin.service.user.ChildService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChildController {
    private final ChildService childService;

}
