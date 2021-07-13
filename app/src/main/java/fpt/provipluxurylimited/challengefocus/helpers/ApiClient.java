package fpt.provipluxurylimited.challengefocus.helpers;

public class ApiClient {
    public static String userProfile = "Users/%s/information";
    public static String feedback = "Feedbacks";
    public static String category = "Categories";

    public static String getUserProfileById(String uid) {
        return String.format(userProfile, uid);
    }
}
