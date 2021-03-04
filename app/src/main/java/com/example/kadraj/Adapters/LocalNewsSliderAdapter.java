package com.example.kadraj.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
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
        Glide.with(context).load(model.getImage()).centerCrop().into(viewHolder.newsImage);
    }

    @Override
    public int getCount() {
        return sliderNewsModelList.size();
    }

    public class Holder extends SliderViewAdapter.ViewHolder{
        TextView newsTitle;
        ImageView newsImage;
        BlurView blurView;

        public Holder(View itemView) {
            super(itemView);
            newsTitle = itemView.findViewById(R.id.newstitle);
            newsImage = itemView.findViewById(R.id.newsimage);
            blurView = itemView.findViewById(R.id.blurview);
        }
    }
}
