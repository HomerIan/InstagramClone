package com.homerianreyes.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtLoginEmail;
    private EditText edtLoginPassword;
    private Button btnSignUpLoginActivity;
    private Button btnLoginLogInLoginActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Log In");

        edtLoginEmail = findViewById(R.id.edtLoginEmail);
        edtLoginPassword = findViewById(R.id.edtLoginPassword);
        edtLoginPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){

                    onClick(btnLoginLogInLoginActivity);
                }

                return false;
            }
        });
        btnSignUpLoginActivity = findViewById(R.id.btnSignUpLoginActivity);
        btnSignUpLoginActivity.setOnClickListener(this);
        btnLoginLogInLoginActivity = findViewById(R.id.btnLogInLoginActivity);
        btnLoginLogInLoginActivity.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null){
            ParseUser.getCurrentUser().logOut();
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btnLogInLoginActivity:

                if (edtLoginEmail.getText().toString().equals("") ||
                        edtLoginPassword.getText().toString().equals("")){

                    FancyToast.makeText(LoginActivity.this, "Username and Password required",FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                } else {

                    final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setMessage("Wait for a moment...");
                    progressDialog.show();

                    ParseUser.logInInBackground(edtLoginEmail.getText().toString(), edtLoginPassword.getText().toString(), new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {

                            if (user != null && e == null) {
                                FancyToast.makeText(LoginActivity.this, user.getUsername() + " Successfully Log In", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                            } else {
                                FancyToast.makeText(LoginActivity.this, e.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                            }
                            progressDialog.dismiss();
                            edtLoginEmail.getText().clear();
                            edtLoginPassword.getText().clear();
                        }
                    });
                }
                break;
            case R.id.btnSignUpLoginActivity:
                Intent intent = new Intent(LoginActivity.this, SignUp.class);
                startActivity(intent);
                break;
        }
    }
    public void rootLayoutTapped(View view) {

        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}