package com.example.a2021_wedge.StoreFrag;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.a2021_wedge.R;

public class viewHolder extends RecyclerView.ViewHolder {

    TextView tvName;

    public viewHolder(View a_itemView, final StoreListAdapter.OnItemClickEventListener a_itemClickListener) {
        super(a_itemView);

        // Click event
        a_itemView.setOnClickListener(a_view -> {
            final int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                a_itemClickListener.onItemClick(position);
            }
        });
        tvName = a_itemView.findViewById(R.id.store_name);
    }
}
