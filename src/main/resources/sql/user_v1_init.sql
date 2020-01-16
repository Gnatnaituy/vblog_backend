/* 2020/01/06 */
create table vblog.user
(
    id             bigint(40)   primary key not null comment '用户ID',

    username       varchar(16)  not null comment '用户名/用户账户',
    password       varchar(255) not null comment '密码',
    nickname       varchar(16)  null comment '昵称',
    gender         varchar(8)   null comment '性别',
    age            int(4)       null comment '年龄',
    bio            varchar(32)  null comment '签名',
    country        varchar(64)  null comment '国家',
    province       varchar(64)  null comment '省',
    city           varchar(64)  null comment '市',
    status         varchar(32)  null comment '用户状态',

    create_user    bigint(40)   null comment '创建用户',
    create_time    date(40)     null comment '创建时间',
    update_user    bigint(40)   null comment '更新用户',
    update_time    date(40)     null comment '更新时间',
    is_deleted     int(1)       null comment '是否已删除',
    version        int(1)       null comment '版本',
    constraint table_name_id_uindex unique (id)
);

alter table vblog.user add primary key (id);