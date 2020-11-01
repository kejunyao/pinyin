package com.kejunyao.video;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kejunyao.arch.recycler.BaseRecyclerHolder;
import com.kejunyao.arch.recycler.ViewHolderUtils;
import com.kejunyao.lecture.lesson.Video;
import com.kejunyao.lecture.pinyin.R;

/**
 * $类描述$
 *
 * @author kejunyao
 * @since 2020年10月27日
 */
public class VideoViewHolder extends BaseRecyclerHolder<Video> {

    public static VideoViewHolder create(ViewGroup parent) {
        return new VideoViewHolder(ViewHolderUtils.inflate(parent, R.layout.action_item_view));
    }

    private TextView mTextView;
    private TextView mDurationView;
    private View mLineView;

    private VideoViewHolder(View itemView) {
        super(itemView);
        mTextView = findViewById(R.id.text);
        mLineView = findViewById(R.id.line);
        mDurationView = findViewById(R.id.time);
    }

    private Video mData;

    @Override
    public void refresh(Video data) {
        mData = data;
        mTextView.setText(mData.getTitle());
    }
}
