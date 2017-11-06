package com.wsd.knowledge.util;
import com.wsd.knowledge.cache.Cache;
import com.wsd.knowledge.cache.CacheManager;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static jdk.nashorn.internal.objects.NativeDate.getTime;

public class DateUtil {


    public String getSystemTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
        String str = df.format(new Date());// new Date()为获取当前系统时间
        return str;
    }
//    public static String getSystemSmallTime() {
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
//        String str = df.format(new Date());// new Date()为获取当前系统时间
//        return str;
//    }
/**
 *    获取当前星期第一天
  */

public static String getWeekStartDate(){
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    Date date = cal.getTime();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    String str = format.format(date);
    return str;
}
    /**
     * 获取本周日日期
     * @return
     */
    public static String getWeekSunday(){
        Calendar cal =Calendar.getInstance();
//  System.out.println(cal.getTime());
        //这种输出的是上个星期周日的日期，因为老外那边把周日当成第一天
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
//  System.out.println(cal.getTime());

        //增加一个星期，才是我们中国人理解的本周日的日期
        cal.add(Calendar.WEEK_OF_YEAR, 1);
//  System.out.println(cal.getTime()); //本周日
        String time = getTime(cal)+" 23:59:59";
        System.out.println(time);
        return time;
    }

    /**
     * 获得当月第一天
     * @return
     */
    public static String  getMonthFirstDay(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm ");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
        String first = format.format(c.getTime());
        System.out.println("===============first:"+first);
        return first;
    }
    /**
     * 获得当月最后一天
     * @return
     */
    public static String  getMonthLastDay(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm ");
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = format.format(ca.getTime());
        System.out.println("===============last:"+last);
        return last;
    }
    public static String DayStartTime(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm ");
        Calendar c1 = new GregorianCalendar();
        c1.set(Calendar.HOUR_OF_DAY, 0);
        c1.set(Calendar.MINUTE, 0);

        String str1=format.format(c1.getTime());
        return str1;
    }
    public static String DayEndTime(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm ");
        Calendar c2 = new GregorianCalendar();
        c2.set(Calendar.HOUR_OF_DAY, 23);
        c2.set(Calendar.MINUTE, 59);
        String str2=format.format(c2.getTime());
        return str2;
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
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm ");
//        Calendar c = Calendar.getInstance();
//        c.add(Calendar.MONTH, 0);
//        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
//        String first = format.format(c.getTime());
//        System.out.println("===============first:"+first);
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm ");
//        Calendar c1 = new GregorianCalendar();
//        c1.set(Calendar.HOUR_OF_DAY, 0);
//        c1.set(Calendar.MINUTE, 0);
//
//        String str1=format.format(c1.getTime());
//        System.out.println(str1);
//        Calendar c2 = new GregorianCalendar();
//        c2.set(Calendar.HOUR_OF_DAY, 23);
//        c2.set(Calendar.MINUTE, 59);
//
//        String str2=format.format(c2.getTime());
//        System.out.println(str2);
    }

}
