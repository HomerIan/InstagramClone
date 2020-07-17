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
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private EditText edtSignUpEmail;
    private EditText edtSignUpUsername;
    private EditText edtSignUpPassword;
    private Button btnSignUp;
    private Button btnLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setTitle("Sign Up");

        edtSignUpEmail = findViewById(R.id.edtSignUpEmail);
        edtSignUpUsername = findViewById(R.id.edtSignUpUsername);
        edtSignUpPassword = findViewById(R.id.edtSignUpPassword);
        edtSignUpPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){

                    onClick(btnSignUp);
                }
                return false;
            }
        });
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(this);
        btnLogIn = findViewById(R.id.btnLogIn);
        btnLogIn.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null) {
            ParseUser.getCurrentUser().logOut();
        }
    }//onCreate method

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btnSignUp:

                if (edtSignUpEmail.getText().toString().equals("") ||
                    edtSignUpUsername.getText().toString().equals("") ||
                    edtSignUpPassword.getText().toString().equals("")){

                    FancyToast.makeText(SignUp.this, "Please fill up the form",FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                } else {

                    final ParseUser appUser = new ParseUser();
                    appUser.setEmail(edtSignUpEmail.getText().toString());
                    appUser.setUsername(edtSignUpUsername.getText().toString());
                    appUser.setPassword(edtSignUpPassword.getText().toString());

                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Signing up " + edtSignUpUsername.getText().toString());
                    progressDialog.show();

                    appUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {

                            if (e == null) {
                                FancyToast.makeText(SignUp.this, appUser.getUsername() + " Successfully signed up", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                            } else {
                                FancyToast.makeText(SignUp.this, e.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                            }
                            progressDialog.dismiss();
                            edtSignUpEmail.getText().clear();
                            edtSignUpUsername.getText().clear();
                            edtSignUpPassword.getText().clear();
                        }
                    });
                }
                break;
            case R.id.btnLogIn:

                Intent intent = new Intent(SignUp.this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
    public void rootLayoutTapped(View view){

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        try {
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }catch (Exception e){
            e.printStackTrace();
            FancyToast.makeText(SignUp.this, e.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
        }
    }
}