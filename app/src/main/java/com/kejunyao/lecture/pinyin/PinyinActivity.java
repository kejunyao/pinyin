package com.kejunyao.lecture.pinyin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;

public class PinyinActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private PinyinPlayer mPinyinPlayer;
    private LinearLayoutManager mLinearLayoutManager;
    private LetterAdapter mLetterAdapter;
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPinyinPlayer = new PinyinPlayer(this);
        setContentView(R.layout.activity_pinyin);
        mRecyclerView = findViewById(R.id.pinyin_recycler);

        mLinearLayoutManager = new LinearLayoutManager(
                this,
                RecyclerView.VERTICAL,
                false
        );
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mLetterAdapter = new LetterAdapter();
        mLetterAdapter.setData(PinyinFactory.buildData(this));
        mRecyclerView.setAdapter(mLetterAdapter);

        mLetterAdapter.setOnItemClickListener(new OnItemClickListener<Letter>() {
            @Override
            public void onItemClick(Letter data) {
                mPinyinPlayer.play(data);
                mLetterAdapter.refreshSelected(data);
                location(data);
            }
        });
        mPinyinPlayer.setSource(mLetterAdapter.getLetters());
        mPinyinPlayer.setOnPlayCompleteListener(new PinyinPlayer.OnPlayCompleteListener() {

            @Override
            public void onPlayComplete(final Letter letter) {
                mLetterAdapter.refreshSelected(letter);
                location(letter);
            }
        });
        final PlayModeView playModeView = findViewById(R.id.play_mode_group);
        playModeView.setOnModeCheckedListener(new PlayModeView.OnModeCheckedListener() {
            @Override
            public void onModeChecked(PinyinPlayer.PlayMode mode) {
                mPinyinPlayer.setPlayMode(mode);
            }
        });

        mPinyinPlayer.setOnPlayModeChangedListener(new PinyinPlayer.OnPlayModeChangedListener() {
            @Override
            public void onPlayModeChanged(PinyinPlayer.PlayMode mode) {
                playModeView.setPlayMode(mode);
            }
        });
    }

    private void location(Letter letter) {
        if (mPosition == letter.position) {
            return;
        }
        int pos = mLinearLayoutManager.findFirstCompletelyVisibleItemPosition();
        int distance = PinyinFactory.getDistance(
                mLetterAdapter,
                pos,
                letter.position,
                letter.position > mPosition
        );
        mRecyclerView.scrollBy(0, distance);
        mPosition = letter.position;
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