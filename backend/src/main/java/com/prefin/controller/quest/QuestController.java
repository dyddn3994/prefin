package com.prefin.controller.quest;

import com.prefin.domain.quest.Quest;
import com.prefin.domain.user.Parents;
import com.prefin.dto.quest.QuestDto;
import com.prefin.repository.quest.QuestRepository;
import com.prefin.repository.user.ParentRepository;
import com.prefin.service.quest.QuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class QuestController {
    private final QuestService questService;
    private final QuestRepository questRepository;
    private final ParentRepository parentRepository;

    @PostMapping("/quest")
    public void makeQuest(@RequestBody QuestDto questDto) {
        questService.makeQuest(questDto);
    }

    @DeleteMapping("/quest/{id}")
    public ResponseEntity<Boolean> deleteQuest(@PathVariable long id) {
        return questService.deleteQuest(id);
    }

    // QuestDto로 수정하세요
    @GetMapping("/quest/{id}")
    public QuestDto getQuest(@PathVariable long id) {
        return questService.getQuestById(id);
    }

    @GetMapping("/quests/{id}")
    public List<QuestDto> getQuestByParent(@PathVariable Long id) {

        return questService.findByParent(id);
    }
}
