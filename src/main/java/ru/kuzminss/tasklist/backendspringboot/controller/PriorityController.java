package ru.kuzminss.tasklist.backendspringboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kuzminss.tasklist.backendspringboot.entity.Priority;
import ru.kuzminss.tasklist.backendspringboot.repo.PriorityRepository;
import ru.kuzminss.tasklist.backendspringboot.search.PrioritySearchValues;

import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping ("/priority")
public class PriorityController {


    private PriorityRepository priorityRepository;


    public PriorityController(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }


    @GetMapping("/all")
    public List<Priority> findAll() {

        return priorityRepository.findAllByOrderByIdAsc();

    }



    @PostMapping("/add")
    public ResponseEntity<Priority> add(@RequestBody Priority priority){


        if (priority.getId() != null && priority.getId() != 0) {
            // id создается автоматически в БД (autoincrement), поэтому его передавать не нужно, иначе может быть конфликт уникальности значения
            return new ResponseEntity("redundant param: id MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }


        if (priority.getTitle() == null || priority.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }


        if (priority.getColor() == null || priority.getColor().trim().length() == 0) {
            return new ResponseEntity("missed param: color", HttpStatus.NOT_ACCEPTABLE);
        }


        return ResponseEntity.ok(priorityRepository.save(priority));
    }


    @PutMapping("/update")
    public ResponseEntity update(@RequestBody Priority priority){


        if (priority.getId() == null || priority.getId() == 0) {
            return new ResponseEntity("missed param: id", HttpStatus.NOT_ACCEPTABLE);
        }


        if (priority.getTitle() == null || priority.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }


        if (priority.getColor() == null || priority.getColor().trim().length() == 0) {
            return new ResponseEntity("missed param: color", HttpStatus.NOT_ACCEPTABLE);
        }


        return ResponseEntity.ok(priorityRepository.save(priority));

    }


    @GetMapping("/id/{id}")
    public ResponseEntity<Priority> findById(@PathVariable Long id) {

        Priority priority = null;


        try{
            priority = priorityRepository.findById(id).get();
        }catch (NoSuchElementException e){
            e.printStackTrace();
            return new ResponseEntity("id="+id+" not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return  ResponseEntity.ok(priority);
    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {


        try {
            priorityRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            e.printStackTrace();
            return new ResponseEntity("id="+id+" not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK); // не возвращаем удаленный объект
    }


    // поиск по любым параметрам CategorySearchValues
    @PostMapping("/search")
    public ResponseEntity<List<Priority>> search(@RequestBody PrioritySearchValues prioritySearchValues){

        // если вместо текста будет пусто или null - вернутся все категории
        return ResponseEntity.ok(priorityRepository.findByTitle(prioritySearchValues.getText()));
    }


}