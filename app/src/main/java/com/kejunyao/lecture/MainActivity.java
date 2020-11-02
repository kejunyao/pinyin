package com.kejunyao.lecture;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kejunyao.arch.recycler.AdapterData;
import com.kejunyao.arch.thread.Processor;
import com.kejunyao.arch.thread.ThreadPoolUtils;
import com.kejunyao.arch.util.ActivityUtils;
import com.kejunyao.lecture.lesson.Lesson;
import com.kejunyao.lecture.lesson.LessonAdapter;
import com.kejunyao.lecture.lesson.LessonFactory;
import com.kejunyao.lecture.video.Video;
import com.kejunyao.lecture.pinyin.OnItemClickListener;
import com.kejunyao.lecture.pinyin.PinyinActivity;
import com.kejunyao.lecture.pinyin.R;
import com.kejunyao.lecture.video.VideoActivity;
import com.kejunyao.lecture.video.VideoListActivity;
import java.lang.ref.WeakReference;
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
        loadData();
    }

    private void loadData() {
        final WeakReference<MainActivity> ref = new WeakReference<>(this);
        ThreadPoolUtils.runOnMultiple(new Processor<List<AdapterData>>() {
            @Override
            public List<AdapterData> onProcess() {
                List<AdapterData> result = new ArrayList<>();
                result.add(LessonFactory.createPinyin());
                List<Lesson> lessons = AssetsUtils.parseLessons();
                for (Lesson lesson : lessons) {
                    result.add(LessonFactory.createLesson(lesson));
                }
                result.add(LessonFactory.createTest());
                return result;
            }

            @Override
            public void onResult(List<AdapterData> result) {
                if (ActivityUtils.isFinishing(ref.get())) {
                    return;
                }
                refresh(result);
            }
        });
    }

    private LessonAdapter mVideoAdapter;
    private void refresh(List<AdapterData> options) {
        if (mVideoAdapter == null) {
            mVideoAdapter = new LessonAdapter();
            mVideoAdapter.setOnItemClickListener(new OnItemClickListener<AdapterData>() {
                @Override
                public void onItemClick(AdapterData data) {
                    switch (data.type) {
                        case LessonFactory.OPTION_ID_PINYIN:
                            startActivity(new Intent(MainActivity.this, PinyinActivity.class));
                            break;
                        case LessonFactory.OPTION_ID_LESSON:
                            ((Lesson) data.data).startActivity(MainActivity.this);
                            break;
                        case LessonFactory.OPTION_ID_TEST_VIDEO: {
                            ThreadPoolUtils.runOnMultiple(new Processor<String>() {

                                @Override
                                public String onProcess() {
                                    return Utils.parseTencentVideoUrl("https://v.qq.com/x/page/v0512ks1qhu.html");
                                }

                                @Override
                                public void onResult(String result) {
                                    Video video = new Video();
                                    video.setId(11111);
                                    video.setTitle("哑舍");
                                    video.setUrl(result);
                                    ArrayList<String> urls = new ArrayList<>();
                                    urls.add(result);
                                    video.startActivity(MainActivity.this);
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
}
