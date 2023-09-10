package com.prefin.controller.quest;

import com.prefin.domain.quest.QuestOwned;
import com.prefin.domain.user.Child;
import com.prefin.dto.quest.QuestOwnedDto;
import com.prefin.repository.quest.QuestOwnedRepository;
import com.prefin.repository.user.ChildRepository;
import com.prefin.service.quest.QuestOwnedService;
import com.prefin.service.quest.QuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class QuestOwnedController {
    private final QuestOwnedService questOwnedService;
    private final QuestOwnedRepository questOwnedRepository;
    private final ChildRepository childRepository;
    @PostMapping("/questowned")
    public void makeQuestOwned(@RequestBody QuestOwnedDto questOwnedDto) {
        questOwnedService.makeQuestOwned(questOwnedDto);
    }

    @PutMapping("/questowned/request/{id}")
    public void requestQuestComplete(@PathVariable long id) { questOwnedService.requestQuestComplete(id);}

    @PutMapping("/questowned/complete/{id}")
    public void setQuestCompleted(@PathVariable long id) { questOwnedService.setQuestCompleted(id);}

    @GetMapping("/questowneds/{id}")
    public List<QuestOwned> getQuestOwnedByChild(@PathVariable long id) {
        Child child = childRepository.findById(id).orElse(null);

        return questOwnedRepository.findByChild(child);
    }

    @GetMapping("questowned/{id}")
    public QuestOwned getQuestOwnedById(@PathVariable long id) {
        QuestOwned questOwned = questOwnedRepository.findById(id).orElse(null);

        return questOwned;
    }

}
