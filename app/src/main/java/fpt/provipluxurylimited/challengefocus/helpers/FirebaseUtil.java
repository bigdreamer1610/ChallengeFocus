package fpt.provipluxurylimited.challengefocus.helpers;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseUtil {
    public static DatabaseReference mDatabaseReference;
    public static FirebaseUtil shared = new FirebaseUtil();

    //create a private constructor to avoid this class being instantiated
    private FirebaseUtil(){
        if (mDatabaseReference == null) {
            mDatabaseReference = FirebaseDatabase.getInstance(Constants.firebaseURL).getReference();
        }
    }

    public DatabaseReference getReference() {
        return mDatabaseReference;
    }

}
