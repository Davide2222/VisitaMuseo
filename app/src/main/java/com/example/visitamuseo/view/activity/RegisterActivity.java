package com.example.visitamuseo.view.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.visitamuseo.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.dmoral.toasty.Toasty;

public class RegisterActivity extends AppCompatActivity {

    private EditText registerUserIdEditText;
    private EditText registerPasswordEditText;
    private EditText registerConfirmPasswordEditText;
    private Button signupButton;
    private RadioGroup typeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

       /* registerUserIdEditText=findViewById(R.id.UserIdEditText_signup);
        registerPasswordEditText= findViewById(R.id.NewPasswordEditText_signup);
        registerConfirmPasswordEditText= findViewById(R.id.ConfirmPasswordEditText_signup);
        signupButton=findViewById(R.id.signup_button);*/
        typeUser=findViewById(R.id.radioGroupTypeUser);


        signupButton.setOnClickListener(view -> {
            String userId=registerUserIdEditText.getText().toString();
            String password=registerPasswordEditText.getText().toString();
            String newPassword=registerConfirmPasswordEditText.getText().toString();
            if(checkEmptyField(userId)&&checkEmptyField(password)&&checkEmptyField(newPassword)){
                if(validatePassword() && check2Password()){

                } else {
                    Toasty.warning(this, "Attenzione! Controllare la password", Toasty.LENGTH_SHORT, true).show();
                }
            }
        });
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