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
  `api_name` varchar(100) DEFAULT NULL COMMENT '命名',
  `api_group` varchar(255) DEFAULT NULL COMMENT '分组',
  `api_desc` varchar(255) DEFAULT NULL COMMENT '请求描述',
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

-- 测试项目
INSERT INTO `project` VALUES (1, '接口自动化测试', 'api-testing', 1, 0, 'admin', NOW(), 'admin', NOW());
-- 项目配置
INSERT INTO `project_config` VALUES (1, 1, 1, '{\n  \"baseUrl\": \"http://127.0.0.1:8080\",\n  \"username\": \"admin\",\n  \"password\": 123456,\n  \"dingtalkRobotAccessToken\": \"\"\n}', '// 该脚本是在所有脚本执行前触发，策略开始前执行\n// 脚本适用于提前登录，请求/响应拦截处理\n\n// 登录\nlet result = http.postJson(\"${baseUrl}/api/auth/login\", {\n  \"username\": \"${username}\",\n  \"password\": \"${password}\"\n});\n\nlet token = result.data.token;\nlog.info(\'登录用户：\' + env.eval(\'${username}\') + \'，token：\' + token);\n\n// 请求拦截处理，设置授权headers\nscriptContext.addRequestProcessor(function(request) {\n  request.addHeaders(\"Authorization\", token);\n});\n\n// 响应拦截处理\nscriptContext.addResponseProcessor(function(response, status, log) {\n  // 这里判断请求是否成功（这里是接口失败，并不是脚本执行异常）\n  if (status == 200 && response && response.code && response.code != \'200\') {\n    // code不等于200时请求失败，修改日志状态: 2成功 3 失败\n    log.status = 3;\n	// 记录失败日志\n    log.appendLog(response.message || \"\");\n  }\n});', '// 该脚本是在所有脚本执行后触发，策略正常执行完成后执行\n// 脚本适用于用来监听脚本执行情况\n\n// 发送钉钉群通知示例\nlet projectName = scriptContext.project.name;\nlet strategyName = scriptContext.strategy != null ? scriptContext.strategy.name : null\nif (scriptContext.strategy && scriptContext.strategy.strategy == 2) {\n	// TODO 压测不处理\n} else {\n	// 定时任务\n	if (scriptContext.refLog.status == 2) {\n		// 全部执行成功\n		sendNotice(projectName + \', 策略\' + strategyName + \'执行通过\');\n	} else {\n		// 策略执行不通过\n		for (let i in scriptContext.apiLogList) {\n			let apiLog = scriptContext.apiLogList[i];\n			if (apiLog.status == 3) {\n				// 执行不通过的请求\n				sendNotice(projectName + \', 接口: \' + apiLog.url + \' 执行失败\');\n			}\n		}\n	}\n}\n\nfunction sendNotice(msg) {\n	// 发送钉钉群通知\n	let accessToken = env.eval(\'${dingtalkRobotAccessToken}\');\n    if (!accessToken) {\n      // 没有配置钉钉机器人群token\n      return\n    }\n	http.postJson(\'https://oapi.dingtalk.com/robot/send?access_token=\' + accessToken, {\n		msgtype: \'text\', \n		text: {\n			content: \'接口自动化测试：\' + msg\n		}\n	});\n}', '// 该脚本是在策略执行异常时触发\n// 脚本适用于异常监听通知\n\n// 发送钉钉群通知示例\nlet projectName = scriptContext.project.name;\nlet strategyName = scriptContext.strategy != null ? scriptContext.strategy.name : null\n// 发送钉钉群通知\nlet accessToken = env.eval(\'${dingtalkRobotAccessToken}\');\nif (accessToken) {\n  http.postJson(\'https://oapi.dingtalk.com/robot/send?access_token=\' + accessToken, {\n    msgtype: \'text\', \n    text: {\n      content: \'接口自动化测试：\' + projectName + \', 策略\' + strategyName + \'执行异常\'\n    }\n  });\n}', 1, 0, 'admin', NOW(), 'admin', NOW());
-- 测试接口
INSERT INTO `api_request` VALUES (1, 1, '/user/listPage', '默认分组', '用户列表', 'GET', '/api/user/listPage?pageNo=${pageNo|1}&pageSize=${pageSize|10}', '', NULL, NULL, 1, 0, 'admin', NOW(), 'admin', NOW());
INSERT INTO `api_request` VALUES (2, 1, '/serverInfo', '默认分组', '服务器信息', 'GET', '/api/public/serverInfo', '', NULL, NULL, 1, 0, 'admin', NOW(), 'admin', NOW());
INSERT INTO `api_request` VALUES (3, 1, '/runScript', '默认分组', '运行脚本', 'POST', '/api/script/runScript', '{\"Content-Type\":\"application/json\"}', '{\n  \"projectId\": 1,\n  \"script\": \"log.info(\'hello api-testing !\');\"\n}', NULL, 1, 0, 'admin', NOW(), 'admin', NOW());
INSERT INTO `api_request` VALUES (4, 1, '/project/getById', '默认分组', '根据ID查询项目', 'GET', '/api/project/getById?id=${id}', '', NULL, NULL, 1, 0, 'admin', '2021-09-22 15:36:24', 'admin', '2021-09-22 15:36:24');
-- 测试用例
INSERT INTO `api_test_case` VALUES (1, 1, '根据ID查询项目', '/project/getById', '项目管理', 'veasion', 'let response = http.request(\'/project/getById\', {\n	\"id\": scriptContext.project.id\n});\nassertNotNull(response);\nassertEquals(response.data.name, scriptContext.project.name);\n', 1, 0, 'admin', NOW(), 'admin', NOW());
-- 测试执行策略
INSERT INTO `api_execute_strategy` VALUES (1, 1, '接口自动化定时执行', '定时执行所有case', 1, 1, '1 */10 * ? * *', NULL, '{\"type\":1,\"loopCount\":null,\"timeInMillis\":null,\"intervalInMillis\":-1,\"userEnvType\":1,\"userEnvMaps\":[]}', NULL, NULL, 1, 0, 'admin', NOW(), 'admin', NOW());
INSERT INTO `api_execute_strategy` VALUES (2, 1, '查询项目接口压测', '压测 /project/getById', 2, 2, '1 */10 * ? * *', 10, '{\"type\":1,\"loopCount\":null,\"timeInMillis\":10000,\"intervalInMillis\":-1,\"userEnvType\":1,\"userEnvMaps\":[]}', NULL, 2, 1, 0, 'admin', NOW(), 'admin', NOW());
-- 策略case关联
INSERT INTO `strategy_case_relation` VALUES (1, 2, 1, 0, NOW(), NOW());