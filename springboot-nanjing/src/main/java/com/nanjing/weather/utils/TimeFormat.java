package com.nanjing.weather.utils;

import java.sql.Timestamp;

public class TimeFormat {
    public static String getTime(Timestamp time){
        String[] times = time.toString().split(" ");
        String[] split1 = times[0].split("-");
        String[] split2 = times[1].split(":");
        String str = split1[0]+"年"+split1[1]+"月"+split1[2]+"日"+split2[0]+"时"+split2[1]+"分";
        return str;
    }
}
