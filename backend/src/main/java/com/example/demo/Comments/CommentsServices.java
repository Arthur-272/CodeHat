package com.example.demo.Comments;

import com.example.demo.Classes.ClassesRepositories;
import com.example.demo.Posts.Posts;
import com.example.demo.Posts.PostsRepositories;
import com.example.demo.Posts.PostsServices;
import com.example.demo.Users.Users;
import com.example.demo.Users.UsersRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentsServices {

    @Autowired
    private CommentsRepositories commentsRepositories;

    @Autowired
    private ClassesRepositories classesRepositories;

    @Autowired
    private UsersRepositories usersRepositories;

    @Autowired
    private PostsServices postsServices;

    @Autowired
    private PostsRepositories postsRepositories;

    public void addComment(long userId, long classId, long postId, Comments comment)throws Exception{

        if(classesRepositories.findById(classId).get().getStudents().contains(usersRepositories.findById(userId).get()) ||
            classesRepositories.findById(classId).get().getTeachers().contains(usersRepositories.findById(userId).get())) {

//        Getting the user who is adding the comment
            Users user = usersRepositories.findById(userId).get();

//        Adding the current date
            comment.setDateAdded(new Date());

//        Adding the user to comment
            comment.setUser(user);

//        Getting the post to which the comment is being added
            Posts post = postsServices.findPostById(postId).get();

//        Getting the list of comments already present in the posts
            List<Comments> commentsInPost = post.getComments();

//        Adding the new comment to the list
            commentsInPost.add(comment);

//        Saving the new comment to the db
            commentsRepositories.save(comment);

//        Updating the list of comments in the post
            post.setComments(commentsInPost);

//        Saving the changes to db
            postsRepositories.save(post);
        } else{
            throw new Exception("User not a member of the class");
        }
    }

    public List<Comments> showAllComments(long userId, long classId, long postId) throws Exception{
        List<Comments> list;
        if(classesRepositories.findById(classId).get().getStudents().contains(usersRepositories.findById(userId).get()) ||
                classesRepositories.findById(classId).get().getTeachers().contains(usersRepositories.findById(userId).get())) {
            list = postsServices.findPostById(postId).get().getComments();
        } else{
            throw new Exception("User not a member of the class");
        }
        return list;
    }
    public void deleteById(long id) {
        commentsRepositories.deleteById(id);
    }
}
