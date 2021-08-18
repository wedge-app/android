package com.example.a2021_wedge.Sajang;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.a2021_wedge.R;
import com.example.a2021_wedge.retrofit.DeleteStoreWaitingRequest;

import org.json.JSONException;
import org.json.JSONObject;

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
        Button button_del;
        String s, t;


        public ViewHolder(View itemView, final OnWaitingListItemClickListener listener) {
            super(itemView);

            textView2 = itemView.findViewById(R.id.countteam);
            textView = itemView.findViewById(R.id.count);
            button_del = itemView.findViewById(R.id.button_del);


            button_del.setOnClickListener(view -> {
                int position = getAdapterPosition();

                if (listener != null) {
                    listener.onItemClick(ViewHolder.this, view, position);
                }

                try{
                    s = textView.getText().toString(); //대기번호
                    t = textView2.getText().toString(); //인원
                    System.out.println(s + " 번/ 총 : " + t + "명");
                } catch (NullPointerException e) {
                    System.out.println("Null");
                }

                Response.Listener<String> responseListener = response -> {
                    System.out.println("Listener 진입 성공/ response 값 : " + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");

                        if (success) {
                            System.out.println("연결 성공555");
                        } else {
                            System.out.println("연결 실패5555");
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                };
                System.out.println(s + " / " + t);

                DeleteStoreWaitingRequest deleteStoreWaitingRequest = new DeleteStoreWaitingRequest(t, s, responseListener);
                RequestQueue queue2 = Volley.newRequestQueue(button_del.getContext());
                queue2.add(deleteStoreWaitingRequest);


            });
        }

        public void setItem(ItemWaitingList item) {
            textView.setText(item.getPersonnel());
            textView2.setText(item.getNickname());
        }

    }
}

