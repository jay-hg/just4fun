import com.acai.just4fun.spider.CrawlerService;

import java.io.IOException;

public class TestSpider {
    public static void main(String[] args) {
        String urlStr = "https://www.zhipin.com/mobile/jobs.json";
        CrawlerService cs = new CrawlerService();
        try {
            cs.crawl(urlStr);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("爬取失败,io异常");
        }
    }
}
