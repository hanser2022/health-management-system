/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80029 (8.0.29)
 Source Host           : localhost:3306
 Source Schema         : db_health

 Target Server Type    : MySQL
 Target Server Version : 80029 (8.0.29)
 File Encoding         : 65001

 Date: 10/04/2026
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for detail
-- ----------------------------
DROP TABLE IF EXISTS `detail`;
CREATE TABLE `detail` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sport_type` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL,
  `disease` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `method` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `notes` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of detail
-- ----------------------------
BEGIN;
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (1, '体操', '心脏病', '适度运动，不要剧烈运动，保持心情愉快', '避免在气温过高或过低的环境下运动');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (2, '太极拳', '高血压', '轻柔的动作，缓慢而稳定的呼吸', '保持心情平稳，不要过度紧张');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (3, '快跑', '关节炎', '选择跑步道或软质地面进行，适当热身', '避免过度的冲击力，注意饮食，保持合理体重');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (4, '慢跑', '哮喘', '避免在雾霾天气、气温过高或过低的环境下运动', '控制呼吸，使用合适的防护装备');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (6, '抖空竹', '眼疾', '注意眼睛的休息和保护，不要盯着空竹太久', '避免在光线昏暗的环境下玩抖空竹');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (7, '抽陀螺', '高血压', '避免剧烈运动，不要在悬崖陡坡等高度场所运动', '避免在气温过高或过低的环境下运动');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (8, '拔河', '心脏病', '注意不要过度用力，保持呼吸稳定', '人数相对平衡，避免人数不足或过多');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (9, '排球', '扭伤', '注意热身，保持肌肉灵活', '避免在硬地面和湿滑地面上运动，注意落地姿势');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (10, '放风筝', '过敏性鼻炎', '避免在花粉高发季节和污染严重的地区放风筝', '选择空气清新的场地，不要在风力过大的环境下放风筝');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (11, '橄榄球', '骨折', '选择合适的防护装备，不要使用过度生硬的球', '规范比赛流程，避免激烈对抗');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (12, '游泳', '心脏病、心律失常、哮喘、高血压', '游泳是一项全身性运动，但对于患有心脏病、心律失常、哮喘等疾病的人来说，游泳容易加重病情，甚至引发突发病情，因此应该避免。而患有高血压的人可以适当游泳，但是游泳时间和强度不宜过大。', '游泳时一定要注意安全，避免溺水事故的发生；同时注意保持游泳池的清洁卫生，避免感染泳池病毒。');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (13, '滑冰', '骨折、关节炎、心脏病、高血压', '滑冰是一项高风险运动，容易导致骨折、关节炎等损伤，对于患有心脏病、高血压等疾病的人来说，也需要注意保护自己，避免剧烈运动。', '滑冰时要穿好防护装备，注意自己的身体状况，避免过度运动造成身体损伤。');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (14, '爬山', '高血压、心脏病、脑血管疾病', '选择较平缓的山路，避免过于陡峭的路段；掌握正确的呼吸方法，避免缺氧；', '切勿贪多嚼不烂，随时可以补充水分和能量；注意天气变化，避免在大雾、大风、雷雨天气攀登；');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (15, '瑜伽', '脊椎病、膝盖受伤、关节病变', '选择适合自己身体情况的瑜伽动作；呼吸要自然流畅，不要憋气或过度呼吸；动作要准确，避免造成不必要的压力或伤害；', '练习前应注意饮食、排便，不宜在空腹或饱腹时练习；练习时注意周围环境，保持安静的氛围；练习后应适当休息，以便身体恢复；');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (16, '篮球', '心脏病、脑血管疾病、骨折', '保持适度的身体活动，不要过度用力；掌握正确的篮球技巧，避免不必要的伤害；在比赛中要遵守规则，注意安全；', '合理安排练球时间和强度，不要过度训练；练球时应注意防护措施，如佩戴护具等；注意饮食，保证充足的营养摄入；');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (17, '网球', '肩部受伤、腕关节疼痛、脱臼', '掌握正确的发球姿势和击球技巧；加强肌肉的训练，提高关节的灵活性；注意休息，避免过度疲劳；', '选择合适的球拍和鞋子，避免不必要的伤害；保持场地干燥，防止滑倒；定期进行身体检查，及时发现问题；');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (18, '羽毛球', '肌肉劳损', '逐渐增加训练量，充足的热身与休息，避免过度用力', '注意营养、充足的睡眠，避免疲劳、缺氧');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (19, '越野跑', '膝盖疼痛', '选择软质路面，加强膝关节周围肌肉的锻炼', '注意营养、饮食的平衡，避免过度减重、缺氧');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (20, '足球', '膝盖疼痛', '选择软质路面，加强膝关节周围肌肉的锻炼', '注意营养、饮食的平衡，避免过度减重、缺氧');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (21, '跆拳道', '扭伤、拉伤', '逐渐增加训练量，充足的热身与休息，避免过度用力', '注意营养、充足的睡眠，避免疲劳、缺氧');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (22, '跑步', '膝盖疼痛', '选择软质路面，加强膝关节周围肌肉的锻炼', '注意营养、饮食的平衡，避免过度减重、缺氧');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (23, '跳板', '脊椎损伤', '注意技术细节，逐渐增加训练难度，避免过度用力', '可选择针对脊柱的力量训练，加强腰背肌群的锻炼');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (24, '跳皮筋', '肌肉劳损', '逐渐增加训练量，充足的热身与休息，避免过度用力', '注意营养、充足的睡眠，避免疲劳、缺氧');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (25, '跳绳', '膝盖或脚踝疼痛、肌肉酸痛、运动损伤', '手持跳绳，双脚交替跳跃，以规定时间内跳跃次数最多者获胜。', '需要注意跳绳时的姿势和呼吸。');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (26, '跳高', '扭伤、肌肉拉伤、腰椎间盘突出、关节炎', '又称跳高栏，运动员在助跑后跳过竞赛高度悬挂的水平杠，以完成跳高动作，高度逐渐升高，未能过去的选手则被淘汰。', '需要注意跳高时的跑道、助跑和跳跃姿势。');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (27, '踢毽子', '扭伤、拉伤', '将毽子踢起来并在空中进行一系列动作，规定时间内完成动作最多的人获胜。', '需要注意毽子的质量和天气情况。');
INSERT INTO `detail` (`id`, `sport_type`, `disease`, `method`, `notes`) VALUES (28, '长跑', '心脏病、高血压、哮喘、慢性阻塞性肺疾病', '一项有氧耐力运动，比赛时要跑过规定的路程，规定时间内到达终点的人获胜。', '需要注意长跑时的呼吸和水分补给。');
COMMIT;

-- ----------------------------
-- Table structure for j_body
-- ----------------------------
DROP TABLE IF EXISTS `j_body`;
CREATE TABLE `j_body` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `age` int DEFAULT NULL,
  `gender` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `height` float DEFAULT NULL,
  `weight` float DEFAULT NULL,
  `bloodSugar` float DEFAULT NULL,
  `bloodPressure` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `bloodLipid` float DEFAULT NULL,
  `heart_rate` decimal(10,0) DEFAULT NULL,
  `vision` int DEFAULT NULL,
  `sleep_duration` decimal(10,0) DEFAULT NULL,
  `sleep_quality` text CHARACTER SET utf8mb3 COLLATE utf8_general_ci,
  `smoking` tinyint(1) DEFAULT NULL,
  `drinking` tinyint(1) DEFAULT NULL,
  `exercise` tinyint(1) DEFAULT NULL,
  `food_types` text CHARACTER SET utf8mb3 COLLATE utf8_general_ci,
  `water_consumption` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of j_body (测试健康数据 - id与用户id对应)
-- ----------------------------
BEGIN;
INSERT INTO `j_body` (`id`, `name`, `age`, `gender`, `height`, `weight`, `bloodSugar`, `bloodPressure`, `bloodLipid`, `heart_rate`, `vision`, `sleep_duration`, `sleep_quality`, `smoking`, `drinking`, `exercise`, `food_types`, `water_consumption`) VALUES (1, '管理员', 28, '男', 175, 70, 5.1, '120/80', 4.3, 72, 0, 7, '好', 0, 0, 1, '均衡饮食', 2000);
INSERT INTO `j_body` (`id`, `name`, `age`, `gender`, `height`, `weight`, `bloodSugar`, `bloodPressure`, `bloodLipid`, `heart_rate`, `vision`, `sleep_duration`, `sleep_quality`, `smoking`, `drinking`, `exercise`, `food_types`, `water_consumption`) VALUES (34, '张伟', 25, '男', 175, 72, 5.2, '120/80', 4.5, 72, 0, 7, '好', 0, 0, 1, '均衡饮食', 2000);
COMMIT;

-- ----------------------------
-- Table structure for j_body_notes
-- ----------------------------
DROP TABLE IF EXISTS `j_body_notes`;
CREATE TABLE `j_body_notes` (
  `notes_id` int NOT NULL AUTO_INCREMENT,
  `id` int DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `age` int DEFAULT NULL,
  `gender` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `height` float DEFAULT NULL,
  `weight` float DEFAULT NULL,
  `bloodSugar` float DEFAULT NULL,
  `bloodPressure` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `bloodLipid` float DEFAULT NULL,
  `heart_rate` decimal(10,0) DEFAULT NULL,
  `vision` int DEFAULT NULL,
  `sleep_duration` decimal(10,0) DEFAULT NULL,
  `sleep_quality` text CHARACTER SET utf8mb3 COLLATE utf8_general_ci,
  `smoking` tinyint(1) DEFAULT NULL,
  `drinking` tinyint(1) DEFAULT NULL,
  `exercise` tinyint(1) DEFAULT NULL,
  `food_types` text CHARACTER SET utf8mb3 COLLATE utf8_general_ci,
  `water_consumption` decimal(10,0) DEFAULT NULL,
  `Date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`notes_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of j_body_notes (历史健康记录)
-- ----------------------------
BEGIN;
INSERT INTO `j_body_notes` (`notes_id`, `id`, `name`, `age`, `gender`, `height`, `weight`, `bloodSugar`, `bloodPressure`, `bloodLipid`, `heart_rate`, `vision`, `sleep_duration`, `sleep_quality`, `smoking`, `drinking`, `exercise`, `food_types`, `water_consumption`, `Date`) VALUES (1, 1, '管理员', 28, '男', 175, 72, 5.6, '128/85', 4.8, 82, 48, 6.0, '一般', 0, 0, 0, '肉类', 1500, '2026-04-01 09:30:00');
INSERT INTO `j_body_notes` (`notes_id`, `id`, `name`, `age`, `gender`, `height`, `weight`, `bloodSugar`, `bloodPressure`, `bloodLipid`, `heart_rate`, `vision`, `sleep_duration`, `sleep_quality`, `smoking`, `drinking`, `exercise`, `food_types`, `water_consumption`, `Date`) VALUES (2, 1, '管理员', 28, '男', 175, 71, 5.3, '124/82', 4.5, 76, 49, 6.5, '一般', 0, 0, 1, '均衡饮食', 1800, '2026-04-06 14:20:00');
INSERT INTO `j_body_notes` (`notes_id`, `id`, `name`, `age`, `gender`, `height`, `weight`, `bloodSugar`, `bloodPressure`, `bloodLipid`, `heart_rate`, `vision`, `sleep_duration`, `sleep_quality`, `smoking`, `drinking`, `exercise`, `food_types`, `water_consumption`, `Date`) VALUES (3, 1, '管理员', 28, '男', 175, 70, 5.1, '120/80', 4.3, 72, 50, 7.0, '好', 0, 0, 1, '均衡饮食', 2000, '2026-04-11 10:00:00');
COMMIT;

-- ----------------------------
-- Table structure for j_menu
-- ----------------------------
DROP TABLE IF EXISTS `j_menu`;
CREATE TABLE `j_menu` (
  `menu_id` int NOT NULL AUTO_INCREMENT,
  `component` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `redirect` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `parent_id` int DEFAULT NULL,
  `is_leaf` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `hidden` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of j_menu
-- ----------------------------
BEGIN;
INSERT INTO `j_menu` (`menu_id`, `component`, `path`, `redirect`, `name`, `title`, `icon`, `parent_id`, `is_leaf`, `hidden`) VALUES (1, 'Layout', '/dashboard', 'index', 'dashboard', '首页', 'el-icon-s-home', 0, 'N', 0);
INSERT INTO `j_menu` (`menu_id`, `component`, `path`, `redirect`, `name`, `title`, `icon`, `parent_id`, `is_leaf`, `hidden`) VALUES (4, 'Layout', '/sys', 'user', 'sysManage', '系统管理', 'el-icon-setting', 0, 'N', 0);
INSERT INTO `j_menu` (`menu_id`, `component`, `path`, `redirect`, `name`, `title`, `icon`, `parent_id`, `is_leaf`, `hidden`) VALUES (5, 'Layout', '/health', 'body', 'health', '健康管理', 'el-icon-first-aid-kit', 0, 'N', 0);
INSERT INTO `j_menu` (`menu_id`, `component`, `path`, `redirect`, `name`, `title`, `icon`, `parent_id`, `is_leaf`, `hidden`) VALUES (12, 'Layout', '/ai', 'index', 'aiAssistant', 'AI助手', 'el-icon-chat-dot-round', 0, 'N', 0);
INSERT INTO `j_menu` (`menu_id`, `component`, `path`, `redirect`, `name`, `title`, `icon`, `parent_id`, `is_leaf`, `hidden`) VALUES (14, 'dashboard/index', 'index', NULL, 'Dashboard', '首页', 'el-icon-s-home', 1, 'Y', 0);
INSERT INTO `j_menu` (`menu_id`, `component`, `path`, `redirect`, `name`, `title`, `icon`, `parent_id`, `is_leaf`, `hidden`) VALUES (2, 'sys/user', 'user', NULL, 'userList', '账号管理', 'el-icon-s-custom', 4, 'Y', 0);
INSERT INTO `j_menu` (`menu_id`, `component`, `path`, `redirect`, `name`, `title`, `icon`, `parent_id`, `is_leaf`, `hidden`) VALUES (3, 'sys/role', 'role', NULL, 'roleList', '角色管理', 'el-icon-s-management', 4, 'Y', 0);
INSERT INTO `j_menu` (`menu_id`, `component`, `path`, `redirect`, `name`, `title`, `icon`, `parent_id`, `is_leaf`, `hidden`) VALUES (8, 'sys/sportDetails', 'sportDetails', '', 'sportDetails', '运动知识文章', 'el-icon-document', 4, 'Y', 0);
INSERT INTO `j_menu` (`menu_id`, `component`, `path`, `redirect`, `name`, `title`, `icon`, `parent_id`, `is_leaf`, `hidden`) VALUES (9, 'sys/DetailsManage', 'DetailsManage', NULL, 'DetailsManage', '运动详情数据', 'el-icon-s-data', 4, 'Y', 0);
INSERT INTO `j_menu` (`menu_id`, `component`, `path`, `redirect`, `name`, `title`, `icon`, `parent_id`, `is_leaf`, `hidden`) VALUES (10, 'sys/userBodyManage', 'userBodyManage', NULL, 'userBodyManage', '用户健康记录', 'el-icon-tickets', 4, 'Y', 0);
INSERT INTO `j_menu` (`menu_id`, `component`, `path`, `redirect`, `name`, `title`, `icon`, `parent_id`, `is_leaf`, `hidden`) VALUES (6, 'body/index', 'body', '', 'bodyInfo', '健康信息录入', 'el-icon-edit', 5, 'Y', 0);
INSERT INTO `j_menu` (`menu_id`, `component`, `path`, `redirect`, `name`, `title`, `icon`, `parent_id`, `is_leaf`, `hidden`) VALUES (7, 'sport/index', 'sport', '', 'sport', '运动知识', 'el-icon-bicycle', 5, 'Y', 0);
INSERT INTO `j_menu` (`menu_id`, `component`, `path`, `redirect`, `name`, `title`, `icon`, `parent_id`, `is_leaf`, `hidden`) VALUES (11, 'body/manage', 'bodyManage', NULL, 'bodyManage', '我的健康记录', 'el-icon-s-claim', 5, 'Y', 0);
INSERT INTO `j_menu` (`menu_id`, `component`, `path`, `redirect`, `name`, `title`, `icon`, `parent_id`, `is_leaf`, `hidden`) VALUES (15, 'ai/index', 'index', NULL, 'AiAssistant', 'AI助手', 'el-icon-chat-dot-round', 12, 'Y', 0);
COMMIT;

-- ----------------------------
-- Table structure for j_role
-- ----------------------------
DROP TABLE IF EXISTS `j_role`;
CREATE TABLE `j_role` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `role_desc` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of j_role
-- ----------------------------
BEGIN;
INSERT INTO `j_role` (`role_id`, `role_name`, `role_desc`) VALUES (1, 'admin', '超级管理员');
INSERT INTO `j_role` (`role_id`, `role_name`, `role_desc`) VALUES (3, 'normal', '普通用户');
COMMIT;

-- ----------------------------
-- Table structure for j_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `j_role_menu`;
CREATE TABLE `j_role_menu` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_id` int DEFAULT NULL,
  `menu_id` int DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=195 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of j_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `j_role_menu` (`id`, `role_id`, `menu_id`) VALUES (179, 3, 1);
