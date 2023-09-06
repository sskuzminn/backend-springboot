package ru.kuzminss.tasklist.backendspringboot.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kuzminss.tasklist.backendspringboot.entity.Category;
import ru.kuzminss.tasklist.backendspringboot.repo.CategoryRepository;
import ru.kuzminss.tasklist.backendspringboot.search.CategorySearchValues;


import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping ("/category") // базовый адрес
public class CategoryController {


    private CategoryRepository categoryRepository;


    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @GetMapping("/all")
    public List<Category> findAll() {

        return categoryRepository.findAllByOrderByTitleAsc();

    }


    @PostMapping("/add")
    public ResponseEntity<Category> add(@RequestBody Category category){


        if (category.getId() != null && category.getId() != 0) {

            return new ResponseEntity("redundant param: id MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }


        if (category.getTitle() == null || category.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }


        return ResponseEntity.ok(categoryRepository.save(category));
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody Category category){

        // проверка на обязательные параметры
        if (category.getId() == null || category.getId() == 0) {
            return new ResponseEntity("missed param: id", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение title
        if (category.getTitle() == null || category.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        // save работает как на добавление, так и на обновление
        return ResponseEntity.ok(categoryRepository.save(category));

    }

    // параметр id передаются не в BODY запроса, а в самом URL
    @GetMapping("/id/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id) {

        Category category = null;


        try{
            category = categoryRepository.findById(id).get();
        }catch (NoSuchElementException e){ // если объект не будет найден
            e.printStackTrace();
            return new ResponseEntity("id="+id+" not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return  ResponseEntity.ok(category);
    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {


        try {
            categoryRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            e.printStackTrace();
            return new ResponseEntity("id="+id+" not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK);
    }


//Поиск по любым параметрам CategorySearchValues
    @PostMapping("/search")
    public ResponseEntity<List<Category>> search(@RequestBody CategorySearchValues categorySearchValues){

        //если вместо текста будет пусто или null - вернутся все категории
        return ResponseEntity.ok(categoryRepository.findByTitle(categorySearchValues.getText()));
    }







}