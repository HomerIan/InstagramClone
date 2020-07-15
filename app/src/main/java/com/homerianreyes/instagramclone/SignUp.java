package com.homerianreyes.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private EditText edtKickBoxerName;
    private EditText edtPunchSpeed;
    private EditText edtPunchPower;
    private EditText edtKickSpeed;
    private EditText edtKickPower;
    private Button btnSave;
    private Button btnGetAllData;
    private TextView txtGetData;
    private Button btnNextActivity;

    private String allKickBoxers;

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
        btnGetAllData = findViewById(R.id.btnGetAllData);
        btnNextActivity = findViewById(R.id.btnNextActivity);

        txtGetData = findViewById(R.id.txtGetData);
        txtGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> parseQuery = com.parse.ParseQuery.getQuery("KickBoxer");
                parseQuery.getInBackground("V008Wk0N7O", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if(object != null && e == null){
                            txtGetData.setText(object.get("name") + "-" + "Punch Power: " + object.get("punch_power"));
                        }
                    }
                });
            }
        });

        btnGetAllData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allKickBoxers = "";
                ParseQuery<ParseObject> queryAll = ParseQuery.getQuery("KickBoxer");

                //queryAll.whereGreaterThan("punch_power",500);
                queryAll.whereGreaterThanOrEqualTo("punch_power", 1000);
                queryAll.setLimit(1);

                queryAll.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {

                        if (e == null) {

                            for(ParseObject kickBoxer : objects){
                                allKickBoxers = allKickBoxers + kickBoxer.get("name") + " "
                                                            + kickBoxer.get("punch_speed") + " "
                                                            + kickBoxer.get("punch_power") + " "
                                                            + kickBoxer.get("kick_speed") + " "
                                                            + kickBoxer.get("kick_power") + "\n";
                            }

                            if(objects.size() > 0){
                                FancyToast.makeText(SignUp.this, allKickBoxers, FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                            }else{
                                FancyToast.makeText(SignUp.this, e.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                            }
                        }
                    }
                });
            }
        });

        btnNextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this,SignUpLoginActivity.class);
                startActivity(intent);
            }
        });
    }//onCreate method

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