package com.example.universityportaluser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.universityportaluser.model_classes.AcademicsComplaints;
import com.example.universityportaluser.model_classes.FinancialComplaints;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Academics_Composing_Complaints extends AppCompatActivity {

    EditText txtcompose;
    Button btnsubmit;

    // new method
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference AllComplaints, UserComplaints;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference documentReference;
    AcademicsComplaints member;
    String uid;

    // To Here

//    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
//    DatabaseReference userdatabase =database.child("Complaints").child("Academics Complaints");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academics__composing__complaints);

        // new method
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserid = user.getUid();
        // to here

        txtcompose=findViewById(R.id.composetxt);
        btnsubmit=findViewById(R.id.submitbtn);

        // new method
        documentReference = db.collection("user").document(currentUserid);

        AllComplaints = database.getReference("All Complaints Academics");
        UserComplaints = database.getReference("User Complaints Academics").child(currentUserid);

        member = new AcademicsComplaints();
        // to here


        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // new method
                String complaints = txtcompose.getText().toString();

                Calendar cdate = Calendar.getInstance();
                SimpleDateFormat currentdate = new SimpleDateFormat("dd-MMMM-yyyy");
                final String savedate = currentdate.format(cdate.getTime());

                Calendar ctime = Calendar.getInstance();
                SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss");
                final String savetime = currenttime.format(ctime.getTime());

                String time = savedate +":"+ savetime;

                if (complaints != null){
                    member.setAcademicscomplaints(complaints);
                    member.setUserid(uid);
                    member.setTime(time);

                    String id = UserComplaints.push().getKey();
                    UserComplaints.child(id).setValue(member);

                    String child = AllComplaints.push().getKey();
                    member.setKey(id);
                    AllComplaints.child(child).setValue(member);

                    Toast.makeText(Academics_Composing_Complaints.this, "Submitted", Toast.LENGTH_SHORT).show();

                    
                }else {

                    Toast.makeText(Academics_Composing_Complaints.this, "Add a complaint first", Toast.LENGTH_SHORT).show();
                }

//                if (v.getId()==R.id.submitbtn)
//                {
//                    String academicscomplaints=txtcompose.getText().toString();
//                    if (TextUtils.isEmpty(academicscomplaints))
//                    {
//                        txtcompose.setError("Write Your Complaint");
//                    }
//
//                    if (!TextUtils.isEmpty(academicscomplaints))
//                    {
//                        String ukey= userdatabase.push().getKey();
//                        AcademicsComplaints academicsComplaints = new AcademicsComplaints(academicscomplaints);
//                        userdatabase.child(ukey).setValue(academicscomplaints);
//
//                        finish();
//
//
//                    }
//
//                }
            }
        });
    }
// new method
    @Override
    protected void onStart() {
        super.onStart();
        documentReference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.getResult().exists()){
                            uid = task.getResult().getString("uid");
                        }else {
                            Toast.makeText(Academics_Composing_Complaints.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    // to here
}