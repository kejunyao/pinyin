package com.kejunyao.pinyin;

import android.content.Context;
import android.content.res.Resources;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.kejunyao.arch.util.Utility;
import java.util.ArrayList;
import java.util.List;

/**
 * $类描述$
 *
 * @author kejunyao
 * @since 2020年10月08日
 */
public final class PinyinPlayer {

    public interface OnPlayCompleteListener {
        void onPlayComplete(Letter letter);
    }

    public enum PlayMode {
        MANUAL_PLAYBACK, // 手动播放
        SIGNAL_REPEAT, // 单字母复读
        ALL_SEQUENCE, // 顺序播放
        ALL_SEQUENCE_REPEAT, // 顺序循环播放
        ALL_SHENGMU_REPEAT, // 所有的声母顺序循环播放
        ALL_YUNMU_REPEAT, // 所有的韵母顺序循环播放
        ALL_ZHENGTI_REPEAT, // 所有的整体音节顺序循环播放
    }

    private static final boolean DEBUG = false;
    private static final String TAG  = "PinyinPlayer";
    private static final String A    = "a";
    private static final String W    = "w";
    private static final String B    = "b";
    private static final String ONG  = "ong";
    private static final String ZHI  = "zhi";
    private static final String YING = "ying";

    private static final long TIME_DELAY_PLAY = 1200;

    private final SoundPool mSoundPool;

    private final Context mContext;

    private int mSoundId = -1;
    private int mStreamId = -1;

    private List<Letter> mLetters = new ArrayList<>();

    private static final int MSG_MANUAL_PLAYBACK      = PlayMode.MANUAL_PLAYBACK.ordinal();
    private static final int MSG_SIGNAL_REPEAT        = PlayMode.SIGNAL_REPEAT.ordinal();
    private static final int MSG_ALL_SEQUENCE         = PlayMode.ALL_SEQUENCE.ordinal();
    private static final int MSG_ALL_SEQUENCE_REPEAT  = PlayMode.ALL_SEQUENCE_REPEAT.ordinal();
    private static final int MSG_ALL_SHENGMU_REPEAT   = PlayMode.ALL_SHENGMU_REPEAT.ordinal();
    private static final int MSG_ALL_YUNMU_REPEAT     = PlayMode.ALL_YUNMU_REPEAT.ordinal();
    private static final int MSG_ALL_ZHENGTI_REPEAT   = PlayMode.ALL_ZHENGTI_REPEAT.ordinal();

