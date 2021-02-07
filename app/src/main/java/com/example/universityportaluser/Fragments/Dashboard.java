package com.example.universityportaluser.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.universityportaluser.Academics_Composing_Complaints;
import com.example.universityportaluser.Canteen_Composing_Complaints;
import com.example.universityportaluser.Departmental_Composing_Complaints;
import com.example.universityportaluser.Financial_Composing_Complaint;
import com.example.universityportaluser.MainActivity;
import com.example.universityportaluser.Progress;
import com.example.universityportaluser.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Dashboard extends Fragment implements View.OnClickListener {

    CardView btnfinancial, btndepartmental, btnacademics, btncanteen, btnprogress;
    ImageButton imageButtonMenu;
    TextView txtverifytext;
    Button verifynowbtn,btnsignout;
//    ImageButton editprofile,menudashboard;

    FirebaseAuth fAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.dashboard, container,false);
        return view;

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        menudashboard = getActivity().findViewById(R.id.dashboardmenu);
        btnfinancial=getActivity().findViewById(R.id.financialbtn);
        btndepartmental=getActivity().findViewById(R.id.departmentalbtn);
        btnacademics=getActivity().findViewById(R.id.academicsbtn);
        btncanteen=getActivity().findViewById(R.id.canteenbtn);
        btnprogress=getActivity().findViewById(R.id.progressbtn);

        fAuth = FirebaseAuth.getInstance();


        final FirebaseUser user = fAuth.getCurrentUser();



        btnfinancial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Financial_Composing_Complaint.class);
                startActivity(i);
            }
        });
        btndepartmental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Departmental_Composing_Complaints.class);
                startActivity(i);
            }
        });
        btnacademics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Academics_Composing_Complaints.class);
                startActivity(i);
            }
        });
        btncanteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Canteen_Composing_Complaints.class);
                startActivity(i);
            }
        });
        btnprogress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Progress.class);
                startActivity(i);
            }
        });
    }


    @Override
    public void onClick(View v) {


    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
        }


    }
}
