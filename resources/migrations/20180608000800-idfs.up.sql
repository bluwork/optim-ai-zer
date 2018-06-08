CREATE TABLE `idfs`(
`id` bigint(20) not null auto_increment, 
`word` varchar(255) not null,
`idfvalue` double,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
