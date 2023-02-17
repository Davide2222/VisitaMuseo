package com.example.visitamuseo.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.visitamuseo.CommunicationThreads.CommunicationThreadReceiveData;
import com.example.visitamuseo.CommunicationThreads.CommunicationThreadRegister;
import com.example.visitamuseo.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.dmoral.toasty.Toasty;

public class RegisterActivity extends FullscreenActivity {

    private EditText registerUserIdEditText;
    private EditText registerPasswordEditText;
    private EditText registerConfirmPasswordEditText;
    private Button signupButton;
    CompoundButton previousCheckedCompoundButton;

    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerUserIdEditText = findViewById(R.id.UserIdEditText_signup);
        registerPasswordEditText = findViewById(R.id.NewPasswordEditText_signup);
        registerConfirmPasswordEditText = findViewById(R.id.ConfirmPasswordEditText_signup);
        signupButton = findViewById(R.id.signup_button);

        signupButton.setOnClickListener(view -> {
            String userId = registerUserIdEditText.getText().toString();
            String password = registerPasswordEditText.getText().toString();
            String newPassword = registerConfirmPasswordEditText.getText().toString();
            if (checkEmptyField(userId) && checkEmptyField(password) && checkEmptyField(newPassword)) {
                if (validatePassword() && check2Password()) {
                    if (previousCheckedCompoundButton != null) {
                        String data=registerUserIdEditText.getText()+","+registerPasswordEditText.getText()+","+previousCheckedCompoundButton.getText().toString();
                        executor.execute(() -> {
                        CommunicationThreadRegister communicationThreadRegister = new CommunicationThreadRegister(data);
                        boolean status=communicationThreadRegister.registerUser();
                        handler.post(() -> {
                            if(status){
                                Toasty.success(this, "Registrazione avvenuta con successo!", Toast.LENGTH_SHORT, true).show();
                                startActivity(new Intent(this, LoginActivity.class));
                            } else {
                                Toasty.error(this, "Utente già presente!", Toast.LENGTH_SHORT, true).show();
                            }
                        });
                    });
                    } else {
                        Toasty.warning(this, "Attenzione! Scegliere la tipologia di utente", Toasty.LENGTH_SHORT, true).show();
                    }
                } else {
                    Toasty.warning(this, "Attenzione! Controllare la password", Toasty.LENGTH_SHORT, true).show();
                }
            } else {
                Toasty.warning(this, "Attenzione! Compilare tutti i campi", Toasty.LENGTH_SHORT, true).show();
            }

        });

        CompoundButton.OnCheckedChangeListener onRadioButtonCheckedListener = (buttonView, isChecked) -> {
            if (!isChecked) return;
            if (previousCheckedCompoundButton != null) {
                previousCheckedCompoundButton.setChecked(false);
                previousCheckedCompoundButton = buttonView;
            } else {
                previousCheckedCompoundButton = buttonView;
            }
        };

        RadioButton radioButton1 = findViewById(R.id.radioButtonSingle);
        RadioButton radioButton2 = findViewById(R.id.radioButtonFamily);
        RadioButton radioButton3 = findViewById(R.id.radioButtonGroup);
        RadioButton radioButton4 = findViewById(R.id.radioButtonExpert);
        RadioButton radioButton5 = findViewById(R.id.radioButtonSchool);

        radioButton1.setOnCheckedChangeListener(onRadioButtonCheckedListener);
        radioButton2.setOnCheckedChangeListener(onRadioButtonCheckedListener);
        radioButton3.setOnCheckedChangeListener(onRadioButtonCheckedListener);
        radioButton4.setOnCheckedChangeListener(onRadioButtonCheckedListener);
        radioButton5.setOnCheckedChangeListener(onRadioButtonCheckedListener);
    }

    private boolean checkEmptyField(String text) {
        if (text.isEmpty()) {
            Toasty.warning(this, "Attenzione! Compilare tutti i campi", Toasty.LENGTH_SHORT, true).show();
            return false;
        }
        return true;
    }

    public boolean validatePassword() {
        String password = registerPasswordEditText.getText().toString();
        if (password.equals("")) {
            registerPasswordEditText.setError("La password non può essere vuota");
            return false;
        }

        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{3,10}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);

        if (!matcher.matches())
            registerPasswordEditText.setError("Caratteristiche della password:\n" +
                    "3-10 caratteri\n" +
                    "1 numero\n" +
                    "1 carattere maiuscolo\n" +
                    "1 carattere minuscolo\n");

        return matcher.matches();
    }

    public boolean check2Password() {
        String password = registerPasswordEditText.getText().toString();
        String newPassword = registerConfirmPasswordEditText.getText().toString();
        return password.equals(newPassword);
    }

}