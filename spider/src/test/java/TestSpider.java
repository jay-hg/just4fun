import com.acai.just4fun.spider.CrawlerService;

import java.io.IOException;

public class TestSpider {
    public static void main(String[] args) throws IOException {
        String urlStr = "https://www.zhihu.com";
        CrawlerService cs = new CrawlerService();
        cs.crawl(urlStr);
    }
}
