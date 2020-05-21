package com.vcdev.wil_gr7_dev.create_scentence_builder_collections;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vcdev.wil_gr7_dev.MainActivity;
import com.vcdev.wil_gr7_dev.R;

import java.util.ArrayList;
import java.util.List;

public class AddScentencesToCollectionActivity extends AppCompatActivity implements View.OnClickListener {
    EditText scentence;
    TextView textViewScentence;
    int numberOfScentencesRequired;
    int numOfScentences;
    Button btnAddScentence,btnCreateNewCollection;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    List<String> listOfScentences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_scentences_to_collection);
        numOfScentences=0;
        scentence = findViewById(R.id.input_scentence);
        textViewScentence = findViewById(R.id.tv_scentance);
        btnAddScentence=findViewById(R.id.btn_add_scentence);
        btnCreateNewCollection=findViewById(R.id.btn_create_new_collection);
        textViewScentence.setText("Please enter scentence number: " + (numOfScentences+1));
        btnCreateNewCollection.setVisibility(View.INVISIBLE);
        findViewById(R.id.btn_add_scentence).setOnClickListener(this);
        findViewById(R.id.btn_create_new_collection).setOnClickListener(this);
        findViewById(R.id.btn_cancel).setOnClickListener(this);
        numberOfScentencesRequired = getIntent().getIntExtra("num_of_scentences", 0);
        listOfScentences = new ArrayList<String>();
    }

    public void addScentenceToCollection() {
        DatabaseReference myRef = database.getReference("users").child("teacher_admin");
        DatabaseReference levelRef = myRef.child(getIntent().getStringExtra("level"));
        DatabaseReference scentenceCollectionRef=levelRef.child("ScentenceBuilder");
        DatabaseReference collectionRef = scentenceCollectionRef.child(getIntent().getStringExtra("collection_name"));
        numOfScentences+=1;
        textViewScentence.setText("Please enter scentence number: " + (numOfScentences+1));
        if (numberOfScentencesRequired >= numOfScentences) {
            listOfScentences.add(scentence.getText().toString().trim());
            if (numberOfScentencesRequired == numOfScentences){
                collectionRef.setValue(listOfScentences);
                textViewScentence.setText("All the scentences have been added!!");
                Toast.makeText(this, "All scentences have been added!", Toast.LENGTH_LONG).show();
                btnAddScentence.setVisibility(View.INVISIBLE);
                scentence.setVisibility(View.INVISIBLE);
                btnCreateNewCollection.setVisibility(View.VISIBLE);
            }
        }
        scentence.setText("");
    }

    public void deleteObjectFromFirebase() {
        DatabaseReference myRef = database.getReference("users").child("teacher_admin");
        DatabaseReference levelRef = myRef.child(getIntent().getStringExtra("level"));
        DatabaseReference scentenceCollectionRef=levelRef.child("ScentenceBuilder");
        DatabaseReference collectionRef = scentenceCollectionRef.child(getIntent().getStringExtra("collection_name"));
        collectionRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(AddScentencesToCollectionActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();     
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_scentence:
                addScentenceToCollection();
                break;
            case R.id.btn_cancel:
                startActivity(new Intent(AddScentencesToCollectionActivity.this, MainActivity.class));
                deleteObjectFromFirebase();
                break;
        }
    }

    
}
