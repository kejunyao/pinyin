package com.kejunyao.lecture.video;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import com.kejunyao.lecture.LaunchParam;

import org.json.JSONObject;

/**
 * $类描述$
 *
 * @author kejunyao
 * @since 2020年10月27日
 */
public class Video extends LaunchParam implements Parcelable {

    public static final int SOURCE_TENCENT = 1;

    private long id;
    private String title;
    private String original;
    private String url;
    private int source;

    public Video() {
    }

    @Override
    public void startActivity(Context context) {
        Intent intent = new Intent(context, VideoActivity.class);
        if (context instanceof Activity) {
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        intent.putExtra(INTENT_EXTRA_PARAM, this);
        context.startActivity(intent);
    }

    protected Video(Parcel in) {
        id = in.readLong();
        title = in.readString();
        original = in.readString();
        url = in.readString();
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

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(original);
        dest.writeString(url);
        dest.writeInt(source);
    }

    public static Video parse(JSONObject jo) {
        Video video = new Video();
        video.id = jo.optLong("id");
        video.title = jo.optString("title");
        video.url = jo.optString("url");
        video.original = jo.optString("original");
        video.source = jo.optInt("source");
        return video;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }
}
