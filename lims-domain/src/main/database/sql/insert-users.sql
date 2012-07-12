INSERT INTO member 
	(email, password_hash, first_name, last_name) 
	VALUES ('laurens@runnable.nl', '21232f297a57a5a743894a0e4a801fc3', 'Laurens', 'Fridael')
;

INSERT INTO member 
	(email, password_hash, first_name, prefix, last_name) 
	VALUES ('robtenhove@gmail.com', '21232f297a57a5a743894a0e4a801fc3', 'Robert', 'ten', 'Hove')
;

INSERT INTO member 
	(email, password_hash, first_name, last_name) 
	VALUES ('lfridael@xs4all.nl', '098f6bcd4621d373cade4e832627b4f6', 'Test', 'Gebruiker')
;

INSERT INTO role (name) VALUES ('admin'), ('user');

INSERT INTO member_role
	(member_id, role_id)
	SELECT DISTINCT member.id, role.id FROM member, role 
;

INSERT INTO groups (name) VALUES ('technician'), ('cmb');

INSERT INTO member_group
	(member_id, group_id)
	SELECT DISTINCT member.id, groups.id FROM member, groups 
;
