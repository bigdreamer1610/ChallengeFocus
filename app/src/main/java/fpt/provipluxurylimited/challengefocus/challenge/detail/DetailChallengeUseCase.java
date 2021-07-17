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
import fpt.provipluxurylimited.challengefocus.models.ToDoItem;

public class DetailChallengeUseCase {
    public interface DetailChallengeUseCaseDelegate extends BaseUseCaseDelegate {
        void onSuccessGetItems(ArrayList<ToDoItem> list);
        void onSuccessUploadImage(String imageName);
    }

    private DetailChallengeUseCaseDelegate delegate;

    public void setDelegate(DetailChallengeUseCaseDelegate delegate) {
        this.delegate = delegate;
    }

    public void getToDoItemsList(String userId, String status){
        FirebaseUtil.shared.getReference().child(ApiClient.myChallenge).child("fsdffd").child(ApiClient.items)
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

    public void addItem(String title) {
        if (!title.isEmpty()) {
            FirebaseUtil.shared.getReference().child(ApiClient.myChallenge).child("fsdffd").child(ApiClient.items)
                    .push().setValue(new ToDoItem(title, false));
        }
    }

    public void removeItem(String id) {
        FirebaseUtil.shared.getReference().child(ApiClient.myChallenge).child("fsdffd").child(ApiClient.items)
                .child(id).getRef().removeValue();
    }

    public void uploadImageToStorage(Uri uri, ToDoItem item) {
        final String randomKey = UUID.randomUUID().toString();
        FirebaseUtil.shared.getStorageReference().child("results/" + randomKey).putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        requestImageUrl(randomKey, item);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Log.e("Download failed", "You are shit");
                    }
                });
    }

    private void updateItemWithImage(String id,ToDoItem item) {
        FirebaseUtil.shared.getReference().child(ApiClient.myChallenge).child("fsdffd").child(ApiClient.items)
                .child(id)
                .setValue(item);
    }


    private void requestImageUrl(String imageName, ToDoItem item) {
        FirebaseUtil.shared.getStorageReference().child("results/" + imageName)
                .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.e("link", uri.toString());
                ToDoItem updateItem = new ToDoItem(item.getTitle(), Utils.convertDateToString(new Date()), true, uri.toString());
                updateItemWithImage(item.getId(), updateItem);
            }
        });
    }
}

