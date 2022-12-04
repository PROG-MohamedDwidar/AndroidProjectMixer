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

        //connect xml with class attributes
        loginButton=findViewById(R.id.loginButtonmain);
        email=findViewById(R.id.editTextEmail);
        password=findViewById(R.id.editTextPassword);
        emailError=findViewById(R.id.emailError);
        passwordError=findViewById(R.id.passwordError);


    }
    //function for login without password and email
    private void secretdoor(){
        login = new Intent(MainActivity.this, dashboard.class);
        startActivity(login);
    }

    //login logic
    public void loginAction(View view)
    {
        //secretdoor();

        //get data from text fields
        eholder = email.getText().toString();
        pholder=password.getText().toString();

        //check the email in the shared preferences file
        String checkers;
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        checkers=sharedPref.getString(eholder,"none");//if it exists return the stored password else return "none"

        if(pholder.equals(checkers)){//does the typed pass equal the stored one
            //then let the user in
            login = new Intent(MainActivity.this, dashboard.class);
            startActivity(login);
        }
        else{
            //Wrong password
            passwordError.setText("wrong password or email");
        }

    }
    //Registeration logic
    public void registerAction(View view)
    {
        //get typed data
        eholder = email.getText().toString();
        pholder = password.getText().toString();

        if(!eholder.contains("@")){//if the email is not in the right format
            emailError.setText("wrong email format");
            return;
        }
        else{//reset error hint if the email is ok
            emailError.setText("");
        }
        //password pattern customizaion
        Pattern ppatt=Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).8,{20}$");
        if(!ppatt.matcher(pholder).matches()){//if password format is wrong
            passwordError.setText("must be 8-20 chars long and must have at least one number, upper case and symbol");
            return;
        }
        else{//reset error hint if right
            passwordError.setText("");
        }

        String checkers;

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        checkers=sharedPref.getString(eholder,"none");//check if email stored before if not return none

        if(checkers.equals("none")){//if none then it's a new email
            //then store it in shared prefs as key with password as value
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(eholder,pholder);
            editor.apply();
            //Toast is a small message box that appears momentarily
            Toast.makeText(this,"registeration successful",Toast.LENGTH_SHORT);
            //auto log the user in after registeration
            loginAction(view);
        }
        else{//if email was found in shared prefs then it was registered before
            emailError.setText("email already exists");
        }

    }
}