create table vblog.user
(
    id             bigint(40)   not null comment '用户ID',
    account        varchar(16)  not null comment '用户名/用户账户',
    password       varchar(255) not null comment '用户密码',
    nickname       varchar(16)  null comment '用户昵称',
    realname       varchar(16)  null comment '用户真实姓名',
    gender         varchar(8)   null comment '用户性别',
    age            int(4)       null comment '用户年龄',
    bio            varchar(32)  null comment ' 用户签名',
    region         varchar(64)  null comment '用户国家',
    location       varchar(255) null comment '用户地址经纬度',
    register_time  bigint(40)   null comment '用户注册时间',
    create_user    varchar(16)  null comment ' 创建用户',
    create_time    bigint(40)   null comment '创建时间',
    update_user    varchar(16)  null comment ' 更新人',
    update_time    bigint(40)   null comment '更新时间',
    is_deleted     char         null comment '是否已删除(0未删除/1已删除)',
    version        int(1)       null comment '版本',
    constraint table_name_id_uindex unique (id)
);

alter table vblog.user add primary key (id);