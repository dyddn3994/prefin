package com.prefin.controller.quest;

import com.prefin.service.quest.QuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class QuestOwnedController {
    private final QuestService questService;
}
