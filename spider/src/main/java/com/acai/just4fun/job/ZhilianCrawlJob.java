package com.acai.just4fun.job;

import com.acai.just4fun.entity.JobInfo;
import com.acai.just4fun.service.CrawlerService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
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

    public final static String ZHILIAN_CRAWL_URL = "https://m.zhaopin.com/shanghai-538/";

    private List<NameValuePair> params = new LinkedList<>();

    @Override
    public void run() {
        try {
            crawlerService.crawl(ZHILIAN_CRAWL_URL, params, this);
        } catch (IOException e) {
            log.error("爬取失败,io异常,e=",e);
        }
    }

    @Override
    public List<JobInfo> extractJobInfo(String responseBody) {
        log.info(responseBody);
        return null;
    }
}