INSERT INTO `j_role_menu` (`id`, `role_id`, `menu_id`) VALUES (180, 3, 5);
INSERT INTO `j_role_menu` (`id`, `role_id`, `menu_id`) VALUES (181, 3, 6);
INSERT INTO `j_role_menu` (`id`, `role_id`, `menu_id`) VALUES (182, 3, 7);
INSERT INTO `j_role_menu` (`id`, `role_id`, `menu_id`) VALUES (183, 3, 11);
INSERT INTO `j_role_menu` (`id`, `role_id`, `menu_id`) VALUES (184, 3, 12);
INSERT INTO `j_role_menu` (`id`, `role_id`, `menu_id`) VALUES (185, 3, 14);
INSERT INTO `j_role_menu` (`id`, `role_id`, `menu_id`) VALUES (186, 3, 15);
INSERT INTO `j_role_menu` (`id`, `role_id`, `menu_id`) VALUES (188, 1, 1);
INSERT INTO `j_role_menu` (`id`, `role_id`, `menu_id`) VALUES (189, 1, 2);
INSERT INTO `j_role_menu` (`id`, `role_id`, `menu_id`) VALUES (190, 1, 3);
INSERT INTO `j_role_menu` (`id`, `role_id`, `menu_id`) VALUES (191, 1, 4);
INSERT INTO `j_role_menu` (`id`, `role_id`, `menu_id`) VALUES (192, 1, 5);
INSERT INTO `j_role_menu` (`id`, `role_id`, `menu_id`) VALUES (193, 1, 6);
INSERT INTO `j_role_menu` (`id`, `role_id`, `menu_id`) VALUES (194, 1, 7);
INSERT INTO `j_role_menu` (`id`, `role_id`, `menu_id`) VALUES (195, 1, 8);
INSERT INTO `j_role_menu` (`id`, `role_id`, `menu_id`) VALUES (196, 1, 9);
INSERT INTO `j_role_menu` (`id`, `role_id`, `menu_id`) VALUES (197, 1, 10);
INSERT INTO `j_role_menu` (`id`, `role_id`, `menu_id`) VALUES (198, 1, 11);
INSERT INTO `j_role_menu` (`id`, `role_id`, `menu_id`) VALUES (199, 1, 12);
INSERT INTO `j_role_menu` (`id`, `role_id`, `menu_id`) VALUES (200, 1, 14);
INSERT INTO `j_role_menu` (`id`, `role_id`, `menu_id`) VALUES (201, 1, 15);
COMMIT;

