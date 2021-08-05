package com.example.a2021_wedge.RecyclerView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a2021_wedge.R;
import com.example.a2021_wedge.Sajang.StoreManagement;
import com.example.a2021_wedge.RecyclerView.Dictionary;

import java.util.ArrayList;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private ArrayList<Dictionary> mList;
    private Context mContext;

    public CustomAdapter(StoreManagement context, ArrayList<com.example.a2021_wedge.RecyclerView.Dictionary> mArrayList) {
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder
            implements View.OnCreateContextMenuListener {
        protected TextView person;
        protected TextView table;


        public CustomViewHolder(View view) {
            super(view);
            this.person = (TextView) view.findViewById(R.id.item_person);
            this.table = (TextView) view.findViewById(R.id.item_table);

            view.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {  // 3. 메뉴 추가
            MenuItem Delete = menu.add(Menu.NONE, 1001, 1, "삭제");
            Delete.setOnMenuItemClickListener(onEditMenu);

        }

        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {


                switch (item.getItemId()) {
                    case 1002:

                        mList.remove(getAdapterPosition());
                        // 7. 어댑터에서 RecyclerView에 반영하도록 합니다.
                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(), mList.size());



                }
                return true;
            }
        };

    }


    public CustomAdapter(Context context, ArrayList<Dictionary> list) {
        mList = list;
        mContext = context;
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

        viewholder.person.setText(mList.get(position).getTable_person());
        viewholder.table.setText(mList.get(position).getTable_amount());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}
