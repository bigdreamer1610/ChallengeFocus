package fpt.provipluxurylimited.challengefocus.challenge.detail;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import fpt.provipluxurylimited.challengefocus.R;
import fpt.provipluxurylimited.challengefocus.challenge.ItemFragment;
import fpt.provipluxurylimited.challengefocus.challenge.NoItemFragment;
import fpt.provipluxurylimited.challengefocus.helpers.Constants;
import fpt.provipluxurylimited.challengefocus.helpers.base.BaseActivity;
import fpt.provipluxurylimited.challengefocus.models.Challenge;
import fpt.provipluxurylimited.challengefocus.models.ChallengeStatus;
import fpt.provipluxurylimited.challengefocus.models.ToDoItem;

public class DetailChallengeActivity extends BaseActivity implements DetailChallengePresenter.DetailChallengePresenterDelegate {

    ItemFragment fragmentItem;
    NoItemFragment fragmentNoItem;
    ImageView imageViewBack;
    TextView textViewTitle;
    TextView textViewPercentage;
    FloatingActionButton floatButton;
    TextView textViewChooseDate;
    Context context;
    Dialog dialog;
    AppCompatButton btnAgree;
    AppCompatButton btnReject;
    FragmentActivity fragmentActivity;

    BottomSheetDialog bottomSheetDialog;
    final Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener onDateSetListener;

    private boolean hasItem = false;
    String dateString;
    Date date;
    private ChallengeStatus challengeStatus = ChallengeStatus.doing;
    private DetailChallengePresenter presenter;
    private ArrayList<ToDoItem> list;
    int PICK_IMAGE = 1;
    Uri imageUrl;
    ToDoItem selectedItem;

    private FirebaseStorage storage;
    private StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_challenge);
        setUp();
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
        setUpDialog();
        setUpStorage();
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



    void setUp() {
        presenter = new DetailChallengePresenter(new DetailChallengeUseCase(), this);
        presenter.setDelegate(this);
    }

    void setUpStorage() {
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }

    void initData() {
        list = new ArrayList<>();
        Gson gson = new Gson();
        String challengeString = getIntent().getStringExtra("challenge");
        if (challengeString != null) {
            Challenge challenge = gson.fromJson(challengeString, Challenge.class);
            textViewChooseDate.setEnabled(false);
            dateString = challenge.getDueDate();
            textViewPercentage.setText(challenge.getPercentage() + "%");
            textViewTitle.setText(challenge.getTitle());
            textViewChooseDate.setText(challenge.getDueDate());
            challengeStatus = Constants.getStatus(challenge.getStatus());
            presenter.getItemList("id1", "doing");
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
                if (dateString == null) {
                    showDialog("Bạn cần đặt deadline trước khi thêm item", context);
                } else {
                    hasItem = true;
                    updateFragment();
                    bottomSheetDialog = new BottomSheetDialog(view.getContext());
                    bottomSheetDialog.setContentView(R.layout.add_item);
                    AppCompatButton btnAdd = bottomSheetDialog
                            .findViewById(R.id.btnAdd);
                    btnAdd.setEnabled(false);
                    EditText editTextName = bottomSheetDialog.findViewById(R.id.editTextName);
                    bottomSheetDialog.show();

                    btnAdd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            presenter.addItem(editTextName.getText().toString());
                            bottomSheetDialog.dismiss();
                        }
                    });

                    editTextName.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            btnAdd.setEnabled(charSequence.length() != 0);
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

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

    void setUpDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.upload_image_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        btnAgree = dialog.findViewById(R.id.btnAgree);
        btnReject = dialog.findViewById(R.id.btnReject);
        btnAgree.setText(Constants.DialogConstants.optionUpload);
        btnReject.setText(Constants.DialogConstants.optionCancel);
        btnAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeDialog();
                pickImage();

            }
        });

        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeDialog();
            }
        });
    }

    public void showDialog() {
        dialog.show();
    }

    public void closeDialog() {
        dialog.dismiss();
    }

    public void setClickItem(ToDoItem item) {
        selectedItem = item;
    }

    void pickImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Upload Image"), PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUrl = data.getData();
            presenter.uploadImageToStorage(imageUrl, selectedItem);
        }
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

    public void updateData(ArrayList<ToDoItem> list) {
        this.list = list;
        hasItem = list.size() != 0;
        updateFragment();
    }

    public void removeItem(ToDoItem item) {
        presenter.removeItem(item.getId());
    }

    @Override
    public void responseItemList(ArrayList<ToDoItem> list) {
        this.list = list;
        fragmentItem.setList(list);
        hasItem = list.size() != 0;
        updateFragment();
    }

    @Override
    public void showError(String error) {
        Log.e("Error", error);
    }
}