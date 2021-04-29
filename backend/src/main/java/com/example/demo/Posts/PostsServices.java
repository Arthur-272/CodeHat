package com.example.demo.Posts;

import com.example.demo.Classes.Classes;
import com.example.demo.Classes.ClassesServices;
import com.example.demo.Comments.Comments;
import com.example.demo.Comments.CommentsServices;
import com.example.demo.Problems.Problems;
import com.example.demo.Problems.ProblemsRepositories;
import com.example.demo.Users.Users;
import com.example.demo.Users.UsersServices;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostsServices {

    @Autowired
    PostsRepositories postsRepositories;

    @Autowired
    ProblemsRepositories problemsRepositories;

    @Autowired
    private UsersServices usersServices;

    @Autowired
    private CommentsServices commentsServices;

    @Autowired
    private ClassesServices classesServices;

    public Optional<Posts> findPostById(long id){
        return postsRepositories.findById(id);
    }

    public ResponseEntity createPost(long userId, long classId, Posts newPost) throws Exception{
        if(classesServices.getAllTeachersByClassId(classId).contains(usersServices.getUserById(userId))){
            Classes classes = classesServices.findById(classId);
            List<Posts> postsInClass = classes.getPosts();

//            Setting the current date to the new Post.
            newPost.setDateAdded(new Date());

//            Setting the list of empty comments on the new post
            List<Comments> comments = new ArrayList<Comments>();
            newPost.setComments(comments);

//            Setting the current user as the owner of the post.
            Users user = usersServices.getUserById(userId);
            newPost.setOwner(user);

//            Setting the users concerned to the new Post.
            List<Users> userConcerned = new ArrayList<>();
            userConcerned.add(user);
            newPost.setUsersConcerning(userConcerned);

//            Setting the current class to the new Post.
            newPost.setClasses(classes);


//            Adding the new post in the db
            postsRepositories.save(newPost);

//            Adding the post to the list of post already in the class
            postsInClass.add(newPost);
            classes.setPosts(postsInClass);

//            Saving changes to the db
            classesServices.updateClass(classes);

        } else{
            throw new Exception("Invalid user accessing the class");
        }
        return ResponseEntity.ok().body(newPost.getId());
    }


    public ResponseEntity addProblemsToPost(long userId,
                                            long classId,
                                            long postId,
                                            JSONArray problemIds) throws JSONException {
        Users user = usersServices.getUserById(userId);
        if(user != null){
            Classes classes = classesServices.findById(classId);
            if(classes != null & classes.getOwnerId() == userId){
                Optional<Posts> post = postsRepositories.findById(postId);
                if(post.isPresent() && post.get().getClasses().getId() == classId){
                    List<Problems> problems = post.get().getProblems();
                    for(int i=0;i<problemIds.length();i++){
                        long problemId = problemIds.getLong(i);
                        Optional<Problems> problem = problemsRepositories.findById(problemId);
                        if(problem.isPresent()){
                            problems.add(problem.get());
                        } else{
                            System.out.println("No such problem");
                        }
                    }

                    post.get().setProblems(problems);
                    postsRepositories.save(post.get());
                    return ResponseEntity.accepted().build();
                } else{
                    return ResponseEntity.badRequest().build();
                }
            } else{
                return ResponseEntity.badRequest().build();
            }
        } else{
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity deleteById(long id) {
        List<Comments> comments = postsRepositories.findById(id).get().getComments();
        for(Comments comment : comments){
            commentsServices.deleteById(comment.getId());
        }
        postsRepositories.deleteById(id);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity addUsersToPost(long ownerId,
                                         long classId,
                                         long postId,
                                         JSONArray userIds) throws JSONException {
        Users owner = usersServices.getUserById(ownerId);
        if(owner != null){
            Classes classes = classesServices.findById(classId);
            if(classes != null & classes.getOwnerId() == ownerId){
                Optional<Posts> post = postsRepositories.findById(postId);
                if(post.isPresent() & post.get().getClasses().getId() == classId){
                    List<Users> users = post.get().getUsersConcerning();
                    for(int i=0;i<userIds.length();i++){
                        long userId = userIds.getLong(i);
                        Users user = usersServices.getUserById(userId);
                        if(user != null &
                                (classes.getTeachers().contains(user) | classes.getStudents().contains(user)) &
                                (!post.get().getUsersConcerning().contains(user))
                            ){
                            users.add(user);
                        } else{
                            System.out.println("Error adding student");
                        }
                    }

                    post.get().setUsersConcerning(users);
                    postsRepositories.save(post.get());
                    return ResponseEntity.accepted().build();
                } else{
                    return ResponseEntity.badRequest().build();
                }
            } else{
                return ResponseEntity.badRequest().build();
            }
        } else{
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity removeUsersFromPost(long ownerId,
                                              long classId,
                                              long postId,
                                              JSONArray userIds) throws JSONException {
        Users owner = usersServices.getUserById(ownerId);
        if(owner != null){
            Classes classes = classesServices.findById(classId);
            if(classes != null & classes.getOwnerId() == ownerId){
                Optional<Posts> post = postsRepositories.findById(postId);
                if(post.isPresent() & post.get().getClasses().getId() == classId){
                    List<Users> users = post.get().getUsersConcerning();
                    for(int i=0;i<userIds.length();i++){
                        long userId = userIds.getLong(i);
                        Users user = usersServices.getUserById(userId);
                        if(
                                user != null &
                                (classes.getTeachers().contains(user) | classes.getStudents().contains(user))
                        ){
                            users.remove(user);
                        } else{
                            System.out.println("Error adding student");
                        }
                    }

                    post.get().setUsersConcerning(users);
                    postsRepositories.save(post.get());
                    return ResponseEntity.accepted().build();
                } else{
                    return ResponseEntity.badRequest().build();
                }
            } else{
                return ResponseEntity.badRequest().build();
            }
        } else{
            return ResponseEntity.badRequest().build();
        }
    }


    /**
     * Use this when you want to get posts concerning the currently logged in user.
     * */
    /*public ResponseEntity findAllPostsByUserIdAndClassId(long userId, long classId){
        Users user = usersServices.getUserById(userId);
        if(user != null){
            Classes classes = classesServices.findById(classId);
            if(classes != null & (classes.getTeachers().contains(user) | classes.getStudents().contains(user))){
                return ResponseEntity.ok().body(postsRepositories.findAllPostsByUserIdAndClassId(userId, classId));
            } else{
                return ResponseEntity.badRequest().build();
            }
        } else{
            return ResponseEntity.badRequest().build();
        }
    }*/

    public ResponseEntity findAllPostsByClassId(long userId, long classId) {
        Users user = usersServices.getUserById(userId);
        if(user != null) {
            Classes classes = classesServices.findById(classId);
            if(classes != null & (classes.getTeachers().contains(user) | classes.getStudents().contains(user))) {
                List<Posts> posts = postsRepositories.findAllPostsByClassId(classId);
                return ResponseEntity.ok().body(posts);
            } else{
                return ResponseEntity.badRequest().build();
            }
        } else{
            return ResponseEntity.badRequest().build();
        }
    }
}
