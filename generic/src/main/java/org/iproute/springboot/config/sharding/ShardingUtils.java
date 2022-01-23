package org.iproute.springboot.config.sharding;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.TreeSet;

/**
 * ShardingUtils
 *
 * @author winterfell
 * @since 2022/1/23
 */
public class ShardingUtils {

    /**
     * Gets suffix list for range.
     *
     * @param lowerSuffix the lower suffix
     * @param upperSuffix the upper suffix
     * @return the suffix list for range
     */
    public static TreeSet<String> getSuffixListForRange(String lowerSuffix, String upperSuffix) {
        TreeSet<String> suffixList = new TreeSet<>();
        if (lowerSuffix.equals(upperSuffix)) { //上下界在同一张表
            suffixList.add(lowerSuffix);
        } else {  //上下界不在同一张表  计算间隔的所有表
            String tempSuffix = lowerSuffix;
            while (!tempSuffix.equals(upperSuffix)) {
                suffixList.add(tempSuffix);
                String[] ym = tempSuffix.split("_");
                Date tempDate = null;
                try {
                    tempDate = DateUtils.parseDate(ym[0] + (ym[1].length() == 1 ? "0" + ym[1] : ym[1]), "yyyyMM");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar cal = Calendar.getInstance();
                cal.setTime(tempDate);
                cal.add(Calendar.MONTH, 1);
                tempSuffix = ShardingUtils.getSuffixByYearMonth(cal.getTime());
            }
            suffixList.add(tempSuffix);
        }
        return suffixList;
    }

    /**
     * Gets suffix by year month.
     *
     * @param date the date
     * @return the suffix by year month
     */
    public static String getSuffixByYearMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR) + "_" + (calendar.get(Calendar.MONTH) + 1);
    }

    /**
     * Gets prev suffix.
     *
     * @param suffix the suffix
     * @return the prev suffix
     */
    public static String getPrevSuffix(String suffix) {
        if (StringUtils.isBlank(suffix)) {
            return getSuffixByYearMonth(new Date());
        }
        String[] arr = suffix.split("_");
        if ("1".equals(arr[1])) {
            return (Integer.parseInt(arr[0]) - 1) + "_12";
        } else {
            return arr[0] + "_" + (Integer.parseInt(arr[1]) - 1);
        }
    }
}
