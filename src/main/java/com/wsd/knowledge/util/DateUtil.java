package com.wsd.knowledge.util;
import com.wsd.knowledge.cache.Cache;
import com.wsd.knowledge.cache.CacheManager;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
public class DateUtil {
    public String getSystemTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
        String str = df.format(new Date());// new Date()为获取当前系统时间
        return str;
    }
    public String getTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String str = df.format(new Date());// new Date()为获取当前系统时间
        return str;
    }
    //当前日期加上day天
    public static String getAfterDate(String date,int day) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d=format.parse(date);
            Date date2 = new Date(d.getTime() + day * 24 * 60 * 60 * 1000);
            return format.format(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
//    public static String getSystemSmallTime() {
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
//        String str = df.format(new Date());// new Date()为获取当前系统时间
//        return str;
//    }

    /**
     * 获取当前星期第一天
     */

//public static String getWeekStartDate(){
//    Calendar cal = Calendar.getInstance();
//    cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
//    cal.set(Calendar.HOUR_OF_DAY, 0);
//    cal.set(Calendar.MINUTE, 0);
//    cal.set(Calendar.SECOND, 0);
//    Date date = cal.getTime();
//    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//    String str = format.format(date);
//    return str;
//}
    public static Map<String, String> mondayToSunday() {
        Date now = new Date();
        Date time = new Date(now.getYear(), now.getMonth(), now.getDate());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 设置时间格式
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        String imptimeBegin = sdf.format(cal.getTime());
        Date mondayDate = cal.getTime();
       // System.out.println("所在周星期一的日期：" + imptimeBegin);

        cal.add(Calendar.DATE, 6);
        cal.set(Calendar.HOUR, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);

        String imptimeEnd = sdf.format(cal.getTime());
        Date sundayDate = cal.getTime();
//      System.out.println("所在周星期日的日期：" + imptimeEnd);
        DateFormat datetimeDf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//      System.out.println("星期一的开始："+datetimeDf.format(mondayDate));
//      System.out.println("星期天的结束："+datetimeDf.format(sundayDate));
        Map<String, String> map = new HashMap<>();
        map.put("beginDate", datetimeDf.format(mondayDate));
        map.put("endDate", datetimeDf.format(sundayDate));
        return map;
    }

    /**
     * 获得当月第一天
     *
     * @return
     */
    public static String getMonthFirstDay() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        String first = format.format(c.getTime());
        return first;
    }

    /**
     * 获得当月最后一天
     *
     * @return
     */
    public static String getMonthLastDay() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = format.format(ca.getTime());
//        System.out.println("===============last:"+last);
        return last;
    }

    public static String DayStartTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
        Calendar c1 = new GregorianCalendar();
        c1.set(Calendar.HOUR_OF_DAY, 0);
        c1.set(Calendar.MINUTE, 0);
        c1.set(Calendar.SECOND, 0);

        String str1 = format.format(c1.getTime());
        return str1;
    }

    public static String DayEndTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
        Calendar c2 = new GregorianCalendar();
        c2.set(Calendar.HOUR_OF_DAY, 23);
        c2.set(Calendar.MINUTE, 59);
        c2.set(Calendar.SECOND, 59);
        String str2 = format.format(c2.getTime());
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
        System.out.println(DateUtil.DayStartTime());
        System.out.println(DateUtil.DayEndTime());
        System.out.println(DateUtil.getMonthFirstDay());
        System.out.println(DateUtil.getMonthLastDay());
        System.out.println(DateUtil.mondayToSunday().get("beginDate"));
        System.out.println(DateUtil.mondayToSunday().get("endDate"));
    }

}
