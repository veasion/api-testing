SET NAMES utf8mb4;

-- 数据库
create database `api_testing` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
use `api_testing`;

-- 用户
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账号',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色',
  `is_available` tinyint(4) NULL DEFAULT NULL COMMENT '是否可用',
  `is_deleted` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_username` (`username`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户';

-- 项目
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '项目id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目名称',
  `description` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目描述',
  `email` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `is_available` tinyint(4) NULL DEFAULT NULL COMMENT '是否可用',
  `is_deleted` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '项目';

-- 项目配置
DROP TABLE IF EXISTS `project_config`;
CREATE TABLE `project_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_id` int(11) NOT NULL COMMENT '项目id',
  `global_var_json` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '全局变量',
  `exception_script` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '脚本: 异常监听',
  `before_script` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '脚本: 请求前',
  `do_script` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '脚本: 请求中',
  `after_script` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '脚本: 请求后',
  `notify_script` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '脚本：通知',
  `is_available` tinyint(4) NULL DEFAULT NULL COMMENT '是否可用',
  `is_deleted` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_project_id` (`project_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '全局配置';

-- 接口请求
DROP TABLE IF EXISTS `api_request`;
CREATE TABLE `api_request`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_id` int(11) NOT NULL COMMENT '项目id',
  `api_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '命名',
  `api_desc` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求描述',
  `method` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求方法',
  `url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求url',
  `body` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '请求body',
  `script` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'http脚本',
  `is_available` tinyint(4) NULL DEFAULT NULL COMMENT '是否可用',
  `is_deleted` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_project_id` (`project_id`),
  KEY `idx_api_name` (`api_name`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '请求接口';

-- 测试用例
DROP TABLE IF EXISTS `api_test_case`;
CREATE TABLE `api_test_case`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_id` int(11) NOT NULL COMMENT '项目id',
  `case_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用例名称',
  `case_desc` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用例描述',
  `module` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模块',
  `author` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '作者',
  `script` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'js脚本',
  `is_available` tinyint(4) NULL DEFAULT NULL COMMENT '是否可用',
  `is_deleted` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_project_id` (`project_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '测试用例';

-- 执行策略
DROP TABLE IF EXISTS `api_execut_strategy`;
CREATE TABLE `api_execut_strategy`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_id` int(11) NOT NULL COMMENT '项目id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `desc` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `type` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '类型: 1 所有case 2 指定case 3 自定义脚本',
  `strategy` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '执行策略: 1 定时任务 2 压测',
  `job_cron` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务执行CRON',
  `thread_count` int(11) NULL DEFAULT NULL COMMENT '并发线程数',
  `thread_strategy_json` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '线程创建策略（瞬时创建、间隔增量创建、指定时间内创建）',
  `run_time` int(11) NULL DEFAULT NULL COMMENT '压测时间(ms)',
  `loop_count` int(11) NULL DEFAULT NULL COMMENT '循环次数',
  `script` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'js脚本',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '执行开始-时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '执行结束-时间',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '运行状态: 1 执行中 2 执行成功 3 执行失败',
  `is_available` tinyint(4) NULL DEFAULT NULL COMMENT '是否可用',
  `is_deleted` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_project_id` (`project_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '执行策略';

-- 策略case关联
CREATE TABLE `strategy_case_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `execut_strategy_id` int(11) DEFAULT NULL COMMENT '策略id',
  `case_id` int(11) DEFAULT NULL COMMENT '用例id',
  `is_deleted` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`),
  KEY `idx_strategy_id` (`execut_strategy_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '策略case关联';

-- 请求日志
DROP TABLE IF EXISTS `api_log`;
CREATE TABLE `api_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_id` int(11) NOT NULL COMMENT '项目id',
  `execut_strategy_id` int(11) NOT NULL COMMENT '策略id',
  `test_case_id` int(11) NOT NULL COMMENT '用例id',
  `api_request_id` int(11) NOT NULL COMMENT '接口请求id',
  `msg` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '日志',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '运行状态: 1 执行中 2 执行成功 3 执行失败',
  `time` int(11) DEFAULT NULL COMMENT '请求接口耗时(ms)',
  `is_available` tinyint(4) NULL DEFAULT NULL COMMENT '是否可用',
  `is_deleted` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_project_request_id` (`project_id`, `api_request_id`),
  KEY `idx_execut_strategy_id` (`execut_strategy_id`),
  KEY `idx_test_case_id` (`test_case_id`),
  KEY `idx_create_time` (`create_time`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '日志';

-- 初始化admin用户: admin / 123456
INSERT INTO `user`(`username`, `password`, `role`, `is_available`, `is_deleted`) VALUES ('admin', '$2a$10$2KCqRbra0Yn2TwvkZxtfLuWuUP5KyCWsljO/ci5pLD27pqR3TV1vy', 'ROLE_ADMIN', 1, 0);
