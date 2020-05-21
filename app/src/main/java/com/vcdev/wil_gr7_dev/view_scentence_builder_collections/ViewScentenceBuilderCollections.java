package com.vcdev.wil_gr7_dev.view_scentence_builder_collections;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vcdev.wil_gr7_dev.MainActivity;
import com.vcdev.wil_gr7_dev.R;
import com.vcdev.wil_gr7_dev.ScentenceBuilderActivity;

import java.util.ArrayList;
import java.util.List;

public class ViewScentenceBuilderCollections extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    Spinner levels, collections;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    List<String> levelList;
    List<String> collectionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_scentence_builder_collections);
        levels = findViewById(R.id.spinner_levels);
        levels.setOnItemSelectedListener(this);
        collections = findViewById(R.id.spinner_collections);
        levelList = new ArrayList<String>();
        collectionList = new ArrayList<String>();

        findViewById(R.id.btn_view_collection).setOnClickListener(this);
        loadUI();
    }

    public void loadUI() {
        loadLevelSpinner();

    }

    private void loadCollectionSpinner() {
        DatabaseReference myRef = database.getReference("users").child("teacher_admin").child(levels.getSelectedItem().toString()).child("ScentenceBuilder");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                collectionList.clear();
                collectionList.add("Select Collection");
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String value = ds.getKey();
                    if (value != null) {
                        collectionList.add(value);
                    }
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ViewScentenceBuilderCollections.this, android.R.layout.simple_spinner_item, collectionList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                collections.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void loadLevelSpinner() {

        DatabaseReference myRef = database.getReference("users").child("teacher_admin");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                levelList.clear();
                levelList.add("Select Level");
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String value = ds.getKey();
                    if (value != null) {
                        levelList.add(value);
                    }
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ViewScentenceBuilderCollections.this, android.R.layout.simple_spinner_item, levelList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                levels.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinner_levels:
                    loadCollectionSpinner();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_view_collection:
                startActivity(new Intent(ViewScentenceBuilderCollections.this, UnjumbleCollectionScentences.class)
                        .putExtra("level",levels.getSelectedItem().toString())
                        .putExtra("collection_name",collections.getSelectedItem().toString()));
        }
    }
}
