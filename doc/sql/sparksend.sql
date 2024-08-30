drop database if exists sparksend;
create database sparksend;
use sparksend;
drop table IF EXISTS message_template;
create table message_template
(
    id               bigint auto_increment
        primary key,
    name             varchar(100) default '' not null comment '标题',
    audit_status     tinyint      default 0  not null comment '当前消息审核状态： 10.待审核 20.审核成功 30.被拒绝',
    flow_id          varchar(50)             null comment '工单ID',
    msg_status       tinyint      default 0  not null comment '当前消息状态：10.新建 20.停用 30.启用 40.等待发送 50.发送中 60.发送成功 70.发送失败',
    cron_task_id     bigint                  null comment '定时任务Id (xxl-job-admin返回)',
    cron_crowd_path  varchar(500)            null comment '定时发送人群的文件路径',
    expect_push_time varchar(100)            null comment '期望发送时间：0:立即发送 定时任务以及周期任务:cron表达式',
    id_type          tinyint      default 0  not null comment '消息的发送ID类型：10. userId 20.did 30.手机号 40.openId 50.email 60.企业微信userId',
    send_channel     tinyint      default 0  not null comment '消息发送渠道：10.IM 20.Push 30.短信 40.Email 50.公众号 60.小程序 70.企业微信 80.钉钉机器人 90.钉钉工作通知 100.企业微信机器人 110.飞书机器人 110. 飞书应用消息 ',
    template_type    tinyint      default 0  not null comment '10.运营类 20.技术类接口调用',
    msg_type         tinyint      default 0  not null comment '10.通知类消息 20.营销类消息 30.验证码类消息',
    shield_type      tinyint      default 0  not null comment '10.夜间不屏蔽 20.夜间屏蔽 30.夜间屏蔽(次日早上9点发送)',
    msg_content      varchar(600) default '' not null comment '消息内容 占位符用{$var}表示',
    send_account     tinyint      default 0  not null comment '发送账号 一个渠道下可存在多个账号',
    creator          varchar(45)  default '' not null comment '创建者',
    updator          varchar(45)  default '' not null comment '更新者',
    auditor          varchar(45)  default '' not null comment '审核人',
    team             varchar(45)  default '' not null comment '业务方团队',
    proposer         varchar(45)  default '' not null comment '业务方',
    is_deleted       tinyint      default 0  not null comment '是否删除：0.不删除 1.删除',
    created          int          default 0  not null comment '创建时间',
    updated          int          default 0  not null comment '更新时间'
)
    comment '消息模板信息' engine = InnoDB
                           collate = utf8mb4_unicode_ci;

create index idx_channel
    on message_template (send_channel);

create table sms_record
(
    id                  bigint auto_increment
        primary key,
    message_template_id bigint       default 0  not null comment '消息模板ID',
    phone               bigint       default 0  not null comment '手机号',
    supplier_id         tinyint      default 0  not null comment '发送短信渠道商的ID',
    supplier_name       varchar(40)  default '' not null comment '发送短信渠道商的名称',
    msg_content         varchar(600) default '' not null comment '短信发送的内容',
    series_id           varchar(100) default '' not null comment '下发批次的ID',
    charging_num        tinyint      default 0  not null comment '计费条数',
    report_content      varchar(50)  default '' not null comment '回执内容',
    status              tinyint      default 0  not null comment '短信状态： 10.发送 20.成功 30.失败',
    send_date           int          default 0  not null comment '发送日期：20211112',
    created             int          default 0  not null comment '创建时间',
    updated             int          default 0  not null comment '更新时间'
)
    comment '短信记录信息' engine = InnoDB
                           collate = utf8mb4_unicode_ci;

create index idx_send_date
    on sms_record (send_date);


