package com.test.shileiyu.jetpack.common.bean.cal;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author shilei.yu
 * @date 2018/8/28
 */

public class Day {
    public Month parent;
    public Date date;
    public boolean isOutBound = false;
    public boolean isSelected = false;

    public boolean isMatchParent() {
        Date parentFirstDay = parent.firstDay.date;
        int year = parentFirstDay.getYear();
        int year1 = this.date.getYear();
        int month = parentFirstDay.getMonth();
        int month1 = this.date.getMonth();

        return year == year1 && month == month1;
    }

    public Day(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Day) {
            Date compare = ((Day) obj).date;
            return date.getYear() == compare.getYear() && date.getMonth() == compare.getMonth() && date.getDate() == compare.getDate();
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        String format = sdf.format(date);
        return "Day " + format;
    }
}
