package com.example.a2021_wedge.Rev;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2021_wedge.R;
import com.example.a2021_wedge.RecyclerView.CustomAdapter;
import com.example.a2021_wedge.RecyclerView.Dictionary;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private ArrayList<ReviewData> sList;

    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        protected TextView review;
        protected TextView userID;
        protected TextView stars;


        public ReviewViewHolder(View view) {
            super(view);
            this.userID = (TextView) view.findViewById(R.id.item_user);
            this.review = (TextView) view.findViewById(R.id.item_review);
            this.stars = (TextView) view.findViewById(R.id.item_star);
        }
    }


    public ReviewAdapter(ArrayList<ReviewData> list) {
        this.sList = list;
    }


    @Override
    public ReviewAdapter.ReviewViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.review_item, viewGroup, false);

        ReviewAdapter.ReviewViewHolder viewHolder = new ReviewAdapter.ReviewViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ReviewViewHolder holder, int position) {
        holder.userID.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        holder.review.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        holder.stars.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);

        //holder.review.setGravity(Gravity.CENTER);
        //holder.userID.setGravity(Gravity.CENTER);

        holder.userID.setText(sList.get(position).getUserId() + "님");
        holder.review.setText(sList.get(position).getReview());
        holder.stars.setText("별점 : " + sList.get(position).getRate());
    }



    @Override
    public int getItemCount() {
        return (null != sList ? sList.size() : 0);
    }
}
