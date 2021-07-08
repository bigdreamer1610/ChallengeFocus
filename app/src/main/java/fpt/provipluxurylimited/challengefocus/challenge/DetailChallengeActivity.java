package fpt.provipluxurylimited.challengefocus.challenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import fpt.provipluxurylimited.challengefocus.R;

public class DetailChallengeActivity extends AppCompatActivity {

    Fragment fragmentItem;
    Fragment fragmentNoItem;
    ImageView imageViewBack;
    TextView textViewTitle;
    TextView textViewPercentage;
    FloatingActionButton floatButton;
    TextView textViewChooseDate;

    BottomSheetDialog bottomSheetDialog;
    final Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener onDateSetListener;

    private boolean hasItem = false;
    Date date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_challenge);
        initComponents();
    }

    private void initComponents() {
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
                if (date == null) {
                    showAlertDialog();
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
                new DatePickerDialog(DetailChallengeActivity.this, onDateSetListener,myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
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

    // when user has not picked deadline -> show dialog
    void showAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Bạn cần đặt deadline trước khi thêm item");
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}