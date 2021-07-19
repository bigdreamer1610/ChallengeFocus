package fpt.provipluxurylimited.challengefocus.helpers;

public class ApiClient {
    public static String userProfile = "Users/%s/information";
    public static String caption = "Users/%s/information/caption";
    public static String feedback = "Feedbacks";
    public static String category = "Categories";

    public static String pomodoro = "Pomo";
    public static String myChallenge = "Users/%s/challenges";

    public static String items = "items";
    public static String quotes = "Quotes";

    public static String getUserProfileById(String uid) {
        return String.format(userProfile, uid);
    }

    public static String getCaptionById(String uid) {
        return String.format(caption, uid);
    }

    public static String getMyChallenge(String uid) {
        return String.format(myChallenge, uid);
    }



}
