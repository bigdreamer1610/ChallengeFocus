package fpt.provipluxurylimited.challengefocus.challenge.detail;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import fpt.provipluxurylimited.challengefocus.R;
import fpt.provipluxurylimited.challengefocus.challenge.ItemFragment;
import fpt.provipluxurylimited.challengefocus.challenge.NoItemFragment;
import fpt.provipluxurylimited.challengefocus.helpers.Constants;
import fpt.provipluxurylimited.challengefocus.helpers.SaveSharedPreference;
import fpt.provipluxurylimited.challengefocus.helpers.Utils;
import fpt.provipluxurylimited.challengefocus.helpers.base.BaseActivity;
import fpt.provipluxurylimited.challengefocus.models.Challenge;
import fpt.provipluxurylimited.challengefocus.models.ChallengeStatus;
import fpt.provipluxurylimited.challengefocus.models.Quote;
import fpt.provipluxurylimited.challengefocus.models.ToDoItem;
import me.ibrahimsn.lib.CirclesLoadingView;

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
    Dialog dialogWarning;
    Dialog dialogCongrats;
    Dialog dialogMessage;
    Dialog dialogCalendar;
    AppCompatButton btnAgree;
    AppCompatButton btnReject;
    CirclesLoadingView loadingView;

    BottomSheetDialog bottomSheetDialog;
    final Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener onDateSetListener;

    private boolean hasItem = false;
    Date date;
    private ChallengeStatus challengeStatus = ChallengeStatus.doing;
    private DetailChallengePresenter presenter;
    private ArrayList<ToDoItem> list;
    int PICK_IMAGE = 1;
    Uri imageUrl;
    ToDoItem selectedItem;
    Challenge challenge;
    Quote quote;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    Date today = new Date();


    @Override
    protected void onStop() {
        super.onStop();
        fragmentItem = null;
        fragmentNoItem = null;
    }

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
        loadingView = findViewById(R.id.loadingView);
        imageViewBack.setClickable(true);
        updateFragment();
        clickBack();
        clickFloat();
        setUpDialog();
        setUpDialogWarning();
        setUpDialogCongrats();
        setUpDialogMessage();
        setUpStorage();
        setUpDialogCalendar();
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

    void showLoading() {
        loadingView.setVisibility(View.VISIBLE);
    }

    void hideLoading() {
        loadingView.clearAnimation();
        loadingView.setVisibility(View.GONE);
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
        getQuote();
        if (challengeString != null) {
            challenge = gson.fromJson(challengeString, Challenge.class);
            bindData(challenge);
        }
    }

    void bindData(Challenge challenge) {
        Log.e("challenge", challenge.toString());
        String duedate = challenge.getDueDate();

        int percentage = challenge.getPercentage();
        challengeStatus = Constants.getStatus(challenge.getStatus());
        String title = challenge.getTitle();

        textViewPercentage.setText(percentage + "%");
        textViewTitle.setText(title);
        textViewChooseDate.setEnabled(duedate == null ? true : false);
        if (duedate != null) {
            date = Utils.convertStringToDate(duedate);
            textViewChooseDate.setEnabled(false);
            textViewChooseDate.setText(duedate);
            getListItem();
        } else {
            hideLoading();
            textViewChooseDate.setEnabled(true);
        }
        updateViewOnStatus(challengeStatus);

    }

    void getListItem() {
        showLoading();
        presenter.getItemList(SaveSharedPreference.getUserId(context), challenge.getId());
    }

    void getQuote() {
        presenter.getQuote();
    }

    void updateViewOnStatus(ChallengeStatus status) {
        switch (status) {
            case doing:
                floatButton.setVisibility(View.VISIBLE);
                break;
            default:
                floatButton.setVisibility(View.INVISIBLE);
                break;
        }
    }

    void clickBack() {
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // no date & no item
                if (date == null && !hasItem) {
                    finish();
                    // there's date but no item
                } else if (date != null && !hasItem) {
                    showWarningDialog();
                } else {
                    finish();
                }
            }
        });
    }

    void clickFloat() {
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // when user has not picked the deadline => show dialog
                if (date == null) {
                    showMessageDialog();
                } else {
                    // open bottom sheet to input new item
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
                            String title = editTextName.getText().toString().trim();
                            // if this is the first item of the list
                            // add new challenge -> then add this item to this challnge
                            if (!hasItem) {
                                presenter.addFirstIitem(SaveSharedPreference.getUserId(context), challenge, new ToDoItem(title, false));
                                // if not the first -> just add this item to the existing challenge
                            } else {
                                presenter.addItem(SaveSharedPreference.getUserId(context), challenge.getId(), new ToDoItem(title, false));
                            }
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
                    DatePickerDialog datePickerDialog = new DatePickerDialog(DetailChallengeActivity.this, R.style.CalendarTheme , onDateSetListener,myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.show();
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

    void setUpDialogWarning() {
        dialogWarning = new Dialog(this);
        dialogWarning.setContentView(R.layout.confirm_dialog);
        dialogWarning.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        TextView warningTitle = dialogWarning.findViewById(R.id.dialogTitle);
        AppCompatButton warningAgreeButton = dialogWarning.findViewById(R.id.btnAgree);
        AppCompatButton warningRejectButton = dialogWarning.findViewById(R.id.btnReject);

        warningTitle.setText(Constants.DialogConstants.warningAddItem);
        warningAgreeButton.setText(Constants.DialogConstants.optionAgreeAddItem);
        warningRejectButton.setText(Constants.DialogConstants.optionRejectAddITem);

        warningAgreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogWarning.dismiss();
            }
        });

        warningRejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogWarning.dismiss();
                finish();
            }
        });
    }

    void setUpDialogMessage() {
        dialogMessage = new Dialog(this);
        dialogMessage.setContentView(R.layout.message_dialog);
        dialogMessage.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        TextView textViewMessage = dialogMessage.findViewById(R.id.dialogTitle);
        AppCompatButton btnOk = dialogMessage.findViewById(R.id.btnOk);

        textViewMessage.setText(Constants.DialogConstants.deadline);
        btnOk.setText("OK");
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogMessage.dismiss();
            }
        });
    }

    void setUpDialogCalendar() {
        dialogCalendar = new Dialog(this);
        dialogCalendar.setContentView(R.layout.message_dialog);
        dialogCalendar.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        TextView textViewMessage = dialogCalendar.findViewById(R.id.dialogTitle);
        AppCompatButton btnOk = dialogCalendar.findViewById(R.id.btnOk);

        textViewMessage.setText(Constants.DialogConstants.calendarMessage);
        btnOk.setText("OK");
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogCalendar.dismiss();
            }
        });
    }

    void setUpDialogCongrats() {
        dialogCongrats = new Dialog(this);
        dialogCongrats.setContentView(R.layout.congrat_screen);
        dialogCongrats.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        AppCompatButton btnOk = dialogCongrats.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogCongrats.dismiss();
            }
        });
    }

    public void showDialog() {
        dialog.show();
    }

    public void closeDialog() {
        dialog.dismiss();
    }

    void showWarningDialog() {
        dialogWarning.show();
    }

    void showCongratDialog() {
        dialogCongrats.show();
    }

    void showMessageDialog() {
        dialogMessage.show();
    }

    void showCalendarDialog() {
        dialogCalendar.show();
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
            showLoading();
            presenter.uploadImageToStorage(SaveSharedPreference.getUserId(context), challenge.getId(), selectedItem, imageUrl);
        }
    }

    void updateDate() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat format = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        if (!myCalendar.getTime().after(today)) {
            showCalendarDialog();
        } else {
            date = myCalendar.getTime();
            challenge.setDueDate(Utils.convertDateToString(date));
            textViewChooseDate.setText(format.format(date));
            textViewChooseDate.setEnabled(false);
        }
    }

    void updateFragment() {
        fragmentItem = (ItemFragment) this.getSupportFragmentManager().findFragmentById(R.id.fragmentItem);
        fragmentNoItem = (NoItemFragment) this.getSupportFragmentManager().findFragmentById(R.id.fragmentNoItem);
        if (fragmentNoItem != null && fragmentItem != null) {
            if (hasItem) {
                fragmentNoItem.getView().setVisibility(View.GONE);
                fragmentItem.getView().setVisibility(View.VISIBLE);
            } else {
                fragmentItem.getView().setVisibility(View.GONE);
                fragmentNoItem.getView().setVisibility(View.VISIBLE);
            }
        }
    }

    public void removeItem(ToDoItem item) {
        presenter.removeItem(SaveSharedPreference.getUserId(context), challenge.getId(), item.getId());
    }

    @Override
    public void responseItemList(ArrayList<ToDoItem> list) {
        hideLoading();
        this.list = list;
        hasItem = list.size() != 0;
        updateFragment();
        fragmentItem.setList(list);
        fragmentItem.setAllowSwipe(challengeStatus == ChallengeStatus.doing ? true : false);
        if (challengeStatus == ChallengeStatus.doing) {
            presenter.updatePercentage(SaveSharedPreference.getUserId(context), challenge.getId(), list);
        }
        if (this.list.size() == 0 && challenge.getId() != null) {
            // remove challenge
            presenter.removeEmptyChallenge(SaveSharedPreference.getUserId(context), challenge.getId());
            resetChallenge();
        }
    }

    void resetChallenge() {
        challenge.setId(null);
    }

    @Override
    public void showError(String error) {
        Log.e("Error", error);
    }

    @Override
    public void responseChallengeId(String challengeId) {
        challenge.setId(challengeId);
        getListItem();
    }

    @Override
    public void responsePercentage(int percentage) {
        challenge.setPercentage(percentage);
        textViewPercentage.setText(percentage + "%");
        if (percentage == 100 && challengeStatus == ChallengeStatus.doing) {
            showCongratDialog();
            challengeStatus = ChallengeStatus.done;
            updateViewOnStatus(challengeStatus);
            fragmentItem.setAllowSwipe(false);
        }
    }

    @Override
    public void responseQuote(Quote quote) {
        this.quote = quote;
        fragmentNoItem.setQuote(quote);
    }

}