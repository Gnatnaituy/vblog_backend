create table vblog.comment
(
    id             bigint(40)   not null comment '评论ID',

    post_id         bigint(40)  not null comment '动态ID',
    target_user_id  bigint(40) not null comment '评论对象用户ID',
    comment_user_id bigint(40)  not null comment '评论用户ID',
    content         varchar(255) not null comment '评论内容',
    visibility      int(1)      not null comment '可见性',
    comment_time    bigint(40)  not null comment '评论时间',

    create_user    varchar(16)  null comment '创建用户',
    create_time    bigint(40)   null comment '创建时间',
    update_user    varchar(16)  null comment '更新人',
    update_time    bigint(40)   null comment '更新时间',
    is_deleted     char(1)      null comment '是否已删除(0未删除/1已删除)',
    version        int(1)       null comment '版本',

    constraint table_name_id_uindex unique (id)
);

alter table vblog.comment add primary key (id);