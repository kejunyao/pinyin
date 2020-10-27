package com.kejunyao.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kejunyao.lesson.LessonListActivity;
import com.kejunyao.pinyin.OnItemClickListener;
import com.kejunyao.pinyin.PinyinActivity;
import com.kejunyao.pinyin.R;

import java.util.ArrayList;
import java.util.List;

/**
 * $类描述$
 *
 * @author kejunyao
 * @since 2020年10月25日
 */
public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signal_recycler_view);
        mRecyclerView = findViewById(R.id.recycler_view);

        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);

        List<Option> options = new ArrayList<>();
        options.add(OptionFactory.createPinyinOption());
        options.add(OptionFactory.createLessonOption());
        refresh(options);
    }

    private OptionAdapter mVideoAdapter;
    private void refresh(List<Option> options) {
        if (mVideoAdapter == null) {
            mVideoAdapter = new OptionAdapter();
            mVideoAdapter.setOnItemClickListener(new OnItemClickListener<Option>() {
                @Override
                public void onItemClick(Option data) {
                    switch (data.id) {
                        case OptionFactory.OPTION_ID_PINYIN:
                            startActivity(new Intent(MainActivity.this, PinyinActivity.class));
                            break;
                        case OptionFactory.OPTION_ID_LESSON:
                            startActivity(new Intent(MainActivity.this, LessonListActivity.class));
                            break;
                        default:
                            break;
                    }
                }
            });
            mVideoAdapter.setData(options);
            mRecyclerView.setAdapter(mVideoAdapter);
        } else {
            mVideoAdapter.setData(options);
            mVideoAdapter.notifyDataSetChanged();
        }
    }
}
