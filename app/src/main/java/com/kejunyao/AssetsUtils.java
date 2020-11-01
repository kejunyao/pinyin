package com.kejunyao;

import com.kejunyao.arch.file.FileUtils;
import com.kejunyao.lecture.PinyinApp;
import com.kejunyao.lecture.lesson.Video;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;

/**
 * $类描述$
 *
 * @author kejunyao
 * @since 2020年11月01日
 */
public final class AssetsUtils {


    private AssetsUtils() {
    }


    public static JSONObject read(String filename) {
        JSONObject result = null;
        try {
            InputStream is = PinyinApp.getContext().getAssets().open(filename);
            byte[] bytes = FileUtils.getBytesFromInputStream(is);
            result = new JSONObject(new String(bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
