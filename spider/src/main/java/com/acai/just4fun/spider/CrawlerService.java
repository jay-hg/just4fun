package com.acai.just4fun.spider;

import com.acai.just4fun.spider.entity.JobInfo;
import com.acai.just4fun.spider.service.JobInfoService;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class CrawlerService {

    @Autowired
    private JobInfoService jobInfoService;

    public void crawl(String urlString,List<NameValuePair> params) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(urlString);
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");

        httpPost.setEntity(new UrlEncodedFormEntity(params));

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);

            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                String responseBody = EntityUtils.toString(response.getEntity());
                List<JobInfo> jobInfoList = ExtractService.extract(responseBody);

                //数据入库
                for (JobInfo jobInfo : jobInfoList) {
                    jobInfoService.save(jobInfo);
                    log.info("入库成功:{}",jobInfo);
                }
            } else {
                log.info("CrawlerService.crawl请求失败,request url:{},request param:{}",urlString,params);
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }
}
