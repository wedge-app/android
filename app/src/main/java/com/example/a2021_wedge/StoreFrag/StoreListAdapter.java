package com.example.a2021_wedge.StoreFrag;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.a2021_wedge.First.join;
import com.example.a2021_wedge.R;
import com.example.a2021_wedge.retrofit.DeleteWaitingRequest;
import com.example.a2021_wedge.retrofit.RegisterRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StoreListAdapter extends RecyclerView.Adapter<com.example.a2021_wedge.StoreFrag.StoreListAdapter.ViewHolder>
        implements OnStoreListItemClickListener {
    ArrayList<ItemStore> items = new ArrayList<>();

    OnStoreListItemClickListener listener;

    @NonNull
    @Override
    public com.example.a2021_wedge.StoreFrag.StoreListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_now_waiting, viewGroup, false);

        return new com.example.a2021_wedge.StoreFrag.StoreListAdapter.ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.a2021_wedge.StoreFrag.StoreListAdapter.ViewHolder viewHolder, int position) {
        ItemStore item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(ItemStore item) {
        items.add(item);
    }

    public void setItems(ArrayList<ItemStore> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public ItemStore getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, ItemStore item) {
        items.set(position, item);
        notifyItemChanged(position);
    }

    public void setOnItemClickListener(OnStoreListItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(com.example.a2021_wedge.StoreFrag.StoreListAdapter.ViewHolder holder, View view, int position) {
        if (listener != null) {
            listener.onItemClick(holder, view, position);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        Button btn;
        String s = "";

        public ViewHolder(View itemView, final OnStoreListItemClickListener listener) {
            super(itemView);

            textView = itemView.findViewById(R.id.store_name);
            btn = itemView.findViewById(R.id.btn_cancel);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();

                if (listener != null) {
                    listener.onItemClick(com.example.a2021_wedge.StoreFrag.StoreListAdapter.ViewHolder.this, view, position);
                }
            });

            btn.setOnClickListener(v -> {
                s = textView.getText().toString();
                Response.Listener<String> responseListener = response -> {
                    System.out.println("Listener 진입 성공/ response 값 : " + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");

                        if (success) {
                            System.out.println("연결 성공");
                        } else {
                            System.out.println("연결 실패");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                };
                System.out.println(s);
                DeleteWaitingRequest deleteWaitingRequest = new DeleteWaitingRequest(s, responseListener);
                RequestQueue queue = Volley.newRequestQueue(btn.getContext());
                queue.add(deleteWaitingRequest);
            });
        }

        public void setItem(ItemStore item) {
            textView.setText(item.getName());
        }
    }
}
