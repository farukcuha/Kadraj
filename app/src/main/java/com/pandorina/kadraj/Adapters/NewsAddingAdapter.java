package com.pandorina.kadraj.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pandorina.kadraj.Models.NewsCategoryModel;
import com.pandorina.kadraj.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NewsAddingAdapter extends RecyclerView.Adapter<NewsAddingAdapter.Holder> {
    List<NewsCategoryModel> list;
    Context context;
    ChipGroup chipGroup;
    List<String> chipList = new ArrayList<>();
    SharedPreferences sharedPreferences;

    public NewsAddingAdapter(List<NewsCategoryModel> list, Context context, ChipGroup chipGroup) {
        this.list = list;
        this.context = context;
        this.chipGroup = chipGroup;

        sharedPreferences = context.getSharedPreferences("kadrajcloud", Context.MODE_PRIVATE);
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Chip chip = (Chip) LayoutInflater.from(context).inflate(R.layout.chip_item_close, chipGroup, false);
                if (chipList.contains(model.getNewsName())){
                    Toast.makeText(context, "Farklı kaynaklar seçiniz", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (chipGroup.getChildCount() < 4){
                        chip.setText(model.getNewsName());
                        chip.setOnCloseIconClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                chipGroup.removeView(chip);

                            }
                        });
                        chipGroup.addView(chip);
                        chipList.clear();
                        for (int i = 0; i < chipGroup.getChildCount(); i++){
                            String s = ((Chip) chipGroup.getChildAt(i)).getText().toString();
                            chipList.add(s);
                        }
                        Set<String> set = new HashSet<>();
                        set.addAll(chipList);
                        sharedPreferences.edit().putStringSet("newschiplist", set).apply();

                        Log.d("a", String.valueOf(chipList));
                    }
                    else {
                        Toast.makeText(context, "4 adet kaynak seçebilirsiniz", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
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
