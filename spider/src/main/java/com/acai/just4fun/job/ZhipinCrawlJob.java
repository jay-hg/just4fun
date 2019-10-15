package com.acai.just4fun.job;

import com.acai.just4fun.entity.JobInfo;
import com.acai.just4fun.enums.JobSourceEnum;
import com.acai.just4fun.service.CrawlerService;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Setter
@Getter
@Component
@Scope(value = "prototype")
public class ZhipinCrawlJob implements ICrawlJob {

    @Autowired
    private CrawlerService crawlerService;

    public final static String ZHIPIN_CRAWL_URL = "https://www.zhipin.com/mobile/jobs.json";

    //detail_url字段的地址前缀
    private final static String URL_PRIFIX = "https://www.zhipin.com";

    private List<NameValuePair> params = new LinkedList<>();

    @Override
    public void run() {
        try {
            crawlerService.crawl(ZHIPIN_CRAWL_URL, params, this);
        } catch (IOException e) {
            log.error("爬取失败,io异常,e=",e);
        }
    }

    @Override
    public List<JobInfo> extractJobInfo(String responseBody) {
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
            jobInfo.setSource(JobSourceEnum.BOSS);
            jobInfoList.add(jobInfo);
        }
        return jobInfoList;
    }
}
