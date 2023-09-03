package com.prefin.service.quest;

import com.prefin.repository.quest.QuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestService {
    private final QuestRepository questRepository;
}
