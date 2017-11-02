package com.wsd.knowledge.util;
import com.wsd.knowledge.cache.Cache;
import com.wsd.knowledge.cache.CacheManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class DateUtil {

    public static int differnceDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = new Date();
        long time1 = d1.getTime();
        long time2 = 0;
        try {
            Date d2 = sdf.parse(date);
            time2 = d2.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long between_days = (time1 - time2) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }


    public String getSystemTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
        String str = df.format(new Date());// new Date()为获取当前系统时间
        return str;
    }


    public static String getSystemSmallTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String str = df.format(new Date());// new Date()为获取当前系统时间
        return str;
    }



    public String cacheExist(String user_id) {
        String key = user_id;
        Cache cache = CacheManager.getCacheInfo(key);
        if (null == cache || cache.getValue() == null) {
            cache = new Cache();
            cache.setKey(key);
            cache.setValue(1);
            CacheManager.putCache(key, cache);
            return "empty";
        } else {
            return "full";
        }
    }

    public static void main(String[] args) {

    }

}
