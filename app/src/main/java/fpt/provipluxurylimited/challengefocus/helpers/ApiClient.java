package fpt.provipluxurylimited.challengefocus.helpers;

public class ApiClient {
    public static String userProfile = "Users/%s/information";
    public static String caption = "Users/%s/information/caption";
    public static String feedback = "Feedbacks";
    public static String category = "Categories";

    public static String getUserProfileById(String uid) {
        return String.format(userProfile, uid);
    }

    public static String getCaptionById(String uid) {
        return String.format(caption, uid);
    }
    public static String myDoing = "Users/id1/challenges/doing";
    public static String myDone = "Users/id1/challenges/done";
    public static String myFailed = "Users/id1/challenges/failed";
}
