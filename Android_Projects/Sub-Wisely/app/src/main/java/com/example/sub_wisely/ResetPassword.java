package com.example.sub_wisely;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ResetPassword extends AppCompatActivity {

    EditText resetNewPassword,resetConfirmPassword;
    Button updatePass;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        //assigning local variable with xml id
        resetNewPassword = findViewById(R.id.resetNewPassword);
        resetConfirmPassword =findViewById(R.id.resetConfirmPassword);

        user = FirebaseAuth.getInstance().getCurrentUser();
        updatePass = findViewById(R.id.updatePassword);
        updatePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //checks if it is not empty
                if(resetNewPassword.getText().toString().isEmpty()){
                    resetNewPassword.setError("Required Fields");
                }

                if(resetConfirmPassword.getText().toString().isEmpty()){
                    resetConfirmPassword.setError("Required Field");
                }

                //if reset pass does not match
                if(resetNewPassword.getText().toString().equals(resetConfirmPassword.getText().toString())){
                    resetConfirmPassword.setError("Password Does not match");
                }

                //if there is a sucess event
                user.updatePassword(resetNewPassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ResetPassword.this,"Password Updated",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                    }
                    //if it fails
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ResetPassword.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}