package ru.kuzminss.tasklist.backendspringboot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.kuzminss.tasklist.backendspringboot.entity.Stat;
import ru.kuzminss.tasklist.backendspringboot.repo.StatRepository;

@RestController
public class StatController {

    private final StatRepository statRepository;

    public StatController(StatRepository statRepository) {
        this.statRepository = statRepository;
    }

    private final Long defaultId = 1L;

    public ResponseEntity<Stat> findById(){
        return ResponseEntity.ok(statRepository.findById(defaultId).get());
    }

}
