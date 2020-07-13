package com.homerianreyes.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private EditText edtKickBoxerName;
    private EditText edtPunchSpeed;
    private EditText edtPunchPower;
    private EditText edtKickSpeed;
    private EditText edtKickPower;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(SignUp.this);

        edtKickBoxerName = findViewById(R.id.edtKickBoxerName);
        edtPunchSpeed = findViewById(R.id.edtPunchSpeed);
        edtPunchPower = findViewById(R.id.edtPunchPower);
        edtKickSpeed = findViewById(R.id.edtKickSpeed);
        edtKickPower = findViewById(R.id.edtKickPower);
    }

    @Override
    public void onClick(View v) {
        try {
            final ParseObject KickBoxer = new ParseObject("KickBoxer");
            KickBoxer.put("name", edtKickBoxerName.getText().toString());
            KickBoxer.put("punch_speed", Integer.parseInt(edtKickSpeed.getText().toString()));
            KickBoxer.put("punch_power", Integer.parseInt(edtPunchPower.getText().toString()));
            KickBoxer.put("kick_speed", Integer.parseInt(edtKickSpeed.getText().toString()));
            KickBoxer.put("kick_power", Integer.parseInt(edtKickPower.getText().toString()));
            KickBoxer.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                    if (e == null) {
                        FancyToast.makeText(SignUp.this, KickBoxer.get("name") + " is saved successfully!", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                    } else {
                        FancyToast.makeText(SignUp.this, e.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                    }
                }
            });
        }catch (Exception e){
            FancyToast.makeText(SignUp.this, e.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
        }
    }
}