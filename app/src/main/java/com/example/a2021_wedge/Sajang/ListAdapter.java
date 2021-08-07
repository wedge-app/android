package com.example.a2021_wedge.Sajang;
import com.example.a2021_wedge.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ItemViewHolder>
        implements ItemTouchHelperListener {
    ArrayList<Person> items = new ArrayList<>();

    public ListAdapter() {
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_list_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        //ItemViewHolder가 생성되고 넣어야할 코드들을 넣어준다.
        holder.onBind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Person person) {
        items.add(person);
    }

    @Override
    public boolean onItemMove(int from_position, int to_position) {
        //이동할 객체 저장
        Person person = items.get(from_position);
        //이동할 객체 삭제
        items.remove(from_position);
        //이동하고 싶은 position에 추가
        items.add(to_position,person);

        //Adapter에 데이터 이동알림
        notifyItemMoved(from_position,to_position);
        return true;
    }

    @Override
    public void onItemSwipe(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView list_name,list_number;
        ImageView list_image;

        public ItemViewHolder(View itemView) {
            super(itemView);
            list_name = itemView.findViewById(R.id.list_name);
            list_number = itemView.findViewById(R.id.list_number);
            list_image = itemView.findViewById(R.id.list_image);
        }

        public void onBind(Person person) {
            list_name.setText(person.getName());
            list_number.setText(String.valueOf(person.getNumber()));
            list_image.setImageResource(person.getImage());
        }
    }
}
