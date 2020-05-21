package com.vcdev.wil_gr7_dev;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.vcdev.wil_gr7_dev.models.ScentenceCollectionObject;
import com.vcdev.wil_gr7_dev.models.ScentenceObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class ScentenceBuilderStudent extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private static final String TAG = "ScentenceBuilderStudent";
    private ColAdapter adapter;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CollectionReference col_ref;
    Spinner levelsDropDown;
    ScentenceObject scentenceObject;
    List<ScentenceObject> scentences;
    ArrayList<String> levels;

    ScentenceCollectionObject scentenceCollectionObject;
    List<ScentenceCollectionObject> collections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scentence_builder_student);
        scentences=new ArrayList<>();
        collections=new ArrayList<>();
        loadObjectList();
        initUi();
        setupRecyclerView();
    }
    private void setupRecyclerView(){
        Query query=col_ref.orderBy("name",Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<ScentenceCollectionObject>options=new FirestoreRecyclerOptions.Builder<ScentenceCollectionObject>()
                .setQuery(query,ScentenceCollectionObject.class)
                .build();
        adapter=new ColAdapter(options);
        RecyclerView recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private void initUi(){
        String[] levels = new String[]{"Level 1","Level 2","Level 3","Level 4","Level 5","Level 6","Level 7","Level 8","Level 9","Level 10"};
        ArrayAdapter<String> arrayAdapter= new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,levels);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        levelsDropDown=(Spinner)findViewById(R.id.spinner_levels);
        levelsDropDown.setAdapter(arrayAdapter);
        levelsDropDown.setOnItemSelectedListener(this);
    }
    public void loadObjectList(){
        try{
            scentenceObject=new ScentenceObject("My name is Dave");
            scentences.add(scentenceObject);
            scentenceObject=new ScentenceObject("My name is Mary");
            scentences.add(scentenceObject);
            scentenceObject=new ScentenceObject("My name is John");
            scentences.add(scentenceObject);
            scentenceObject=new ScentenceObject("My name is Mike");
            scentences.add(scentenceObject);
            scentenceObject=new ScentenceObject("My name is Ben");
            scentences.add(scentenceObject);
        }catch (NullPointerException e){
            Log.e(TAG, "loadObjectList: NullPointerException"+e.getMessage() );
        }

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 1:
               col_ref=db.collection("Level 1");
                break;
            case 2:
                displayScentences(2);
                // Whatever you want to happen when the thrid item gets selected
                break;
            case 3:
                displayScentences(3);
                // Whatever you want to happen when the first item gets selected
                break;
            case 4:
                displayScentences(4);
                // Whatever you want to happen when the second item gets selected
                break;
            case 5:
                displayScentences(5);
                // Whatever you want to happen when the thrid item gets selected
                break;
            case 6:
                displayScentences(6);
                // Whatever you want to happen when the first item gets selected
                break;
            case 7:
                displayScentences(7);
                // Whatever you want to happen when the second item gets selected
                break;
            case 8:
                displayScentences(8);
                // Whatever you want to happen when the thrid item gets selected
                break;
            case 9:
                displayScentences(9);
                // Whatever you want to happen when the first item gets selected
                break;
            case 10:
                displayScentences(10);
                // Whatever you want to happen when the first item gets selected
                break;
        }
    }
    private void displayScentences(int level) {
        //LinearLayout m_ll = (LinearLayout) findViewById(R.id.linLayoutTvContainer);
        /*for (ScentenceCollectionObject col:collections){
            if(col.getLevel()==level){
                for (ScentenceObject scentence : col.getScentences())
                {
                    TextView text = new TextView(this);
                    text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    text.setText(scentence.getScentence().toString());
                    m_ll.addView(text);
                }
            }
        }*/
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
