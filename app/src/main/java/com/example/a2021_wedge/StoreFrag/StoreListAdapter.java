package com.example.a2021_wedge.StoreFrag;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2021_wedge.R;

import java.util.List;

public class StoreListAdapter extends RecyclerView.Adapter<viewHolder> {

    public interface OnItemClickEventListener {
        void onItemClick(int a_position);
    }

    private List<ItemStore> mItemList;

    private OnItemClickEventListener mItemClickListener = new OnItemClickEventListener() {
        @Override
        public void onItemClick(int a_position) {
            notifyItemChanged(mCheckedPosition, null);
            mCheckedPosition = a_position;
            notifyItemChanged(a_position, null);
        }
    };

    private int mCheckedPosition = -1;

    public StoreListAdapter(List<ItemStore> a_list) {
        mItemList = a_list;
    }

    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup a_viewGroup, int a_position) {
        View view = LayoutInflater.from(a_viewGroup.getContext()).inflate(R.layout.item_now_waiting, a_viewGroup, false);
        return new viewHolder(view, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder a_viewHolder, int a_position) {
        final ItemStore item = mItemList.get(a_position);

        a_viewHolder.tvName.setText(item.getName());
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public ItemStore getSelected() {
        if (mCheckedPosition > -1) {
            return mItemList.get(mCheckedPosition);
        }
        return null;
    }

    public void clearSelected() {
        mCheckedPosition = -1;
    }
}