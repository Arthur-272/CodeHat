package com.example.demo.Classes;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassesRepositories extends CrudRepository<Classes, Long> {

    List<Classes> getAllByOwnerId(Long id);


    Optional<Classes> findByClassCode(String classCode);

    @Query(value = "select *from classes where " +
            "classes.id in (select classes_students.classes_id from classes_students where classes_students.students_id=:userId) or " +
            "classes.id in (select classes_teachers.classes_id from classes_teachers where classes_teachers.teachers_id=:userId)", nativeQuery = true)
    List<Classes> getAllClassesByUserId(long userId);

}
