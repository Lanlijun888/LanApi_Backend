use api_db;

-- 接口信息表
create table if not exists my_db.`interface_info`
(
    `id` bigint not null auto_increment comment '主键' primary key,
    `name` varchar(256) not null comment '名称',
    `url` varchar(512) not null comment '接口地址',
    `description` varchar(256) null comment '描述',
    `requestParams` text null comment '请求参数',
    `requestHeader` text null comment '请求头',
    `responseHeader` text null comment '响应头',
    `userId` varchar(512) not null comment '创建人',
    `status` int default 0 not null comment '接口状态（0 - 关闭， 1 - 开启））',
    `method` varchar(256) not null comment '请求类型',
    `createTime` datetime not null on update CURRENT_TIMESTAMP comment '创建时间',
    `updateTime` datetime not null on update CURRENT_TIMESTAMP comment '更新时间',
    `isDelete` tinyint default 0 not null comment '是否删除(0-未删, 1-已删)'
) comment '接口信息表';


insert into my_db.`interface_info` (`name`, `url`, `requestHeader`, `responseHeader`, `userId`, `method`, `createTime`, `updateTime`) values ('唱', 'www.gale-rodriguez.biz', 'xX', 'jqY', '袁健柏', 'yXCX6', '2022-05-12 22:26:12', '2022-04-04 14:20:25');
insert into my_db.`interface_info` (`name`, `url`, `requestHeader`, `responseHeader`, `userId`, `method`, `createTime`, `updateTime`) values ('个人练习生', 'www.efrain-lueilwitz.org', 'S7', 'E2FGN', '邹聪健', 'TPC8', '2022-06-02 04:11:04', '2022-03-28 09:09:58');
insert into my_db.`interface_info` (`name`, `url`, `requestHeader`, `responseHeader`, `userId`, `method`, `createTime`, `updateTime`) values ('跳', 'www.kelley-gibson.name', 'AMn', 'nJDK', '范鹏涛', 'DQHF', '2022-09-28 20:13:06', '2022-05-05 09:55:58');
insert into my_db.`interface_info` (`name`, `url`, `requestHeader`, `responseHeader`, `userId`, `method`, `createTime`, `updateTime`) values ('rap', 'www.tamesha-dicki.net', 'G9I0u', 'JnAp', '孔笑愚', '6K2pn', '2022-10-11 16:12:06', '2022-09-28 15:34:28');
insert into my_db.`interface_info` (`name`, `url`, `requestHeader`, `responseHeader`, `userId`, `method`, `createTime`, `updateTime`) values ('个人练习生', 'www.jettie-christiansen.biz', 'NMU', 'IGcs', '高晋鹏', 'dOK', '2022-03-30 05:20:40', '2022-04-28 15:13:41');
insert into my_db.`interface_info` (`name`, `url`, `requestHeader`, `responseHeader`, `userId`, `method`, `createTime`, `updateTime`) values ('rap', 'www.lashanda-weissnat.net', 'ZtcPF', 'snw', '罗越彬', 'GS', '2022-04-03 13:22:02', '2022-03-12 17:15:39');
insert into my_db.`interface_info` (`name`, `url`, `requestHeader`, `responseHeader`, `userId`, `method`, `createTime`, `updateTime`) values ('跳', 'www.marcellus-roberts.com', 'E3b', 'GlA', '姚烨华', 'Deh1U', '2022-05-30 22:47:03', '2022-08-11 18:33:50');
insert into my_db.`interface_info` (`name`, `url`, `requestHeader`, `responseHeader`, `userId`, `method`, `createTime`, `updateTime`) values ('跳', 'www.claudio-tremblay.org', 'tUO', 'NMZ', '顾智宸', 'VgFsA', '2022-06-02 11:57:11', '2022-08-10 20:41:09');
insert into my_db.`interface_info` (`name`, `url`, `requestHeader`, `responseHeader`, `userId`, `method`, `createTime`, `updateTime`) values ('篮球', 'www.whitney-schulist.name', 'g4j', 'hFemz', '孟金鑫', '8dJ', '2022-04-18 01:21:12', '2022-09-13 00:43:53');
insert into my_db.`interface_info` (`name`, `url`, `requestHeader`, `responseHeader`, `userId`, `method`, `createTime`, `updateTime`) values ('蔡徐坤', 'www.austin-abbott.name', 'dwNH', 'ZKM', '韦烨华', 'xw', '2022-12-08 11:54:25', '2022-08-04 23:06:56');
insert into my_db.`interface_info` (`name`, `url`, `requestHeader`, `responseHeader`, `userId`, `method`, `createTime`, `updateTime`) values ('篮球', 'www.larisa-kemmer.co', 'zzAb', 'mC', '严煜城', 'xe', '2022-07-23 17:42:01', '2022-02-27 10:04:07');
insert into my_db.`interface_info` (`name`, `url`, `requestHeader`, `responseHeader`, `userId`, `method`, `createTime`, `updateTime`) values ('篮球', 'www.fermin-pacocha.org', 'Bw', 'gmaJ', '曹鑫鹏', 'Jred', '2022-11-29 18:56:23', '2022-10-25 15:04:58');
insert into my_db.`interface_info` (`name`, `url`, `requestHeader`, `responseHeader`, `userId`, `method`, `createTime`, `updateTime`) values ('时长', 'www.augustus-stoltenberg.biz', '7b', '384', '崔皓轩', 'tQnq', '2022-02-25 04:13:50', '2022-12-24 07:23:34');
insert into my_db.`interface_info` (`name`, `url`, `requestHeader`, `responseHeader`, `userId`, `method`, `createTime`, `updateTime`) values ('跳', 'www.julianne-gaylord.io', 'SP', 'hhFo', '黎子涵', 'BOq', '2022-05-07 15:23:21', '2022-02-15 07:15:19');
insert into my_db.`interface_info` (`name`, `url`, `requestHeader`, `responseHeader`, `userId`, `method`, `createTime`, `updateTime`) values ('鸡', 'www.lorean-abshire.io', 'Rm', 'MJm', '杨泽洋', '2d', '2022-09-09 00:27:34', '2022-10-24 01:09:50');
insert into my_db.`interface_info` (`name`, `url`, `requestHeader`, `responseHeader`, `userId`, `method`, `createTime`, `updateTime`) values ('两年半', 'www.hal-kunde.biz', '9Jj', 'TFv3N', '彭立诚', 'w1lID', '2022-01-20 11:36:17', '2022-12-10 15:17:51');
insert into my_db.`interface_info` (`name`, `url`, `requestHeader`, `responseHeader`, `userId`, `method`, `createTime`, `updateTime`) values ('时长', 'www.kenny-hills.info', 'jUJ', 'Yf', '邱果', '3yOap', '2022-08-31 13:38:23', '2022-08-23 22:09:21');
insert into my_db.`interface_info` (`name`, `url`, `requestHeader`, `responseHeader`, `userId`, `method`, `createTime`, `updateTime`) values ('music', 'www.jarod-hammes.net', 'K9N1F', 'RU0', '王炫明', '4560', '2022-09-06 22:47:51', '2022-07-22 23:48:28');
insert into my_db.`interface_info` (`name`, `url`, `requestHeader`, `responseHeader`, `userId`, `method`, `createTime`, `updateTime`) values ('你', 'www.lajuana-gusikowski.biz', '6wpo', '5cC', '萧鹏飞', 'FWFll', '2022-01-12 03:41:55', '2022-01-18 12:11:51');
insert into my_db.`interface_info` (`name`, `url`, `requestHeader`, `responseHeader`, `userId`, `method`, `createTime`, `updateTime`) values ('蔡徐坤', 'www.sergio-brown.org', 'k6cd4', '6SIU', '苏金鑫', 'XJVC', '2022-12-22 16:45:15', '2022-11-10 10:45:06');


-- 用户调用接口关系
create table if not exists api_db.`user_interface_info`
(
    `id` bigint not null auto_increment comment '主键' primary key,
    `userId` bigint not null comment '调用用户Id',
    `interfaceId` bigint not null comment '接口Id',
    `totalNum` int default 0 not null comment '总调用次数',
    `leftNum` int default 0 not null comment '剩余调用次数',
    `status` int default 0 not null comment '0-正常，1-禁用',
    `createTime` datetime default CURRENT_TIMESTAMP null comment '创建时间',
    `updateTime` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `isDelete` tinyint default 0 not null comment '0-未删，1-删除'
) comment '用户调用接口关系';