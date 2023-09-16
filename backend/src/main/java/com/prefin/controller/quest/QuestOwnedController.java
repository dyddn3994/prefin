package com.prefin.controller.quest;

import com.prefin.domain.quest.QuestOwned;
import com.prefin.domain.user.Child;
import com.prefin.dto.quest.QuestOwnedDto;
import com.prefin.dto.quest.QuestOwnedQuestDto;
import com.prefin.repository.quest.QuestOwnedRepository;
import com.prefin.repository.user.ChildRepository;
import com.prefin.service.quest.QuestOwnedService;
import com.prefin.service.quest.QuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class QuestOwnedController {
    private final QuestOwnedService questOwnedService;
    private final QuestOwnedRepository questOwnedRepository;
    private final ChildRepository childRepository;
    @PostMapping("/questowned")
    public void makeQuestOwned(@RequestBody QuestOwnedDto questOwnedDto) throws IOException {
        questOwnedService.makeQuestOwned(questOwnedDto);
    }

    @PutMapping("/questowned/request/{id}")
    public ResponseEntity<Boolean> requestQuestComplete(@PathVariable long id) throws IOException { return questOwnedService.requestQuestComplete(id);}

    @PutMapping("/questowned/complete/{id}")
    public ResponseEntity<Boolean> setQuestCompleted(@PathVariable long id) throws IOException { return questOwnedService.setQuestCompleted(id);}

    @GetMapping("/questowneds/{id}")
    public List<QuestOwnedQuestDto> getQuestOwnedByChild(@PathVariable long id) {

        return questOwnedService.findByChild(id);
    }

    @GetMapping("/questowned/{id}")
    public QuestOwnedQuestDto getQuestOwnedById(@PathVariable long id) {

        return questOwnedService.findById(id);
    }

    @PostMapping("/questowned/unregister/{id}")
    public ResponseEntity<Boolean> unregisterQuestOwnedById(@PathVariable long id) {
        return questOwnedService.unregisterQuestOwned(id);
    }
}
