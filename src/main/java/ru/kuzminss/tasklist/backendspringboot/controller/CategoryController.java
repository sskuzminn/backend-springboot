package ru.kuzminss.tasklist.backendspringboot.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kuzminss.tasklist.backendspringboot.entity.Category;
import ru.kuzminss.tasklist.backendspringboot.repo.CategoryRepository;


import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping ("/category") // базовый адрес
public class CategoryController {

    // доступ к данным из БД
    private CategoryRepository categoryRepository;

    // автоматическое внедрение экземпляра класса через конструктор
    // не используем @Autowired ля переменной класса, т.к. "Field injection is not recommended "
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    // для тестирования адрес: http://localhost:8080/category/test
    @GetMapping("/test")
    public List<Category> test() {

        List<Category> list = categoryRepository.findAll();


        return list; // JSON формат будет использоваться автоматически

    }


    @PostMapping("/add")
    public ResponseEntity<Category> add(@RequestBody Category category){

        // проверка на обязательные параметры
        if (category.getId() != null && category.getId() != 0) {
            // id создается автоматически в БД (autoincrement), поэтому его передавать не нужно, иначе может быть конфликт уникальности значения
            return new ResponseEntity("redundant param: id MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение title
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




    @GetMapping("/id/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id) {

        Category category = null;

        // можно обойтись и без try-catch, тогда будет возвращаться полная ошибка (stacktrace)
        // здесь показан пример, как можно обрабатывать исключение и отправлять свой текст/статус
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
        return new ResponseEntity(HttpStatus.OK); // не возвращаем удаленный объект
    }



}