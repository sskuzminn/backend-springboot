package ru.kuzminss.tasklist.backendspringboot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kuzminss.tasklist.backendspringboot.entity.Category;



@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}