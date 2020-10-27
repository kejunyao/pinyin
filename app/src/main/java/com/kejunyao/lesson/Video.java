package com.kejunyao.lesson;

import android.text.TextUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * $类描述$
 *
 * @author kejunyao
 * @since 2020年10月27日
 */
public class Video {
    private long id;
    private String title;
    private long duration;
    private ArrayList<String> urls;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public ArrayList<String> getUrls() {
        return urls;
    }

    public void setUrls(ArrayList<String> urls) {
        this.urls = urls;
    }

    public static Video parse(JSONObject jo) {
        Video video = new Video();
        video.id = jo.optLong("id");
        video.title = jo.optString("title");
        video.duration = jo.optLong("duration");
        video.urls = new ArrayList<>();
        JSONArray array = jo.optJSONArray("urls");
        for (int i = 0, size = array.length(); i < size; i++) {
            String url = array.optString(i);
            if (!TextUtils.isEmpty(url)) {
                video.urls.add(url);
            }
        }
        return video;
    }
}
