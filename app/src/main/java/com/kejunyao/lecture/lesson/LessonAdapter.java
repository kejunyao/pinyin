package com.kejunyao.lecture.lesson;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kejunyao.arch.recycler.AdapterData;
import com.kejunyao.arch.recycler.BaseRecyclerAdapter;
import com.kejunyao.lecture.ActionViewHolder;
import com.kejunyao.lecture.pinyin.OnItemClickListener;

/**
 * $类描述$
 *
 * @author kejunyao
 * @since 2020年10月25日
 */
public class LessonAdapter extends BaseRecyclerAdapter<AdapterData> {

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final ActionViewHolder holder = ActionViewHolder.create(parent);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    AdapterData data = getItem(holder.getAdapterPosition());
                    mOnItemClickListener.onItemClick(data);
                }
            }
        });
        return holder;
    }

    private OnItemClickListener<AdapterData> mOnItemClickListener;
    public void setOnItemClickListener(OnItemClickListener<AdapterData> listener) {
        mOnItemClickListener = listener;
    }
}
