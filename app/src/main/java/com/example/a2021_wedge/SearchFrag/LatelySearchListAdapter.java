package com.example.a2021_wedge.SearchFrag;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2021_wedge.R;
import com.example.a2021_wedge.Sajang.ItemWaitingList;
import com.example.a2021_wedge.Sajang.OnWaitingListItemClickListener;
import com.example.a2021_wedge.Sajang.WaitingListAdapter;

import java.util.ArrayList;

public class LatelySearchListAdapter extends RecyclerView.Adapter<LatelySearchListAdapter.ViewHolder>
        implements OnLatelySearchListItemClickListener {
    ArrayList<ItemLatelySearch> items = new ArrayList<>();

    OnLatelySearchListItemClickListener listener;

    @NonNull
    @Override
    public LatelySearchListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_lately_search_list, viewGroup, false);

        return new LatelySearchListAdapter.ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull LatelySearchListAdapter.ViewHolder viewHolder, int position) {
        ItemLatelySearch item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(ItemLatelySearch item) {
        items.add(item);
    }

    public void setItems(ArrayList<ItemLatelySearch> items) {
        this.items = items;
    }

    public ItemLatelySearch getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, ItemLatelySearch item) {
        items.set(position, item);
    }

    public void setOnItemClickListener(OnLatelySearchListItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(LatelySearchListAdapter.ViewHolder holder, View view, int position) {
        if (listener != null) {
            listener.onItemClick(holder, view, position);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(View itemView, final OnLatelySearchListItemClickListener listener) {
            super(itemView);

            textView = itemView.findViewById(R.id.store_name);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();

                if (listener != null) {
                    listener.onItemClick(LatelySearchListAdapter.ViewHolder.this, view, position);
                }
            });
        }

        public void setItem(ItemLatelySearch item) {
            textView.setText(item.getName());
        }
    }
}