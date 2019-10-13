package com.acai.just4fun.job;

import com.acai.just4fun.entity.JobInfo;
import com.acai.just4fun.service.CrawlerService;
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
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Setter
@Getter
@Component
@Scope(value = "prototype")
public class ZhilianCrawlJob implements ICrawlJob {

    @Autowired
    private CrawlerService crawlerService;

    //detail_url字段的地址前缀
    private final static String URL_PRIFIX = "https://m.zhaopin.com";

    public final static String ZHILIAN_CRAWL_URL = "https://m.zhaopin.com/shanghai-538/";

    private List<NameValuePair> params = new LinkedList<>();

    @Override
    public void run() {
        try {
            crawlerService.crawl(ZHILIAN_CRAWL_URL, params, this);
        } catch (IOException e) {
            log.error("爬取失败,io异常,e=", e);
        }
    }

    @Override
    public List<JobInfo> extractJobInfo(String responseBody) {
        List<JobInfo> jobInfos = new LinkedList<>();
        Document document = Jsoup.parse(responseBody);
        Elements elements = document.select("#r_content section");
        for (Element element : elements) {
            JobInfo jobInfo = new JobInfo();
            String detailUrl = URL_PRIFIX + element.selectFirst("a").attr("data-link");
            jobInfo.setDetailUrl(detailUrl);
            jobInfo.setSalary(element.select(".job-sal").text());
            Element jobItem = element.select(".jobmenu").first();
            jobInfo.setCompanyName(jobItem.attr("data-companname"));
            jobInfo.setJobType(jobItem.attr("data-jobname"));

            jobInfos.add(jobInfo);
        }
        return jobInfos;
    }
}
