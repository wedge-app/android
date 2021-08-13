package com.example.a2021_wedge.RecyclerView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a2021_wedge.R;

import java.util.ArrayList;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private ArrayList<Dictionary> mList;

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView person;
        protected TextView table;


        public CustomViewHolder(View view) {
            super(view);
            this.person = (TextView) view.findViewById(R.id.item_person);
            this.table = (TextView) view.findViewById(R.id.item_table);
        }
    }


    public CustomAdapter(ArrayList<Dictionary> list) {
        this.mList = list;
    }



    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }




    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

        viewholder.person.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        viewholder.table.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);

        viewholder.person.setGravity(Gravity.CENTER);
        viewholder.table.setGravity(Gravity.CENTER);



        viewholder.person.setText(mList.get(position).getTable_person() + "인석 ");
        viewholder.table.setText(mList.get(position).getTable_amount() + "개");
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}