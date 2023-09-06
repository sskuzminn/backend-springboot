package ru.kuzminss.tasklist.backendspringboot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kuzminss.tasklist.backendspringboot.entity.Priority;

import java.util.List;

@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long> {

    // если title == null или =='', то получим все значения
    @Query("SELECT c FROM Priority c where " +
            "(:title is null or :title='' or lower(c.title) like lower(concat('%', :title,'%')))  " +
            "order by c.title asc")
    List<Priority> findByTitle(@Param("title") String title);

    // получить все значения, сортировка по id
    List<Priority> findAllByOrderByIdAsc();

}