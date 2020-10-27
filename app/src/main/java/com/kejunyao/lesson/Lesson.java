package com.kejunyao.lesson;

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
    private String url;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static Lesson parse(JSONObject jo) {
        Lesson lesson = new Lesson();
        lesson.id = jo.optLong("id");
        lesson.title = jo.optString("title");
        lesson.cover = jo.optString("cover");
        lesson.url = jo.optString("url");
        return lesson;
    }
}
