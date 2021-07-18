package fpt.provipluxurylimited.challengefocus.challenge.detail;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import fpt.provipluxurylimited.challengefocus.helpers.ApiClient;
import fpt.provipluxurylimited.challengefocus.helpers.FirebaseUtil;
import fpt.provipluxurylimited.challengefocus.helpers.Utils;
import fpt.provipluxurylimited.challengefocus.helpers.base.BaseUseCaseDelegate;
import fpt.provipluxurylimited.challengefocus.models.Challenge;
import fpt.provipluxurylimited.challengefocus.models.ToDoItem;

public class DetailChallengeUseCase {
    public interface DetailChallengeUseCaseDelegate extends BaseUseCaseDelegate {
        void onSuccessGetItems(ArrayList<ToDoItem> list);
        void onSuccessAddFirstItem(String challengeId);
        void onSuccessUploadImage(String imageName);
    }

    private DetailChallengeUseCaseDelegate delegate;

    public void setDelegate(DetailChallengeUseCaseDelegate delegate) {
        this.delegate = delegate;
    }

    public void getToDoItemsList(String userId, String challengeId){
        FirebaseUtil.shared.getReference().child(ApiClient.getMyChallenge(userId))
                .child(challengeId)
                .child(ApiClient.items)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        ArrayList<ToDoItem> list = new ArrayList<>();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            ToDoItem item = ds.getValue(ToDoItem.class);
                            item.setId(ds.getKey());
                            item.setExpanded(false);
                            System.out.println(item.toString());
                            list.add(item);
                        }
                        delegate.onSuccessGetItems(list);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        delegate.onFailure(error.getMessage());
                    }
                });
    }

    public void addNewChallenge(String userId, Challenge challenge, ToDoItem firstItem) {
        challenge.setStatus(null);
        String key = FirebaseUtil.shared.getReference().push().getKey();
        FirebaseUtil.shared.getReference().child(ApiClient.getMyChallenge(userId))
                .child(key)
                .setValue(challenge)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        addFirstToDoItem(userId, key, firstItem);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        delegate.onFailure(e.getMessage());
                    }
                });
    }

    private void addFirstToDoItem(String userId, String challengeId, ToDoItem item) {
        FirebaseUtil.shared.getReference().child(ApiClient.getMyChallenge(userId)).child(challengeId).child(ApiClient.items)
                .push()
                .setValue(item)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        delegate.onSuccessAddFirstItem(challengeId);
                    }
                });
    }

    public void addItem(String userId, String id, ToDoItem item) {
        FirebaseUtil.shared.getReference().child(ApiClient.getMyChallenge(userId))
                .child(id)
                .child(ApiClient.items)
                .push()
                .setValue(item);
    }

    public void removeItem(String id) {
        FirebaseUtil.shared.getReference().child(ApiClient.myChallenge).child("fsdffd").child(ApiClient.items)
                .child(id).getRef().removeValue();
    }

    // upload image to storage
    public void uploadImageToStorage(String userId, String challengeId , ToDoItem item, Uri uri) {
        final String randomKey = UUID.randomUUID().toString();
        FirebaseUtil.shared.getStorageReference().child("results/" + randomKey).putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // success
                        // then get downloaded url
                        requestImageUrl(userId, challengeId, randomKey,item);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Log.e("Download failed", "You are shit");
                    }
                });
    }

    private void updateItemWithImage(String userId, String challengeId, String id, ToDoItem item) {
        FirebaseUtil.shared.getReference().child(ApiClient.getMyChallenge(userId))
                .child(challengeId).child(ApiClient.items)
                .child(id)
                .setValue(item);
    }


    private void requestImageUrl(String userId, String challengeId, String imageName, ToDoItem item) {
        FirebaseUtil.shared.getStorageReference().child("results/" + imageName)
                .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.e("link", uri.toString());
                ToDoItem updateItem = new ToDoItem(item.getTitle(), Utils.convertDateToString(new Date()), true, uri.toString());
                updateItemWithImage(userId, challengeId, item.getId(), updateItem);
            }
        });
    }
}

