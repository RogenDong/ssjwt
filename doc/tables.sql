drop schema if exists ssjwt;
create schema ssjwt;
use ssjwt;

create table if not exists sys_account
(
    `id`       int auto_increment primary key,
    `codename` varchar(50) not null comment '登录号',
    `password` varchar(68) not null comment '登录密码',
    constraint sys_account_codename_uindex unique (`codename`)
) comment '账户表';

create table sys_rule_mapping
(
    `id`         int auto_increment primary key,
    `account_id` int not null comment '账户id',
    `rule_code`  int not null comment '系统角色 - 枚举',
    constraint sys_rule_mapping_uindex unique (`account_id`, `rule_code`)
) comment '账户与角色的映射';

create table sys_user_info
(
    `id`           int auto_increment primary key,
    `account_id`   int         not null comment '账户',
    `nickname`     varchar(60) not null comment '昵称',
    `gender`       tinyint     null comment '性别',
    `sign_up_date` date        not null comment '注册年月',
    constraint sys_user_info_nickname_uindex unique (`nickname`)
) comment '用户信息表';

# 密码使用 BCrypt 加密
insert into wind_dev.sys_account (id, codename, password)
values (1, 'admin', '$2a$10$Y07S7tgCwDyFMuK4hKJbfer59V8fJHY7okDVzjAbGmBGL8/4AvexO');

# rule_code 是角色枚举的序号（0开始）
insert into wind_dev.sys_rule_mapping (id, account_id, rule_code)
values  (1, 1, 3);

# admin 账户不配置对照的 user_info；建议另起账户来配置新的 user_info
