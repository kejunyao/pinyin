package com.kejunyao.pinyin;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArraySet;
import com.kejunyao.arch.widget.BaseLinearLayout;

/**
 * $类描述$
 *
 * @author kejunyao
 * @since 2020年10月08日
 */
public class PlayModeView extends BaseLinearLayout {

    public interface OnModeCheckedListener {
        void onModeChecked(PinyinPlayer.PlayMode mode);
    }

    public PlayModeView(@NonNull Context context) {
        super(context);
    }

    public PlayModeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PlayModeView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PlayModeView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected int layoutId() {
        return R.layout.play_mode_view;
    }

    private RadioButton mManualPlaybackView;
    private RadioButton mSignalRepeatView;
    private RadioButton mAllSequenceView;
    private RadioButton mAllSequenceRepeatView;
    private RadioButton mAllShengmuRepeatView;
    private RadioButton mAllYunmuRepeatView;
    private RadioButton mAllZhengtiRepeatView;

    private ArraySet<RadioButton> mButtons;

    private OnModeCheckedListener mOnCheckedListener;
    public void setOnModeCheckedListener(OnModeCheckedListener listener) {
        mOnCheckedListener = listener;
    }

    @Override
    protected void findViews() {
        mButtons = new ArraySet<>();
        mManualPlaybackView = findViewById(R.id.manual_playback);
        mSignalRepeatView = findViewById(R.id.signal_repeat);
        mAllSequenceView = findViewById(R.id.all_sequence);
        mAllSequenceRepeatView = findViewById(R.id.all_sequence_repeat);
        mAllShengmuRepeatView = findViewById(R.id.all_shengmu_repeat);
        mAllYunmuRepeatView = findViewById(R.id.all_yunmu_repeat);
        mAllZhengtiRepeatView = findViewById(R.id.all_zhengti_repeat);

        mButtons.add(mManualPlaybackView);
        mButtons.add(mSignalRepeatView);
        mButtons.add(mAllSequenceView);
        mButtons.add(mAllSequenceRepeatView);
        mButtons.add(mAllShengmuRepeatView);
        mButtons.add(mAllYunmuRepeatView);
        mButtons.add(mAllZhengtiRepeatView);
    }

    private final OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            changeCheckStatus(v);
            if (mOnCheckedListener == null) {
                return;
            }
            switch (v.getId()) {
                case R.id.manual_playback:
                    mOnCheckedListener.onModeChecked(PinyinPlayer.PlayMode.MANUAL_PLAYBACK);
                    break;
                case R.id.signal_repeat:
                    mOnCheckedListener.onModeChecked(PinyinPlayer.PlayMode.SIGNAL_REPEAT);
                    break;
                case R.id.all_sequence:
                    mOnCheckedListener.onModeChecked(PinyinPlayer.PlayMode.ALL_SEQUENCE);
                    break;
                case R.id.all_sequence_repeat:
                    mOnCheckedListener.onModeChecked(PinyinPlayer.PlayMode.ALL_SEQUENCE_REPEAT);
                    break;
                case R.id.all_shengmu_repeat:
                    mOnCheckedListener.onModeChecked(PinyinPlayer.PlayMode.ALL_SHENGMU_REPEAT);
                    break;
                case R.id.all_yunmu_repeat:
                    mOnCheckedListener.onModeChecked(PinyinPlayer.PlayMode.ALL_YUNMU_REPEAT);
                    break;
                case R.id.all_zhengti_repeat:
                    mOnCheckedListener.onModeChecked(PinyinPlayer.PlayMode.ALL_ZHENGTI_REPEAT);
                    break;
                default:
                    break;
            }
        }
    };

    public void setOrientation(int orientation) {
    }

    @Override
    protected void setAttrs(AttributeSet attrs) {
        super.setOrientation(VERTICAL);
        post(new Runnable() {
            @Override
            public void run() {
                for (RadioButton button : mButtons) {
                    button.setOnClickListener(mOnClickListener);
                }
            }
        });
    }

    private void changeCheckStatus(View view) {
        for (RadioButton button : mButtons) {
            button.setChecked(button.getId() == view.getId());
        }
    }
}
