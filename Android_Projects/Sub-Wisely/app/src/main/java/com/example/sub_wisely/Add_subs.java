package com.example.sub_wisely;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class Add_subs extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView sub_category_text;
    private Spinner spinner;
    Button addbtn,cancelbtn;
    FirebaseDatabase database;
    DatabaseReference reference;
    Member member;
    String item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subs);

        sub_category_text = findViewById(R.id.subCategory);
        spinner = findViewById(R.id.subSpinner);


       /* String [] category = {"Choose Category","Netflix","HBO","PRIME","OTHER"};
        spinner.setOnItemSelectedListener(this);*/

        member = new Member();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinner.setAdapter(arrayAdapter);


   /*     addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveValue(item);
            }
        });
*/
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Add_subs.this,Register.class));

            }
        });
    }

    private void SaveValue(String item) {
        if(item=="Choose Category"){
            Toast.makeText(this,"Please select category",Toast.LENGTH_SHORT).show();
        }
        else{
            member.setSpinner(item);
            String id = reference.push().getKey();
            reference.child(id).setValue(member);
            Toast.makeText(this,"added",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


        item=spinner.getSelectedItem().toString();
        sub_category_text.setText(item);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}