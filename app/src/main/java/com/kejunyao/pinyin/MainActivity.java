package com.kejunyao.pinyin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private PinyinPlayer mPinyinPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPinyinPlayer = new PinyinPlayer(this);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.pinyin_recycler);

        final LinearLayoutManager manager = new LinearLayoutManager(
                this,
                RecyclerView.VERTICAL,
                false
        );
        mRecyclerView.setLayoutManager(manager);

        final LetterAdapter adapter = new LetterAdapter();
        adapter.setData(PinyinFactory.buildData(this));
        mRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener<Letter>() {
            @Override
            public void onItemClick(Letter data) {
                adapter.refreshSelected(data);
                mPinyinPlayer.play(data);
            }
        });
        mPinyinPlayer.setSource(adapter.getLetters());
        mPinyinPlayer.setOnPlayCompleteListener(new PinyinPlayer.OnPlayCompleteListener() {
            @Override
            public void onPlayComplete(final Letter letter) {
                adapter.refreshSelected(letter);
                int position = letter.position;
                // TODO 定位需要优化
                mRecyclerView.smoothScrollToPosition(position - 1);
            }
        });
        PlayModeView playModeView = findViewById(R.id.play_mode_group);
        playModeView.setOnModeCheckedListener(new PlayModeView.OnModeCheckedListener() {
            @Override
            public void onModeChecked(PinyinPlayer.PlayMode mode) {
                mPinyinPlayer.setPlayMode(mode);
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        // mPinyinPlayer.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPinyinPlayer.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPinyinPlayer.stop();
        mPinyinPlayer.release();
    }

}