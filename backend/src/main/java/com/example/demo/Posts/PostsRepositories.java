package com.example.demo.Posts;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PostsRepositories extends CrudRepository<Posts, Long> {

    /**
     * Use when you want to get posts concerning the currently logged in user.
     * */
    @Query(value = "select *from Posts where Posts.classes_id=:classId and Posts.id in (select posts_users_concerning.posts_id from posts_users_concerning where posts_users_concerning.users_concerning_id=:userId)", nativeQuery = true)
    List<Posts> findAllPostsByUserIdAndClassId(long userId, long classId);

    /*@Modifying
    @Transactional
    @Query(value = "delete from Posts where Posts.id=:postId", nativeQuery = true)
    void deletePost(long postId);

    @Modifying
    @Transactional
    @Query(value = "delete from posts_comments where posts_comments.posts_id=:postId", nativeQuery = true)
    void deletePosts_Comments(long postId);

    @Modifying
    @Transactional
    @Query(value = "delete from posts_problems where posts_problems.posts_id=:postId", nativeQuery = true)
    void deletePosts_Problems(long postId);

    @Modifying
    @Transactional
    @Query(value = "delete from classes_posts where classes_posts.posts_id=:postId", nativeQuery = true)
    void deleteClasses_Posts(long postId);

    @Modifying
    @Transactional
    @Query(value = "delete from posts_users_concerning where posts_users_concerning.posts_id=:postId", nativeQuery = true)
    void deletePosts_Concerning_Users(long postId);*/

    @Query(value = "select *from posts where posts.classes_id=:classId", nativeQuery = true)
    List<Posts> findAllPostsByClassId(long classId);
}