-- ----------------------------
-- Table structure for j_user
-- ----------------------------
DROP TABLE IF EXISTS `j_user`;
CREATE TABLE `j_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `status` varchar(5) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `avatar` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `deleted` int DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of j_user (用户数据)
-- ----------------------------
BEGIN;
INSERT INTO `j_user` (`id`, `username`, `password`, `phone`, `status`, `email`, `avatar`, `deleted`) VALUES (1, 'admin', 'YOUR_PASSWORD_HERE', '13800138000', '1', 'admin@example.com', NULL, 0);
INSERT INTO `j_user` (`id`, `username`, `password`, `phone`, `status`, `email`, `avatar`, `deleted`) VALUES (34, 'zhangwei', 'YOUR_PASSWORD_HERE', '13900139000', '1', 'zhangwei@example.com', NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for j_user_role
-- ----------------------------
DROP TABLE IF EXISTS `j_user_role`;
CREATE TABLE `j_user_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `role_id` int DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of j_user_role (用户角色关联)
-- ----------------------------
BEGIN;
INSERT INTO `j_user_role` (`id`, `user_id`, `role_id`) VALUES (1, 1, 1);
INSERT INTO `j_user_role` (`id`, `user_id`, `role_id`) VALUES (2, 34, 3);
COMMIT;

