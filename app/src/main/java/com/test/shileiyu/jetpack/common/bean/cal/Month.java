package com.test.shileiyu.jetpack.common.bean.cal;

import com.test.shileiyu.jetpack.common.bean.TwoTuple;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author shilei.yu
 * @date 2018/8/28
 */

public class Month {
    public String name;

    public Day firstDay;

    public List<Day> children;

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(firstDay.date);
        return "Day1=" + format + " child=" + children.size();
    }

    public Month(Date firstDate) {
        firstDay = new Day(firstDate);
    }

    public static void main(String[] a) {
        TwoTuple<List<Month>, List<Day>> tuple = get(new Date(), 2);
        List<Month> first = tuple.first;
        for (Month m : first) {
            System.out.println(m.toString());
        }
    }

    public static TwoTuple<List<Month>, List<Day>> get(Date now, int monthCount) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(now);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
        //本月第一天
        Date nowMonthFirstDay = calendar.getTime();

        List<Month> data = new ArrayList<>(monthCount);
        for (int i = 0; i < monthCount; i++) {
            Date time = calendar.getTime();
            data.add(new Month(time));
            calendar.add(Calendar.MONTH, 1);
        }
        Calendar start = Calendar.getInstance(Locale.CHINA);
        Calendar end = Calendar.getInstance(Locale.CHINA);

        calendar.setTime(nowMonthFirstDay);
        //上个月第一天
        calendar.add(Calendar.MONTH, -1);
        start.setTime(calendar.getTime());
        //结束月的下一个月
        calendar.add(Calendar.MONTH, monthCount + 2);
        end.setTime(calendar.getTime());
        List<Day> days = new ArrayList<>();
        while (start.before(end)) {
            days.add(new Day(start.getTime()));
            start.add(Calendar.DATE, 1);
        }
        for (Day d : days) {
            System.out.println(d.toString());
        }
        for (Month m : data) {
            m.clipChildren(days);
        }

        return new TwoTuple<>(data,days);
    }

    private void clipChildren(List<Day> days) {
        if (children == null) {
            children = new ArrayList<>(36);
        }
        children.clear();
        //找到这个月的第一天
        int preCount = getPreCount();
        int index = days.indexOf(firstDay);
        int start = index - preCount;
        int end = start + 42;
        if (start >= 0) {
            children.addAll(days.subList(start, end));
        }
        if (children.size() > 0) {
            for (Day d : children) {
                d.parent = this;
            }
        }
        //查找其在集合的位置
        //截取出36个
    }

    private int getPreCount() {
        Calendar instance = Calendar.getInstance(Locale.CHINA);
        instance.setTime(firstDay.date);
        return instance.get(Calendar.DAY_OF_WEEK) - 1;
    }

    private static void log(Calendar cal) {
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int dayInWeek = cal.get(Calendar.DAY_OF_WEEK);
        System.out.println("year=" + year + " month=" + month + " day=" + day + " dayInWeek=" + dayInWeek);
    }
}
