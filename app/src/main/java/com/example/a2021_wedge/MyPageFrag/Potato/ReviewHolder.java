package com.example.a2021_wedge.MyPageFrag.Potato;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.a2021_wedge.R;
import com.example.a2021_wedge.StoreFrag.StoreListAdapter;

public class ReviewHolder extends RecyclerView.ViewHolder {

    TextView store_name, rate, review;

    public ReviewHolder(View a_itemView, final StoreListAdapter.OnItemClickEventListener a_itemClickListener) {
        super(a_itemView);

        a_itemView.setOnClickListener(a_view -> {
            final int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                a_itemClickListener.onItemClick(position);
            }
        });

        store_name = a_itemView.findViewById(R.id.store_name);
        rate = a_itemView.findViewById(R.id.rate);
        review = a_itemView.findViewById(R.id.review);
    }
}
