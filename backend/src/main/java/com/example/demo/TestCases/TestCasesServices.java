package com.example.demo.TestCases;

import com.example.demo.Problems.Problems;
import com.example.demo.Problems.ProblemsRepositories;
import com.example.demo.Problems.ProblemsServices;
import com.example.demo.Users.Users;
import com.example.demo.Users.UsersServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TestCasesServices {

    @Autowired
    private UsersServices usersServices;

    @Autowired
    private TestCasesRepository testCasesRepository;

    @Autowired
    private ProblemsServices problemsServices;

    @Autowired
    private ProblemsRepositories problemsRepositories;

    public ResponseEntity addTestCases(Long userId, Long problemId, List<TestCases> testCases) throws Exception {
        Users user = usersServices.getUserById(userId);
        if (user != null) {
            Problems problem = problemsServices.getProblemById(problemId);
            if (problem != null & problem.getAuthor().equals(user)) {
                if (problem.getAuthor().equals(user)) {
                    List<TestCases> list = problem.getTestCases();

                    for (TestCases testCase : testCases) {
                        testCase.setProblem(problem);
                        testCasesRepository.save(testCase);
                    }
                    list.addAll(testCases);
                    problem.setTestCases(list);
                    problem.setNumOfTestCases(list.size());

                    problemsRepositories.save(problem);
                    return ResponseEntity.accepted().build();
                } else {
                    return ResponseEntity.badRequest().build();
                }
            } else {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity deleteTestCases(Long userId, Long problemId, Long testCaseId) throws Exception {
        System.out.println();
        Users user = usersServices.getUserById(userId);
        if (user != null) {
            Problems problem = problemsServices.getProblemById(problemId);
            if (problem != null & problem.getAuthor().equals(user)) {
                List<Long> list = new ArrayList<>();
                List<TestCases> testCases = problem.getTestCases();
                testCases.forEach(testCase -> {
                    list.add(testCase.getId());
                });
                Optional<TestCases> testCase = testCasesRepository.findById(testCaseId);
                if (list.contains(testCaseId) && testCase.isPresent()) {
                    testCases.remove(testCase.get());
                    problem.setTestCases(testCases);
                    problem.setNumOfTestCases(problem.getNumOfTestCases() - 1);
                    problemsRepositories.save(problem);
                    testCasesRepository.deleteById(testCaseId);
                } else {
                    System.out.println("No Test Case Found");
                }
            }
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    public ResponseEntity updateTestCases(Long userId, Long problemId, Long testCaseId, TestCases updatedTestCase) throws Exception {
        Users user = usersServices.getUserById(userId);
        if (user != null) {
            Problems problem = problemsServices.getProblemById(problemId);
            if (problem != null & problem.getAuthor().equals(user)) {
                List<TestCases> list = problem.getTestCases();
                for (int i = 0; i < list.size(); i++) {
                    TestCases testCase = list.get(i);
                    if (testCase.getId() == testCaseId) {
                        list.remove(testCase);
                        list.add(updatedTestCase);
                        problem.setTestCases(list);
                        updatedTestCase.setId(testCaseId);
                        updatedTestCase.setProblem(problem);
                        testCasesRepository.save(updatedTestCase);
                        problemsRepositories.save(problem);
                        break;
                    }
                }
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public List<TestCases> getTestCases(Long userId, Long problemId) throws Exception {
        Users user = usersServices.getUserById(userId);
        if (user != null) {
            Problems problem = problemsServices.getProblemById(problemId);
            if (problem != null & problem.getAuthor().equals(user)) {
                List<TestCases> testCases = problem.getTestCases();
                return testCases;
            } else {
                throw new Exception("Error");
            }
        } else {
            throw new Exception("User not found");
        }
    }

    public List<TestCases> getTestCases(Long problemId) throws Exception {


            Problems problem = problemsServices.getProblemById(problemId);
            List<TestCases> testCasesList=new ArrayList<>();
            if (problem != null ) {
                List<TestCases> testCases = problem.getTestCases();
                if(testCases.size()==1)
                    return testCases;
                else{
                    for(int i=0;i<2;i++){
                        testCasesList.add(testCases.get(i));
                    }
                    return testCasesList;
                }
            } else {
                throw new Exception("Error");
            }

    }
}
