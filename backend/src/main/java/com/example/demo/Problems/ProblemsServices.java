package com.example.demo.Problems;

import com.example.demo.Classes.Classes;
import com.example.demo.Classes.ClassesServices;
import com.example.demo.Solutions.SolutionsServices;
import com.example.demo.TestCases.TestCases;
import com.example.demo.TestCases.TestCasesRepository;
import com.example.demo.TestCases.TestCasesServices;
import com.example.demo.Users.Users;
import com.example.demo.Users.UsersServices;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProblemsServices {

    @Autowired
    private ProblemsRepositories problemsRepositories;

    @Autowired
    private UsersServices usersServices;

    @Autowired
    private TestCasesServices testCasesServices;

    @Autowired
    private SolutionsServices solutionsServices;

    @Autowired
    private TestCasesRepository testCasesRepository;

    @Autowired
    private ClassesServices classesServices;

    public Long addProblem(long userId, ProblemsDTO problemDTO) throws Exception{

        if(usersServices.checkIfUserExists(userId)) {

            Problems problem = new Problems(
                    problemDTO.getStatement(),
                    problemDTO.getTitle(),
                    problemDTO.getSolution().getBytes(),
                    problemDTO.getScore(),
                    problemDTO.getNumOfTestCases(),
                    problemDTO.getCategory(),
                    problemDTO.getDifficulty()
            );
            problem.setProblemDate(new Date());
            problem.setAuthor(usersServices.getUserById(userId));

            problemsRepositories.save(problem);
            return problem.getId();
        }else{
            throw new Exception("User does not exists...");
        }
    }

    public Problems getProblemById(Long id) throws Exception{
        Problems problem;
        if(checkIfProblemExists(id)){
            problem = problemsRepositories.findById(id).get();
        } else{
            throw new Exception("Problems does not exists...");
        }
        return problem;
    }

    private boolean checkIfProblemExists(Long id) {
        if(problemsRepositories.findById(id).isPresent()) {
            return true;
        }
        else{
            return false;
        }
    }

    public List<Problems> getAllProblems() {
        List<Problems> list = new ArrayList<>();
        problemsRepositories.findAll().forEach(list::add);
        return list;
    }

    public List<Problems> getProblemByCategory(String problemCategory) {
        List<Problems> list = new ArrayList<>();
        problemsRepositories.findByCategoryIgnoreCase(problemCategory).forEach(list::add);
        return list;
    }

    public List<Problems> getProblemByTitle(String problemTitle){
        List<Problems> list = new ArrayList<>();
        problemsRepositories.findByTitleIgnoreCase(problemTitle).forEach(list::add);
        return list;
    }

    public List<Problems> getProblemByDifficulty(String problemDifficulty){
        List<Problems> list = new ArrayList<>();
        problemsRepositories.findByDifficultyIgnoreCase(problemDifficulty).forEach(list::add);
        return list;
    }

    public ResponseEntity deleteProblemByProblemId(Long userId, Long problemId){
        Users user = usersServices.getUserById(userId);
        if(user != null) {
            Optional<Problems> problem = problemsRepositories.findById(problemId);
            if (problem.isPresent() & problem.get().getAuthor().equals(user)) {
                List<Long> listOfTestCaseIds = new ArrayList<>();
                problem.get().getTestCases().forEach(testCase -> {
                    listOfTestCaseIds.add(testCase.getId());
                });
                for(Long testCaseId : listOfTestCaseIds){
                    try {
                        testCasesServices.deleteTestCases(userId, problemId, testCaseId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                try {
                    solutionsServices.deleteSolutionsByProblemId(problemId);
                    problemsRepositories.deleteById(problemId);
                    return ResponseEntity.ok().build();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        } else{
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity updateProblemByProblemId(Long userId, Long problemId, ProblemsDTO updatedProblemDTO){
        Users user = usersServices.getUserById(userId);
        if(user != null){
            Optional<Problems> problem = problemsRepositories.findById(problemId);
            if(problem.isPresent() & problem.get().getAuthor().equals(user)){
                Problems updatedProblem = null;
                try {
                    updatedProblem = new Problems(
                            updatedProblemDTO.getStatement(),
                            updatedProblemDTO.getTitle(),
                            updatedProblemDTO.getSolution().getBytes(),
                            updatedProblemDTO.getScore(),
                            updatedProblemDTO.getNumOfTestCases(),
                            updatedProblemDTO.getCategory(),
                            updatedProblemDTO.getDifficulty()
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
                assert updatedProblem != null;
                updatedProblem.setId(problemId);
                updatedProblem.setProblemDate(new Date());
                updatedProblem.setAuthor(user);
                problemsRepositories.save(updatedProblem);
                return ResponseEntity.ok().build();
            } else{
                return ResponseEntity.badRequest().build();
            }
        } else{
            return ResponseEntity.notFound().build();
        }
    }


    public ResponseEntity findAllProblemsAssignedInPost(long userId, long classId, long postId) {
        Users user = usersServices.getUserById(userId);
        if(user != null){
            Optional<Classes> classes = classesServices.getClassById(classId);
            if(classes.isPresent() && (classes.get().getTeachers().contains(user) | classes.get().getStudents().contains(user))){
                return ResponseEntity.ok().body(problemsRepositories.findAllProblemsAssignedInPost(postId));
            } else{
                return ResponseEntity.badRequest().build();
            }
        } else{
            return ResponseEntity.badRequest().build();
        }
    }
}
