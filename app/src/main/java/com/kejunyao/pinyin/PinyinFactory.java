package com.kejunyao.pinyin;

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
        AdapterData data = new AdapterData(LetterAdapter.TYPE_TITLE, "声母（23个）");
        result.add(data);
        result.addAll(groupData(context, R.array.shengmu, LetterType.SHENG_MU));
        data = new AdapterData(LetterAdapter.TYPE_TITLE, "韵母（24个）");
        result.add(data);
        result.addAll(groupData(context, R.array.yunmu, LetterType.YUN_MU));
        data = new AdapterData(LetterAdapter.TYPE_TITLE, "整体认读音节（16个）");
        result.add(data);
        result.addAll(groupData(context, R.array.zhengti, LetterType.ZHENG_TI));

        int index = 0;
        for (int i = 0, size = result.size(); i < size; i++) {
            index ++;
            AdapterData ad = result.get(i);
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
}
