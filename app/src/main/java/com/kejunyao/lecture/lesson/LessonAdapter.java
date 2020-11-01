package com.kejunyao.lecture.lesson;

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
public class LessonAdapter extends BaseRecyclerAdapter<Lesson> {
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LessonViewHolder holder = LessonViewHolder.create(parent);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    Lesson lesson = getItem(holder.getAdapterPosition());
                    mOnItemClickListener.onItemClick(lesson);
                }
            }
        });
        return holder;
    }

    private OnItemClickListener<Lesson> mOnItemClickListener;
    void setOnItemClickListener(OnItemClickListener<Lesson> listener) {
        mOnItemClickListener = listener;
    }
}
