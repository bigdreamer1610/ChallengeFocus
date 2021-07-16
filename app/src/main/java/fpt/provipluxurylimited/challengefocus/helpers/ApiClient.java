package fpt.provipluxurylimited.challengefocus.helpers;

public class ApiClient {
    public static String userProfile = "Users/id1/information";
    public static String caption = "Users/id1/information/caption";
    public static String feedback = "Feedbacks";
    public static String category = "Categories";
    public static String myChallenge = "Users/id1/challenges";
    public static String items = "items";

    public static String getUserProfileById(String uid) {
        return String.format(userProfile, uid);
    }

    public static String getCaptionById(String uid) {
        return String.format(caption, uid);
    }

}
