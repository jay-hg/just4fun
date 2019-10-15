import com.acai.just4fun.SpiderApplication;
import com.acai.just4fun.job.ZhilianCrawlJob;
import com.acai.just4fun.job.ZhipinCrawlJob;
import com.acai.just4fun.service.CrawlerService;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootTest(classes = SpiderApplication.class)
@RunWith(SpringRunner.class)
public class TestSpider {

    @Autowired
    private CrawlerService cs;

    private ThreadPoolExecutor crawlExecutor = new ThreadPoolExecutor(4, 4, 2000, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>());

    @Autowired
    ZhipinCrawlJob zhipinCrawlJob;

    @Autowired
    ZhilianCrawlJob zhilianCrawlJob;

    @Test
    public void testZhipinCrawlJob() {
        for (int i = 1; i < 3; i++) {
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("page", i+""));
            params.add(new BasicNameValuePair("city", "101020100"));
            params.add(new BasicNameValuePair("query", "java"));
            zhipinCrawlJob.setParams(params);

            crawlExecutor.execute(zhipinCrawlJob);
        }

        crawlExecutor.shutdown();
        try {
            crawlExecutor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testZhilianCrawlJob() {
        for (int i = 1; i < 3; i++) {
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("keyword", "java"));
            params.add(new BasicNameValuePair("pageindex", "1"));
            params.add(new BasicNameValuePair("maprange", "3"));
            params.add(new BasicNameValuePair("islocation", "0"));
            params.add(new BasicNameValuePair("order", "0"));
            zhilianCrawlJob.setParams(params);

            crawlExecutor.execute(zhilianCrawlJob);
        }

        crawlExecutor.shutdown();
        try {
            crawlExecutor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
