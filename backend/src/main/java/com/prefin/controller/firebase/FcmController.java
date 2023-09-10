package com.prefin.controller.firebase;

import com.prefin.dto.firebase.FcmDto;
import com.prefin.service.firebase.FirebaseCloudMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class FcmController {
    private FirebaseCloudMessageService firebaseCloudMessageService;

    @PostMapping("/fcm")
    public void sendFcm(@RequestBody FcmDto fcmDto) throws IOException {
        String token = fcmDto.getMessage().getToken();
        String title = fcmDto.getMessage().getNotification().getTitle();
        String body = fcmDto.getMessage().getNotification().getBody();

        firebaseCloudMessageService.sendMessageTo(token, title, body);
    }
}
