package com.example.kadraj.Fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kadraj.Models.NewsCategoryModel;
import com.example.kadraj.R;

import java.util.List;

public class NewsAddingAdapter extends RecyclerView.Adapter<NewsAddingAdapter.Holder> {
    private List<NewsCategoryModel> list;
    private Context context;

    public NewsAddingAdapter(List<NewsCategoryModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newsaddinglistitem, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        NewsCategoryModel model = list.get(position);

        Glide.with(context).load(model.getNewsImage()).centerCrop().fitCenter().into(holder.newsImage);
        holder.newsText.setText(model.getNewsName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        ImageView newsImage;
        TextView newsText;

        public Holder(@NonNull View itemView) {
            super(itemView);
            newsImage = itemView.findViewById(R.id.newsimage);
            newsText = itemView.findViewById(R.id.newstext);
        }
    }
}
