package com.kejunyao.lecture;

import android.app.Activity;
import android.util.Log;

import com.kejunyao.arch.net.Connection;
import com.kejunyao.arch.thread.Processor;
import com.kejunyao.arch.thread.ThreadPoolUtils;
import com.kejunyao.arch.util.ActivityUtils;
import com.kejunyao.lecture.pinyin.R;
import com.kejunyao.lecture.video.Video;
import com.kejunyao.lecture.video.VideoActivity;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

/**
 * $类描述$
 *
 * @author kejunyao
 * @since 2020年10月08日
 */
public final class Utils {

    private static final String PARSE_URL_VIDEO = "http://v.ranks.xin/video-parse.php";

    private Utils() {
    }

    public static final int SCREEN_WIDTH = App.getContext().getResources().getDisplayMetrics().widthPixels;

    public static final int PINYIN_CIRCLE_MARGIN = getDimensionPixelSize(R.dimen.item_margin);

    public static final int PINYIN_TITLE_HOLDER_HEIGHT = getDimensionPixelSize(R.dimen.pinyin_title_hold_height);

    public static final int PINYIN_CIRCLE_SIZE = (SCREEN_WIDTH - 4 * PINYIN_CIRCLE_MARGIN) / 3;
    public static final int PINYIN_LETTER_STROKE_WIDTH = getDimensionPixelSize(R.dimen.pinyin_letter_stroke_width);


    public static String getString(int resId) {
        return App.getContext().getResources().getString(resId);
    }

    public static int getDimensionPixelSize(int resId) {
        return App.getContext().getResources().getDimensionPixelSize(resId);
    }

    public static String parseTencentVideoUrl(String original) {
        String result = null;
        try {
            Connection conn = new Connection(PARSE_URL_VIDEO);
            conn.setUseGet(true);
            conn.addParameter("url", original);
            Connection.NetworkError error = conn.requestJSON();
            if (error == Connection.NetworkError.OK) {
                JSONObject jo = conn.getResponse();
                if (jo != null && jo.has("data")) {
                    JSONArray array = jo.optJSONArray("data");
                    if (array != null && array.length() > 0) {
                        jo = array.optJSONObject(0);
                        result = jo == null ? null : jo.optString("url");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public static void playVideo(Activity activity, Video video) {
        if (video.getSource() != Video.SOURCE_TENCENT) {
            VideoActivity.startActivity(activity, video);
            return;
        }
        final WeakReference<Activity> ref = new WeakReference<>(activity);
        ThreadPoolUtils.runOnMultiple(new Processor<String>() {
            @Override
            public void onResult(String result) {
                Log.d("playVideo", "result: " + result);
                if (ActivityUtils.isFinishing(ref.get())) {
                    return;
                }
                video.setUrl(result);
                VideoActivity.startActivity(activity, video);
            }

            @Override
            public String onProcess() {
                Log.d("playVideo", "original: " + video.getOriginal());
                return parseTencentVideoUrl(video.getOriginal());
            }
        });
    }
}
