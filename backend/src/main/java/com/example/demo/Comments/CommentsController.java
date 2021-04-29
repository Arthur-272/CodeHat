package com.example.demo.Comments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class CommentsController {

    @Autowired
    private CommentsServices commentsServices;

    @RequestMapping(method = RequestMethod.POST, value="/user/{userId}/classes/{classId}/posts/{postId}/addComment")
    public void addComment(@PathVariable long userId, @PathVariable long classId, @PathVariable long postId, @RequestBody Comments comment) throws Exception{
        commentsServices.addComment(userId, classId, postId, comment);
    }

    @RequestMapping(value="/user/{userId}/classes/{classId}/posts/{postId}/comments")
    public List<Comments> showAllComments(@PathVariable long userId, @PathVariable long classId, @PathVariable long postId) throws Exception{
        return commentsServices.showAllComments(userId, classId, postId);
    }

}
