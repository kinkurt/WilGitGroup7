package com.vcdev.wil_gr7_dev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_scentence_builder).setOnClickListener(this);
    }
    //link to create edit texts dynamically
    //https://stackoverflow.com/questions/23667850/create-certain-number-of-edittexts-based-on-user-input-in-android/40068579
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_scentence_builder:
                finish();
                startActivity(new Intent(MainActivity.this,ScentenceBuilderActivity.class));
                break;
        }
    }
}
