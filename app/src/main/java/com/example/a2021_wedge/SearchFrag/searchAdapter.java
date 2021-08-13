package com.example.a2021_wedge.SearchFrag;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2021_wedge.R;

import java.util.ArrayList;

public class searchAdapter extends RecyclerView.Adapter<searchAdapter.ViewHolder> {

    private ArrayList<searchItem> items;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView store_name;
        public ImageView enter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            store_name = itemView.findViewById(R.id.textView17);
            enter = itemView.findViewById(R.id.imageButton5);
        }

        public void setItem(searchItem item) {
            store_name.setText(item.getStorename());
            enter.setImageDrawable(item.getEnter());
        }
    }

    public searchAdapter(ArrayList<searchItem> mydata){
        this.items = mydata;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_store_item, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.store_name.setText(items.get(position).getStorename());
        holder.enter.setImageDrawable(items.get(position).getEnter());

        holder.enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //입장하기 버튼 눌렀을 때 동작

            }
        });
    }

    @Override
    public int getItemCount() { return items.size(); }
}
