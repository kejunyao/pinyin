package com.kejunyao.lecture.lesson;

import android.os.Parcel;
import android.os.Parcelable;
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
public class Video implements Parcelable {

    private long id;
    private String title;
    private long duration;
    private ArrayList<String> urls;

    public Video() {
    }

    protected Video(Parcel in) {
        id = in.readLong();
        title = in.readString();
        duration = in.readLong();
        urls = in.createStringArrayList();
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeLong(duration);
        dest.writeStringList(urls);
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
