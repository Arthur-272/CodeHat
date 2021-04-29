package com.example.demo.Classes;

import com.example.demo.Users.Users;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class ClassesController {

    @Autowired
    ClassesServices classesServices;

    @GetMapping("/classes")
    public List<Classes> showAllClasses(){
        return classesServices.getAllClasses();
    }

    @PostMapping(value =  "/user/{id}/createClass")
    public ResponseEntity createNewClass(@RequestBody Classes newClass, @PathVariable long id)throws Exception{
        return classesServices.addNewClass(newClass, id);
    }

    @GetMapping("/user/{userId}/classes")
    List<Classes> findAllClassByUserId(@PathVariable long userId) {
        return classesServices.findAllClassByUserId(userId);
    }

    @PostMapping("/user/{userId}/class/{id}/addTeachers")
    public void addTeachers(@PathVariable long userId, @PathVariable long id, @RequestBody String string)throws Exception{
        classesServices.addTeachers(userId, id, new JSONObject(string).getJSONArray("ids"));
    }

    @PostMapping("/user/{userId}/class/{id}/addStudents")
    public void addStudents(@PathVariable long userId, @PathVariable long id,@RequestBody String string) throws Exception{
        classesServices.addStudent(userId, id, new JSONObject(string).getJSONArray("ids"));
    }

    @DeleteMapping("/user/{userId}/class/{id}/deleteStudents")
    public void deleteStudents(@PathVariable long userId, @PathVariable long id, @RequestBody String string) throws Exception{
        classesServices.deleteStudent(userId, id, new JSONObject(string).getJSONArray("ids"));
    }

    @DeleteMapping("/user/{userId}/class/{id}/deleteTeachers")
    public void deleteTeachers(@PathVariable long userId, @PathVariable long id, @RequestBody String string) throws Exception{
        classesServices.deleteTeacher(userId, id, new JSONObject(string).getJSONArray("ids"));
    }

    @DeleteMapping("/user/{userId}/class/{id}/deleteClass")
    public void deleteClass(@PathVariable long userId, @PathVariable long id) throws Exception{
        classesServices.deleteClass(userId, id);
    }

    @PostMapping("/user/{userId}/classes/joinClass")
    public ResponseEntity joinClassUsingClassCode(@PathVariable long userId, @RequestParam String classCode){
        return classesServices.joinClassUsingClassCode(userId, classCode);
    }

    @GetMapping("/class/{classId}/students")
    public List<Users> getStudentsByClassId(@PathVariable long classId){
        return classesServices.getAllStudentsByClassId(classId);
    }

    @GetMapping("/class/{classId}/teachers")
    public List<Users> getTeachersByClassId(@PathVariable long classId){
        return classesServices.getAllTeachersByClassId(classId);
    }
}