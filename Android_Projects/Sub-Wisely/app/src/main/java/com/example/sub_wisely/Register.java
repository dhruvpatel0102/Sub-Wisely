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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    EditText registerFullName,registerEmail,registerPassword,registerConfirmPassword;
    Button registerButton,gotoLogin;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerFullName =findViewById(R.id.registerFullName);
        registerEmail = findViewById(R.id.registerEmail);
        registerPassword = findViewById(R.id.registerPassword);
        registerConfirmPassword = findViewById(R.id.registerConfirmPassword);
        registerButton = findViewById(R.id.registerButton);
        gotoLogin = findViewById(R.id.gotoLogin);

        firebaseAuth = FirebaseAuth.getInstance();

        gotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // extract data from register activity

                String fullName = registerFullName.getText().toString();
                String email = registerEmail.getText().toString();
                String password = registerPassword.getText().toString();
                String conpassword = registerConfirmPassword.getText().toString();

                if(fullName.isEmpty()){
                    registerFullName.setError("Full name is Required.");
                    return;
                }
                if(email.isEmpty()){
                    registerEmail.setError("Email is Required.");
                    return;
                }if(password.isEmpty()){
                    registerPassword.setError("Password is Required.");
                    return;
                }
                if(conpassword.isEmpty()){
                    registerConfirmPassword.setError("Password is Required.");
                    return;
                }

                if(!password.equals(conpassword)){
                    registerConfirmPassword.setError("Password don't match");
                    return;
                }

                //all of the data is validated from above condition

                //Toast.makeText(Register.this,"Registeration Success",Toast.LENGTH_SHORT).show();

                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        // user message if success on register
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

    }
}