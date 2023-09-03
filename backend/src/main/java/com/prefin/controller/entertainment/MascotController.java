package com.prefin.controller.entertainment;

import com.prefin.service.entertainment.MascotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MascotController {
    private final MascotService mascotService;
}
