package com.vcdev.wil_gr7_dev;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.vcdev.wil_gr7_dev.models.ScentenceObject;

import java.util.Random;

public class ScentenceBuilderActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ScentenceBuilderActivit";
    //widgets
    TextView col, scentence;
    EditText col_name_input, scentence_input;
    Button btn_create_collection, btn_add_to_collection, btn_create_new_collection;
    //vars
    private ScentenceObject scentenceObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scentence_builder);
        col = findViewById(R.id.tv_col_name);

        scentence = findViewById(R.id.tv_scentance);

        col_name_input = findViewById(R.id.input_collection_name);

        scentence_input = findViewById(R.id.input_scentence);

        btn_create_collection = findViewById(R.id.btn_create_col);
        btn_create_collection.setOnClickListener(this);

        btn_add_to_collection = findViewById(R.id.btn_add_scentence);
        btn_add_to_collection.setOnClickListener(this);

        btn_create_new_collection = findViewById(R.id.btn_create_new_collection);
        btn_create_new_collection.setOnClickListener(this);
    }

    private void addNewScentenceToCollection() {
        String collection_name = col_name_input.getText().toString();

        String normalScentence = scentence_input.getText().toString();
        String jumbled_scentence = "";

        String[] normal = normalScentence.split(" ");
        String[] jumbled = jumbleScentence(normal);

        for (String word : jumbled) {
            jumbled_scentence += word + " ";
        }
        Log.d(TAG, "addNewScentenceToCollection: scentence collection name: "+collection_name);
        Log.d(TAG, "addNewScentenceToCollection: normal scentence: "+ normalScentence);
        Log.d(TAG, "addNewScentenceToCollection: jumbled scentence: "+jumbled_scentence);
        addToFirestore(normalScentence, jumbled_scentence);
    }

    private String[] jumbleScentence(String[] arr) {
        Random r = new Random();
        for (int i = arr.length - 1; i > 0; i--) {
            int rand = r.nextInt(i);
            String temp = arr[i];
            arr[i] = arr[rand];
            arr[rand] = temp;
        }
        return arr;
    }

    private void addToFirestore(String scentence, String jumbledScentence) {
        // create a collection of type scentence
        // and jumbledScentence and add it to
        // the collection created
        scentenceObject = new ScentenceObject(scentence, jumbledScentence);
        Log.d(TAG, "addToFirestore: scentences"+scentenceObject.toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_create_col:
                col.setVisibility(View.INVISIBLE);
                col_name_input.setVisibility(View.INVISIBLE);
                btn_create_collection.setVisibility(View.INVISIBLE);

                scentence.setVisibility(View.VISIBLE);
                scentence_input.setVisibility(View.VISIBLE);
                btn_add_to_collection.setVisibility(View.VISIBLE);

                btn_create_new_collection.setVisibility(View.VISIBLE);
                break;

            case R.id.btn_add_scentence:
                addNewScentenceToCollection();
                break;

            case R.id.btn_create_new_collection:
                col.setVisibility(View.VISIBLE);
                col_name_input.setVisibility(View.VISIBLE);
                btn_create_collection.setVisibility(View.VISIBLE);

                scentence.setVisibility(View.INVISIBLE);
                scentence_input.setVisibility(View.INVISIBLE);
                btn_add_to_collection.setVisibility(View.INVISIBLE);
                break;
        }
    }
}
