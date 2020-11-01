package com.kejunyao.lecture;

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
 * @since 2020年09月24日
 */
public class ActionViewHolder extends BaseRecyclerHolder<Option> {

    public static ActionViewHolder create(ViewGroup parent) {
        return new ActionViewHolder(ViewHolderUtils.inflate(parent, R.layout.action_item_view));
    }

    private TextView mTextView;
    private TextView mDurationView;
    private View mLineView;

    private ActionViewHolder(View itemView) {
        super(itemView);
        mTextView = findViewById(R.id.text);
        mLineView = findViewById(R.id.line);
        mDurationView = findViewById(R.id.time);
    }

    private Option mData;

    @Override
    public void refresh(Option data) {
        mData = data;
        mTextView.setText(mData.title);
        // mDurationView.setText();
    }
}
