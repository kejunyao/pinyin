package com.kejunyao.video;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.kejunyao.TimeUtils;
import com.kejunyao.arch.util.Utility;
import com.kejunyao.pinyin.R;
import java.util.ArrayList;
import java.util.List;

/**
 * $类描述$
 *
 * @author kejunyao
 * @since 2020年10月27日
 */
public class VideoActivity extends AppCompatActivity {

    private static final boolean DEBUG = true;
    public static final String TAG = "VideoActivity";

    private static final String INTENT_KEY_URLS = "intent_key_urls_e2d";

    public static void startActivity(Context context, ArrayList<String> urls) {
        Intent intent = new Intent(context, VideoActivity.class);
        if (context instanceof Activity) {
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        intent.putStringArrayListExtra(INTENT_KEY_URLS, urls);
        context.startActivity(intent);
    }

    private VideoView mVideoView;
    private SeekBar mSeekBar;
    private ProgressBar mLoadingBar;
    private TextView mPlayTimeView;
    private TextView mDurationView;

    private List<String> mUrls;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        mVideoView = findViewById(R.id.video_view);
        mVideoView = findViewById(R.id.video_view);
        mSeekBar = findViewById(R.id.video_progress);
        mLoadingBar = findViewById(R.id.loading_bar);
        mPlayTimeView = findViewById(R.id.play_time);
        mDurationView = findViewById(R.id.video_duration);

        mUrls = getIntent().getStringArrayListExtra(INTENT_KEY_URLS);
        if (Utility.isNullOrEmpty(mUrls)) {
            Toast.makeText(this, "请传入视频URL地址", Toast.LENGTH_LONG).show();
            return;
        }

        initVideoView();
        initSeekBar();
    }

    private void showLoadingBar(boolean show) {
        mLoadingBar.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }

    private void initVideoView() {
        mVideoView.setKeepScreenOn(true);
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
                playSeekBar();
                if (DEBUG) {
                    Log.d(TAG, "onPrepared, duration: " + mediaPlayer.getDuration());
                }
            }
        });
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopSeekBar();
                if (DEBUG) {
                    Log.d(TAG, "onCompletion");
                }
            }
        });
        mVideoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                if (DEBUG) {
                    Log.d(TAG, "onInfo, what: " + what + ", extra: " + extra);
                }
                if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
                    showLoadingBar(true);
                    stopSeekBar();
                } else if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                    showLoadingBar(false);
                    playSeekBar();
                    isManualPause = false;
                }
                return false;
            }
        });
        mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                if (DEBUG) {
                    Log.d(TAG, "onError, what: " + what + ", extra: " + extra);
                }
                return false;
            }
        });
        mVideoView.setVideoPath(mUrls.get(0));
        showLoadingBar(true);
    }

    private void initSeekBar() {
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int start;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                start = seekBar.getProgress();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                boolean canSeek;
                int progress = seekBar.getProgress();
                if (progress > start) { // 向右
                    canSeek = mVideoView.canSeekForward();
                } else if (progress < start) { // 向左
                    canSeek = mVideoView.canSeekBackward();
                } else {
                    // 进度无变化
                    canSeek = false;
                }
                if (canSeek) {
                    if (mVideoView.canSeekBackward()) {
                        stopSeekBar();
                        mVideoView.seekTo(progress);
                        showLoadingBar(true);
                    }
                }
            }
        });
    }

    private boolean mStop;

    private final Handler mVideoProgressHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (mStop) {
                removeMessages(0);
            } else {
                int current = mVideoView.getCurrentPosition();
                int duration = mVideoView.getDuration();
                if (mVideoView.isPlaying()) {
                    mSeekBar.setMax(duration);
                    mSeekBar.setProgress(current);
                    mPlayTimeView.setText(TimeUtils.getShowTimeText(current));
                    mDurationView.setText(TimeUtils.getShowTimeText(duration));
                }
                if (current <= duration) {
                    sendEmptyMessageDelayed(0, 16);
                } else {
                    stopSeekBar();
                }
            }
        }
    };

    private void playSeekBar() {
        mStop = false;
        mVideoProgressHandler.sendEmptyMessage(0);
    }

    private void stopSeekBar() {
        mStop = true;
        mVideoProgressHandler.removeMessages(0);
    }


    @Override
    public void onPause() {
        super.onPause();
        if (isStop) {
            return;
        }
        if (mVideoView.canPause()) {
            mVideoView.pause();
            stopSeekBar();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isStop) {
            return;
        }
        if (isManualPause) {
            return;
        }
        resume();
    }

    private boolean isManualPause;
    private void pause() {
        if (isStop) {
            return;
        }
        isManualPause = true;
        if (mVideoView.canPause()) {
            mVideoView.pause();
            stopSeekBar();
        }
    }

    private void resume() {
        if (isStop) {
            return;
        }
        isManualPause = false;

        if (mVideoView.isPlaying()) {
            return;
        }
        mVideoView.resume();
        playSeekBar();
        showLoadingBar(true);
    }

    private boolean isStop;
    @Override
    public void onStop() {
        super.onStop();
        isStop = true;
        mVideoView.stopPlayback();
    }

    @Override
    public void onDestroy() {
        stopSeekBar();
        super.onDestroy();
        mVideoView.stopPlayback();
    }
}
