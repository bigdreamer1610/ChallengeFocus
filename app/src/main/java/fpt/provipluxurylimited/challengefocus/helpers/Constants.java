package fpt.provipluxurylimited.challengefocus.helpers;

import android.content.SharedPreferences;

import java.util.Date;

import fpt.provipluxurylimited.challengefocus.models.ChallengeStatus;

public class Constants {
    public static final String pref = "MyPref";
    public static final String userId = "id";
    public static final String userName = "name";
    public static final String imageUrl = "url";
    public static final String DOING = "đang làm";
    public static final String DONE = "hoàn thành";
    public static final String FAIL = "thất bại";
    public static final String doing = "doing";
    public static final String done = "done";
    public static final String failed = "failed";
    public static final String firebaseURL = "https://fptu-challangefocus-default-rtdb.asia-southeast1.firebasedatabase.app/";
    public static final int PRIVATE_MODE = 0;
    public static final String name = "name";
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


    public static class DialogConstants {
        public static String confirmMessage = "Bạn muốn bắt đầu thử thách này chứ?";
        public static String optionAgree = "Đúng vậy";
        public static String optionReject = "Chưa, tôi chưa sẵn sàng";
        public static String optionUpload = "Tải ảnh lên";
        public static String optionCancel = "Huỷ";
        public static String warningAddItem = "Bạn chưa thêm item nào. Ở lại thêm item tiếp nhé?";
        public static String optionAgreeAddItem = "OK tôi sẽ thêm item";
        public static String optionRejectAddITem = "Tôi muốn quay lại";
        public static String restartMessage = "Bạn có muốn bắt đầu lại thử thách này?";
        public static String optionAgreeRestart = "Có, tôi muốn bắt đầu lại";
        public static String optionRejectRestart = "Tôi sẽ xem xét lại";
        public static String feedback = "Feedback đã được gửi lên thành công. Cảm ơn bạn nhé ❤️";
        public static String deadline = "Bạn cần đặt deadline trước khi thêm item";
    }
}
