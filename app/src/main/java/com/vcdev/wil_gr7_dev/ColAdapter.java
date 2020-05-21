package com.vcdev.wil_gr7_dev;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.vcdev.wil_gr7_dev.models.ScentenceCollectionObject;

public class ColAdapter extends FirestoreRecyclerAdapter<ScentenceCollectionObject, ColAdapter.scentenceHolder>{

    public ColAdapter(@NonNull FirestoreRecyclerOptions<ScentenceCollectionObject> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull scentenceHolder holder, int position, @NonNull ScentenceCollectionObject model) {
        holder.col_name.setText(model.getName());
    }

    @NonNull
    @Override
    public scentenceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item,parent,false);
        return new scentenceHolder(view);
    }

    class scentenceHolder extends RecyclerView.ViewHolder{
       TextView col_name;

       public scentenceHolder(View itemView){
           super(itemView);
           col_name=itemView.findViewById(R.id.col_name);
       }
   }
}
