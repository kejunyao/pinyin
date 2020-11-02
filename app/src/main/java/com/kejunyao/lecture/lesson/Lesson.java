package com.kejunyao.lecture.lesson;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import com.kejunyao.lecture.LaunchParam;
import com.kejunyao.lecture.video.VideoListActivity;
import org.json.JSONObject;

/**
 * $类描述$
 *
 * @author kejunyao
 * @since 2020年10月27日
 */
public class Lesson extends LaunchParam implements Parcelable {

    private long id;
    private String title;
    private String cover;
    private String uri;

    public Lesson() {
    }

    @Override
    public void startActivity(Context context) {
        Intent intent = new Intent(context, VideoListActivity.class);
        if (context instanceof Activity) {
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        intent.putExtra(INTENT_EXTRA_PARAM, this);
        context.startActivity(intent);
    }

    protected Lesson(Parcel in) {
        id = in.readLong();
        title = in.readString();
        cover = in.readString();
        uri = in.readString();
    }

    public static final Creator<Lesson> CREATOR = new Creator<Lesson>() {
        @Override
        public Lesson createFromParcel(Parcel in) {
            return new Lesson(in);
        }

        @Override
        public Lesson[] newArray(int size) {
            return new Lesson[size];
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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public static Lesson parse(JSONObject jo) {
        Lesson lesson = new Lesson();
        lesson.id = jo.optLong("id");
        lesson.title = jo.optString("title");
        lesson.cover = jo.optString("cover");
        lesson.uri = jo.optString("uri");
        return lesson;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(cover);
        dest.writeString(uri);
    }
}
