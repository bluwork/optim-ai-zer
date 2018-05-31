CREATE TABLE `article`(
`id` bigint(20) not null auto_increment, 
`title` varchar(255) not null,
`content` text,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
