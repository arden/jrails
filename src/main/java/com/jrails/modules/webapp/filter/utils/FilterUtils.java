package com.jrails.modules.webapp.filter.utils;

/**
 * Created by arden
 * User: <a href="mailto:arden.emily@gmail.com">arden</a>
 * Date: 2009-2-14 14:42:50
 */
public class FilterUtils {
    /**
     * Change all occurrences of orig in mainString to replacement.
     */
    public static String replace(String mainString, String orig, String replacement) {
        String result = "";
        int oldIndex = 0;
        int index = 0;
        int origLength = orig.length();
        while ((index = mainString.indexOf(orig, oldIndex)) != -1) {
            result = result + mainString.substring(oldIndex, index) + replacement;
            oldIndex = index + origLength;
        }
        result = result + mainString.substring(oldIndex);
        return (result);
    }
}
