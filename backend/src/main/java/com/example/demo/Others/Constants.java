package com.example.demo.Others;

public class Constants {
    public final static String SUBMISSIONS_FOLDER;
    public final static String PROJECT_DIRECTORY;
    public final static String OS;
    static{
        PROJECT_DIRECTORY = System.getProperty("user.dir");
        SUBMISSIONS_FOLDER = PROJECT_DIRECTORY + "/src/main/resources/Submissions/";
        OS = System.getProperty("os.name").toLowerCase();
    }
}
