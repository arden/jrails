package com.jrails.commons.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Time utils
 *
 * @author arden
 */
public class TimeUtils {

    static Pattern hours = Pattern.compile("^([0-9]+)h$");
    static Pattern minutes = Pattern.compile("^([0-9]+)mn$");
    static Pattern seconds = Pattern.compile("^([0-9]+)s$");

    /**
     * Parse a duration
     *
     * @param duration 3h, 2mn, 7s
     * @return The number of millis
     */
    public static int parseDuration(String duration) {
        if (duration == null) {
            return 60 * 60 * 24 * 365;
        }
        int toAdd = -1;
        if (hours.matcher(duration).matches()) {
            Matcher matcher = hours.matcher(duration);
            matcher.matches();
            toAdd = Integer.parseInt(matcher.group(1)) * (60 * 60);
        } else if (minutes.matcher(duration).matches()) {
            Matcher matcher = minutes.matcher(duration);
            matcher.matches();
            toAdd = Integer.parseInt(matcher.group(1)) * (60);
        } else if (seconds.matcher(duration).matches()) {
            Matcher matcher = seconds.matcher(duration);
            matcher.matches();
            toAdd = Integer.parseInt(matcher.group(1));
        }
        if (toAdd == -1) {
            throw new IllegalArgumentException("Invalid duration pattern : " + duration);
        }
        return toAdd;
    }
    
    public static long getBetweenTimeSecond(String bettime) {
		long between = 0;
		Date now = new Date();
		SimpleDateFormat sfarmat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strNow = sfarmat.format(now);
		try {
			SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			java.util.Date begin = dfs.parse(bettime);
			java.util.Date end = dfs.parse(strNow);
			between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
		} catch (Exception e) {
			e.printStackTrace();
		}
		return between;
	}
}