package com.example.kadraj.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kadraj.Fragments.AuthorsListFragment;
import com.example.kadraj.Models.PapernewsModel;
import com.example.kadraj.R;

import java.util.List;

public class PapernewsAdapter extends RecyclerView.Adapter<PapernewsAdapter.Holder> {
    private List<PapernewsModel> papernewsimages;
    private Context context;
    private FragmentManager fragmentManager;

    public PapernewsAdapter(List<PapernewsModel> papernewsimages, Context context, androidx.fragment.app.FragmentManager fragmentManager) {
        this.papernewsimages = papernewsimages;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.papernewsitem, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        PapernewsModel model = papernewsimages.get(position);
        Glide.with(context).load(model.getImage()).fitCenter().into(holder.papernewsImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                        .replace(R.id.fragmentcontainer, new AuthorsListFragment(model.getNewspaperName(), model.getUrl()))
                        .addToBackStack("TAG")
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return papernewsimages.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        ImageView papernewsImage;

        public Holder(@NonNull View itemView) {
            super(itemView);
            papernewsImage = itemView.findViewById(R.id.newspaperimage);
        }
    }
}
