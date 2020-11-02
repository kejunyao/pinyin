package com.kejunyao.lecture.lesson;

import com.kejunyao.arch.recycler.AdapterData;

/**
 * $类描述$
 *
 * @author kejunyao
 * @since 2020年10月25日
 */
public final class LessonFactory {

    public static final int OPTION_ID_PINYIN = 1;
    public static final int OPTION_ID_LESSON = 2;
    public static final int OPTION_ID_TEST_VIDEO = 3;

    private LessonFactory() {
    }

    public static AdapterData createPinyin() {
        return new AdapterData(OPTION_ID_PINYIN, "汉语拼音");
    }

    public static AdapterData createLesson(Lesson lesson) {
        return new AdapterData(OPTION_ID_LESSON, lesson);
    }

    public static AdapterData createTest() {
        return new AdapterData(OPTION_ID_TEST_VIDEO, "视频测试");
    }
}
