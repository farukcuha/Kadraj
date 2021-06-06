package com.pandorina.kadraj.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.pandorina.kadraj.Models.SliderNewsModel;
import com.pandorina.kadraj.R;

import com.pandorina.kadraj.Web.NewsWebView;
import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class LocalNewsSliderAdapter extends SliderViewAdapter<LocalNewsSliderAdapter.Holder> {
    private List<SliderNewsModel> sliderNewsModelList;
    private Context context;
    private FragmentManager fragmentManager;
    private Activity activity;
    public SliderView sliderView;

    public LocalNewsSliderAdapter(List<SliderNewsModel> sliderNewsModelList, Context context, FragmentManager fragmentManager, Activity activity, SliderView sliderView) {
        this.sliderNewsModelList = sliderNewsModelList;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.activity = activity;
        this.sliderView = sliderView;
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
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                        .replace(R.id.fragmentcontainer, new NewsWebView(model.getUrl(), "haber"))
                        .addToBackStack("TAG")
                        .commit();
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
        ProgressBar progressBar;

        public Holder(View itemView) {
            super(itemView);
            newsTitle = itemView.findViewById(R.id.newstitle);
            newsImage = itemView.findViewById(R.id.newsimage);
            progressBar = itemView.findViewById(R.id.sliderprogressbar);
        }
    }
}
