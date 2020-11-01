package com.kejunyao.lecture.pinyin;

/**
 * $类描述$
 *
 * @author kejunyao
 * @since 2020年10月08日
 */
public class Letter {

    public final LetterType type;
    public final String text;
    public int position;

    public Letter(LetterType type, String text) {
        this.type = type;
        this.text = text;
    }
}
