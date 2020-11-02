package com.kejunyao.lecture.video;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kejunyao.arch.recycler.BaseRecyclerAdapter;
import com.kejunyao.lecture.pinyin.OnItemClickListener;

/**
 * $类描述$
 *
 * @author kejunyao
 * @since 2020年10月27日
 */
public class VideoAdapter extends BaseRecyclerAdapter<Video> {
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final VideoViewHolder holder = VideoViewHolder.create(parent);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    Video video = getItem(holder.getAdapterPosition());
                    mOnItemClickListener.onItemClick(video);
                }
            }
        });
        return holder;
    }

    private OnItemClickListener<Video> mOnItemClickListener;
    void setOnItemClickListener(OnItemClickListener<Video> listener) {
        mOnItemClickListener = listener;
    }
}
