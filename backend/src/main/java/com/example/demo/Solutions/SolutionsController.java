package com.example.demo.Solutions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
public class SolutionsController {

    @Autowired
    private SolutionsServices solutionsServices;

    @RequestMapping(value = "user/{userId}/problems/{problemId}/addSolution")
    public void addSolution(@RequestBody MultipartFile solution,
                            @RequestParam String language,
                            @PathVariable long userId,
                            @PathVariable long problemId) throws Exception {
        solutionsServices.addSolution(userId, problemId, solution, language);
    }

    @RequestMapping(value="/solutions/view")
    public List<Solutions> getAllSolutions(){
        return solutionsServices.getAllSolutions();
    }

    @RequestMapping(value = "/problem/{problemId}/solutions")
    public List<Solutions> getSolutionsByProblemId(@PathVariable Long problemId){
        return solutionsServices.getSolutionsByProblemId(problemId);
    }

    @RequestMapping(value = "/user/{userId}/Solutions")
    public List<Solutions> getSolutionsByUserId(@PathVariable Long userId){
        return solutionsServices.getSolutionsByUserId(userId);
    }

    /*@PutMapping("/user/{userId}/problems/{problemId}/solution")
    public ResponseEntity updateSolution(@PathVariable Long userId,
                                         @PathVariable Long problemId,
                                         @RequestParam Long solutionId,
                                         @RequestBody MultipartFile solution){
        try {
            return solutionsServices.updateSolution(userId, problemId, solutionId, solution);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }*/

    @GetMapping("/user/{userId}/problem/{problemId}/viewSolutions")
    public List<Solutions> getSolutionsByUserIdAndProblemId(@PathVariable Long userId, @PathVariable Long problemId){
        return solutionsServices.getSolutionsByUserIdAndProblemId(userId, problemId);
    }
}
