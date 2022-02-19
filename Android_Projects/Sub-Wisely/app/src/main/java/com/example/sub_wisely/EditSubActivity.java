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

import java.util.HashMap;
import java.util.Map;

public class EditSubActivity extends AppCompatActivity {

    private TextInputEditText subNameEdt, subDescEdt, subPriceEdt, paidOnBasis, courseImgEdt, courseLinkEdt;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    SubRVModal subRVModal;
    private ProgressBar loadingPB;
    // creating a string for our course id.
    private String subId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);
        // initializing all our variables on below line.
        Button addSubBtn = findViewById(R.id.addSubscriptionbtn);
        subNameEdt = findViewById(R.id.subNameET);
        subDescEdt = findViewById(R.id.subDescrptionET);
        subPriceEdt = findViewById(R.id.subFeesET);
        paidOnBasis = findViewById(R.id.paidOnBasisET);
       /* courseImgEdt = findViewById(R.id.idEdtCourseImageLink);
        courseLinkEdt = findViewById(R.id.idEdtCourseLink);*/
        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        // on below line we are getting our modal class on which we have passed.
        subRVModal = getIntent().getParcelableExtra("subs");
        Button deleteSubBtn = findViewById(R.id.idBtnDeleteCourse);

        if (subRVModal != null) {
            // on below line we are setting data to our edit text from our modal class.
            subNameEdt.setText(subRVModal.getSubName());
            subPriceEdt.setText(subRVModal.getSubPrice());
            paidOnBasis.setText(subRVModal.getPaidOnBasis());
           /* courseImgEdt.setText(courseRVModal.getCourseImg());
            courseLinkEdt.setText(courseRVModal.getCourseLink());*/
            subDescEdt.setText(subRVModal.getSubDesc());
            subId = subRVModal.getSubId();
        }

        // on below line we are initialing our database reference and we are adding a child as our course id.
        databaseReference = firebaseDatabase.getReference("Courses").child(subId);
        // on below line we are adding click listener for our add course button.
        addSubBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are making our progress bar as visible.
                loadingPB.setVisibility(View.VISIBLE);
                // on below line we are getting data from our edit text.
                String subName = subNameEdt.getText().toString();
                String subDesc = subDescEdt.getText().toString();
                String subPrice = subPriceEdt.getText().toString();
                String paidOnBasis = EditSubActivity.this.paidOnBasis.getText().toString();
                String courseImg = courseImgEdt.getText().toString();
                String courseLink = courseLinkEdt.getText().toString();
                // on below line we are creating a map for
                // passing a data using key and value pair.
                Map<String, Object> map = new HashMap<>();
                map.put("subName", subName);
                map.put("subDescription", subDesc);
                map.put("subPrice", subPrice);
                map.put("paidOnBasis", paidOnBasis);
                /*map.put("courseImg", courseImg);
                map.put("courseLink", courseLink);
                */map.put("subId", subId);

                // on below line we are calling a database reference on
                // add value event listener and on data change method
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // making progress bar visibility as gone.
                        loadingPB.setVisibility(View.GONE);
                        // adding a map to our database.
                        databaseReference.updateChildren(map);
                        // on below line we are displaying a toast message.
                        Toast.makeText(EditSubActivity.this, "Sub Updated..", Toast.LENGTH_SHORT).show();
                        // opening a new activity after updating our coarse.
                        startActivity(new Intent(EditSubActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // displaying a failure message on toast.
                        Toast.makeText(EditSubActivity.this, "Fail to update Sub..", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // adding a click listener for our delete course button.
        deleteSubBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calling a method to delete a course.
                deleteSub();
            }
        });

    }

    private void deleteSub() {
        // on below line calling a method to delete the course.
        databaseReference.removeValue();
        // displaying a toast message on below line.
        Toast.makeText(this, "Deleted Sub..", Toast.LENGTH_SHORT).show();
        // opening a main activity on below line.
        startActivity(new Intent(EditSubActivity.this, MainActivity.class));
    }
}