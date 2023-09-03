package com.prefin.service.quest;

import com.prefin.repository.quest.QuestOwnedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestOwnedService {
    private final QuestOwnedRepository questOwnedRepository;
}
