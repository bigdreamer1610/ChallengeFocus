package fpt.provipluxurylimited.challengefocus.challenge.detail;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import fpt.provipluxurylimited.challengefocus.helpers.ApiClient;
import fpt.provipluxurylimited.challengefocus.helpers.FirebaseUtil;
import fpt.provipluxurylimited.challengefocus.helpers.base.BaseUseCaseDelegate;
import fpt.provipluxurylimited.challengefocus.models.ToDoItem;

public class DetailChallengeUseCase {
    public interface DetailChallengeUseCaseDelegate extends BaseUseCaseDelegate {
        void onSuccessGetItems(ArrayList<ToDoItem> list);
    }

    private DetailChallengeUseCaseDelegate delegate;

    public void setDelegate(DetailChallengeUseCaseDelegate delegate) {
        this.delegate = delegate;
    }

    public void getToDoItemsList(String userId, String status){

        FirebaseUtil.shared.getReference().child(ApiClient.getChallengeByStatus(status)).child("1").child(ApiClient.items)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        ArrayList<ToDoItem> list = new ArrayList<>();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            ToDoItem item = ds.getValue(ToDoItem.class);
                            item.setExpanded(false);
                            System.out.println(item.toString());
                            list.add(item);
                        }
                        System.out.println("fuck " + list.size());
                        delegate.onSuccessGetItems(list);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        delegate.onFailure(error.getMessage());
                    }
                });
    }
}

