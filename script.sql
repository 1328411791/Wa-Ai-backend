create table sd_comment
(
    id                 bigint auto_increment
        primary key,
    type               char   default '0' null comment '评论类型（0代表帖子评论，1代表模型评论）',
    article_id         bigint             null comment '所属帖子或模型id',
    root_id            bigint default -1  null comment '根评论id',
    content            varchar(512)       null comment '评论内容',
    to_comment_user_id bigint default -1  null comment '所回复的目标评论的userid',
    to_comment_id      bigint default -1  null comment '回复目标评论id',
    create_time        datetime           null,
    update_time        datetime           null,
    is_delete          int    default 0   null comment '删除标志（0代表未删除，1代表已删除）',
    comment_user_id    bigint             null comment '该评论的用户id（谁回复的）'
)
    comment '评论表' charset = utf8mb4;

create table sd_image
(
    id                  varchar(64)   not null
        primary key,
    checkpoint_model_id int           null,
    vae_model_id        int           null,
    params              varchar(2048) null,
    static_image_id     varchar(64)   null,
    liked               int default 0 null comment '喜欢数',
    user_id             int           null comment '谁创造的',
    update_time         datetime      null,
    create_time         datetime      null,
    type                varchar(255)  null,
    favours             int           null comment '收藏数',
    is_delete           tinyint       null
);

create table sd_image_from_imagespost
(
    id          int          null,
    sdimage_id  varchar(254) null,
    imagespost_ varchar(254) null
);

create table sd_image_from_sdmodels
(
    id         int          null,
    sdimage_id varchar(254) null,
    sdmodel_id int          null
);

create table sd_image_likes
(
    id         int          null,
    sdimage_id varchar(254) null,
    user_id    int          null
);

create table sd_image_tag
(
    id          int          null,
    name_en     varchar(254) null,
    name_cn     varchar(254) null,
    number_refe int          null
);

create table sd_image_user_favour
(
    id          int auto_increment
        primary key,
    user_id     int          not null,
    sd_image_id varchar(255) not null
);

create index `default`
    on sd_image_user_favour (id);

create table sd_model
(
    id           int           null,
    title        varchar(254)  null,
    type         varchar(254)  null,
    body         varchar(254)  null,
    liked        int default 0 null,
    publish_time datetime      null,
    create_time  datetime      null,
    update_time  datetime      null,
    author_id    int           null,
    status       varchar(254)  null,
    filename     varchar(254)  null
);

create table sd_model_favorite
(
    id          int auto_increment
        primary key,
    model_id    int           null,
    user_id     int           null,
    create_time datetime      null,
    update_time datetime      null,
    is_delete   int default 0 null
);

create table sd_model_likes
(
    id         int          not null
        primary key,
    sdmodel_id varchar(254) null,
    user_id    int          null
);

create table sd_post
(
    id          int auto_increment
        primary key,
    user_id     int           null,
    title       varchar(254)  null,
    body        varchar(254)  null,
    numFavours  int           null,
    numLiked    int           null,
    numComment  int           null,
    create_time datetime      null,
    update_time datetime      null,
    is_delete   int default 0 null
);

create table sd_post_favorite
(
    id          int auto_increment
        primary key,
    user_id     int           null,
    post_id     int           null,
    create_time datetime      null,
    update_time datetime      null,
    is_delete   int default 0 null
);

create table sd_post_images
(
    id         int auto_increment
        primary key,
    post_id    int         null,
    sdimage_id varchar(64) null
);

create table sd_post_model
(
    id       int auto_increment
        primary key,
    model_id int null,
    post_id  int null
);

create table sd_role
(
    id          int auto_increment
        primary key,
    role_name   varchar(64)   not null,
    description varchar(128)  null,
    is_delete   int default 0 not null,
    update_time datetime      null,
    create_time datetime      null
);

create table sd_static_image
(
    id          varchar(64)  not null
        primary key,
    image_name  varchar(128) null comment 'image_name',
    file_path   varchar(512) null,
    user_id     int          null,
    hash        varchar(64)  null,
    create_time datetime     null,
    update_time datetime     null,
    is_delete   tinyint      null
)
    comment '静态文件储存';

create table sd_task
(
    id                 varchar(64)   not null
        primary key,
    user_id            int           null,
    txt2image_options  varchar(2048) null,
    extraimage_options varchar(2048) null,
    type               varchar(16)   null comment '类型',
    priority           int           null comment '优先度',
    status             int           null comment '0 待进行 1 正在运行 2已完成 3 错误',
    image_id           varchar(64)   null,
    create_time        datetime      null,
    update_time        datetime      null
);

create table sd_user
(
    id          int auto_increment
        primary key,
    user_name   varchar(64)   not null,
    nick_name   varchar(64)   not null,
    email       varchar(128)  not null,
    avatar      varchar(512)  null,
    description varchar(512)  null,
    gender      varchar(32)   null,
    password    varchar(64)   null,
    role        int default 1 not null,
    is_delete   int default 0 not null,
    create_time datetime      null,
    update_time datetime      null
)
    comment 'user';


