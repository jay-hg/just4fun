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

