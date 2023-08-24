package ru.kuzminss.tasklist.backendspringboot.controller;

import org.springframework.web.bind.annotation.*;
import ru.kuzminss.tasklist.backendspringboot.entity.Category;
import ru.kuzminss.tasklist.backendspringboot.repo.CategoryRepository;


import java.util.List;


@RestController
@RequestMapping ("/category") // базовый адрес
public class CategoryController {


    private CategoryRepository categoryRepository;


    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }



    @GetMapping("/test")
    public List<Category> test() {

        List<Category> list = categoryRepository.findAll();


        return list;

    }


    @PostMapping("/add")
    public Category add(@RequestBody Category category){
        return categoryRepository.save(category);
    }


}