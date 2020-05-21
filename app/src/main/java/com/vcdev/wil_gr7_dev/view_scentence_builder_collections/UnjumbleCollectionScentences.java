package com.vcdev.wil_gr7_dev.view_scentence_builder_collections;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.vcdev.wil_gr7_dev.MainActivity;
import com.vcdev.wil_gr7_dev.R;

import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UnjumbleCollectionScentences extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "UnjumbleCollectionScent";
    //ui components
    EditText scentence;
    TextView textViewJumbledScentence;

    //variables
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    String level, collection;
    List<String> scentenceList;
    List<String> userAnswers;
    int mark;
    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unjumble_collection_scentences);

        //initialize ui components
        scentence = findViewById(R.id.scentence_input);
        textViewJumbledScentence = findViewById(R.id.tv_jumbled_scentence);

        //get intent information from previous activity
        level = getIntent().getStringExtra("level");
        collection = getIntent().getStringExtra("collection_name");

        //set on click listener
        findViewById(R.id.btn_next_scentence).setOnClickListener(this);

        //initializing lists
        scentenceList = new ArrayList<String>();
        userAnswers = new ArrayList<String>();

        //initial counter is 0
        counter = 0;
        //initial mark is 0
        mark=0;
        //method runs at activity start and pulls the sentences from the database into a List of type string
        pullScentences();

    }

    public void pullScentences() {
        //generic list object to pull the items from the database
        final GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {};

        //pulling intent data from previous activity
        String level=getIntent().getStringExtra("level");
        String collectionName=getIntent().getStringExtra("collection_name");

        //database reference to admin account tree
        DatabaseReference myRef = database.getReference("users").child("teacher_admin").child(level).child("ScentenceBuilder").child(collectionName);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String value=ds.getValue(String.class);
                    //call method to add the scentence to the global scentenceList
                    addScentenceToList(value);
                }
                //call method to iterate through the scentence list
                //this method is called first when the info is pulled and then later when the user clicks on "Next"
                iterateThroughList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: "+databaseError.getMessage());
            }

        });
    }

    public void addScentenceToList(String value){
        scentenceList.add(value);
        //ads the data from snapshot to the scentenceList
    }

    public String jumbleScentence(String scentence) {
        //method takes in a scentence, jumbles it and then returns the jumbled scentence
        String[] arr = scentence.split(" ");
        Collections.shuffle(Arrays.asList(arr));
        String jumbledScentence = "";
        for (String item : arr) {
            jumbledScentence += item + " ";
        }
        return jumbledScentence.trim();
    }

    public void iterateThroughList(){
        //clears the text from the textview
        scentence.getText().clear();

        //if the counter > size of scentence list then it means that they have entered all the answers
        //the post to firebase method is then called
        if(!(counter>scentenceList.size()-1)){
            //post the current item in the scentence list to the jumble method and receives the jumbled string
            //which it then adds to the textview
            String jumbledWord=jumbleScentence(scentenceList.get(counter));
            textViewJumbledScentence.setText(jumbledWord);
        }else{
            //calls post to firebase method and passes the getResult method
            postToFirebase(getResult());
        }
    }

    public void captureUserAnswer(){
        //method checks to see if the scentence field is null (..means they havent added an answer)
        if(scentence!=null){
            //adds answer tot the userAnswers list so that we can calculate the marks later
            userAnswers.add(scentence.getText().toString());
        }
        else{
            //toast to tell them to enter an answer
            Toast.makeText(this, "Please enter an answer!!", Toast.LENGTH_SHORT).show();
        }

    }

    public String getResult(){
        //method checks the two list against eachother (scentencelist retrieved from database and useranswerlist)
        //and increments the counters depending on the result of the check
        //then it returns a string that looks like this (mark/total)
        int tempCounter=0;
        int mark=0;
        for (String item:scentenceList)
        {
            if(item.equalsIgnoreCase(userAnswers.get(tempCounter)))
            {
                mark+=1;
            }
            tempCounter+=1;
        }
        return mark+"/"+scentenceList.size();
    }

    public void postToFirebase(String mark){

        //method captures user email from firebase auth and then removes the '.' from the email because
        // firebase doesnt alow it in the database
        String userEmail=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        for(int i=0;i<userEmail.length();i++){
            if(userEmail.charAt(i)=='.')
            {
               userEmail=userEmail.substring(0, i) + userEmail.substring(i + 1);
               break;
            }
        }
        //then it creates a ref to the users own tree and then posts the map object ("mark", the mark received from get results method)
        DatabaseReference myRef = database.getReference("users").child(userEmail).child(getIntent().getStringExtra("level")).child("ScentenceBuilder").child(getIntent().getStringExtra("collection_name"));
        Map<String, Object> userMark = new HashMap<>();
        userMark.put("mark", mark);
        myRef.setValue(userMark).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                startActivity(new Intent(UnjumbleCollectionScentences.this, MainActivity.class));
            }
        });

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_next_scentence:
                if(!(counter>scentenceList.size())){
                    if(scentence.getText().toString().length()!=0&&scentence.getText().toString()!="Unjumble the scentence"){
                        captureUserAnswer();
                        //capture answer before iterating
                        counter++;
                        //increment counter
                        iterateThroughList();
                    }
                    else {
                        Toast.makeText(this, "Please enter an answer", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
        }
    }
}
