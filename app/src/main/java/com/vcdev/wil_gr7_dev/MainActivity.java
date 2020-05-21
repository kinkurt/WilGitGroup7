package com.vcdev.wil_gr7_dev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.vcdev.wil_gr7_dev.create_scentence_builder_collections.CreateScentenceBuilderCollectionActivity;
import com.vcdev.wil_gr7_dev.view_scentence_builder_collections.ViewScentenceBuilderCollections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_scentence_builder).setOnClickListener(this);
        findViewById(R.id.btn_scentence_builder_student).setOnClickListener(this);
    }
    //link to create edit texts dynamically
    //https://stackoverflow.com/questions/23667850/create-certain-number-of-edittexts-based-on-user-input-in-android/40068579
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_scentence_builder:
                finish();
                startActivity(new Intent(MainActivity.this, CreateScentenceBuilderCollectionActivity.class));
                break;
            case R.id.btn_scentence_builder_student:
                finish();
                startActivity(new Intent(MainActivity.this, ViewScentenceBuilderCollections.class));
                break;
        }
    }
}
