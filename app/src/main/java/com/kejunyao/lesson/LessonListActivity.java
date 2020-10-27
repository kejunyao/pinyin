package com.kejunyao.lesson;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.kejunyao.arch.net.Connection;
import com.kejunyao.arch.thread.Processor;
import com.kejunyao.arch.thread.ThreadPoolUtils;
import com.kejunyao.arch.util.ActivityUtils;
import com.kejunyao.pinyin.OnItemClickListener;
import com.kejunyao.pinyin.R;
import com.kejunyao.video.VideoListActivity;
import org.json.JSONArray;
import org.json.JSONObject;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * $类描述$
 *
 * @author kejunyao
 * @since 2020年10月25日
 */
public class LessonListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signal_recycler_view);
        mRecyclerView = findViewById(R.id.recycler_view);

        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);
        loadFromNetwork();
    }

    private void loadFromNetwork() {
        final WeakReference<LessonListActivity> ref = new WeakReference<>(this);
        ThreadPoolUtils.runOnMultiple(new Processor<List<Lesson>>() {
            @Override
            public List<Lesson> onProcess() {
                List<Lesson> lessons = new ArrayList<>();
                try {
                    Connection connection = new Connection("https://raw.githubusercontent.com/kejunyao/res/master/primary_school_lessons.json");
                    connection.setUseGet(true);
                    Connection.NetworkError error = connection.requestJSON();
                    JSONObject rjo = connection.getResponse();
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

            @Override
            public void onResult(List<Lesson> result) {
                if (ActivityUtils.isFinishing(ref.get())) {
                    return;
                }
                ref.get().refresh(result);
            }
        });
    }

    private LessonAdapter mVideoAdapter;
    private void refresh(List<Lesson> lessons) {
        if (mVideoAdapter == null) {
            mVideoAdapter = new LessonAdapter();
            mVideoAdapter.setOnItemClickListener(new OnItemClickListener<Lesson>() {
                @Override
                public void onItemClick(Lesson data) {
                    String url = data.getUrl();
                    // url = "https://raw.githubusercontent.com/kejunyao/res/master/first_grade_chinese_volume_1/chinese_grade_1.json";
                    VideoListActivity.startActivity(LessonListActivity.this, url);
                }
            });
            mVideoAdapter.setData(lessons);
            mRecyclerView.setAdapter(mVideoAdapter);
        } else {
            mVideoAdapter.setData(lessons);
            mVideoAdapter.notifyDataSetChanged();
        }
    }
}
