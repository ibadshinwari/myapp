package com.example.universityportaluser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.universityportaluser.Fragments.User_Profile;
import com.example.universityportaluser.model_classes.UserRecord;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class Update_Profile extends AppCompatActivity {

    EditText etfullname, etusername,etemail,etphonenumber;
    Button submitbtn;
    ProgressBar progressBar;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    StorageReference storageReference;
    UploadTask uploadTask;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference documentReference;
    UserRecord record;
    String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__profile);

        record = new UserRecord();
        etfullname = findViewById(R.id.fullnameup);
        etusername = findViewById(R.id.usernameup);
        etemail = findViewById(R.id.emailup);
        etphonenumber = findViewById(R.id.phonenumberup);
        submitbtn = findViewById(R.id.submitbtnup);
        progressBar = findViewById(R.id.progressbarup);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        currentUserId = user.getUid();
        documentReference = db.collection("user").document(currentUserId);
        databaseReference = database.getReference("All Users");

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadData();
                
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();



        documentReference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.getResult().exists()) {

                            String nameresult = task.getResult().getString("fullname");
                            String usernameresult = task.getResult().getString("username");
                            String emailresult = task.getResult().getString("email");
                            String phoneresult = task.getResult().getString("phonenumber");


                            etfullname.setText(nameresult);
                            etusername.setText(usernameresult);
                            etemail.setText(emailresult);
                            etphonenumber.setText(phoneresult);
                        }else{
                            Toast.makeText(Update_Profile.this, "No Profile Exists", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void uploadData() {

        final String fullname = etfullname.getText().toString();
        final String username = etusername.getText().toString();
        final String email = etemail.getText().toString();
        final String phonenumber = etphonenumber.getText().toString();




        if (!TextUtils.isEmpty(fullname) && !TextUtils.isEmpty(username) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(phonenumber)){
            progressBar.setVisibility(View.VISIBLE);

            Map <String, String> map = new HashMap<String, String>();
            map.put("fullname", fullname);
            map.put("username", username);
            map.put("email", email);
            map.put("phonenumber", phonenumber);
            map.put("uid", currentUserId);

            record.setFullname(fullname);
            record.setUsername(username);
            record.setEmail(email);
            record.setPhonenumber(phonenumber);
            record.setUid(currentUserId);

            databaseReference.child(currentUserId).setValue(record);
            documentReference.set(map)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(Update_Profile.this, "Profile Updated", Toast.LENGTH_SHORT).show();


                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(Update_Profile.this, Dashboard.class);
                                    startActivity(intent);
                                    finish();
                                }
                            },1000);
                        }
                    });
        }else{
            Toast.makeText(this, "Failed to update ", Toast.LENGTH_SHORT).show();
        }
    }
}