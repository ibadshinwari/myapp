package com.example.universityportaluser.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.universityportaluser.R;
import com.example.universityportaluser.Sign_up;
import com.example.universityportaluser.Update_Profile;
import com.example.universityportaluser.model_classes.BottomSheetMenu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class User_Profile extends Fragment {

    ImageButton  editbtn,menubtn;
    TextView etname,etusername,etemail,etphone;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.user_profile, container,false);
        return view;

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        menubtn = getActivity().findViewById(R.id.menubtn);
        editbtn = getActivity().findViewById(R.id.editprofile);
        etname = getActivity().findViewById(R.id.nameuserprofile);
        etusername = getActivity().findViewById(R.id.usernameuserprofile);
        etemail = getActivity().findViewById(R.id.emailuserprofile);
        etphone = getActivity().findViewById(R.id.phonenumberuserprofile);


        // menu button
        menubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BottomSheetMenu bottomSheetMenu = new BottomSheetMenu();
                bottomSheetMenu.show(getFragmentManager(),"bottomsheet");
            }
        });
// edit button
        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Update_Profile.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String currentid = user.getUid();
            DocumentReference reference;
            FirebaseFirestore firestore = FirebaseFirestore.getInstance();

            reference = firestore.collection("user").document(currentid);

            reference.get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.getResult().exists()) {

                                String nameresult = task.getResult().getString("fullname");
                                String usernameresult = task.getResult().getString("username");
                                String emailresult = task.getResult().getString("email");
                                String phoneresult = task.getResult().getString("phonenumber");

                                etname.setText(nameresult);
                                etusername.setText(usernameresult);
                                etemail.setText(emailresult);
                                etphone.setText(phoneresult);

                            } else {
                                Intent intent = new Intent(getContext(), Update_Profile.class);
                                startActivity(intent);

                            }
                        }
                    });


    }
}
