package com.example.a2021_wedge.SearchFrag;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2021_wedge.R;

import java.util.ArrayList;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.ViewHolder>
        implements OnSearchListItemClickListener {
    ArrayList<ItemSearchList> items = new ArrayList<>();

    OnSearchListItemClickListener listener;

    @NonNull
    @Override
    public SearchListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_search_list, viewGroup, false);

        return new SearchListAdapter.ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchListAdapter.ViewHolder viewHolder, int position) {
        ItemSearchList item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(ItemSearchList item) {
        items.add(item);
    }

    public void setItems(ArrayList<ItemSearchList> items) {
        this.items = items;
    }

    public ItemSearchList getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, ItemSearchList item) {
        items.set(position, item);
    }

    public void setOnItemClickListener(OnSearchListItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(SearchListAdapter.ViewHolder holder, View view, int position) {
        if (listener != null) {
            listener.onItemClick(holder, view, position);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(View itemView, final OnSearchListItemClickListener listener) {
            super(itemView);

            textView = itemView.findViewById(R.id.store_name2);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();

                if (listener != null) {
                    listener.onItemClick(SearchListAdapter.ViewHolder.this, view, position);
                }
            });
        }

        public void setItem(ItemSearchList item) {
            textView.setText(item.getName());
        }
    }
}
