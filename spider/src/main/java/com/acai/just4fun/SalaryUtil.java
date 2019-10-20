package com.acai.just4fun;

import com.acai.just4fun.entity.JobInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SalaryUtil {
    private final static String UNIT = "(k|千|万|w)?";
    public final static Pattern PATTERN = Pattern.compile("(\\d+(\\.\\d+)?)" + UNIT + ".(\\d+(\\.\\d+)?)" + UNIT);

    public static boolean resolveSalary(JobInfo jobInfo) {
        Matcher matcher = PATTERN.matcher(jobInfo.getSalary());
        if (matcher.find()) {
            jobInfo.setMinSalary(Float.parseFloat(matcher.group(1)));
            jobInfo.setMaxSalary(Float.parseFloat(matcher.group(4)));

            //参数2的单位
            String numberTwoUnit = matcher.group(6);
            //参数1的单位
            String numberOneUnit = matcher.group(3);
            if ("万".equals(numberTwoUnit) || "w".equals(numberTwoUnit)) {
                jobInfo.setMaxSalary(jobInfo.getMaxSalary() * 10);

                if ("千".equals(numberOneUnit) || "k".equals(numberOneUnit)) {
                    return true;
                }
                jobInfo.setMinSalary(jobInfo.getMinSalary() * 10);
            }
            return true;
        }
        return false;
    }
}
