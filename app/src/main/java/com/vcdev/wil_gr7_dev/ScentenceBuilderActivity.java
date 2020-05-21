package com.vcdev.wil_gr7_dev;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.vcdev.wil_gr7_dev.models.ScentenceCollectionObject;
import com.vcdev.wil_gr7_dev.models.ScentenceObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ScentenceBuilderActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private static final String TAG = "ScentenceBuildActivity";
    //widgets
    ImageButton btnAddLevel;
    Spinner levelsDropDown ;
    TextView col, scentence;
    EditText col_name_input, scentence_input;
    Button btn_create_collection, btn_add_to_collection, btn_create_new_collection;
    //vars
    View mParentLayout;
    List<String> scentences;
    private ScentenceObject scentenceObject;
    List<String> levels;
    private String m_Text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scentence_builder);

        findViewById(R.id.btn_add_level).setOnClickListener(this);





        levelsDropDown = (Spinner) findViewById(R.id.spinner_levels);
        levelsDropDown.setOnItemSelectedListener(this);

        mParentLayout = findViewById(android.R.id.content);

        col = findViewById(R.id.tv_col_name);

        scentence = findViewById(R.id.tv_scentance);

       // col_name_input = findViewById(R.id.input_collection_name);

        scentence_input = findViewById(R.id.input_scentence);

        btn_add_to_collection = findViewById(R.id.btn_add_scentence);
        btn_add_to_collection.setOnClickListener(this);

        btn_create_new_collection = findViewById(R.id.btn_create_new_collection);
        btn_create_new_collection.setOnClickListener(this);

        scentences=new ArrayList<>();

        refreshLevelList();
    }

    public void addLevel(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("users").child("teacher_admin").child("levels");




    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add_level:
                addLevel();
                refreshLevelList();
                break;
        }
    }

    public void refreshLevelList(){
        try {
            levels=new ArrayList<String>();
            final List<String> levelList = new ArrayList<String>();

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("users").child("teacher_admin").child("Levels");

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    levelList.clear();
                    levelList.add("Select Level");
                    for(DataSnapshot ds:dataSnapshot.getChildren()){
                        String value = ds.getKey();
                        if(value!=null){
                            levelList.add(value);
                        }
                    }

                    levels=levelList;

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ScentenceBuilderActivity.this, android.R.layout.simple_spinner_item, levelList);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    levelsDropDown.setAdapter(arrayAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }catch (Exception e){
            Log.d(TAG, "refreshLevelList: Exception: "+e.getMessage());
        }

        

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position!=0){
            Toast.makeText(this, "you have selected "+parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
