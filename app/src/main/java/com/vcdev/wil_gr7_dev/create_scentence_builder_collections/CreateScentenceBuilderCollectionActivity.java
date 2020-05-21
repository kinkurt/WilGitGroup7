package com.vcdev.wil_gr7_dev.create_scentence_builder_collections;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.vcdev.wil_gr7_dev.ColAdapter;
import com.vcdev.wil_gr7_dev.R;

import java.util.ArrayList;
import java.util.List;

public class CreateScentenceBuilderCollectionActivity extends AppCompatActivity implements View.OnClickListener{
    private ColAdapter adapter;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private CollectionReference col_ref;
    Spinner levelsDropDown;
    EditText collectionName,numOfScentences;

    private String m_Text = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_scentence_builder_collection);

        levelsDropDown=findViewById(R.id.spinner_levels);
        collectionName=findViewById(R.id.collection_name);
        numOfScentences=findViewById(R.id.num_of_scentences);

        findViewById(R.id.btn_create_collection).setOnClickListener(this);

        String[] levels = new String[]{"Level 1","Level 2","Level 3","Level 4","Level 5","Level 6","Level 7","Level 8","Level 9","Level 10"};
        ArrayAdapter<String> arrayAdapter= new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,levels);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        levelsDropDown=(Spinner)findViewById(R.id.spinner_levels);
        levelsDropDown.setAdapter(arrayAdapter);

    }
    public void createCollection(){
        DatabaseReference myRef = database.getReference("users").child("teacher_admin");
        DatabaseReference levelRef=myRef.child(levelsDropDown.getSelectedItem().toString());
        DatabaseReference scentenceCollectionRef=levelRef.child("ScentenceBuilder");
        DatabaseReference collectionRef=scentenceCollectionRef.child(collectionName.getText().toString());
        final int qty_scentences=Integer.parseInt(numOfScentences.getText().toString());

        List<String> tempScentences= new ArrayList<String>();
        for(int i=0;i<qty_scentences;i++){
            tempScentences.add("scentence #"+(i+1));
        }

        collectionRef.setValue(tempScentences).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(CreateScentenceBuilderCollectionActivity.this, "Success", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CreateScentenceBuilderCollectionActivity.this,AddScentencesToCollectionActivity.class)
                        .putExtra("num_of_scentences",qty_scentences)
                        .putExtra("level",levelsDropDown.getSelectedItem().toString())
                        .putExtra("collection_name",collectionName.getText().toString()));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CreateScentenceBuilderCollectionActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void newLevel() {
        //not implemented yet

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Level");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

        DatabaseReference myRef = database.getReference("users").child("teacher_admin");

    }

    @Override
    public void onClick(View v) {
      switch (v.getId()){
          case R.id.btn_create_collection:
              createCollection();
              break;
          case R.id.btn_add_level:
             // newLevel();
          break;
      }
    }


}
