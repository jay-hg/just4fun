package com.acai.just4fun.spider;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CrawlerService {

    public void crawl(String urlString) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(urlString);
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("page", "1"));
        nvps.add(new BasicNameValuePair("city", "101020100"));
        nvps.add(new BasicNameValuePair("query", "java"));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps));

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);

            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                String responseBody = EntityUtils.toString(response.getEntity());
                List<JobInfo> jobInfoList = ExtractService.extract(responseBody);
                for (JobInfo jobInfo : jobInfoList) {
                    System.out.println(jobInfo);
                }
            } else {
                System.out.println("请求失败");
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }
}
