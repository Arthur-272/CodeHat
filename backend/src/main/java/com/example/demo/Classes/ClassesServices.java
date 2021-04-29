package com.example.demo.Classes;

import com.example.demo.Comments.CommentsServices;
import com.example.demo.Posts.Posts;
import com.example.demo.Posts.PostsServices;
import com.example.demo.Users.Users;
import com.example.demo.Users.UsersRepositories;
import com.example.demo.Users.UsersServices;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class ClassesServices {

    @Autowired
    private ClassesRepositories classesRepositories;

    @Autowired
    private UsersRepositories usersRepositories;

    @Autowired
    private UsersServices usersServices;

    @Autowired
    private PostsServices postsServices;

    @Autowired
    private CommentsServices commentsServices;

    public List<Classes> getAllClasses() {
        List<Classes> list = new ArrayList<Classes>();
        classesRepositories.findAll().forEach(list::add);
        return list;
    }

    public Optional<Classes> getClassById(long id) {
        return classesRepositories.findById(id);
    }

    public boolean isOwner(long ownerId, long classId){
        return classesRepositories.findById(classId).get().getOwnerId() == ownerId;
    }

    public List<Classes> getClassesByOwnerId(long id){
        return classesRepositories.getAllByOwnerId(id);
    }

    public ResponseEntity addNewClass(Classes newClass, long ownerId) throws Exception{
        if (usersServices.isTeacher(ownerId)) {
//            Making the current logged in user the owner of the class
            Users user = usersRepositories.findById(ownerId).get();
            newClass.setOwnerId(ownerId);
            List<Users> teachers = new ArrayList<Users>();
            teachers.add(user);
            newClass.setTeachers(teachers);

            newClass.setCreatedAt(new Date());

//            Adding an empty list as students in that class
            List<Users> students = new ArrayList<Users>();
            newClass.setStudents(students);

//            Adding an empty list of posts/announcements in that class
            List<Posts> posts = new ArrayList<com.example.demo.Posts.Posts>();
            newClass.setPosts(posts);

//            Creating a random classCode
            String classCode = generateClassCode();
            Optional<Classes> classWithClassCodes;
            while(true){
                classWithClassCodes = classesRepositories.findByClassCode(classCode);
                if(classWithClassCodes.isPresent())
                    classCode = generateClassCode();
                else
                    break;
            }
            newClass.setClassCode(classCode);

//            Adding the class to the db
            classesRepositories.save(newClass);
            return ResponseEntity.accepted().body(newClass.getId());
        } else {
            throw new Exception("Not a teacher");
        }
    }

    public String generateClassCode(){
        String pool = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder string = new StringBuilder();
        Random random = new Random();
        for(int i=0; i<6 ; i++){
            string.append(pool.charAt(random.nextInt(pool.length())));
        }
        return string.toString();
    }

    public void addTeachers(long userId, long classId, JSONArray teacherIds) throws Exception{
        if(teacherIds != null) {
            if(classesRepositories.findById(classId).get().getTeachers().contains(usersServices.getUserById(userId))){
                List<Users> inClass = classesRepositories.findById(classId).get().getTeachers();
                for(int i=0;i<teacherIds.length();i++){
                    long teacherId = teacherIds.getLong(i);
                    if(!inClass.contains(usersServices.getUserById(teacherId))){
                        inClass.add(usersServices.getUserById(teacherId));
                    } else{
                        System.out.println(userId + " is already in class");
                    }
                }
                classesRepositories.findById(classId).get().setTeachers(inClass);
            } else{
                throw new Exception(userId + " is not a teacher");
            }
        } else{
            throw new Exception("No ids passed");
        }
    }

    public void addStudent(long userId, long classId, JSONArray studentIds) throws Exception {
        if(studentIds != null) {
            if (classesRepositories.findById(classId).get().getTeachers().contains(usersServices.getUserById(userId))) {
                List<Users> inClass = classesRepositories.findById(classId).get().getStudents();
                for (int i = 0; i < studentIds.length(); i++) {
                    long studentId = studentIds.getLong(i);
                    if(usersServices.isStudent(studentId)){
                        if(!inClass.contains(usersServices.getUserById(studentId))){
                            inClass.add(usersServices.getUserById(studentId));
                            System.out.println(studentId + " is added in class");
                        } else{
                            System.out.println(studentId + " already in class");
                        }
                    } else{
                        System.out.println(studentId + " is not a student");
                    }
                }
                classesRepositories.findById(classId).get().setStudents(inClass);
            } else {
                throw new Exception("Invalid user accessing the class");
            }
        } else{
            throw new Exception("No ids passed");
        }
    }

    public void deleteStudent(long userId, long classId, JSONArray studentIds) throws Exception{
        if(studentIds != null){
            if(classesRepositories.findById(classId).get().getTeachers().contains(usersServices.getUserById(userId))){
                List<Users> inClass = classesRepositories.findById(classId).get().getStudents();
                for(int i=0;i<studentIds.length();i++){
                    if(!inClass.isEmpty()) {
                        long studentId = studentIds.getLong(i);
                        if (inClass.contains(usersServices.getUserById(studentId))) {
                            inClass.remove(usersServices.getUserById(studentId));
                            System.out.println(studentId + " is removed");
                        } else {
                            System.out.println("No such student with id " + studentId);
                        }
                    } else{
                        throw new Exception("Class empty");
                    }
                }
                classesRepositories.findById(classId).get().setStudents(inClass);
            } else{
                throw new Exception("Invalid user accessing the class");
            }
        } else{
            throw new Exception("No student's list passed");
        }
    }

    public void deleteTeacher(long userId, long classId, JSONArray teacherIds) throws Exception{
        if(teacherIds != null){
            if(isOwner(userId, classId)){
                List<Users> inClass = classesRepositories.findById(classId).get().getTeachers();
                for(int i=0;i< teacherIds.length();i++){
                    long teacherId = teacherIds.getLong(i);
                    if(teacherId != userId) {
                        if (inClass.contains(usersServices.getUserById(teacherId))) {
                            inClass.remove(usersServices.getUserById(teacherId));
                            System.out.println(teacherId + " is removed");
                        } else {
                            System.out.println(teacherId + " is not in class");
                        }
                    } else{
                        throw new Exception("YOu cannot delete owner from the class");
                    }
                }
                classesRepositories.findById(classId).get().setTeachers(inClass);
            } else{
                throw new Exception("Invalid user accessing the class");
            }
        } else{
            throw new Exception("No teacher's list passed");
        }
    }

    public void deleteClass(long userId, long classId) throws Exception{
        if(isOwner(userId, classId)){
            List<Posts> posts = classesRepositories.findById(classId).get().getPosts();
            for(Posts post : posts){
                postsServices.deleteById(post.getId());
            }
            System.out.println("Class deleted");
        } else{
            throw new Exception("Invalid user trying to access the class");
        }
    }

    public List<Classes> findAllByUserId(long userId) throws Exception{
        return classesRepositories.getAllByOwnerId(userId);
    }
    public Classes findById(long id) {
        return classesRepositories.findById(id).get();
    }

    public void updateClass(Classes classes) {
        classesRepositories.save(classes);
    }

    public List<Users> getAllTeachersByClassId(long classId){
        return classesRepositories.findById(classId).get().getTeachers();
    }

    public List<Users> getAllStudentsByClassId(long classId){
        return classesRepositories.findById(classId).get().getStudents();
    }


    public ResponseEntity joinClassUsingClassCode(long userId, String classCode) {
        Optional<Users> user = usersRepositories.findById(userId);
        if(user.isPresent()){
            String role = user.get().getRole();
            Optional<Classes> classToBeJoined = classesRepositories.findByClassCode(classCode);
            if(classToBeJoined.isPresent()){
                long classId = classToBeJoined.get().getId();
                if(role.equals("teacher")){
                    List<Users> inClass = classesRepositories.findById(classId).get().getTeachers();
                    if(!inClass.contains(usersServices.getUserById(userId))){
                        inClass.add(usersServices.getUserById(userId));
                        return ResponseEntity.accepted().build();
                    } else{
                        return ResponseEntity.status(500).build();
                    }
                } else if(role.equals("student")){
                    List<Users> inClass = classesRepositories.findById(classId).get().getStudents();
                    if(!inClass.contains(usersServices.getUserById(userId))){
                        inClass.add(usersServices.getUserById(userId));
                        return ResponseEntity.accepted().build();
                    } else{
                        return ResponseEntity.badRequest().build();
                    }
                } else{
                    System.out.println("Role error");
                    return ResponseEntity.badRequest().build();
                }
            } else{
                System.out.println("No class found");
                return ResponseEntity.badRequest().build();
            }
        } else{
            System.out.println("No user found");
            return ResponseEntity.badRequest().build();
        }
    }

    public List<Classes> findAllClassByUserId(long userId){
        return classesRepositories.getAllClassesByUserId(userId);
    }


}
