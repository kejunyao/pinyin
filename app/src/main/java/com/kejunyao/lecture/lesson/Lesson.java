package com.kejunyao.lecture.lesson;

import org.json.JSONObject;

/**
 * $类描述$
 *
 * @author kejunyao
 * @since 2020年10月27日
 */
public class Lesson {

    private long id;
    private String title;
    private String cover;
    private String uri;

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
}
