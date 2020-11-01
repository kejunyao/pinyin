package com.kejunyao.video;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.kejunyao.arch.util.Utility;
import com.kejunyao.lecture.lesson.Video;
import com.kejunyao.lecture.pinyin.R;
import cn.jzvd.JzvdStd;

/**
 * $类描述$
 *
 * @author kejunyao
 * @since 2020年10月27日
 */
public class VideoActivity extends BaseVideoActivity {

    private static final boolean DEBUG = true;
    public static final String TAG = "VideoActivity";

    private static final String INTENT_KEY_VIDEO = "intent_key_video_e2d";

    public static void startActivity(Context context, Video video) {
        Intent intent = new Intent(context, VideoActivity.class);
        if (context instanceof Activity) {
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        intent.putExtra(INTENT_KEY_VIDEO, video);
        context.startActivity(intent);
    }

    private JzvdStd mVideoView;

    private Video mVideo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        mVideoView = findViewById(R.id.video_view);

        mVideo = getIntent().getParcelableExtra(INTENT_KEY_VIDEO);
        if (mVideo == null || Utility.isNullOrEmpty(mVideo.getUrls())) {
            Toast.makeText(this, "请传入视频URL地址", Toast.LENGTH_LONG).show();
            return;
        }
        mVideoView.setUp(mVideo.getUrls().get(0), mVideo.getTitle());
    }
}