-- ----------------------------
-- Table structure for sport_info
-- ----------------------------
DROP TABLE IF EXISTS `sport_info`;
CREATE TABLE `sport_info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sport_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `suitable_time` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `suitable_heart_rate` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `suitable_frequency` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `recommended_speed` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sport_info
-- ----------------------------
BEGIN;
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (1, '跑步', '30-60分钟', '120-150', '每周3-5次', '6-8公里/小时');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (2, '慢跑', '30-45分钟', '110-130', '每周3-4次', '5-7公里/小时');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (3, '快走', '30-60分钟', '100-120', '每天', '4-6公里/小时');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (4, '游泳', '30-60分钟', '120-150', '每周2-3次', '中等强度');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (5, '瑜伽', '45-90分钟', '80-100', '每周2-3次', '缓慢');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (6, '羽毛球', '30-60分钟', '130-160', '每周2-3次', '中等强度');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (7, '篮球', '40-90分钟', '140-170', '每周2-3次', '高强度');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (8, '跳绳', '15-30分钟', '130-160', '每周3-4次', '中等强度');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (9, '骑自行车', '30-60分钟', '110-140', '每周3-4次', '15-20公里/小时');
INSERT INTO `sport_info` (`id`, `sport_type`, `suitable_time`, `suitable_heart_rate`, `suitable_frequency`, `recommended_speed`) VALUES (10, '健身', '45-60分钟', '100-130', '每周3-4次', '中等强度');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
