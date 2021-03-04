package com.example.kadraj.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.kadraj.Models.SliderNewsModel;
import com.example.kadraj.R;

import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

import eightbitlab.com.blurview.BlurView;

public class LocalNewsSliderAdapter extends SliderViewAdapter<LocalNewsSliderAdapter.Holder> {
    private List<SliderNewsModel> sliderNewsModelList;
    private Context context;

    public LocalNewsSliderAdapter(List<SliderNewsModel> sliderNewsModelList, Context context) {
        this.sliderNewsModelList = sliderNewsModelList;
        this.context = context;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.localnewsslideritem, null);
        return  new Holder(view);
    }

    @Override
    public void onBindViewHolder(LocalNewsSliderAdapter.Holder viewHolder, int position) {
        SliderNewsModel model = sliderNewsModelList.get(position);

        viewHolder.newsTitle.setText(model.getTitle());
        Glide.with(context).load(model.getImage()).centerCrop().listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                viewHolder.progressBar.setVisibility(View.GONE);
                return false;
            }
        }).into(viewHolder.newsImage);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getCount() {
        return sliderNewsModelList.size();
    }

    public class Holder extends SliderViewAdapter.ViewHolder{
        TextView newsTitle;
        ImageView newsImage;
        BlurView blurView;
        ProgressBar progressBar;

        public Holder(View itemView) {
            super(itemView);
            newsTitle = itemView.findViewById(R.id.newstitle);
            newsImage = itemView.findViewById(R.id.newsimage);
            blurView = itemView.findViewById(R.id.blurview);
            progressBar = itemView.findViewById(R.id.sliderprogressbar);
        }
    }
}
