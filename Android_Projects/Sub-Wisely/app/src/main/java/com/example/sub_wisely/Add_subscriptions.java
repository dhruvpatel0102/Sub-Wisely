package com.example.sub_wisely;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Add_subscriptions extends AppCompatActivity {

    private Button addSubBtn;
    private TextInputEditText subName, subDesc, subPrice, paidOnBasis, courseImgEdt, courseLinkEdt;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private ProgressBar loadingPB;
    private String subId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subscriptions2);
        // initializing all our variables.
        addSubBtn = findViewById(R.id.addSubscriptionbtn);
        subName = findViewById(R.id.subNameET);
        subDesc = findViewById(R.id.subDescrptionET);
        subPrice = findViewById(R.id.subFeesET);
        paidOnBasis = findViewById(R.id.paidOnBasisET);
        /*courseImgEdt = findViewById(R.id.idEdtCourseImageLink);
        courseLinkEdt = findViewById(R.id.idEdtCourseLink);
        */
        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        // on below line creating our database reference.
        databaseReference = firebaseDatabase.getReference("Courses");
        // adding click listener for our add course button.
        addSubBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingPB.setVisibility(View.VISIBLE);
                // getting data from our edit text.
                String subName = Add_subscriptions.this.subName.getText().toString();
                String subDesc = Add_subscriptions.this.subDesc.getText().toString();
                String subPrice = Add_subscriptions.this.subPrice.getText().toString();
                String paidOnBasis = Add_subscriptions.this.paidOnBasis.getText().toString();
         /*       String courseImg = courseImgEdt.getText().toString();
                String courseLink = courseLinkEdt.getText().toString();
         */       subId = subName;
                // on below line we are passing all data to our modal class.
                SubRVModal subRVModal = new SubRVModal(subId, subName, subDesc, subPrice, paidOnBasis);// courseImg, courseLink);
                // on below line we are calling a add value event
                // to pass data to firebase database.
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // on below line we are setting data in our firebase database.
                        databaseReference.child(subId).setValue(subRVModal);
                        // displaying a toast message.
                        Toast.makeText(Add_subscriptions.this, "Sub Added..", Toast.LENGTH_SHORT).show();
                        // starting a main activity.
                        startActivity(new Intent(Add_subscriptions.this, MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // displaying a failure message on below line.
                        Toast.makeText(Add_subscriptions.this, "Fail to add Sub..", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}