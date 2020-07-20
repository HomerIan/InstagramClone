package com.homerianreyes.instagramclone;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileTab extends Fragment {

    private EditText edtMyProfileName;
    private EditText edtMyBio;
    private EditText edtMyProfessoion;
    private EditText edtMyHobbies;
    private EditText edtMyFavSport;
    private Button btnUpdateInfo;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileTab() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileTab.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileTab newInstance(String param1, String param2) {
        ProfileTab fragment = new ProfileTab();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_tab, container, false);
        edtMyProfileName = view.findViewById(R.id.edtMyProfileName);
        edtMyBio = view.findViewById(R.id.edtMyBio);
        edtMyProfessoion = view.findViewById(R.id.edtMyProfession);
        edtMyHobbies = view.findViewById(R.id.edtMyHobbies);
        edtMyFavSport = view.findViewById(R.id.edtMyFavSport);

        btnUpdateInfo = view.findViewById(R.id.btnUpdateInfo);

        final ParseUser parseUser = ParseUser.getCurrentUser();

        if (parseUser.get("profileName") == null ||
            parseUser.get("profileBio") == null ||
            parseUser.get("profileProfession") == null ||
            parseUser.get("profileHobbies") == null ||
            parseUser.get("profileFavSport") == null){
            edtMyProfileName.setText("");
            edtMyBio.setText("");
            edtMyProfessoion.setText("");
            edtMyHobbies.setText("");
            edtMyFavSport.setText("");
        } else {

            edtMyProfileName.setText(parseUser.get("profileName").toString());
            edtMyBio.setText(parseUser.get("profileBio").toString());
            edtMyProfessoion.setText(parseUser.get("profileProfession").toString());
            edtMyHobbies.setText(parseUser.get("profileHobbies").toString());
            edtMyFavSport.setText(parseUser.get("profileFavSport").toString());
        }
        btnUpdateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseUser.put("profileName",edtMyProfileName.getText().toString());
                parseUser.put("profileBio",edtMyBio.getText().toString());
                parseUser.put("profileProfession", edtMyProfessoion.getText().toString());
                parseUser.put("profileHobbies", edtMyHobbies.getText().toString());
                parseUser.put("profileFavSport", edtMyFavSport.getText().toString());

                final ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Updating "+ parseUser.getUsername() +"'s Profile");
                progressDialog.show();
                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        if (e == null){
                            FancyToast.makeText(getContext(), "Updated Successfully", FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show();
                        }else{
                            FancyToast.makeText(getContext(), e.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                        }
                        progressDialog.dismiss();
                    }
                });
            }
        });
        return view;
    }
}