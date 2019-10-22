package com.acai.just4fun.service;

import com.acai.just4fun.dto.QueryAverageSalaryDTO;
import com.acai.just4fun.entity.JobInfo;
import com.acai.just4fun.enums.DataStatus;
import com.acai.just4fun.vo.AverageSalaryVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueryService {
    @Autowired
    JobInfoService jobInfoService;

    public AverageSalaryVO queryAverageSalary(QueryAverageSalaryDTO queryDTO) {
        List<JobInfo> jobInfoList = jobInfoService.list(new QueryWrapper<JobInfo>().lambda()
                .eq(JobInfo::getDataStatus, DataStatus.USEABLE)
                .eq(JobInfo::getExperience, queryDTO.getExperience())
                .eq(JobInfo::getLocation, queryDTO.getLocation())
        );

        AverageSalaryVO vo = new AverageSalaryVO();
        int minSalarySum = 0;
        int maxSalarySum = 0;
        for (JobInfo jobInfo : jobInfoList) {
            minSalarySum += jobInfo.getMinSalary();
            maxSalarySum += jobInfo.getMaxSalary();
        }

        if (jobInfoList != null && jobInfoList.size() > 0) {
            vo.setSampleSize(jobInfoList.size());
            vo.setAverageSalaryMin(minSalarySum/vo.getSampleSize());
            vo.setAverageSalaryMax(maxSalarySum/vo.getSampleSize());
            vo.setAverageSalary(vo.getAverageSalaryMax()/2+vo.getAverageSalaryMin()/2);
        }
        return vo;
    }
}
