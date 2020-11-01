package com.kejunyao.lecture.lesson;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kejunyao.arch.recycler.BaseRecyclerHolder;
import com.kejunyao.arch.recycler.ViewHolderUtils;
import com.kejunyao.lecture.pinyin.R;

/**
 * $类描述$
 *
 * @author kejunyao
 * @since 2020年10月27日
 */
public class LessonViewHolder extends BaseRecyclerHolder<Lesson> {

    public static LessonViewHolder create(ViewGroup parent) {
        return new LessonViewHolder(ViewHolderUtils.inflate(parent, R.layout.action_item_view));
    }

    private TextView mTextView;
    private TextView mDurationView;
    private View mLineView;

    private LessonViewHolder(View itemView) {
        super(itemView);
        mTextView = findViewById(R.id.text);
        mLineView = findViewById(R.id.line);
        mDurationView = findViewById(R.id.time);
    }

    private Lesson mData;

    @Override
    public void refresh(Lesson data) {
        mData = data;
        mTextView.setText(mData.getTitle());
    }
}
