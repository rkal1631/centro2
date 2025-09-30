package com.albaihaqi.centro.auth;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.albaihaqi.centro.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    private EditText editFullname, editUsername, editEmail, editAddress;
    private EditText editPassword;
    private EditText editDate;
    private Spinner spinnerStudyProgram;
    private Button btnRegister;
    private String fullname, username, email, password, date, address, studyProgram;
    private Switch switchConfirm;

    private boolean isPasswordVisible = false;
    private boolean isRegistered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        setupWindowInsets();
        initializeViews();
        setupDatePicker();
        setupPasswordToggle();
        setupRegisterButton();
        setupSwitch(); // listener untuk switch
    }

    private void setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initializeViews() {
        editFullname = findViewById(R.id.edt_fullname);
        editUsername = findViewById(R.id.edt_username);
        editEmail = findViewById(R.id.edt_email);
        editPassword = findViewById(R.id.edt_password);
        editDate = findViewById(R.id.edt_date);
        editAddress = findViewById(R.id.edt_address);

        spinnerStudyProgram = findViewById(R.id.spin_prodi);
        btnRegister = findViewById(R.id.btn_register);
        switchConfirm = findViewById(R.id.switch_confirm);

        editDate.setFocusable(false);
        editDate.setClickable(true);
    }

    private void setupDatePicker() {
        editDate.setOnClickListener(v -> showDatePicker());
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(selectedYear, selectedMonth, selectedDay);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    String formattedDate = dateFormat.format(selectedDate.getTime());

                    editDate.setText(formattedDate);
                },
                year, month, day
        );

        datePickerDialog.show();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupPasswordToggle() {
        editPassword.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                int iconWidth = editPassword.getCompoundDrawables()[2].getBounds().width();
                int extraPadding = 50;

                if (event.getRawX() >= (editPassword.getRight() - iconWidth - extraPadding)) {
                    togglePasswordVisibility();
                    return true;
                }
            }
            return false;
        });
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            editPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            editPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_password, 0, R.drawable.ic_eye_off, 0);
            isPasswordVisible = false;
        } else {
            editPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            editPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_password, 0, R.drawable.ic_eye, 0);
            isPasswordVisible = true;
        }
        editPassword.setSelection(editPassword.getText().length());
    }

    private void setupRegisterButton() {
        btnRegister.setOnClickListener(v -> handleRegister());
    }

    private void handleRegister() {
        if (!validateForm()) {
            return;
        }

        fullname = editFullname.getText().toString().trim();
        username = editUsername.getText().toString().trim();
        email = editEmail.getText().toString().trim();
        password = editPassword.getText().toString().trim();
        date = editDate.getText().toString().trim();
        address = editAddress.getText().toString().trim();
        studyProgram = spinnerStudyProgram.getSelectedItem().toString();

        isRegistered = true;
        showRegistrationSuccessDialog();
    }

    private boolean validateForm() {
        if (editFullname.getText().toString().trim().isEmpty()) {
            editFullname.setError("Fullname is required");
            return false;
        }
        if (editUsername.getText().toString().trim().isEmpty()) {
            editUsername.setError("Username is required");
            return false;
        }
        if (editEmail.getText().toString().trim().isEmpty()) {
            editEmail.setError("Email is required");
            return false;
        }
        String email = editEmail.getText().toString().trim();
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Please enter a valid email");
            return false;
        }
        if (editPassword.getText().toString().trim().isEmpty()) {
            editPassword.setError("Password is required");
            return false;
        }
        if (spinnerStudyProgram.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select a study program", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (editDate.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please select your date of birth", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (editAddress.getText().toString().trim().isEmpty()) {
            editAddress.setError("Address is required");
            return false;
        }
        return true;
    }

    private void showRegistrationSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Registration Success");
        builder.setMessage("Your registration has been completed successfully!");
        builder.setPositiveButton("OK", (dialog, which) -> {
            dialog.dismiss();
            resetForm();
        });
        builder.setCancelable(false);
        builder.show();
    }

    private void resetForm() {
        editFullname.setText("");
        editUsername.setText("");
        editEmail.setText("");
        editPassword.setText("");
        editDate.setText("");
        editAddress.setText("");
        spinnerStudyProgram.setSelection(0);
    }

    private void setupSwitch() {
        switchConfirm.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (!isRegistered) {
                    Toast.makeText(this, "Harap isi form dan klik REGISTER dulu", Toast.LENGTH_SHORT).show();
                    switchConfirm.setChecked(false);
                } else {

                    Intent intent = new Intent(RegisterActivity.this, IntentActivity.class);
                    intent.putExtra("fullname", fullname);
                    intent.putExtra("username", username);
                    intent.putExtra("email", email);
                    intent.putExtra("password", password);
                    intent.putExtra("date", date);
                    intent.putExtra("address", address);
                    intent.putExtra("studyProgram", studyProgram);
                    startActivity(intent);
                }
            }
        });
    }
}
