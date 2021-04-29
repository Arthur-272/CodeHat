package com.example.demo.TestCases;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class TestCasesController {

    @Autowired
    private TestCasesServices testCasesServices;

    @PostMapping("/user/{userId}/problem/{problemId}/addTestCases")
    public ResponseEntity addTestCases(@RequestBody List<TestCases> testCases,
                                       @PathVariable Long userId,
                                       @PathVariable Long problemId){
        try{
            return testCasesServices.addTestCases(userId, problemId, testCases);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/user/{userId}/problem/{problemId}/deleteTestCases")
    public ResponseEntity deleteTestCases(@RequestParam Long testCaseId,
                                          @PathVariable Long userId,
                                          @PathVariable Long problemId){
        try{
            return testCasesServices.deleteTestCases(userId, problemId, testCaseId);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/user/{userId}/problem/{problemId}/updateTestCase/{testCaseId}")
    public ResponseEntity updateTestCases(@RequestBody TestCases testCase,
                                          @PathVariable Long userId,
                                          @PathVariable Long problemId,
                                          @PathVariable Long testCaseId){
        try{
            return testCasesServices.updateTestCases(userId, problemId, testCaseId, testCase);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/user/{userId}/problem/{problemId}/testCases")
    public List<TestCases> getTestCases(@PathVariable Long userId,
                                        @PathVariable Long problemId) throws Exception{
        return testCasesServices.getTestCases(userId, problemId);
    }

    @GetMapping("problem/{problemId}/testCases")
    public List<TestCases> getTestCases(@PathVariable Long problemId) throws Exception{
        return testCasesServices.getTestCases(problemId);
    }
}
