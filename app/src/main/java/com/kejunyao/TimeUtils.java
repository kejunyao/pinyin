package com.kejunyao;

/**
 * $类描述$
 *
 * @author kejunyao
 * @since 2020年09月27日
 */
public final class TimeUtils {

    private static final int TIME_IN_MILL = 1000;

    private TimeUtils() {
    }

    public static String getShowTimeText(int millSecond) {
        int second = millSecond / TIME_IN_MILL;
        int hours = second / (60 * 60);
        int minutes = (second % (60 * 60)) / (60);
        int seconds = second % 60;
        StringBuilder sb = new StringBuilder();
        if (hours < 10) {
            sb.append('0');
        }
        sb.append(hours);
        sb.append(':');
        if (minutes < 10) {
            sb.append('0');
        }
        sb.append(minutes);
        sb.append(':');
        if (seconds < 10) {
            sb.append('0');
        }
        sb.append(seconds);

        sb.append('.');
        int mill = millSecond % TIME_IN_MILL;
        if (mill >= 100) {
            sb.append(mill);
        } else if (mill >= 10) {
            sb.append('0').append(mill);
        } else {
            sb.append("00").append(mill);
        }
        return sb.toString();
    }
}
