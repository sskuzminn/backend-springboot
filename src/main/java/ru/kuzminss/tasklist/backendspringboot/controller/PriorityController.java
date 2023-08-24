package ru.kuzminss.tasklist.backendspringboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kuzminss.tasklist.backendspringboot.entity.Category;
import ru.kuzminss.tasklist.backendspringboot.entity.Priority;
import ru.kuzminss.tasklist.backendspringboot.repo.PriorityRepository;

import java.util.List;


@RestController
@RequestMapping("/priority") // базовый адрес
public class PriorityController {

    private PriorityRepository priorityRepository;


    public PriorityController(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }


    // для тестирования адрес: http://localhost:8080/priority/test
    @GetMapping("/test")
    public List<Priority> test() {

        List<Priority> list = priorityRepository.findAll();


        return list; // JSON формат будет использоваться автоматически

    }

    @PostMapping("/add")
    public ResponseEntity<Priority> add(@RequestBody Priority priority) {

        //проверка на обязательные параметры
        if (priority.getId() != null && priority.getId() != 0) {
            return new ResponseEntity("redundant param: id must be null", HttpStatus.NOT_ACCEPTABLE);
        }


        //если передали пустое значение title
        if (priority.getTitle() == null || priority.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }


        return ResponseEntity.ok(priorityRepository.save(priority));
    }


}