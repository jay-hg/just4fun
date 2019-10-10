package com.acai.just4fun.job;

import com.acai.just4fun.entity.JobInfo;

import java.util.List;

public interface ICrawlJob extends Runnable {
    public List<JobInfo> extractJobInfo(String responseBody);
}
