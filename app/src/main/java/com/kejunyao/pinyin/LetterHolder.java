package com.kejunyao.pinyin;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.kejunyao.arch.recycler.AdapterData;
import com.kejunyao.arch.recycler.BaseRecyclerHolder;
import com.kejunyao.arch.recycler.ViewHolderUtils;
import com.kejunyao.arch.util.UIUtils;
import com.kejunyao.arch.util.Utility;
import java.util.List;

/**
 * $类描述$
 *
 * @author kejunyao
 * @since 2020年10月08日
 */
class LetterHolder extends BaseRecyclerHolder<AdapterData> {

    static LetterHolder create(ViewGroup parent, LetterAdapter adapter) {
        LetterHolder holder = new LetterHolder(ViewHolderUtils.inflate(parent, R.layout.holder_letter));
        holder.mAdapter = adapter;
        return holder;
    }

    private TextView mTextView1;
    private TextView mTextView2;
    private TextView mTextView3;
    private LetterAdapter mAdapter;

    private LetterHolder(View itemView) {
        super(itemView);
        mTextView1 = findViewById(R.id.num1);
        mTextView2 = findViewById(R.id.num2);
        mTextView3 = findViewById(R.id.num3);

        UIUtils.setMarginTop(itemView, Utils.PINYIN_CIRCLE_MARGIN);
        UIUtils.setMarginLeft(mTextView1, Utils.PINYIN_CIRCLE_MARGIN);
        UIUtils.setMarginRight(mTextView1, Utils.PINYIN_CIRCLE_MARGIN);
        UIUtils.setMarginRight(mTextView2, Utils.PINYIN_CIRCLE_MARGIN);
        UIUtils.setMarginRight(mTextView3, Utils.PINYIN_CIRCLE_MARGIN);

        UIUtils.setViewSize(mTextView1, Utils.PINYIN_CIRCLE_SIZE, Utils.PINYIN_CIRCLE_SIZE);
        UIUtils.setViewSize(mTextView2, Utils.PINYIN_CIRCLE_SIZE, Utils.PINYIN_CIRCLE_SIZE);
        UIUtils.setViewSize(mTextView3, Utils.PINYIN_CIRCLE_SIZE, Utils.PINYIN_CIRCLE_SIZE);

        mTextView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Letter letter = Utility.getSafely(mLetters, 0);
                if (letter != null) {
                    mAdapter.mOnItemClickListener.onItemClick(letter);
                }
            }
        });
        mTextView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Letter letter = Utility.getSafely(mLetters, 1);
                if (letter != null) {
                    mAdapter.mOnItemClickListener.onItemClick(letter);
                }
            }
        });
        mTextView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Letter letter = Utility.getSafely(mLetters, 2);
                if (letter != null) {
                    mAdapter.mOnItemClickListener.onItemClick(letter);
                }
            }
        });

        View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                v.setBackgroundResource(R.drawable.bg_letter_pressed);
                return true;
            }
        };

        mTextView1.setOnLongClickListener(longClickListener);
        mTextView2.setOnLongClickListener(longClickListener);
        mTextView3.setOnLongClickListener(longClickListener);
    }

    private List<Letter> mLetters;
    @Override
    public void refresh(AdapterData data) {
        Object o = data.data;
        if (o != null) {
            mLetters = (List<Letter>) o;
            setText(mLetters, 0, mTextView1);
            setText(mLetters, 1, mTextView2);
            setText(mLetters, 2, mTextView3);
        } else {
            mTextView1.setVisibility(View.INVISIBLE);
            mTextView2.setVisibility(View.INVISIBLE);
            mTextView3.setVisibility(View.INVISIBLE);
        }
    }

    private void setText(List<Letter> letters, int position, TextView textView) {
        Letter letter = Utility.getSafely(letters, position);
        if (letter == null) {
            textView.setVisibility(View.INVISIBLE);
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setBackgroundResource(R.drawable.bg_letter);
            textView.setSelected(mAdapter.mLetter != null && mAdapter.mLetter.text.equals(letter.text));
            textView.setText(letter.text);
        }
    }
}
