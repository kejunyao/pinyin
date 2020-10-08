package com.kejunyao.pinyin;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kejunyao.arch.recycler.AdapterData;
import com.kejunyao.arch.recycler.BaseRecyclerHolder;
import com.kejunyao.arch.recycler.ViewHolderUtils;

/**
 * $类描述$
 *
 * @author kejunyao
 * @since 2020年10月08日
 */
public class LetterTitleHolder extends BaseRecyclerHolder<AdapterData> {

    public static LetterTitleHolder create(ViewGroup parent) {
        LetterTitleHolder holder = new LetterTitleHolder(ViewHolderUtils.inflate(parent, R.layout.holder_letter_title));
        return holder;
    }

    private TextView mTitleView;

    private LetterTitleHolder(View itemView) {
        super(itemView);
        mTitleView = (TextView) itemView;
    }

    @Override
    public void refresh(AdapterData data) {
        Object o = data.data;
        if (o instanceof String) {
            mTitleView.setText(o.toString());
        }
    }
}
