package com.acai.just4fun.spider.job;

import com.acai.just4fun.spider.CrawlerService;
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
public class ZhipinCrawlJob implements ICrawlJob {

    @Autowired
    private CrawlerService crawlerService;

    public final static String ZHIPIN_CRAWL_URL = "https://www.zhipin.com/mobile/jobs.json";

    private List<NameValuePair> params = new LinkedList<>();

    @Override
    public void run() {
        try {
            crawlerService.crawl(ZHIPIN_CRAWL_URL, params);
        } catch (IOException e) {
            log.error("爬取失败,io异常,e=",e);
        }
    }
}
