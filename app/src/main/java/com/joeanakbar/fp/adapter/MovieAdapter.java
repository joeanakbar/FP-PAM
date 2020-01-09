package com.joeanakbar.fp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.joeanakbar.fp.R;
import com.joeanakbar.fp.model.MovieItem;
import com.joeanakbar.fp.ui.detail.DetailActivity;
import com.joeanakbar.fp.utils.Helper;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private Context context;
    private List<MovieItem> listMovieItem;

    public MovieAdapter(Context context, List<MovieItem> listMovieItem) {
        this.context = context;
        this.listMovieItem = listMovieItem;
    }

    public void refill(ArrayList<MovieItem> items) {
        this.listMovieItem = new ArrayList<>();
        this.listMovieItem.addAll(items);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_movie, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int i) {
        final int position = holder.getAdapterPosition();
        String pathGambar = listMovieItem.get(position).getBannerPath();
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500" + pathGambar)
                .into(holder.imgPoster);
        holder.tvTitle.setText(listMovieItem.get(position).getTitle());
        holder.tvReleaseDate.setText(listMovieItem.get(position).getReleaseDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(Helper.STATE, "movie");
                intent.putExtra(Helper.EXTRA_MOVIE, listMovieItem.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMovieItem.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvTitle, tvReleaseDate;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.imgBannerTvShow);
            tvTitle = itemView.findViewById(R.id.tvTitleTvShow);
            tvReleaseDate = itemView.findViewById(R.id.tvReleaseDateTvShow);
        }
    }
}
