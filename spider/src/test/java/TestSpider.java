import com.acai.just4fun.CrawlerService;
import com.acai.just4fun.ExtractService;
import com.acai.just4fun.SpiderApplication;
import com.acai.just4fun.entity.JobInfo;
import com.acai.just4fun.job.ZhipinCrawlJob;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootTest(classes = SpiderApplication.class)
@RunWith(SpringRunner.class)
public class TestSpider {

    @Autowired
    private CrawlerService cs;

    @Test
    public void testCrawl() {
        String urlStr = "https://www.zhipin.com/mobile/jobs.json";
        List<NameValuePair> params = new LinkedList<>();
        params.add(new BasicNameValuePair("page", "1"));
        params.add(new BasicNameValuePair("city", "101020100"));
        params.add(new BasicNameValuePair("query", "java"));
        try {
            cs.crawl(urlStr,params);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("爬取失败,io异常");
        }
    }

    @Test
    public void testParse() {
        String html = "{\"hasMore\":true,\"resmsg\":\"成功\",\"rescode\":1,\"html\":\"<li class='item'>\\n            <a href='/job_detail/5966a65fb6b8f51f1Hd93ti0EFQ~.html' ka='job_list_0' data-itemId='0' data-lid='7f6svMvH8um.search.1' data-index='0'>\\n                    <img src='https://img.bosszhipin.com/beijin/mcs/chatphoto/20ca9f1be24a4cd2e6f1c6e9e47b9b0f-48bc5e623d2778881n1509i8DggQl9s~.jpeg?x-oss-process=image/resize,w_120,limit_0'/>\\n                <div class='text'>\\n                    <div class='title'><h4>Java</h4><span class='salary'>11-22K</span></div>\\n                    <div class='name'>新致软件</div>\\n                    <div class='msg'><em>上海</em><em>3-5年</em><em>大专</em></div>\\n                </div>\\n            </a>\\n        </li>\\n        <li class='item'>\\n            <a href='/job_detail/b5f747d407a8537803dz2Ny4ElA~.html' ka='job_list_0' data-itemId='0' data-lid='7f6svMvH8um.search.2' data-index='1'>\\n                    <img src='https://img.bosszhipin.com/beijin/mcs/chatphoto/20170324/bb7163b80d64a5ba35bc4c141e775efb466972a7c322c03090246ca0aa1ee6e8_s.jpg?x-oss-process=image/resize,w_120,limit_0'/>\\n                <div class='text'>\\n                    <div class='title'><h4>Java</h4><span class='salary'>15-20K</span></div>\\n                    <div class='name'>平安付</div>\\n                    <div class='msg'><em>上海</em><em>3-5年</em><em>本科</em></div>\\n                </div>\\n            </a>\\n        </li>\\n        <li class='item'>\\n            <a href='/job_detail/0628020f9f9349aa1n1429W8EFE~.html' ka='job_list_0' data-itemId='0' data-lid='7f6svMvH8um.search.3' data-index='2'>\\n                    <img src='https://img.bosszhipin.com/beijin/mcs/chatphoto/20180130/4eef4a1cfe702a18b96855ac863ba54430a2ff552c77b931a3dd6f1ce1267649_s.jpg?x-oss-process=image/resize,w_120,limit_0'/>\\n                <div class='text'>\\n                    <div class='title'><h4>Java</h4><span class='salary'>25-50K</span></div>\\n                    <div class='name'>蚂蚁金服</div>\\n                    <div class='msg'><em>上海</em><em>3-5年</em><em>本科</em></div>\\n                </div>\\n            </a>\\n        </li>\\n        <li class='item'>\\n            <a href='/job_detail/3d5fc2c67fa016760n163tW-GQ~~.html' ka='job_list_0' data-itemId='0' data-lid='7f6svMvH8um.search.4' data-index='3'>\\n                    <img src='https://img.bosszhipin.com/beijin/mcs/bar/20190103/576bd3733f629b0b087d2716db550827be1bd4a3bd2a63f070bdbdada9aad826.jpg?x-oss-process=image/resize,w_120,limit_0'/>\\n                <div class='text'>\\n                    <div class='title'><h4>Java</h4><span class='salary'>20-40K</span></div>\\n                    <div class='name'>深兰科技</div>\\n                    <div class='msg'><em>上海</em><em>3-5年</em><em>大专</em></div>\\n                </div>\\n            </a>\\n        </li>\\n        <li class='item'>\\n            <a href='/job_detail/608a61408c15699b03Zz2N27F1Y~.html' ka='job_list_0' data-itemId='0' data-lid='7f6svMvH8um.search.5' data-index='4'>\\n                    <img src='https://img.bosszhipin.com/beijin/mcs/chatphoto/20180725/873fb1280a60fca35071753f3695ee7ecfcd208495d565ef66e7dff9f98764da_s.jpg?x-oss-process=image/resize,w_120,limit_0'/>\\n                <div class='text'>\\n                    <div class='title'><h4>java</h4><span class='salary'>8-10K</span></div>\\n                    <div class='name'>深圳市锐明技术</div>\\n                    <div class='msg'><em>上海</em><em>1-3年</em><em>本科</em></div>\\n                </div>\\n            </a>\\n        </li>\\n        <li class='item'>\\n            <a href='/job_detail/4b1ca2a6fa90ab521nVz2t21FFE~.html' ka='job_list_0' data-itemId='0' data-lid='7f6svMvH8um.search.6' data-index='5'>\\n                    <img src='https://img.bosszhipin.com/beijin/mcs/chatphoto/20180930/42fcf55d79a50a761a7599b0b8fa755acfcd208495d565ef66e7dff9f98764da_s.jpg?x-oss-process=image/resize,w_120,limit_0'/>\\n                <div class='text'>\\n                    <div class='title'><h4>Java</h4><span class='salary'>20-30K</span></div>\\n                    <div class='name'>万得</div>\\n                    <div class='msg'><em>上海</em><em>5-10年</em><em>本科</em></div>\\n                </div>\\n            </a>\\n        </li>\\n        <li class='item'>\\n            <a href='/job_detail/a626f859ef85124d1XF42tm6FlY~.html' ka='job_list_0' data-itemId='0' data-lid='7f6svMvH8um.search.7' data-index='6'>\\n                    <img src='https://img.bosszhipin.com/beijin/mcs/chatphoto/20190507/9e3f8be3f00bdf5d886011a114be9a61fec840bf266ca1af131b19b7d742f46f_s_s.jpg?x-oss-process=image/resize,w_120,limit_0'/>\\n                <div class='text'>\\n                    <div class='title'><h4>Java</h4><span class='salary'>15-25K</span></div>\\n                    <div class='name'>圆通速递</div>\\n                    <div class='msg'><em>上海</em><em>3-5年</em><em>本科</em></div>\\n                </div>\\n            </a>\\n        </li>\\n        <li class='item'>\\n            <a href='/job_detail/8be70087fe31e4a91Xxy29-6EFE~.html' ka='job_list_0' data-itemId='0' data-lid='7f6svMvH8um.search.8' data-index='7'>\\n                    <img src='https://img.bosszhipin.com/beijin/logo/2d2965d1392c7b201ce1e9e766c3b3f7be1bd4a3bd2a63f070bdbdada9aad826.jpg?x-oss-process=image/resize,w_120,limit_0'/>\\n                <div class='text'>\\n                    <div class='title'><h4>Java</h4><span class='salary'>25-50K</span></div>\\n                    <div class='name'>蚂蚁金服</div>\\n                    <div class='msg'><em>上海</em><em>3-5年</em><em>本科</em></div>\\n                </div>\\n            </a>\\n        </li>\\n        <li class='item'>\\n            <a href='/job_detail/84794be41262d6361XF829q8FVc~.html' ka='job_list_0' data-itemId='0' data-lid='7f6svMvH8um.search.9' data-index='8'>\\n                    <img src='https://img.bosszhipin.com/beijin/mcs/chatphoto/20180130/4eef4a1cfe702a18b96855ac863ba54430a2ff552c77b931a3dd6f1ce1267649_s.jpg?x-oss-process=image/resize,w_120,limit_0'/>\\n                <div class='text'>\\n                    <div class='title'><h4>Java</h4><span class='salary'>15-20K</span></div>\\n                    <div class='name'>蚂蚁金服</div>\\n                    <div class='msg'><em>上海</em><em>3-5年</em><em>本科</em></div>\\n                </div>\\n            </a>\\n        </li>\\n        <li class='item'>\\n            <a href='/job_detail/1dd84d6ca76c77fd1HBz39q_FlQ~.html' ka='job_list_0' data-itemId='0' data-lid='7f6svMvH8um.search.10' data-index='9'>\\n                    <img src='https://img.bosszhipin.com/beijin/mcs/chatphoto/20180130/4eef4a1cfe702a18b96855ac863ba54430a2ff552c77b931a3dd6f1ce1267649_s.jpg?x-oss-process=image/resize,w_120,limit_0'/>\\n                <div class='text'>\\n                    <div class='title'><h4>Java</h4><span class='salary'>20-40K</span></div>\\n                    <div class='name'>蚂蚁金服</div>\\n                    <div class='msg'><em>上海</em><em>5-10年</em><em>本科</em></div>\\n                </div>\\n            </a>\\n        </li>\\n        <li class='item'>\\n            <a href='/job_detail/a0cfcea685b1343f1Xx82t-9GVc~.html' ka='job_list_0' data-itemId='0' data-lid='7f6svMvH8um.search.11' data-index='10'>\\n                    <img src='https://img.bosszhipin.com/beijin/mcs/chatphoto/20190404/c53b9e1dbe023d44c5c0cba109cd8c57f683dbd8f57ddc7dec40acae511bd867_s.jpg?x-oss-process=image/resize,w_120,limit_0'/>\\n                <div class='text'>\\n                    <div class='title'><h4>JAVA技术总监</h4><span class='salary'>40-60K</span></div>\\n                    <div class='name'>平安</div>\\n                    <div class='msg'><em>上海</em><em>5-10年</em><em>本科</em></div>\\n                </div>\\n            </a>\\n        </li>\\n        <li class='item'>\\n            <a href='/job_detail/2d67ba947dec127603B63dS9GFc~.html' ka='job_list_0' data-itemId='0' data-lid='7f6svMvH8um.search.12' data-index='11'>\\n                    <img src='https://img.bosszhipin.com/beijin/mcs/chatphoto/20190309/d6f0340071a81ecdcaab162fee9c9920c7421cffdbdea8d6f11cda4aeefd5586_s.jpg?x-oss-process=image/resize,w_120,limit_0'/>\\n                <div class='text'>\\n                    <div class='title'><h4>JAVA</h4><span class='salary'>15-25K</span></div>\\n                    <div class='name'>软通动力</div>\\n                    <div class='msg'><em>上海</em><em>3-5年</em><em>本科</em></div>\\n                </div>\\n            </a>\\n        </li>\\n        <li class='item'>\\n            <a href='/job_detail/959b57dac60f15d11XBz2ty8EFU~.html' ka='job_list_0' data-itemId='0' data-lid='7f6svMvH8um.search.13' data-index='12'>\\n                    <img src='https://img.bosszhipin.com/beijin/logo/2d2965d1392c7b201ce1e9e766c3b3f7be1bd4a3bd2a63f070bdbdada9aad826.jpg?x-oss-process=image/resize,w_120,limit_0'/>\\n                <div class='text'>\\n                    <div class='title'><h4>Java</h4><span class='salary'>35-70K</span></div>\\n                    <div class='name'>蚂蚁金服</div>\\n                    <div class='msg'><em>上海</em><em>3-5年</em><em>本科</em></div>\\n                </div>\\n            </a>\\n        </li>\\n        <li class='item'>\\n            <a href='/job_detail/425d4979906c78390nR-2Nu8E1c~.html' ka='job_list_0' data-itemId='0' data-lid='7f6svMvH8um.search.14' data-index='13'>\\n                    <img src='https://img.bosszhipin.com/beijin/mcs/bar/brand/12370.jpg?x-oss-process=image/resize,w_120,limit_0'/>\\n                <div class='text'>\\n                    <div class='title'><h4>Java</h4><span class='salary'>15-20K</span></div>\\n                    <div class='name'>中科软</div>\\n                    <div class='msg'><em>上海</em><em>3-5年</em><em>本科</em></div>\\n                </div>\\n            </a>\\n        </li>\\n        <li class='item'>\\n            <a href='/job_detail/704c56e4d9e3e1081nV82tq-E1A~.html' ka='job_list_0' data-itemId='0' data-lid='7f6svMvH8um.search.15' data-index='14'>\\n                    <img src='https://img.bosszhipin.com/beijin/mcs/chatphoto/20180705/8f3d9504aeb0ba094ccfb059b6c31ccc0e9cd96778a5b4dc3010728100164f4a_s.jpg?x-oss-process=image/resize,w_120,limit_0'/>\\n                <div class='text'>\\n                    <div class='title'><h4>Java</h4><span class='salary'>20-40K</span></div>\\n                    <div class='name'>上海三思</div>\\n                    <div class='msg'><em>上海</em><em>5-10年</em><em>本科</em></div>\\n                </div>\\n            </a>\\n        </li>\",\"period\":[{\"code\":0,\"name\":\"不限\",\"subLevelModelList\":null,\"firstChar\":null,\"pinyin\":null,\"rank\":0},{\"code\":1,\"name\":\"一天以内\",\"subLevelModelList\":null,\"firstChar\":null,\"pinyin\":null,\"rank\":0},{\"code\":2,\"name\":\"三天以内\",\"subLevelModelList\":null,\"firstChar\":null,\"pinyin\":null,\"rank\":0},{\"code\":3,\"name\":\"七天以内\",\"subLevelModelList\":null,\"firstChar\":null,\"pinyin\":null,\"rank\":0},{\"code\":4,\"name\":\"十五天以内\",\"subLevelModelList\":null,\"firstChar\":null,\"pinyin\":null,\"rank\":0},{\"code\":5,\"name\":\"一个月以内\",\"subLevelModelList\":null,\"firstChar\":null,\"pinyin\":null,\"rank\":0}],\"page\":1}";
        List<JobInfo> jobInfoList = ExtractService.extract(html);
        for (JobInfo jobInfo : jobInfoList) {
            System.out.println(jobInfo);
        }
    }

    private ThreadPoolExecutor crawlExecutor = new ThreadPoolExecutor(4, 4, 2000, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>());

    @Autowired
    ZhipinCrawlJob job;

    @Test
    public void testZhipinCrawlJob() {
        for (int i = 1; i < 3; i++) {
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("page", i+""));
            params.add(new BasicNameValuePair("city", "101020100"));
            params.add(new BasicNameValuePair("query", "java"));
            job.setParams(params);

            crawlExecutor.execute(job);
        }

        try {
            crawlExecutor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
