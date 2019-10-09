package com.acai.just4fun.spider.job;

import com.acai.just4fun.spider.CrawlerService;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Setter
@Getter
public class ZhipinCrawlJob implements ICrawlJob {
    public final static String ZHIPIN_CRAWL_URL = "https://www.zhipin.com/mobile/jobs.json";

    private List<NameValuePair> params = new LinkedList<>();

    @Override
    public void run() {
        CrawlerService cs = new CrawlerService();
        try {
            cs.crawl(ZHIPIN_CRAWL_URL, params);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("爬取失败,io异常");
        }
    }
}
