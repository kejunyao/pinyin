package com.kejunyao.lecture.pinyin;

import android.content.Context;
import com.kejunyao.arch.recycler.AdapterData;
import java.util.ArrayList;
import java.util.List;

/**
 * $类描述$
 *
 * @author kejunyao
 * @since 2020年10月08日
 */
final class PinyinFactory {

    private static final int GROUP_SIZE = 3;

    private PinyinFactory() {
    }

    static List<AdapterData> buildData(Context context) {
        List<AdapterData> result = new ArrayList<>();
        AdapterData data = new AdapterData(LetterAdapter.TYPE_TITLE, Utils.getString(R.string.shengmu_title));
        result.add(data);
        result.addAll(groupData(context, R.array.shengmu, LetterType.SHENG_MU));
        data = new AdapterData(LetterAdapter.TYPE_TITLE, Utils.getString(R.string.yunmu_title));
        result.add(data);
        result.addAll(groupData(context, R.array.yunmu, LetterType.YUN_MU));
        data = new AdapterData(LetterAdapter.TYPE_TITLE, Utils.getString(R.string.zhengti_title));
        result.add(data);
        result.addAll(groupData(context, R.array.zhengti, LetterType.ZHENG_TI));

        int index = 0;
        for (AdapterData ad : result) {
            index ++;
            if (ad.type == LetterAdapter.TYPE_LETTER) {
                List<Letter> letters = (List<Letter>) ad.data;
                for (Letter letter : letters) {
                    letter.position = index;
                }
            }
        }
        return result;
    }

    private static List<AdapterData> groupData(Context context, int arrayId, LetterType type) {
        List<AdapterData> result = new ArrayList<>();
        String[] array = context.getResources().getStringArray(arrayId);
        final int arraySize = array.length % GROUP_SIZE == 0 ? array.length / GROUP_SIZE : array.length / GROUP_SIZE + 1;
        for (int i = 0; i < arraySize; i++) {
            int firstIndex = i * GROUP_SIZE;
            int secondIndex = firstIndex + 1;
            int thirdIndex = secondIndex + 1;
            AdapterData data = new AdapterData(LetterAdapter.TYPE_LETTER);
            List<Letter> letters = new ArrayList<>();
            data.data = letters;
            result.add(data);
            letters.add(new Letter(type, array[firstIndex]));
            if (secondIndex < array.length) {
                letters.add(new Letter(type, array[secondIndex]));
            }
            if (thirdIndex < array.length) {
                letters.add(new Letter(type, array[thirdIndex]));
            }
        }
        return result;
    }


    static int getDistance(LetterAdapter adapter, int firstIndex, int secondIndex, boolean isDown) {
        int first = 0;
        int second = 0;
        if (firstIndex < secondIndex) { // 向下滑动
            first = firstIndex;
            second = secondIndex;
        } else if (firstIndex > secondIndex) {
            first = secondIndex;
            second = firstIndex;
        } else {
            return isDown ? 0 : -Utils.PINYIN_TITLE_HOLDER_HEIGHT;
        }
        int distance = 0;
        for (int i = first; i < second; i++) {
            AdapterData ad = adapter.getItem(i);
            if (ad.type == LetterAdapter.TYPE_TITLE) {
                distance += Utils.PINYIN_TITLE_HOLDER_HEIGHT;
                if (isDown) {
                } else {
                    distance += Utils.PINYIN_CIRCLE_SIZE;
                }
            } else if (ad.type == LetterAdapter.TYPE_LETTER) {
                if (isDown) {
                    if (i < second - 1) {
                        distance += Utils.PINYIN_CIRCLE_MARGIN + Utils.PINYIN_CIRCLE_SIZE;
                    }
                } else {
                    distance += Utils.PINYIN_CIRCLE_MARGIN + Utils.PINYIN_CIRCLE_SIZE;
                    if (i == second - 1) {
                        distance += Utils.PINYIN_CIRCLE_SIZE;
                    }
                }
            }
        }
        return firstIndex < secondIndex ? distance : -distance;
    }
}
