package com.acai.just4fun.spider;

import com.acai.just4fun.spider.entity.JobInfo;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class ExtractService {
    private final static String URL_PRIFIX = "https://www.zhipin.com";

    public static List<JobInfo> extract(String responseBody) {
        JSONObject jsonObject = JSONObject.parseObject(responseBody);
        String html = jsonObject.getString("html");
        List<JobInfo> jobInfoList = new ArrayList<>();

        Document document = Jsoup.parse(html);
        Elements items = document.select("li");
        for (Element item : items) {
            JobInfo jobInfo = new JobInfo();
            jobInfo.setDetailUrl(URL_PRIFIX + item.selectFirst("a").attr("href"));
            jobInfo.setJobType(item.select("h4").text());
            jobInfo.setSalary(item.select(".salary").text());
            jobInfo.setCompanyName(item.select(".name").text());
            Elements msgs = item.select(".msg em");
            if (msgs != null && msgs.size() >= 3) {
                String location = msgs.get(0).text();
                String experience = msgs.get(1).text();
                String education = msgs.get(2).text();

                jobInfo.setLocation(location);
                jobInfo.setExperience(experience);
                jobInfo.setEducation(education);
            }
            jobInfoList.add(jobInfo);
        }
        return jobInfoList;
    }
}
