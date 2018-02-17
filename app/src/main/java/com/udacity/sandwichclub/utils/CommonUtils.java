package com.udacity.sandwichclub.utils;

import java.util.List;

public class CommonUtils {
    public static String getStringOutOfStringList(List<String> lst){
        String result = "";

        if(lst.size() > 0) {
            StringBuilder builder = new StringBuilder();
            for (String tmpStr : lst) {
                builder.append(tmpStr).append(", ");
            }
            builder.delete(builder.length() - 2, builder.length());
            result = builder.toString();
        }
        return result;
    }
}
