package com.kejunyao.lecture;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kejunyao.arch.file.FileUtils;
import com.kejunyao.arch.net.Connection;
import com.kejunyao.arch.thread.Processor;
import com.kejunyao.arch.thread.ThreadPoolUtils;
import com.kejunyao.lecture.lesson.LessonListActivity;
import com.kejunyao.lecture.lesson.Video;
import com.kejunyao.lecture.pinyin.OnItemClickListener;
import com.kejunyao.lecture.pinyin.PinyinActivity;
import com.kejunyao.lecture.pinyin.R;
import com.kejunyao.video.VideoActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * $类描述$
 *
 * @author kejunyao
 * @since 2020年10月25日
 */
public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signal_recycler_view);
        mRecyclerView = findViewById(R.id.recycler_view);

        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);

        List<Option> options = new ArrayList<>();
        options.add(OptionFactory.createPinyinOption());
        options.add(OptionFactory.createLessonOption());
        options.add(OptionFactory.createTest());
        refresh(options);
    }

    private OptionAdapter mVideoAdapter;

    private void refresh(List<Option> options) {
        if (mVideoAdapter == null) {
            mVideoAdapter = new OptionAdapter();
            mVideoAdapter.setOnItemClickListener(new OnItemClickListener<Option>() {
                @Override
                public void onItemClick(Option data) {
                    switch (data.id) {
                        case OptionFactory.OPTION_ID_PINYIN:
                            startActivity(new Intent(MainActivity.this, PinyinActivity.class));
                            break;
                        case OptionFactory.OPTION_ID_LESSON:
                            startActivity(new Intent(MainActivity.this, LessonListActivity.class));
                            break;
                        case OptionFactory.OPTION_ID_TEST_VIDEO: {
                            ThreadPoolUtils.runOnMultiple(new Processor<String>() {

                                @Override
                                public String onProcess() {
                                    String result = null;
                                    try {
                                        String url = "https://v.qq.com/x/page/v0512ks1qhu.html";
                                        url = "http://v.ranks.xin/video-parse.php?url=" + url;
                                        String json = getString(url);
                                        JSONObject jo = new JSONObject(json);
                                        Log.d("sdjiwdijwijdiwidijw", "json: " + json + ", url: " + url);
                                        if (jo != null && jo.has("data")) {
                                            JSONArray array = jo.optJSONArray("data");
                                            result = array.getJSONObject(0).optString("url");
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    } finally {
                                        return result;
                                    }
                                }

                                @Override
                                public void onResult(String result) {
                                    Log.d("sdjiwdijwijdiwidijw", "result: " + result);
                                    Video video = new Video();
                                    video.setDuration(29188);
                                    video.setId(11111);
                                    video.setTitle("哑舍");
                                    ArrayList<String> urls = new ArrayList<>();
                                    urls.add(result);
                                    video.setUrls(urls);
                                    VideoActivity.startActivity(MainActivity.this, video);
                                }
                            });
                            break;
                        }
                        default:
                            break;
                    }
                }
            });
            mVideoAdapter.setData(options);
            mRecyclerView.setAdapter(mVideoAdapter);
        } else {
            mVideoAdapter.setData(options);
            mVideoAdapter.notifyDataSetChanged();
        }
    }

    public static String getString(String url) {
        String result = null;
        try {
            HttpURLConnection connection = (HttpURLConnection) new java.net.URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);
            connection.connect();
            if (connection.getResponseCode() == 200) {
                InputStream io = connection.getInputStream();
                byte[] bytes = FileUtils.getBytesFromInputStream(io);
                result = new String(bytes);
                FileUtils.closeSafely(io);
            }
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }
}
