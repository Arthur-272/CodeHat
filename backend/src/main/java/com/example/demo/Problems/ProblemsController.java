package com.example.demo.Problems;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class ProblemsController {

    @Autowired
    private ProblemsServices problemsServices;

    @PostMapping("/user/{userId}/addProblems")
    public Long addProblems(@PathVariable long userId,
                            @ModelAttribute ProblemsDTO problem) throws Exception{
        return problemsServices.addProblem(userId, problem);
    }

    @GetMapping("/problems/id/{id}")
    public Problems showProblemById(@PathVariable Long id) throws Exception{
        return problemsServices.getProblemById(id);
    }

    @GetMapping("/problems")
    public List<Problems> showAllProblems(){
        return problemsServices.getAllProblems();
    }

    @GetMapping("/problems/category/{problemCategory}")
    public List<Problems> showProblemsByCategory(@PathVariable String problemCategory){
        return problemsServices.getProblemByCategory(problemCategory);
    }

    @GetMapping("/problems/difficulty/{problemDifficulty}")
    public List<Problems> showProblemsByDifficulty(@PathVariable String problemDifficulty){
        return problemsServices.getProblemByDifficulty(problemDifficulty);
    }

    @GetMapping("/problems/{problemTitle}")
    public List<Problems> showProblemsByTitle(@PathVariable String problemTitle){
        return problemsServices.getProblemByTitle(problemTitle);
    }

    @DeleteMapping("/user/{userId}/problem/{problemId}")
    public ResponseEntity deleteProblem(@PathVariable Long userId,
                                        @PathVariable Long problemId){
        try{
            return problemsServices.deleteProblemByProblemId(userId, problemId);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/user/{userId}/problem/{problemId}")
    public ResponseEntity updateProblemByProblemId(@ModelAttribute ProblemsDTO problemsDTO,
                                                   @PathVariable Long userId,
                                                   @PathVariable Long problemId){
        return problemsServices.updateProblemByProblemId(userId, problemId, problemsDTO);
    }

    @GetMapping("/user/{userId}/class/{classId}/post/{postId}/problems")
    public ResponseEntity findAllProblemsAssignedInPost(@PathVariable long userId,
                                                        @PathVariable long classId,
                                                        @PathVariable long postId){
        return problemsServices.findAllProblemsAssignedInPost(userId, classId, postId);
    }

}
