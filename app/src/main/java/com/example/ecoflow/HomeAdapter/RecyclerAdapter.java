package com.example.ecoflow.HomeAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecoflow.R;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    ArrayList<RecyclerHelperClass> recyclerWater;

    public RecyclerAdapter(ArrayList<RecyclerHelperClass> recyclerWater) {
        this.recyclerWater = recyclerWater;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_card,parent,false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        RecyclerHelperClass recyclerHelperClass = recyclerWater.get(position);

        holder.image.setImageResource(recyclerHelperClass.getImage());
        holder.title.setText(recyclerHelperClass.getTitle());
        holder.desc.setText(recyclerHelperClass.getDesc());
    }

    @Override
    public int getItemCount() {
        return recyclerWater.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView title, desc;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            //Hooks
            image = itemView.findViewById(R.id.recycle_image);
            title = itemView.findViewById(R.id.recycle_title);
            desc = itemView.findViewById(R.id.recycle_desc);
        }
    }
}
