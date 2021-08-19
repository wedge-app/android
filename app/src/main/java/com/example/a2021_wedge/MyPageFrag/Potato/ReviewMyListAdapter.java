package com.example.a2021_wedge.MyPageFrag.Potato;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2021_wedge.R;

import java.util.List;

public class ReviewMyListAdapter extends RecyclerView.Adapter<ReviewHolder> {

    public interface OnItemClickEventListener {
        void onItemClick(int a_position);
    }

    private List<ItemReviewList> mItemList;

    private com.example.a2021_wedge.StoreFrag.StoreListAdapter.OnItemClickEventListener mItemClickListener = new com.example.a2021_wedge.StoreFrag.StoreListAdapter.OnItemClickEventListener() {
        @Override
        public void onItemClick(int a_position) {
            notifyItemChanged(mCheckedPosition, null);
            mCheckedPosition = a_position;
            notifyItemChanged(a_position, null);
        }
    };

    private int mCheckedPosition = -1;

    public ReviewMyListAdapter(List<ItemReviewList> a_list) {
        mItemList = a_list;
    }

    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup a_viewGroup, int a_position) {
        View view = LayoutInflater.from(a_viewGroup.getContext()).inflate(R.layout.item_review_my, a_viewGroup, false);
        return new ReviewHolder(view, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder a_viewHolder, int a_position) {
        final ItemReviewList item = mItemList.get(a_position);

        a_viewHolder.store_name.setText(item.getStore_name());
        a_viewHolder.rate.setText(item.getRate());
        a_viewHolder.review.setText(item.getReview());

    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public ItemReviewList getSelected() {
        if (mCheckedPosition > -1) {
            return mItemList.get(mCheckedPosition);
        }
        return null;
    }

    public void clearSelected() {
        mCheckedPosition = -1;
    }
}
