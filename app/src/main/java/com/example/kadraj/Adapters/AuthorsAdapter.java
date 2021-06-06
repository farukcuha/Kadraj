package com.example.kadraj.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.kadraj.Models.AuthorsModel;
import com.example.kadraj.Web.NewsWebView;
import com.example.kadraj.R;

import java.util.List;

import me.grantland.widget.AutofitHelper;

public class AuthorsAdapter extends RecyclerView.Adapter<AuthorsAdapter.Holder> {
    List<AuthorsModel> authorsList;
    Context context;
    FragmentManager fragmentManager;

    public AuthorsAdapter(List<AuthorsModel> authorsList, Context context, FragmentManager fragmentManager ) {
        this.authorsList = authorsList;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.authorsitems, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        AuthorsModel model = (AuthorsModel) authorsList.get(position);

        Glide.with(context).load(model.getAuthorImage()).transform(new CenterCrop(), new RoundedCorners(30))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                }).into(holder.authorsImage);
        holder.authorsName.setText(model.getAuthorName());
        holder.articleHeader.setText(model.getArticleHeader());

        if (model.getStatus().equals("popular")){
            Glide.with(context).load(model.getNewspaperIcon()).centerCrop().fitCenter().into(holder.newspaperImage);

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                        .replace(R.id.fragmentcontainer, new NewsWebView(model.getArticleUrl(), model.getAuthorName()))
                        .addToBackStack("TAG")

                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return authorsList.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        private ProgressBar progressBar;
        private ImageView authorsImage;
        private TextView authorsName, articleHeader;
        private ImageView newspaperImage;

        public Holder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressbar);
            authorsImage = itemView.findViewById(R.id.authorsimage);
            authorsName = itemView.findViewById(R.id.authorname);
            AutofitHelper.create(authorsName);
            articleHeader = itemView.findViewById(R.id.articleheader);
            newspaperImage = itemView.findViewById(R.id.newspapericon);


        }
    }
}
