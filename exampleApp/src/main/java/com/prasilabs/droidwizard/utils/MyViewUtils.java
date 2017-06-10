package com.prasilabs.droidwizard.utils;

import android.text.TextUtils;

/**
 * Created by Contus team on 10/6/17.
 */

public class MyViewUtils {

    public static String toTitleCase(String text) {
        if (!TextUtils.isEmpty(text)) {
            return text.substring(0, 1).toUpperCase() + text.substring(1);
        } else {
            return "";
        }
    }

    public static String toUpperCase(String text) {
        if (!TextUtils.isEmpty(text)) {
            return text.toUpperCase();
        } else {
            return "";
        }
    }

    public static String toLowerCase(String text) {
        if (!TextUtils.isEmpty(text)) {
            return text.toLowerCase();
        } else {
            return "";
        }
    }

    public static String toCamelCase(String text) {
        if (!TextUtils.isEmpty(text)) {
            String[] words = text.split(" ");
            StringBuilder camelText = new StringBuilder();
            for (String word : words) {
                camelText.append(toTitleCase(word));
                camelText.append(" ");
            }
            return camelText.toString().trim();
        } else {
            return "";
        }
    }
}
