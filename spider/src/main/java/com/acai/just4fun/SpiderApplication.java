package com.acai.just4fun;

import com.acai.just4fun.entity.JobInfo;
import com.acai.just4fun.enums.BossCityCodeEnum;
import com.acai.just4fun.enums.DataStatus;
import com.acai.just4fun.job.ZhipinCrawlJob;
import com.acai.just4fun.service.CrawlerService;
import com.acai.just4fun.service.JobInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@MapperScan("com.acai.just4fun.mapper")
@EnableScheduling
@Slf4j
public class SpiderApplication {

    public static final int BATCH_JOB_COUNT = 50;

    public static void main(String[] args) {
        SpringApplication.run(SpiderApplication.class, args);
    }

    @Autowired
    private CrawlerService cs;

    private ThreadPoolExecutor crawlExecutor = new ThreadPoolExecutor(4, 4, 2000, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>());

    @Autowired
    ZhipinCrawlJob zhipinCrawlJob;

    @Autowired
    JobInfoService jobInfoService;

    @Scheduled(fixedDelay = 1000*60*60)
    public void bossJobInfoCrawl() {
        log.info("定时任务，开始boss jobInfo 爬取");

        for (BossCityCodeEnum bossCityCodeEnum : BossCityCodeEnum.values()) {
            for (int i = 1; i < BATCH_JOB_COUNT; i++) {
                ZhipinCrawlJob zhipinCrawlJob = new ZhipinCrawlJob();
                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("page", i + ""));
                params.add(new BasicNameValuePair("city", bossCityCodeEnum.getCode()));
                params.add(new BasicNameValuePair("query", "java"));
                zhipinCrawlJob.setParams(params);
                zhipinCrawlJob.setCrawlerService(cs);

                crawlExecutor.execute(zhipinCrawlJob);

                log.info("boss第{}次爬取任务已提交,线程池排队数量:{}", i, crawlExecutor.getQueue().size());
            }
        }
    }

    @Scheduled(fixedDelay = 1000*10)
    public void handleRawJobInfoData() {
        log.info("开始处理原始的jobInfo数据");
        if (crawlExecutor.getActiveCount() > 0) {
            log.info("爬虫线程还在执行，暂不处理...");
            return;
        }

        //获取待处理列表
        List<JobInfo> jobInfoList = jobInfoService.list(new QueryWrapper<JobInfo>().lambda()
            .eq(JobInfo::getDataStatus, DataStatus.NEED_HANDLE)
        );

        for (JobInfo jobInfo : jobInfoList) {
            boolean isValidateSalary = SalaryUtil.resolveSalary(jobInfo);
            if (isValidateSalary) {
                jobInfo.setDataStatus(DataStatus.USEABLE);
            } else {
                jobInfo.setDataStatus(DataStatus.INVALID);
            }

            jobInfoService.updateById(jobInfo);
            log.info("更新完成:{}",jobInfo);
        }
    }
}
