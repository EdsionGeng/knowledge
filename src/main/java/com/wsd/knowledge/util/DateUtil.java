package com.wsd.knowledge.util;
import com.wsd.knowledge.cache.Cache;
import com.wsd.knowledge.cache.CacheManager;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {


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
