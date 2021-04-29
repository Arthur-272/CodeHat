package com.example.demo.Problems;

import com.example.demo.Problems.Problems;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemsRepositories extends CrudRepository <Problems, Long> {
    List<Problems> findByTitleIgnoreCase(String title);

    List<Problems> findByCategoryIgnoreCase(String category);

    List<Problems> findByDifficultyIgnoreCase(String difficulty);

    @Modifying
//    @Query(value = "delete from Problems p  where p.author_id = :id", nativeQuery = true)
    @Query(value = "delete from Problems p where p.author.id = (select u.id from Users u where u.firstName = :id)")
    void deleteProblemByAuthorId(@Param("id") String authorId);


    @Query(value = "select *from problems where problems.id in " +
            "(select posts_problems.problems_id from posts_problems where posts_problems.posts_id=:postId)", nativeQuery = true)
    List<Problems> findAllProblemsAssignedInPost(long postId);
}
// /user/98/classes