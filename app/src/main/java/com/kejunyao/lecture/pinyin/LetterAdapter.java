package com.kejunyao.lecture.pinyin;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.kejunyao.arch.recycler.AdapterData;
import com.kejunyao.arch.recycler.BaseRecyclerAdapter;
import com.kejunyao.arch.recycler.NoSupportHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * $类描述$
 *
 * @author kejunyao
 * @since 2020年10月08日
 */
public class LetterAdapter extends BaseRecyclerAdapter<AdapterData> {

    public static final int TYPE_TITLE  = 1;
    public static final int TYPE_LETTER = 2;

    @Override
    public int getItemViewType(int position) {
        AdapterData item = getItem(position);
        return item == null ? -1 : item.type;
    }

    OnItemClickListener<Letter> mOnItemClickListener;
    void setOnItemClickListener(OnItemClickListener<Letter> listener) {
        mOnItemClickListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_TITLE: return LetterTitleHolder.create(parent);
            case TYPE_LETTER: return LetterHolder.create(parent, this);
            default: return NoSupportHolder.create(parent);
        }
    }

    Letter mLetter;
    void refreshSelected(Letter letter) {
        mLetter = letter;
        notifyDataSetChanged();
    }

    List<Letter> getLetters() {
        List<Letter> letters = new ArrayList<>();
        for (AdapterData adapterData : mData) {
            if (adapterData.type == TYPE_LETTER) {
                List<Letter> lts = (List<Letter>) adapterData.data;
                letters.addAll(lts);
            }
        }
        return letters;
    }
}
