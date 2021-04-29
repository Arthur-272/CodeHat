package com.example.demo.Solutions;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SolutionsRepositories extends CrudRepository <Solutions , Long>{

    List<Solutions> findAllByProblems_Id(long problemId);

    List<Solutions> findAllByUsersId(long userId);

    @Modifying
    @Query(value = "delete from Solutions where Solutions.problems_id=:problemId", nativeQuery = true)
    void deleteSolutionsByProblemId(Long problemId);


    @Query(value = "select *from Solutions where Solutions.users_id=:userId and Solutions.problems_id=:problemId", nativeQuery = true)
    List<Solutions> findAllSolutionsByUserIdAndProblemId(long userId, long problemId);
}
