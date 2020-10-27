package com.kejunyao.main;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kejunyao.arch.recycler.BaseRecyclerAdapter;
import com.kejunyao.pinyin.OnItemClickListener;

/**
 * $类描述$
 *
 * @author kejunyao
 * @since 2020年10月25日
 */
public class OptionAdapter extends BaseRecyclerAdapter<Option> {

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final ActionViewHolder holder = ActionViewHolder.create(parent);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    Option option = getItem(holder.getAdapterPosition());
                    mOnItemClickListener.onItemClick(option);
                }
            }
        });
        return holder;
    }

    private OnItemClickListener<Option> mOnItemClickListener;
    void setOnItemClickListener(OnItemClickListener<Option> listener) {
        mOnItemClickListener = listener;
    }
}
