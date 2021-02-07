package com.example.universityportaluser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.universityportaluser.model_classes.UserRecord;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class Sign_up extends AppCompatActivity {

    EditText txtconfirmpassword,txtpassword,txtemail;
    Button btnsignup;
    TextView alreadyhaveaccount;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    CheckBox checkBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);




        txtconfirmpassword=findViewById(R.id.confirmpasswordsignup);
        txtpassword=findViewById(R.id.passwordsignup);
        txtemail=findViewById(R.id.emailsignup);
        btnsignup=findViewById(R.id.signupbtn);
        alreadyhaveaccount=findViewById(R.id.alreadyhaveaccount);
        progressBar=findViewById(R.id.progressbarsignup);
        checkBox=findViewById(R.id.checkboxsignup);



        mAuth= FirebaseAuth.getInstance();



        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    txtpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    txtconfirmpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    txtpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    txtconfirmpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });


        alreadyhaveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Sign_up.this,MainActivity.class);
                startActivity(i);
            }
        });

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                    String confirmpassword=txtconfirmpassword.getText().toString();
                    String password=txtpassword.getText().toString();
                    String email=txtemail.getText().toString();

                    if (!TextUtils.isEmpty(confirmpassword) || !TextUtils.isEmpty(password) || !TextUtils.isEmpty(email)){

                        if (password.equals(confirmpassword)){

                            progressBar.setVisibility(View.VISIBLE);
                            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()){
                                        sendtoMain();
                                        progressBar.setVisibility(View.INVISIBLE);
                                    }else {

                                        String error = task.getException().getMessage();
                                        Toast.makeText(Sign_up.this, "Error :"+error, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(Sign_up.this, "Password and confirm password is not matching", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(Sign_up.this, "Please fill all field", Toast.LENGTH_SHORT).show();
                    }




            }



        });
        progressBar.setVisibility(View.GONE);
    }

    private void sendtoMain() {
        Intent intent = new Intent(Sign_up.this, com.example.universityportaluser.Fragments.Dashboard.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            sendtoMain();
        }
    }
}