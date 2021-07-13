package fpt.provipluxurylimited.challengefocus.helpers;

public class ApiClient {
    public static String userProfile = "Users/id1/information";
    public static String feedback = "Feedbacks";
    public static String category = "Categories";

    public static String getUserProfileById(String uid) {
        return String.format(userProfile, uid);
    }
    public static String myDoing = "Users/id1/challenges/doing";
    public static String myDone = "Users/id1/challenges/done";
    public static String myFailed = "Users/id1/challenges/failed";
}
