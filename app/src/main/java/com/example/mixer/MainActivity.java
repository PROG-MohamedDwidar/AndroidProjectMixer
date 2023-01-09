package com.example.mixer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    String eholder,pholder;
    Context current;
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
        mAuth = FirebaseAuth.getInstance();
        current = this;
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

        //check the email
        mAuth.signInWithEmailAndPassword(eholder, pholder)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                            login = new Intent(MainActivity.this, dashboard.class);
                            startActivity(login);
                        } else {
                            // If sign in fails, display a message to the user.
                            passwordError.setText("Wrong email or password");


                        }

                        // ...
                    }
                });






//        String checkers;
//        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
//        checkers=sharedPref.getString(eholder,"none");//if it exists return the stored password else return "none"
//
//        if(pholder.equals(checkers)){//does the typed pass equal the stored one
//            //then let the user in
//            login = new Intent(MainActivity.this, dashboard.class);
//            startActivity(login);
//        }
//        else{
//            //Wrong password
//            passwordError.setText("wrong password or email");
//        }

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
        Pattern ppatt=Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
        if(ppatt.matcher(pholder).matches()){//if password format is right

//            passwordError.setText("must be 8-20 chars long and must have at least one number, upper case and symbol");
            passwordError.setText("");
        }
        else{//reset error hint if right
            passwordError.setText(pholder);
            return;
        }




        mAuth.createUserWithEmailAndPassword(eholder, pholder)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            passwordError.setText("success");
                            //FirebaseUser user = mAuth.getCurrentUser();
                            //Toast.makeText(current, "successful.", Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            passwordError.setText("fail check internet");
                            //Toast.makeText(current, "Authentication failed.", Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });



        /*SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
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
        }*/

    }
}