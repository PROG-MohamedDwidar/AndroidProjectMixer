package com.example.mixer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    String eholder,pholder;
    Intent login;
    EditText email;
    EditText password;
    Button loginButton;
    TextView emailError;
    TextView passwordError;
    public static final String emaill="email";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        loginButton=findViewById(R.id.loginButtonmain);
        email=findViewById(R.id.editTextEmail);
        password=findViewById(R.id.editTextPassword);
        emailError=findViewById(R.id.emailError);
        passwordError=findViewById(R.id.passwordError);


    }
    private void secretdoor(){
        login = new Intent(MainActivity.this, dashboard.class);
        startActivity(login);
    }
    public void loginAction(View view)
    {
        secretdoor();
        eholder = email.getText().toString();
        pholder=password.getText().toString();


        String checkers;
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        checkers=sharedPref.getString(eholder,"none");

        if(pholder.equals(checkers)){
            login = new Intent(MainActivity.this, dashboard.class);
//          login.putExtra(emaill, eholder);
            startActivity(login);
        }
        else{
            passwordError.setText("wrong password or email");
        }

    }
    public void registerAction(View view)
    {
        eholder = email.getText().toString();
        pholder = password.getText().toString();

        if(!eholder.contains("@")){
            emailError.setText("wrong email format");
            return;
        }
        else{
            emailError.setText("");
        }
        Pattern ppatt=Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$");
        if(!ppatt.matcher(pholder).matches()){
            passwordError.setText("must be 8-20 chars long and must have at least one number, upper case and symbol");
            return;
        }
        else{
            passwordError.setText("");
        }

        String checkers;

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        checkers=sharedPref.getString(eholder,"none");

        if(checkers.equals("none")){

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(eholder,pholder);
            editor.apply();
            Toast.makeText(this,"registeration successful",Toast.LENGTH_SHORT);
            loginAction(view);
        }
        else{
            emailError.setText("email already exists");
        }

    }
}