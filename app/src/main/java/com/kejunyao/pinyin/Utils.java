package com.kejunyao.pinyin;

/**
 * $类描述$
 *
 * @author kejunyao
 * @since 2020年10月08日
 */
final class Utils {

    private Utils() {
    }

    static final int SCREEN_WIDTH = PinyinApp.getContext().getResources().getDisplayMetrics().widthPixels;

    static final int PINYIN_CIRCLE_MARGIN = getDimensionPixelSize(R.dimen.item_margin);

    static final int PINYIN_TITLE_HOLDER_HEIGHT = getDimensionPixelSize(R.dimen.pinyin_title_hold_height);

    static final int PINYIN_CIRCLE_SIZE = (SCREEN_WIDTH - 4 * PINYIN_CIRCLE_MARGIN) / 3;
    static final int PINYIN_LETTER_STROKE_WIDTH = getDimensionPixelSize(R.dimen.pinyin_letter_stroke_width);


    static String getString(int resId) {
        return PinyinApp.getContext().getResources().getString(resId);
    }

    static int getDimensionPixelSize(int resId) {
        return PinyinApp.getContext().getResources().getDimensionPixelSize(resId);
    }

}
