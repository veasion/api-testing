SET NAMES utf8mb4;

-- 数据库
create database `api_testing` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
use `api_testing`;

-- 用户
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '账号',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `role` varchar(50) DEFAULT NULL COMMENT '角色',
  `is_available` tinyint(4) DEFAULT NULL COMMENT '是否可用',
  `is_deleted` tinyint(4) DEFAULT '0' COMMENT '是否删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_username` varchar(255) DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='用户';

-- 项目
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '项目id',
  `name` varchar(255) DEFAULT NULL COMMENT '项目名称',
  `description` varchar(300) DEFAULT NULL COMMENT '项目描述',
  `is_available` tinyint(4) DEFAULT NULL COMMENT '是否可用',
  `is_deleted` tinyint(4) DEFAULT '0' COMMENT '是否删除',
  `create_username` varchar(255) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_username` varchar(255) DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='项目';

-- 项目配置
DROP TABLE IF EXISTS `project_config`;
CREATE TABLE `project_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_id` int(11) NOT NULL COMMENT '项目id',
  `open_req_log` tinyint(4) DEFAULT '0' COMMENT '记录请求日志',
  `global_var_json` text COMMENT '全局变量（脚本中可以通过${xxx}方式使用全局变量）',
  `before_script` text COMMENT '前置脚本（策略开始前执行，用来设置登录信息和拦截修改请求响应）',
  `after_script` text COMMENT '后置脚本（策略正常结束后执行，用来监听脚本执行情况，发送邮件等）',
  `exception_script` text COMMENT '异常执行脚本（策略代码异常时执行，邮件通知）',
  `is_available` tinyint(4) DEFAULT NULL COMMENT '是否可用',
  `is_deleted` tinyint(4) DEFAULT '0' COMMENT '是否删除',
  `create_username` varchar(255) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_username` varchar(255) DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_project_id` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='全局配置';

-- 接口请求
DROP TABLE IF EXISTS `api_request`;
CREATE TABLE `api_request` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_id` int(11) NOT NULL COMMENT '项目id',
  `api_name` varchar(50) DEFAULT NULL COMMENT '命名',
  `api_desc` varchar(100) DEFAULT NULL COMMENT '请求描述',
  `method` varchar(20) DEFAULT NULL COMMENT '请求方法',
  `url` varchar(500) DEFAULT NULL COMMENT '请求url',
  `headers_json` varchar(300) DEFAULT NULL COMMENT '请求头',
  `body` text COMMENT '请求body',
  `script` text COMMENT 'http脚本',
  `is_available` tinyint(4) DEFAULT NULL COMMENT '是否可用',
  `is_deleted` tinyint(4) DEFAULT '0' COMMENT '是否删除',
  `create_username` varchar(255) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_username` varchar(255) DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_project_id` (`project_id`),
  KEY `idx_api_name` (`api_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='请求接口';

-- 测试用例
DROP TABLE IF EXISTS `api_test_case`;
CREATE TABLE `api_test_case` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_id` int(11) NOT NULL COMMENT '项目id',
  `case_name` varchar(50) DEFAULT NULL COMMENT '用例名称',
  `case_desc` varchar(300) DEFAULT NULL COMMENT '用例描述',
  `module` varchar(50) DEFAULT NULL COMMENT '模块',
  `author` varchar(100) DEFAULT NULL COMMENT '作者',
  `script` text COMMENT 'js脚本',
  `is_available` tinyint(4) DEFAULT NULL COMMENT '是否可用',
  `is_deleted` tinyint(4) DEFAULT '0' COMMENT '是否删除',
  `create_username` varchar(255) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_username` varchar(255) DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_project_id` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='测试用例';

-- 执行策略
DROP TABLE IF EXISTS `api_execute_strategy`;
CREATE TABLE `api_execute_strategy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_id` int(11) NOT NULL COMMENT '项目id',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `desc` varchar(300) DEFAULT NULL COMMENT '描述',
  `type` tinyint(4) DEFAULT NULL COMMENT '类型: 1 所有case 2 指定case 3 自定义脚本',
  `strategy` tinyint(4) DEFAULT NULL COMMENT '执行策略: 1 定时任务 2 压测',
  `job_cron` varchar(128) DEFAULT NULL COMMENT '任务执行CRON',
  `thread_count` int(11) DEFAULT NULL COMMENT '并发线程数',
  `thread_strategy_json` text COMMENT '线程创建策略（瞬时并发、并发间隔、执行次数、压测时间）',
  `script` text COMMENT 'js脚本',
  `status` tinyint(4) DEFAULT NULL COMMENT '最后一次执行状态：1 部分成功 2 全部成功 3 失败',
  `is_available` tinyint(4) DEFAULT NULL COMMENT '是否可用',
  `is_deleted` tinyint(4) DEFAULT '0' COMMENT '是否删除',
  `create_username` varchar(255) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_username` varchar(255) DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_project_id` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='执行策略';

-- 策略case关联
CREATE TABLE `strategy_case_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `execute_strategy_id` int(11) DEFAULT NULL COMMENT '策略id',
  `case_id` int(11) DEFAULT NULL COMMENT '用例id',
  `is_deleted` tinyint(4) DEFAULT '0' COMMENT '是否删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_strategy_id` (`execute_strategy_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='策略case关联';

-- 请求日志
DROP TABLE IF EXISTS `api_log`;
CREATE TABLE `api_log` (
  `id` varchar(36) NOT NULL,
  `ref_id` varchar(36) DEFAULT '0' COMMENT '关联日志id',
  `project_id` int(11) DEFAULT NULL COMMENT '项目id',
  `execute_strategy_id` int(11) DEFAULT NULL COMMENT '策略id',
  `test_case_id` int(11) DEFAULT NULL COMMENT '用例id',
  `api_request_id` int(11) DEFAULT NULL COMMENT '接口请求id',
  `url` varchar(500) DEFAULT NULL COMMENT '请求url',
  `msg` text COMMENT '日志',
  `status` tinyint(4) DEFAULT NULL COMMENT '运行状态: 1 执行中 2 执行成功 3 执行失败',
  `time` int(11) DEFAULT NULL COMMENT '请求接口耗时(ms)',
  `exec_time` int(11) DEFAULT NULL COMMENT '执行任务耗时(ms)',
  `is_deleted` tinyint(4) DEFAULT '0' COMMENT '是否删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_ref_id` (`ref_id`),
  KEY `idx_project_request_id` (`project_id`,`api_request_id`),
  KEY `idx_execute_strategy_id` (`execute_strategy_id`),
  KEY `idx_test_case_id` (`test_case_id`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='日志';

-- 初始化admin用户: admin / 123456
INSERT INTO `user`(`username`, `password`, `role`, `is_available`, `is_deleted`) VALUES ('admin', '$2a$10$2KCqRbra0Yn2TwvkZxtfLuWuUP5KyCWsljO/ci5pLD27pqR3TV1vy', 'ROLE_ADMIN', 1, 0);
