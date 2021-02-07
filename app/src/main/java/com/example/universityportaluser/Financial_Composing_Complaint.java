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

import com.example.universityportaluser.model_classes.CanteenComplaints;
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

public class Financial_Composing_Complaint extends AppCompatActivity {

    EditText txtcompose;
    Button btnsubmit;

    // new method
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference AllComplaints, UserComplaints;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference documentReference;
    FinancialComplaints member;
    String uid;

    // To Here

//    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
//    DatabaseReference userdatabase =database.child("Complaints").child("Financial Complaints");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financial_composing__complaint);

        // new method
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserid = user.getUid();
        // to here

        txtcompose=findViewById(R.id.composetxt);
        btnsubmit=findViewById(R.id.submitbtn);

        // new method
        documentReference = db.collection("user").document(currentUserid);

        AllComplaints = database.getReference("All Complaints Canteen");
        UserComplaints = database.getReference("User Complaints Canteen").child(currentUserid);

        member = new FinancialComplaints();
        // to here

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String complaints = txtcompose.getText().toString();

                Calendar cdate = Calendar.getInstance();
                SimpleDateFormat currentdate = new SimpleDateFormat("dd-MMMM-yyyy");
                final String savedate = currentdate.format(cdate.getTime());

                Calendar ctime = Calendar.getInstance();
                SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss");
                final String savetime = currenttime.format(ctime.getTime());

                String time = savedate +":"+ savetime;

                if (complaints != null){
                    member.setFinancialcomplaint(complaints);
                    member.setUserid(uid);
                    member.setTime(time);

                    String id = UserComplaints.push().getKey();
                    UserComplaints.child(id).setValue(member);

                    String child = AllComplaints.push().getKey();
                    member.setKey(id);
                    AllComplaints.child(child).setValue(member);

                    Toast.makeText(Financial_Composing_Complaint.this, "Submitted", Toast.LENGTH_SHORT).show();


                }else {

                    Toast.makeText(Financial_Composing_Complaint.this, "Add a complaint first", Toast.LENGTH_SHORT).show();
                }
//                if (v.getId()==R.id.submitbtn)
//                {
//                    String financialcomplaint=txtcompose.getText().toString();
//                    if (TextUtils.isEmpty(financialcomplaint))
//                    {
//                       txtcompose.setError("Write Your Complaint");
//                    }
//
//                    if (!TextUtils.isEmpty(financialcomplaint))
//                    {
//                        String ukey= userdatabase.push().getKey();
//                        FinancialComplaints financialComplaints = new FinancialComplaints(financialcomplaint);
//                        userdatabase.child(ukey).setValue(financialComplaints);
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
                            Toast.makeText(Financial_Composing_Complaint.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    // to here
}