    private final Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == MSG_SIGNAL_REPEAT) {
                signalRepeat();
            } else if (msg.what == MSG_MANUAL_PLAYBACK) {
            } else {
                autoPlay();
            }
        }

        private void signalRepeat() {
            play(mCurrentPlayLetter);
            mHandler.sendEmptyMessageDelayed(MSG_SIGNAL_REPEAT, TIME_DELAY_PLAY);
        }
    };

    public PinyinPlayer(Context context) {
        mContext = context.getApplicationContext();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            SoundPool.Builder builder = new SoundPool.Builder();
            // 传入音频数量
            builder.setMaxStreams(5);
            // AudioAttributes是一个封装音频各种属性的方法
            AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
            // 设置音频流的合适的属性
            attrBuilder.setLegacyStreamType(AudioManager.STREAM_MUSIC);//STREAM_MUSIC
            // 加载一个AudioAttributes
            builder.setAudioAttributes(attrBuilder.build());
            mSoundPool = builder.build();
        } else {
            mSoundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }
        mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                if (mSoundId >= 0) {
                    mStreamId = mSoundPool.play(mSoundId, 1, 1, 0, 0, 1);
                }
                clearAllMessage();
                if (mPlayMode == PlayMode.MANUAL_PLAYBACK) {
                    return;
                }
                mHandler.sendEmptyMessageDelayed(mPlayMode.ordinal(), TIME_DELAY_PLAY);
            }
        });
    }

    private void clearAllMessage() {
        for (PlayMode mode : PlayMode.values()) {
            mHandler.removeMessages(mode.ordinal());
        }
    }

    private OnPlayCompleteListener mOnPlayCompleteListener;
    public void setOnPlayCompleteListener(OnPlayCompleteListener listener) {
        mOnPlayCompleteListener = listener;
    }

    public void setSource(List<Letter> letters) {
        mLetters.clear();
        if (!Utility.isNullOrEmpty(letters)) {
            mLetters.addAll(letters);
        }
    }

    private int loadSound(Letter letter) {
        String text = letter.text.replaceAll("ü", "v");
        Resources resources = mContext.getResources();
        int id = resources.getIdentifier(text, "raw", mContext.getPackageName());
        return mSoundPool.load(mContext, id, 1);
    }

    public void release() {
        mSoundId = -1;
        mStreamId = -1;
        mSoundPool.release();
    }

    public void resume() {
        if (mStreamId >= 0 && mCurrentPlayLetter != null) {
            play(mCurrentPlayLetter);
        }
    }

    public void pause() {
        if (mStreamId >= 0) {
            clearAllMessage();
            mSoundPool.pause(mStreamId);
        }
    }

    public void stop() {
        if (mStreamId >= 0) {
            mSoundPool.stop(mStreamId);
        }
    }

    private Letter mCurrentPlayLetter;
    public void play(Letter letter) {
        if (letter == null) {
            return;
        }
        mCurrentPlayLetter = letter;
        if (mSoundId > 0) {
            mSoundPool.stop(mStreamId);
            mSoundPool.unload(mSoundId);
            clearAllMessage();
        }
        if (mOnPlayCompleteListener != null && mCurrentPlayLetter != null) {
            mOnPlayCompleteListener.onPlayComplete(mCurrentPlayLetter);
        }
        mSoundId = loadSound(letter);
    }

    private void autoPlay() {
        if (mPlayMode == PlayMode.MANUAL_PLAYBACK) {
            return;
        }
        if (mCurrentPlayLetter == null || TextUtils.isEmpty(mCurrentPlayLetter.text)) {
            return;
        }
        if (mLetters.isEmpty()) {
            return;
        }
        if (mPlayMode == PlayMode.ALL_SHENGMU_REPEAT && mCurrentPlayLetter.text.equals(W)) {
            play(mLetters.get(0));
            return;
        }
        if (mPlayMode == PlayMode.ALL_YUNMU_REPEAT && mCurrentPlayLetter.text.equals(ONG)) {
            for (Letter letter : mLetters) {
                if (letter.text.equals(A)) {
                    play(letter);
                    return;
                }
            }
            return;
        }
        if (mPlayMode == PlayMode.ALL_ZHENGTI_REPEAT && mCurrentPlayLetter.text.equals(YING)) {
            for (Letter letter : mLetters) {
                if (letter.text.equals(ZHI)) {
                    play(letter);
                    return;
                }
            }
            return;
        }
        if (mPlayMode == PlayMode.ALL_SEQUENCE_REPEAT && mCurrentPlayLetter.text.equals(YING)) {
            play(mLetters.get(0));
            return;
        }
        boolean find = false;
        for (Letter letter : mLetters) {
            if (find) {
                play(letter);
                return;
            }
            if (letter.text.equals(mCurrentPlayLetter.text)) {
                find = true;
            }
        }
    }

    private PlayMode mPlayMode = PlayMode.MANUAL_PLAYBACK;

    public void setPlayMode(PlayMode playMode) {
        mPlayMode = playMode;
        clearAllMessage();
        if (mPlayMode == PlayMode.MANUAL_PLAYBACK) {
            return;
        }
        if (mPlayMode == PlayMode.ALL_SHENGMU_REPEAT) {
            playShengmuRepeat();
            return;
        }
        if (mPlayMode == PlayMode.ALL_YUNMU_REPEAT) {
            playYunmuRepeat();
            return;
        }
        if (mPlayMode == PlayMode.ALL_ZHENGTI_REPEAT) {
            playZhengtiRepeat();
            return;
        }
        play(mCurrentPlayLetter);
    }

    private void playShengmuRepeat() {
        playSegmentRepeat(B, W);
    }

    private void playYunmuRepeat() {
        playSegmentRepeat(A, ONG);
    }

    private void playZhengtiRepeat() {
        playSegmentRepeat(ZHI, YING);
    }

    private void playSegmentRepeat(String start, String end) {
        Letter first = null;
        for (Letter letter : mLetters) {
            if (letter.text.equals(start)) {
                first = letter;
            }
            if (first != null) {
                if (mCurrentPlayLetter == null) {
                    play(first);
                    return;
                }
                if (mCurrentPlayLetter.text.equals(letter.text)) {
                    play(letter);
                    return;
                }
                if (letter.text.equals(end)) {
                    play(first);
                    return;
                }
            }
        }
    }
}
