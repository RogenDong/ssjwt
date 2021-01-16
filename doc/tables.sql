drop schema if exists ssjwt;
create schema ssjwt;
use ssjwt;

create table sys_account
(
    `id`       bigint auto_increment primary key not null,
    `codename` varchar(50)                       not null comment '登录号',
    `password` varchar(68)                       not null comment '登录密码',
    constraint sys_account_codename_u_index unique (`codename`)
) comment '账户表';

create table sys_rule_mapping
(
    `id`         bigint auto_increment primary key not null,
    `account_id` bigint                            not null comment '账户id',
    `rule_code`  bigint                            not null comment '系统角色 - 枚举',
    constraint sys_rule_mapping_u_index unique (`account_id`, `rule_code`)
) comment '账户与角色的映射';

create table sys_user_info
(
    `id`           bigint auto_increment primary key not null,
    `account_id`   bigint                            not null comment '账户',
    `nickname`     varchar(60)                       not null comment '昵称',
    `gender`       tinyint default 2                 not null comment '性别',
    `sign_up_date` date                              not null comment '注册年月',
    constraint sys_user_info_nickname_u_index unique (`nickname`)
) comment '用户信息表';

# 密码使用 BCrypt 加密
insert into sys_account (codename, password)
# admin 123456
values ('admin', '$2y$10$f/IA9z5we52W9Jbk/LWMm.GD6w/6CqMlZ.CplnC7DkuCatYcjwqSK'),
# test-user 112233
       ('test-user', '$2y$12$iwOnZtGF5FcjB5.RNejWX.kQO/Xp0DnnK84tXEhhjhqQaYlSguW6a');

# rule_code 是角色枚举的序号（0开始）
insert into sys_rule_mapping (account_id, rule_code)
values (1, 3);

# 用户信息
select id into @AID_TS from sys_account where codename = 'test-user';
insert into sys_user_info (account_id, nickname, gender, sign_up_date)
values (@AID_TS, 'test', 2, current_timestamp);
