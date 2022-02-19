package com.example.sub_wisely;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SubRVAdapter.SubClickInterface {
    TextView verifyMessage;
    Button verifyEmail;
    FirebaseAuth firebaseAuth;
    AlertDialog.Builder reset_alert;
    LayoutInflater inflater;

    private RecyclerView subRV;
    private FirebaseAuth mAuth;
    private ProgressBar loadingPB;
    private ArrayList<SubRVModal> subRVModalArrayList;
    private FloatingActionButton addSubFAB;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private SubRVAdapter subRVAdapter;
    private RelativeLayout bottomSheetRL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        subRV = findViewById(R.id.idRVSubs);
        //homeRL = findViewById(R.id.idRLBSheet);
        loadingPB = findViewById(R.id.idPBLoading);
        addSubFAB = findViewById(R.id.idFABAddSub);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
      /*  verifyMessage =findViewById(R.id.verifyEmailMsg);
        verifyEmail =findViewById(R.id.verifyEmailDashboard);
*/








        subRVModalArrayList = new ArrayList<>();
        // on below line we are getting database reference.
        databaseReference = firebaseDatabase.getReference("Courses");
        // on below line adding a click listener for our floating action button.
        addSubFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // opening a new activity for adding a course.
                Intent i = new Intent(MainActivity.this, Add_subscriptions.class);
                startActivity(i);
            }
        });
        // on below line initializing our adapter class.
        subRVAdapter = new SubRVAdapter(subRVModalArrayList, this, this::onCourseClick);
        // setting layout malinger to recycler view on below line.
        subRV.setLayoutManager(new LinearLayoutManager(this));
        // setting adapter to recycler view on below line.
        subRV.setAdapter(subRVAdapter);
        // on below line calling a method to fetch courses from database.
        getSubs();








        //custom alert
        reset_alert = new AlertDialog.Builder(this);
        inflater = this.getLayoutInflater();

        // if email not verified
  /*      if(!firebaseAuth.getCurrentUser().isEmailVerified()){
            verifyEmail.setVisibility(View.VISIBLE);
            verifyMessage.setVisibility(View.VISIBLE);

        }

        verifyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send verification email

                firebaseAuth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this,"Verrification Email Sent",Toast.LENGTH_SHORT).show();
                        verifyEmail.setVisibility(View.GONE);
                        verifyMessage.setVisibility(View.GONE);
                    }
                });
            }
        });
*/
    }

    private void getSubs() {
        // on below line clearing our list.
        subRVModalArrayList.clear();
        // on below line we are calling add child event listener method to read the data.
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // on below line we are hiding our progress bar.
                loadingPB.setVisibility(View.GONE);
                // adding snapshot to our array list on below line.
                subRVModalArrayList.add(snapshot.getValue(SubRVModal.class));
                // notifying our adapter that data has changed.
                subRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // this method is called when new child is added
                // we are notifying our adapter and making progress bar
                // visibility as gone.
                loadingPB.setVisibility(View.GONE);
                subRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                // notifying our adapter when child is removed.
                subRVAdapter.notifyDataSetChanged();
                loadingPB.setVisibility(View.GONE);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // notifying our adapter when child is moved.
                subRVAdapter.notifyDataSetChanged();
                loadingPB.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public void onCourseClick(int position) {
        displayBottomSheet(subRVModalArrayList.get(position));
    }


    private void displayBottomSheet(SubRVModal modal) {
        // on below line we are creating our bottom sheet dialog.
        final BottomSheetDialog bottomSheetTeachersDialog = new BottomSheetDialog(this);
        // on below line we are inflating our layout file for our bottom sheet.
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_layout, bottomSheetRL);
        // setting content view for bottom sheet on below line.
        bottomSheetTeachersDialog.setContentView(layout);
        // on below line we are setting a cancelable
        bottomSheetTeachersDialog.setCancelable(false);
        bottomSheetTeachersDialog.setCanceledOnTouchOutside(true);
        // calling a method to display our bottom sheet.
        bottomSheetTeachersDialog.show();
        // on below line we are creating variables for
        // our text view and image view inside bottom sheet
        // and initialing them with their ids.
        TextView courseNameTV = layout.findViewById(R.id.subNameET);
        TextView courseDescTV = layout.findViewById(R.id.subDescrptionET);
        TextView suitedForTV = layout.findViewById(R.id.idTVSuitedFor);
        TextView priceTV = layout.findViewById(R.id.idTVCoursePrice);
        ImageView courseIV = layout.findViewById(R.id.idIVSub);
        // on below line we are setting data to different views on below line.
        courseNameTV.setText(modal.getSubName());
        courseDescTV.setText(modal.getSubDesc());
        suitedForTV.setText("Paid on Basis " + modal.getPaidOnBasis());
        priceTV.setText("$" + modal.getSubPrice());
        //Picasso.get().load(modal.getCourseImg()).into(courseIV);
        Button viewBtn = layout.findViewById(R.id.idBtnVIewDetails);
        Button editBtn = layout.findViewById(R.id.idBtnEditCourse);

        // adding on click listener for our edit button.
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are opening our EditCourseActivity on below line.
                Intent i = new Intent(MainActivity.this, EditSubActivity.class);
                // on below line we are passing our course modal
                i.putExtra("sub", modal);
                startActivity(i);
            }
        });
        // adding click listener for our view button on below line.
        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are navigating to browser
                // for displaying course details from its url
                Intent i = new Intent(Intent.ACTION_VIEW);
                //i.setData(Uri.parse(modal.getCourseLink()));
                startActivity(i);
            }
        });
    }


























    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.resetPassword){
            startActivity(new Intent(getApplicationContext(),ResetPassword.class));
        }

        if(item.getItemId()==R.id.updateEmailMenu){

            //custom alert dialog
            View v = inflater.inflate(R.layout.reset_alert, null);
            reset_alert.setTitle("Update Email")
                    .setMessage("Enter New Email")
                    .setPositiveButton("Update EMail", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //validate email address

                            EditText email = v.findViewById(R.id.reset_email_alert);
                            if (email.getText().toString().isEmpty()) {
                                email.setError("Required Field");
                                return;
                            }

                            //send the reset link

                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            user .updateEmail(email.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                    Toast.makeText(MainActivity.this,"Email Update", Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                                }
                            });


                        }
                    }).setNegativeButton("Cancel", null)
                    .setView(v)
                    .create().show();

        }

        if(item.getItemId()==R.id.delteAccountMenu){
            reset_alert.setTitle("Delete Account permanently?")
                    .setMessage("Are you Sure?")
                    .setPositiveButton("Ok",new DialogInterface.OnClickListener(){
                     @Override
                    public void onClick(DialogInterface dialog, int which){
                         FirebaseUser user = firebaseAuth.getCurrentUser();
                         user.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                             @Override
                             public void onSuccess(Void unused) {
                                 Toast.makeText(MainActivity.this,"Account Deleted",Toast.LENGTH_SHORT).show();
                                 firebaseAuth.signOut();
                                 startActivity(new Intent(getApplicationContext(),Login.class));
                                 finish();
                             }
                         }).addOnFailureListener(new OnFailureListener() {
                             @Override
                             public void onFailure(@NonNull Exception e) {
                                 Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                             }
                         });


                     }

            }).setNegativeButton("Cancel",null)
            .create().show();
        }
        return super.onOptionsItemSelected(item);
    }


}