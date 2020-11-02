package com.kejunyao.lecture.video;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.kejunyao.lecture.AssetsUtils;
import com.kejunyao.arch.thread.Processor;
import com.kejunyao.arch.thread.ThreadPoolUtils;
import com.kejunyao.arch.util.ActivityUtils;
import com.kejunyao.lecture.Utils;
import com.kejunyao.lecture.lesson.Lesson;
import com.kejunyao.lecture.pinyin.OnItemClickListener;
import com.kejunyao.lecture.pinyin.R;
import org.json.JSONArray;
import org.json.JSONObject;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * $类描述$
 *
 * @author kejunyao
 * @since 2020年10月27日
 */
public class VideoListActivity extends AppCompatActivity {

    private Lesson mLesson;
    private RecyclerView mRecyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signal_recycler_view);
        mRecyclerView = findViewById(R.id.recycler_view);

        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);
        mLesson = Lesson.obtainParam(getIntent());
        if (mLesson == null || TextUtils.isEmpty(mLesson.getUri())) {
            finish();
            return;
        }
        setTitle(mLesson.getTitle());
        loadData(mLesson.getUri());
    }

    private void loadData(final String uri) {
        final WeakReference<VideoListActivity> ref = new WeakReference<>(this);
        ThreadPoolUtils.runOnMultiple(new Processor<List<Video>>() {
            @Override
            public List<Video> onProcess() {
                List<Video> videos = new ArrayList<>();
                try {
                    JSONObject rjo = AssetsUtils.read(uri);
                    if (rjo != null && rjo.optInt("code") == 200) {
                        JSONArray array = rjo.optJSONArray("data");
                        for (int i = 0, size = array.length(); i < size; i++) {
                            JSONObject jo = array.optJSONObject(i);
                            if (jo != null) {
                                videos.add(Video.parse(jo));
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    return videos;
                }
            }

            @Override
            public void onResult(List<Video> result) {
                if (ActivityUtils.isFinishing(ref.get())) {
                    return;
                }
                ref.get().refresh(result);
            }
        });
    }

    private VideoAdapter mVideoAdapter;
    private void refresh(List<Video> videos) {
        if (mVideoAdapter == null) {
            mVideoAdapter = new VideoAdapter();
            mVideoAdapter.setOnItemClickListener(new OnItemClickListener<Video>() {
                @Override
                public void onItemClick(Video data) {
                    Utils.playVideo(VideoListActivity.this, data);
                }
            });
            mVideoAdapter.setData(videos);
            mRecyclerView.setAdapter(mVideoAdapter);
        } else {
            mVideoAdapter.setData(videos);
            mVideoAdapter.notifyDataSetChanged();
        }
    }
}
