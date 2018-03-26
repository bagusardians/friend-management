create schema if not exists friend_management default character set utf8;
use friend_management;
drop table if exists user;
drop table if exists user_relation;

CREATE TABLE `user` (
  `id` varchar(36) NOT NULL,
  `email` varchar(50) NOT NULL
);

CREATE TABLE `user_relation` (
  `id` varchar(36) NOT NULL,
  `related_id` varchar(36) NOT NULL,
  `relation_type` varchar(15) NOT NULL,
  `is_block` tinyint(4) NOT NULL DEFAULT '0'
);


ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email_idx` (`email`);

ALTER TABLE `user_relation`
  ADD PRIMARY KEY (`id`,`related_id`);
COMMIT;
