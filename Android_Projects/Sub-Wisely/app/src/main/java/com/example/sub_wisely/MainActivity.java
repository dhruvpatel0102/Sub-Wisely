package com.example.sub_wisely;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    TextView verifyMessage;
    Button verifyEmail;
    FirebaseAuth firebaseAuth;
    AlertDialog.Builder reset_alert;
    LayoutInflater inflater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        verifyMessage =findViewById(R.id.verifyEmailMsg);
        verifyEmail =findViewById(R.id.verifyEmailDashboard);


        //custom alert
        reset_alert = new AlertDialog.Builder(this);
        inflater = this.getLayoutInflater();

        // if email not verified
        if(!firebaseAuth.getCurrentUser().isEmailVerified()){
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

        Button logoutDasboard = findViewById(R.id.logoutDashboard);
        logoutDasboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
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