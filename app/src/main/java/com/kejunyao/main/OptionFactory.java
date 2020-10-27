package com.kejunyao.main;

/**
 * $类描述$
 *
 * @author kejunyao
 * @since 2020年10月25日
 */
public final class OptionFactory {

    public static final int OPTION_ID_PINYIN = 1;
    public static final int OPTION_ID_LESSON = 2;

    private OptionFactory() {
    }

    public static Option createPinyinOption() {
        return new Option(OPTION_ID_PINYIN, "拼音");
    }

    public static Option createLessonOption() {
        return new Option(OPTION_ID_LESSON, "一年级语文上册");
    }
}
