package com.kejunyao.lecture.video;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.kejunyao.arch.util.Utility;
import com.kejunyao.lecture.pinyin.R;
import cn.jzvd.JzvdStd;

/**
 * $类描述$
 *
 * @author kejunyao
 * @since 2020年10月27日
 */
public class VideoActivity extends BaseVideoActivity {

    private static final String TAG = "XT_VideoActivity";

    private JzvdStd mVideoView;

    private Video mVideo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        mVideoView = findViewById(R.id.video_view);
        // JZUtils.hideStatusBar(this);
        // JZUtils.hideSystemUI(this);
        mVideo = Video.obtainParam(getIntent());
        if (mVideo == null || Utility.isNullOrEmpty(mVideo.getUrl())) {
            Toast.makeText(this, "请传入视频URL地址", Toast.LENGTH_LONG).show();
            return;
        }
        mVideoView.setUp(mVideo.getUrl(), mVideo.getTitle());
        // mVideoView.gotoScreenFullscreen();
        mVideoView.startVideo();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
