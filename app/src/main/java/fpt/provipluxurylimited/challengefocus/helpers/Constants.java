package fpt.provipluxurylimited.challengefocus.helpers;

import fpt.provipluxurylimited.challengefocus.models.ChallengeStatus;

public class Constants {
    public static final String DOING = "đang làm";
    public static final String DONE = "hoàn thành";
    public static final String FAIL = "thất bại";
    public static final String doing = "doing";
    public static final String done = "done";
    public static final String failed = "failed";
    public static final String firebaseURL = "https://fptu-challangefocus-default-rtdb.asia-southeast1.firebasedatabase.app/";

    public static final String pref = "pref";
    public static final int PRIVATE_MODE = 0;

    public static final String userId = "uid";
    public static final String name = "name";
    public static final String imageUrl = "imageUrl";
    public static final String caption = "caption";

    public static ChallengeStatus getStatus(String statusString) {
        ChallengeStatus status = ChallengeStatus.doing;
        switch (statusString) {
            case "done":
                status = ChallengeStatus.done;
                break;
            case "doing":
                status = ChallengeStatus.doing;
                break;
            case "failed":
                status = ChallengeStatus.failed;
                break;
        }
        return status;
    }
}
