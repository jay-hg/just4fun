package com.acai.just4fun.service;

import com.acai.just4fun.entity.JobInfo;
import com.acai.just4fun.job.ICrawlJob;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class CrawlerService {

    public static final String JOB_INFO_URL_KEY = "jobInfoUrl";
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    private JobInfoService jobInfoService;

    public void crawl(String urlString, List<NameValuePair> params, ICrawlJob crawlJob) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(urlString);
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");

        httpPost.setEntity(new UrlEncodedFormEntity(params));

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);

            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                String responseBody = EntityUtils.toString(response.getEntity());
                List<JobInfo> jobInfoList = crawlJob.extractJobInfo(responseBody);

                //数据入库
                for (JobInfo jobInfo : jobInfoList) {
                    //校验数据是否重复
                    if (redisTemplate.opsForSet().isMember(JOB_INFO_URL_KEY,jobInfo.getDetailUrl())) {
                        continue;
                    }

                    jobInfoService.save(jobInfo);
                    redisTemplate.opsForSet().add(JOB_INFO_URL_KEY, jobInfo.getDetailUrl());
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
