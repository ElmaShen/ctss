/*
-- Query: SELECT * FROM ecs_backup.system_message
LIMIT 0, 1000

-- Date: 2017-07-12 03:51
*/
-- 預設的系統參數
INSERT INTO `system_message` (`sm_id`,`sm_code`,`sm_message`) VALUES (1,0,'成功');
INSERT INTO `system_message` (`sm_id`,`sm_code`,`sm_message`) VALUES (2,-9999,'系統錯誤');
INSERT INTO `system_message` (`sm_id`,`sm_code`,`sm_message`) VALUES (4,-9998,'操作錯誤');
INSERT INTO `system_message` (`sm_id`,`sm_code`,`sm_message`) VALUES (5,-9997,'參數錯誤');
INSERT INTO `system_message` (`sm_id`,`sm_code`,`sm_message`) VALUES (6,-9904,'重複登入');
INSERT INTO `system_message` (`sm_id`,`sm_code`,`sm_message`) VALUES (7,-9903,'登入逾時');
INSERT INTO `system_message` (`sm_id`,`sm_code`,`sm_message`) VALUES (8,-9902,'密碼輸入錯誤');
INSERT INTO `system_message` (`sm_id`,`sm_code`,`sm_message`) VALUES (9,-9901,'帳號輸入錯誤');
INSERT INTO `system_message` (`sm_id`,`sm_code`,`sm_message`) VALUES (10,-9900,'尚未登入');
INSERT INTO `system_message` (`sm_id`,`sm_code`,`sm_message`) VALUES (11,-9905,'登入失敗');
INSERT INTO `system_message` (`sm_id`,`sm_code`,`sm_message`) VALUES (12,-9800,'驗證碼錯誤。');
INSERT INTO `system_message` (`sm_id`,`sm_code`,`sm_message`) VALUES (13,-9801,'驗證碼過期。');
INSERT INTO `system_message` (`sm_id`,`sm_code`,`sm_message`) VALUES (14,-9802,'圖形驗證碼錯誤');
INSERT INTO `system_message` (`sm_id`,`sm_code`,`sm_message`) VALUES (15,-8000,'日期範圍錯誤');

INSERT INTO `system_setting` (`ss_id`,`ss_code`,`ss_creation_time`,`ss_creator`,`ss_creation_type`,`ss_description`,`ss_last_update_time`,`ss_mdyer`,`ss_mdyer_type`,`ss_mode`,`ss_name`,`ss_temp_value`,`ss_type`,`ss_value`) 
VALUES (1,'7000','2017-01-01',1,'a','','2017-01-01',0,'a','DEV','Ldap jndi','','7','ldap://localhost:389');


-- 預設的帳號
INSERT INTO `admin_info` (`ai_id`,`user_id`,`user_name`) VALUES (1,'matthewhui@test.idv','Matt Hui');

-- 預設的晶舍
INSERT INTO `user_place_mapping_info`
(`upmi_id`,`create_dt`,`creator_id`,`creator_ip`,`creator_name`,`examiner1`,`examiner1id`,
	`examiner2`,`examiner2id`,`is_enabled`,`is_need_daily_check_fire`,
	`manager`,`manager_id`,`place`,`place_id`,`place_type`,`unit_code`,
	`update_dt`,`updator_id`,`updator_ip`,`updator_name`)
VALUES
(1,'2017-01-01','matthewhui@test.idv','','Matthew Hui','Matthew Hui','matthewhui@test.idv',
	'Matthew Hui','matthewhui@test.idv',1,1,
	'Matthew Hui','matthewhui@test.idv','place062','p1','pppp','062',
	'2017-01-01','matthewhui@test.idv','','Matthew Hui'),
(2,'2017-01-01','blair@test.idv','','Matthew Hui','Matthew Hui','matthewhui@test.idv',
	'Blair Ku','blair@test.idv',1,1,
	'Matthew Hui','matthewhui@test.idv','place063','p1','pppp','063',
	'2017-01-01','matthewhui@test.idv','','Matthew Hui');

-- 預設的測試用公告
INSERT INTO `announcement_set_info`
(`asi_id`,`announcement_content`,`announcement_dt`,`announcement_subject`,`create_dt`,
	`creator_id`,`creator_ip`,`creator_name`,`is_enabled`,`message_id`,`update_dt`,
	`updator_id`,`updator_ip`,`updator_name`)
VALUES
(1,'M10606001 test ahahahahahaha中文你好','2017-01-01','上線公告','2017-01-01',
	'matthewhui@test.idv','192.168.2.1','Matthew Hui',1,'M10606001','2017-01-01',
	'matthewhui@test.idv','192.168.2.1','Matthew Hui'),
(2,'M10606002 你好 系統正式上線！','2017-01-02','上線公告','2017-01-01',
	'matthewhui@test.idv','192.168.2.1','Matthew Hui',1,'M10606002','2017-01-01',
	'matthewhui@test.idv','192.168.2.1','Matthew Hui');
