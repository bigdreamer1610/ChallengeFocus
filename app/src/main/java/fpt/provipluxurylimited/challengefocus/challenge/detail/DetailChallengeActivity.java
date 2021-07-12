package fpt.provipluxurylimited.challengefocus.challenge.detail;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import fpt.provipluxurylimited.challengefocus.R;
import fpt.provipluxurylimited.challengefocus.challenge.ItemFragment;
import fpt.provipluxurylimited.challengefocus.challenge.NoItemFragment;
import fpt.provipluxurylimited.challengefocus.helpers.Constants;
import fpt.provipluxurylimited.challengefocus.helpers.base.BaseActivity;
import fpt.provipluxurylimited.challengefocus.models.Challenge;
import fpt.provipluxurylimited.challengefocus.models.ChallengeStatus;

public class DetailChallengeActivity extends BaseActivity {

    Fragment fragmentItem;
    Fragment fragmentNoItem;
    ImageView imageViewBack;
    TextView textViewTitle;
    TextView textViewPercentage;
    FloatingActionButton floatButton;
    TextView textViewChooseDate;
    Context context;

    BottomSheetDialog bottomSheetDialog;
    final Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener onDateSetListener;

    private boolean hasItem = false;
    Date date;
    private ChallengeStatus challengeStatus = ChallengeStatus.doing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_challenge);
        initComponents();
        initData();
    }

    private void initComponents() {
        context = this;
        fragmentItem = (ItemFragment) this.getSupportFragmentManager().findFragmentById(R.id.fragmentItem);
        fragmentNoItem = (NoItemFragment) this.getSupportFragmentManager().findFragmentById(R.id.fragmentNoItem);
        textViewPercentage = findViewById(R.id.textViewPercentage);
        textViewTitle = findViewById(R.id.textViewTitle);
        imageViewBack = findViewById(R.id.btnBack);
        floatButton = findViewById(R.id.floatingButton);
        textViewChooseDate = findViewById(R.id.textViewDate);
        imageViewBack.setClickable(true);
        updateFragment();
        clickBack();
        clickFloat();

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                myCalendar.set(Calendar.YEAR, i);
                myCalendar.set(Calendar.MONTH, i1);
                myCalendar.set(Calendar.DAY_OF_MONTH, i2);
                updateDate();
            }
        };
        clickChooseDate();
    }

    void initData() {
        Gson gson = new Gson();
        String challengeString = getIntent().getStringExtra("challenge");
        if (challengeString != null) {
            Challenge challenge = gson.fromJson(challengeString, Challenge.class);
            textViewPercentage.setText(challenge.getPercentage() + "%");
            textViewTitle.setText(challenge.getTitle());
            textViewChooseDate.setText(challenge.getDueDate());
            challengeStatus = Constants.getStatus(challenge.getStatus());
            updateViewOnStatus(challengeStatus);
        }
    }

    void updateViewOnStatus(ChallengeStatus status) {
        System.out.println("status: " + status.name());
        switch (status) {
            case done:
                floatButton.setVisibility(View.INVISIBLE);
                break;
            case doing:
                floatButton.setVisibility(View.VISIBLE);
                break;
            case failed:
                floatButton.setVisibility(View.INVISIBLE);
                break;
        }
    }

    void clickBack() {
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    void clickFloat() {
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // when user has not picked the deadline => show dialog
                if (date == null) {
                    showDialog("Bạn cần đặt deadline trước khi thêm item", context);
                } else {
                    hasItem = true;
                    updateFragment();
                    bottomSheetDialog = new BottomSheetDialog(view.getContext());
                    bottomSheetDialog.setContentView(R.layout.add_item);
                    AppCompatButton btnAdd = bottomSheetDialog
                            .findViewById(R.id.btnAdd);
                    EditText editTextName = bottomSheetDialog.findViewById(R.id.editTextName);
                    bottomSheetDialog.show();

                    btnAdd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            bottomSheetDialog.dismiss();
                        }
                    });
                }
            }
        });
    }

    void clickChooseDate() {
        textViewChooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (challengeStatus == ChallengeStatus.doing) {
                    new DatePickerDialog(DetailChallengeActivity.this, onDateSetListener,myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                } else {
                    return;
                }
            }
        });
    }

    void updateDate() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat format = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        date = myCalendar.getTime();
        textViewChooseDate.setText(format.format(date));
    }

    void updateFragment() {
        if (hasItem) {
            fragmentNoItem.getView().setVisibility(View.GONE);
            fragmentItem.getView().setVisibility(View.VISIBLE);
        } else {
            fragmentItem.getView().setVisibility(View.GONE);
            fragmentNoItem.getView().setVisibility(View.VISIBLE);
        }
    }

}