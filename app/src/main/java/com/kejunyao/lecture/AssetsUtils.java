package com.kejunyao.lecture;

import com.kejunyao.arch.file.FileUtils;
import com.kejunyao.lecture.lesson.Lesson;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * $类描述$
 *
 * @author kejunyao
 * @since 2020年11月01日
 */
public final class AssetsUtils {

    private static final String LESSONS_CONFIG_FILE = "primary_school_lessons.json";

    private AssetsUtils() {
    }


    public static JSONObject read(String filename) {
        JSONObject result = null;
        try {
            InputStream is = App.getContext().getAssets().open(filename);
            byte[] bytes = FileUtils.getBytesFromInputStream(is);
            result = new JSONObject(new String(bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<Lesson> parseLessons() {
        List<Lesson> lessons = new ArrayList<>();
        try {
            JSONObject rjo = AssetsUtils.read(LESSONS_CONFIG_FILE);
            if (rjo != null && rjo.optInt("code") == 200) {
                JSONArray array = rjo.optJSONArray("data");
                for (int i = 0, size = array.length(); i < size; i++) {
                    JSONObject jo = array.optJSONObject(i);
                    if (jo != null) {
                        lessons.add(Lesson.parse(jo));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return lessons;
        }
    }

}
