package com.martel.database.viewholder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.martel.database.R;
import com.martel.database.models.Post;

public class PostViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView;
    public TextView authorView;
    public ImageView starView;
    public TextView numStarsView;
    public TextView bodyView;
    private TextView mData;
    private TextView mHora;
    private TextView mQtd;
    private TextView mObs;

    public PostViewHolder(View itemView) {
        super(itemView);

        titleView = itemView.findViewById(R.id.post_title);
        authorView = itemView.findViewById(R.id.post_author);
        starView = itemView.findViewById(R.id.star);
        numStarsView = itemView.findViewById(R.id.post_num_stars);
        bodyView = itemView.findViewById(R.id.post_body);
        mObs = itemView.findViewById(R.id.tv_obs);
        mData = itemView.findViewById(R.id.tv_data);
        mHora = itemView.findViewById(R.id.tv_hora);
        mQtd = itemView.findViewById(R.id.tv_qtd);

    }

    public void bindToPost(Post post, View.OnClickListener starClickListener) {
        titleView.setText(post.title);
        authorView.setText(post.author);
        mObs.setText("Observação: " + post.getObs());
        mData.setText(post.getDataa());
        mHora.setText(post.getHora());
        mQtd.setText(post.getPeliculas());
        numStarsView.setText(String.valueOf(post.starCount));
        bodyView.setText(post.body);

        Log.e("ITEM", post.title + post.author + post.body);

        starView.setOnClickListener(starClickListener);
    }
}
