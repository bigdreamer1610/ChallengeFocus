package fpt.provipluxurylimited.challengefocus.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FirebaseUtil {
    public static FirebaseDatabase mFirebaseDatabase;
    public static DatabaseReference mDatabaseReference;
    private static FirebaseUtil firebaseUtil;

    //create a private constructor to avoid this class being instantiated
    private FirebaseUtil(){}


    public static void openFbReference(String ref) {
        if (firebaseUtil == null) {
            firebaseUtil = new FirebaseUtil();
            mFirebaseDatabase = FirebaseDatabase.getInstance("https://fptu-challangefocus-default-rtdb.asia-southeast1.firebasedatabase.app/");
        }
        mDatabaseReference = mFirebaseDatabase.getReference().child(ref);
    }

}
