package com.example.demo.Solutions;

import com.example.demo.Problems.Problems;
import com.example.demo.Problems.ProblemsRepositories;
import com.example.demo.TestCases.TestCases;
import com.example.demo.Users.Users;
import com.example.demo.Users.UsersRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.example.demo.Others.Constants.OS;
import static com.example.demo.Others.Constants.SUBMISSIONS_FOLDER;
import static org.junit.Assert.assertEquals;

@Service
public class SolutionsServices {

    @Autowired
    private SolutionsRepositories solutionsRepositories;

    @Autowired
    private ProblemsRepositories problemsRepositories;

    @Autowired
    private UsersRepositories usersRepositories;

    public ResponseEntity addSolution(long userId, long problemId, MultipartFile file, String language) throws Exception {

        Optional<Users> user = usersRepositories.findById(userId);
        boolean flag = false;
        if (user.isPresent()) {
            Optional<Problems> problem = problemsRepositories.findById(problemId);
            if (problem.isPresent()) {

                List<Solutions> solutions = solutionsRepositories.findAllSolutionsByUserIdAndProblemId(userId, problemId);
                int maxTestCasesPassed = 0;

                if (solutions != null) {
                    for (Solutions solution : solutions) {
                        if (solution.getTestCasesPassed() > maxTestCasesPassed)
                            maxTestCasesPassed = solution.getTestCasesPassed();
                    }
                }


                if (maxTestCasesPassed == problem.get().getNumOfTestCases()) {
//                    return ResponseEntity.badRequest().build();
                    flag = true;
                }



                List<TestCases> testCases = problem.get().getTestCases();
                if (testCases == null)
                    return ResponseEntity.noContent().build();

                File userSolution = new File(SUBMISSIONS_FOLDER + file.getOriginalFilename());
                file.transferTo(userSolution);
                int totalTestCases = problem.get().getNumOfTestCases();
                int testCasesPassed = 0;
                switch (language) {
                    case "python3":
                        testCasesPassed = evaluatePython(testCases, userSolution, 3);
                        break;
                    case "python2":
                        testCasesPassed = evaluatePython(testCases, userSolution, 2);
                        break;
                    case "java":
                        testCasesPassed = evaluateJava(testCases, userSolution);
                        break;
                    case "C":
                        testCasesPassed = evaluateC(testCases, userSolution);
                        break;
                    case "CPP":
                        testCasesPassed = evaluateCPP(testCases, userSolution);
                        break;

                }

                /**
                 * Subtracting the score that the user scored in his previous solution and if it's user's
                 * first solution then it subtract 0, affecting nothing.
                 * */
                int testCasesFailed = totalTestCases - testCasesPassed;
                double scorePerTestCase = ((problem.get().getScore()) / totalTestCases);
                double usersPreviousScore = maxTestCasesPassed * scorePerTestCase;
                double usersCurrentScore = testCasesPassed * scorePerTestCase;
                double score = 0;
                if (usersCurrentScore > usersPreviousScore && !flag) {
                    score = usersCurrentScore;
                    user.get().setScore(user.get().getScore() + (score - usersPreviousScore));
                }
                else if(!flag) {
                    score = usersPreviousScore;
                    user.get().setScore(user.get().getScore() + (score - usersPreviousScore));
                }


                Solutions solution = new Solutions(
                        file.getBytes(),
                        testCasesPassed,
                        testCasesFailed,
                        usersCurrentScore,
                        language,
                        problem.get(),
                        user.get()
                );
                solution.setSolutionSubmittedOn(new Date());
                usersRepositories.save(user.get());
                solutionsRepositories.save(solution);

                return ResponseEntity.accepted().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public int compileAndRun(String[] cmd, String output) throws Exception{
        Process process;
        int count = 0;
        try {
            process = new ProcessBuilder(cmd).start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String userOutput = "";
            String line;
            while ((line = reader.readLine()) != null) {
                userOutput += line;
            }

            reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            try {
//                    System.out.println(userOutput + " " + output);
                assertEquals(userOutput, output);
                count++;
            } catch (AssertionError ae) {
                System.out.println("Failed");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    private int evaluateC(List<TestCases> testCases, File file) throws Exception{
        int count = 0;
        String[] cmd = new String[3];

        if(OS.contains("windows")) {
            cmd[0] = "cmd";
            cmd[1] = "/c";
            cmd[2] = "cd " + SUBMISSIONS_FOLDER + " && gcc " + file.getName();
        }

        else if(OS.contains("linux") | OS.contains("mac")) {
            cmd[0] = "/bin/bash";
            cmd[1] = "-c";
            cmd[2] = "cd " + SUBMISSIONS_FOLDER + " && gcc " + file.getName();
        }

        Process process = Runtime.getRuntime().exec(cmd);
        process.waitFor();
        for (TestCases testCase : testCases) {
            String input = testCase.getInput();
            String output = testCase.getOutput();

            if(OS.contains("windows")) {
                cmd[0] = "cmd";
                cmd[1] = "/c";
                cmd[2] = "cd " + SUBMISSIONS_FOLDER + " && a.exe " + input;
            }
            else if(OS.contains("linux") | OS.contains("mac")) {
                cmd[0] = "/bin/bash";
                cmd[1] = "-c";
                cmd[2] = "cd " + SUBMISSIONS_FOLDER + " && ./a.out " + input;
            }
            count += compileAndRun(cmd, output);
        }

        file.delete();
        if(OS.contains("windows"))
            file = new File(SUBMISSIONS_FOLDER + "a.exe");
        else if(OS.contains("linux") | OS.contains("mac"))
            file = new File(SUBMISSIONS_FOLDER + "a.out");
        file.delete();

        return count;
    }

    private int evaluateCPP(List<TestCases> testCases, File file) throws Exception{
        System.out.println("In C++");
        int count = 0;
        String[] cmd = new String[3];
        if(OS.contains("windows")) {
            cmd[0] = "cmd";
            cmd[1] = "/c";
            cmd[2] = "cd " + SUBMISSIONS_FOLDER + " && g++ -std=c++11 " + file.getName();
        }
        else if(OS.contains("linux") | OS.contains("mac")) {
            cmd[0] = "/bin/bash";
            cmd[1] = "-c";
            cmd[2] = "cd " + SUBMISSIONS_FOLDER + " && g++ -std=c++11 " + file.getName();
        }
        System.out.println(cmd);
        Process process = Runtime.getRuntime().exec(cmd);
        process.waitFor();
        for (TestCases testCase : testCases) {
            String input = testCase.getInput();
            String output = testCase.getOutput();

            if(OS.contains("windows")) {
                cmd[0] = "cmd";
                cmd[1] = "/c";
                cmd[2] = "cd " + SUBMISSIONS_FOLDER + " && a.exe " + input;
            }
            else if(OS.contains("linux") | OS.contains("mac")) {
                cmd[0] = "cmd";
                cmd[1] = "/c";
                cmd[2] = "cd " + SUBMISSIONS_FOLDER + " && ./a.out " + input;
            }
            System.out.println(cmd);
            count += compileAndRun(cmd, output);
        }

        file.delete();
        if(OS.contains("windows"))
            file = new File(SUBMISSIONS_FOLDER + "a.exe");
        else if(OS.contains("linux") | OS.contains("mac"))
            file = new File(SUBMISSIONS_FOLDER + "a.out");
        file.delete();

        return count;
    }

    public int evaluateJava(List<TestCases> testCases, File file) throws Exception {
        int count = 0;
        if(OS.contains("windows")) {
            String[] cmd = new String[3];
            cmd[0] = "cmd";
            cmd[1] = "/c";
            cmd[2] = "javac " + file.getAbsolutePath();
            Process process = new ProcessBuilder(cmd).start();
            process.waitFor();
            for (TestCases testCase : testCases) {
                String input = testCase.getInput();
                String output = testCase.getOutput();

                cmd[0] = "cmd";
                cmd[1] = "/c";
                cmd[2] = "cd " + SUBMISSIONS_FOLDER + " && java " + file.getName().split("\\.")[0] + " " + input;
                count += compileAndRun(cmd, output);
            }
        }
        else if(OS.contains("linux") | OS.contains("mac")){
            String[] cmd = new String[3];
            cmd[0] = "/bin/bash";
            cmd[1] = "-c";
            cmd[2] = "javac " + file.getAbsolutePath();
            Process process = new ProcessBuilder(cmd).start();
            process.waitFor();
            for(TestCases testCase : testCases) {
                String input = testCase.getInput();
                String output = testCase.getOutput();

                cmd[0] = "/bin/bash";
                cmd[1] = "-c";
                cmd[2] = "cd " + SUBMISSIONS_FOLDER + " && java " + file.getName().split("\\.")[0] + " " + input;
                count += compileAndRun(cmd, output);
            }
        }

        file.delete();
        file = new File(SUBMISSIONS_FOLDER + file.getName().split("\\.")[0] + ".class");
        file.delete();
        return count;
    }

    public int evaluatePython(List<TestCases> testCases, File file, int pythonVersion) throws Exception {
        int count = 0;
        String[] cmd = new String[3];
        for (TestCases testCase : testCases) {
            String input = testCase.getInput();
            String output = testCase.getOutput();
            if(OS.contains("windows")) {
                if (pythonVersion == 2) {
                    cmd[0] = "cmd";
                    cmd[1] = "/c";
                    cmd[2] = "cd " + SUBMISSIONS_FOLDER + " && python2 " + file.getName() + " " + input;
                }
                else {
                    cmd[0] = "cmd";
                    cmd[1] = "/c";
                    cmd[2] = "cd " + SUBMISSIONS_FOLDER + " && python " + file.getName() + " " + input;
                }
                count += compileAndRun(cmd, output);
            }
            else if(OS.contains("linux") | OS.contains("mac")){
                if (pythonVersion == 2) {
                    cmd[0] = "/bin/bash";
                    cmd[1] = "-c";
                    cmd[2] = "cd " + SUBMISSIONS_FOLDER + " && python2 " + file.getName() + " " + input;
                }
                else {
                    cmd[0] = "/bin/bash";
                    cmd[1] = "-c";
                    cmd[2] = "cd " + SUBMISSIONS_FOLDER + " && python " + file.getName() + " " + input;
                }
                count += compileAndRun(cmd, output);
            }
        }
        file.delete();
        return count;
    }

    public List<Solutions> getAllSolutions() {
        List<Solutions> list = new ArrayList<Solutions>();
        solutionsRepositories.findAll().forEach(list::add);
        return list;
    }

    public List<Solutions> getSolutionsByProblemId(Long problemId) {
        return solutionsRepositories.findAllByProblems_Id(problemId);
    }

    public List<Solutions> getSolutionsByUserId(Long userId) {
        return solutionsRepositories.findAllByUsersId(userId);
    }

    public void deleteSolutionsByProblemId(Long problemId) {
        solutionsRepositories.deleteSolutionsByProblemId(problemId);
    }

    public List<Solutions> getSolutionsByUserIdAndProblemId(Long userId, Long problemId){
        return solutionsRepositories.findAllSolutionsByUserIdAndProblemId(userId, problemId);
    }

}
