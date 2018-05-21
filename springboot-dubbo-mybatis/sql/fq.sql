CREATE DATABASE IF NOT EXISTS fq default charset utf8 COLLATE utf8_general_ci; 

DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(10) DEFAULT NULL,
  `age` int(5) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('2', 'lifeng', '18');
INSERT INTO `tb_user` VALUES ('3', 'lifeng', '18');
INSERT INTO `tb_user` VALUES ('4', 'lifeng', '18');
INSERT INTO `tb_user` VALUES ('5', 'lifeng', '18');
INSERT INTO `tb_user` VALUES ('6', '可以的', '58');
