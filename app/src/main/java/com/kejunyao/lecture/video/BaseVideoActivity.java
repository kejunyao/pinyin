package com.kejunyao.lecture.video;

import androidx.appcompat.app.AppCompatActivity;
import cn.jzvd.Jzvd;

/**
 * Video基类
 *
 * @author kejunyao
 * @since 2020年10月31日
 */
public class BaseVideoActivity extends AppCompatActivity {
    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }
}
