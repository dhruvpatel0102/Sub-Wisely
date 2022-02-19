package com.example.sub_wisely;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SubRVAdapter extends RecyclerView.Adapter<SubRVAdapter.ViewHolder> {
    // creating variables for our list, context, interface and position.
    private ArrayList<SubRVModal> subRVModalArrayList;
    private Context context;
    private SubClickInterface subClickInterface;
    int lastPos = -1;

    // creating a constructor.
    public SubRVAdapter(ArrayList<SubRVModal> subRVModalArrayList, Context context, SubClickInterface subClickInterface) {
        this.subRVModalArrayList = subRVModalArrayList;
        this.context = context;
        this.subClickInterface = subClickInterface;
    }

    @NonNull
    @Override
    public SubRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating our layout file on below line.
        View view = LayoutInflater.from(context).inflate(R.layout.course_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // setting data to our recycler view item on below line.
        SubRVModal subRVModal = subRVModalArrayList.get(position);
        holder.subTV.setText(subRVModal.getSubName());
        holder.subPriceTV.setText("Rs. " + subRVModal.getSubPrice());
        //Picasso.get().load(courseRVModal.getCourseImg()).into(holder.courseIV);
        // adding animation to recycler view item on below line.
        holder.subIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subClickInterface.onCourseClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subRVModalArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // creating variable for our image view and text view on below line.
        private ImageView subIV;
        private TextView subTV, subPriceTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing all our variables on below line.
            subIV = itemView.findViewById(R.id.idIVSub);
            subTV = itemView.findViewById(R.id.idTVSubName);
            subPriceTV = itemView.findViewById(R.id.idTVSubPrice);
        }
    }

    // creating a interface for on click
    public interface SubClickInterface {
        void onCourseClick(int position);
    }
}