package com.example.a2021_wedge.Sajang;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2021_wedge.R;

import java.util.ArrayList;

public class WaitingListAdapter extends RecyclerView.Adapter<WaitingListAdapter.ViewHolder>
        implements OnWaitingListItemClickListener {
    ArrayList<ItemWaitingList> items = new ArrayList<>();

    OnWaitingListItemClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_waiting_list, viewGroup, false);

        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        ItemWaitingList item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(ItemWaitingList item) {
        items.add(item);
    }

    public void setItems(ArrayList<ItemWaitingList> items) {
        this.items = items;
    }

    public ItemWaitingList getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, ItemWaitingList item) {
        items.set(position, item);
    }

    public void setOnItemClickListener(OnWaitingListItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if (listener != null) {
            listener.onItemClick(holder, view, position);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView, textView2;

        public ViewHolder(View itemView, final OnWaitingListItemClickListener listener) {
            super(itemView);

            textView2 = itemView.findViewById(R.id.nickname);
            textView = itemView.findViewById(R.id.personnel);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();

                if (listener != null) {
                    listener.onItemClick(ViewHolder.this, view, position);
                }
            });
        }

        public void setItem(ItemWaitingList item) {
            textView.setText(item.getPersonnel());
            textView2.setText(item.getNickname());
        }

    }
}

