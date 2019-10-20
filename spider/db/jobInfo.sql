/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2019/10/9 15:22:45                           */
/*==============================================================*/


drop table if exists spider_job_info;

/*==============================================================*/
/* Table: spider_job_info                                       */
/*==============================================================*/
create table spider_job_info
(
   id                   varchar(32) not null,
   detail_url           varchar(128),
   job_type             varchar(32),
   salary               varchar(32),
   company_name         varchar(128),
   location             varchar(32),
   experience           varchar(32),
   education            varchar(32),
   primary key (id)
);

-- 2019.10.15 增加数据来源字段
ALTER TABLE `spider`.`spider_job_info`
ADD COLUMN `source` enum('OTHER','BOSS','ZHILIAN','LAGOU','JOB51') NOT NULL DEFAULT 'OTHER' AFTER `education`;

-- 2019.10.20 添加三个字段：平均薪资下限、上限和数据状态
ALTER TABLE `spider`.`spider_job_info`
ADD COLUMN `min_salary` float(10, 0) NULL DEFAULT NULL AFTER `source`,
ADD COLUMN `max_salary` float(10, 0) NULL DEFAULT NULL AFTER `min_salary`,
ADD COLUMN `data_status` enum('USEABLE','INVALID','NEED_HANDLE') NOT NULL DEFAULT 'NEED_HANDLE' COMMENT '数据状态' AFTER `max_salary`;