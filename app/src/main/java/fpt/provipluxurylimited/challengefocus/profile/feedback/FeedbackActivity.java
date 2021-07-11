package fpt.provipluxurylimited.challengefocus.profile.feedback;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import fpt.provipluxurylimited.challengefocus.R;
import fpt.provipluxurylimited.challengefocus.helpers.base.BaseActivity;
import fpt.provipluxurylimited.challengefocus.models.Feedback;
import fpt.provipluxurylimited.challengefocus.profile.classes.StarRecyclerAdapter;

public class FeedbackActivity extends BaseActivity implements StarRecyclerAdapter.ItemClickListener, FeedbackPresenter.FeedbackPresenterDelegate {

    private int noOfStars = 0;
    RecyclerView recyclerView;
    StarRecyclerAdapter adapter;
    ImageView btnBack;
    AppCompatButton btnSend;
    EditText editTextContent;
    private Context context;
    CoordinatorLayout mainContent;

    private List<Boolean> list;
    private FeedbackPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        setUp();
        initData();
        initComponents();
    }

    void setUp() {
        presenter = new FeedbackPresenter(new FeedbackUseCase(), this);
        presenter.setDelegate(this);
    }

    private void initData() {
        list = new ArrayList<>();
        list.add(Boolean.FALSE);
        list.add(Boolean.FALSE);
        list.add(Boolean.FALSE);
        list.add(Boolean.FALSE);
        list.add(Boolean.FALSE);
    }

    private void initComponents() {
        context = this;
        editTextContent = findViewById(R.id.editTextContent);
        btnSend = findViewById(R.id.btnSend);
        btnBack = findViewById(R.id.btnBack);
        recyclerView = findViewById(R.id.recyclerViewStar);
        adapter = new StarRecyclerAdapter(list, this);
        adapter.setClickListener(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        btnBack.setClickable(true);
        clickBack();
        clickSend();

    }

    void clickBack() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    void clickSend() {
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (noOfStars == 0 ) {
                    System.out.println("Please choose stars");
                    return;
                }
                Feedback feedback;
                String content = editTextContent.getText().toString();
                if (content.isEmpty()) {
                    feedback = new Feedback(noOfStars, "id1");
                } else {
                    feedback = new Feedback(noOfStars, "id1", content);
                }
                presenter.sendFeedback(feedback);

            }
        });
    }

    @Override
    public void onClick(View view, int position) {
        noOfStars = 0;
        for(int i = 0; i < list.size(); i++) {
            if (i <= position) {
                list.set(i, true);
                noOfStars++;
            } else {
                list.set(i, false);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void sendFeedbackSuccess() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showDialog("Gửi feedback thành công, cảm ơn bạn nhaa!",context);
            }
        });
    }

    @Override
    public void showError(String error) {
        System.out.println("error: " + error);
    }
}