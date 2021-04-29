package com.example.demo.TestCases;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestCasesRepository extends CrudRepository<TestCases, Long> {

    @Modifying
    @Query(value = "delete from Test_Cases where Test_Cases.problem_id=:problemId", nativeQuery = true)
    void deleteByProblemId(Long problemId);
}
