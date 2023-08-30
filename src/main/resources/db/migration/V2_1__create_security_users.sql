insert into healthy_meet_user (user_id, user_name,email, password, active) values (1, 'jan_lekarski','j.lekarski@medi.com', '$2a$12$TwQsp1IusXTDl7LwZqL0qeu49Ypr6vRdEzRq2vAsgb.zvOtrnzm5G', true);
insert into healthy_meet_user (user_id, user_name,email, password, active) values (2, 'tomasz_hibernate','t.hiber@medi.com','$2a$12$TwQsp1IusXTDl7LwZqL0qeu49Ypr6vRdEzRq2vAsgb.zvOtrnzm5G', true);
insert into healthy_meet_user (user_id, user_name,email, password, active) values (3, 'krzysztof_cool','k.cool@medi.com', '$2a$12$TwQsp1IusXTDl7LwZqL0qeu49Ypr6vRdEzRq2vAsgb.zvOtrnzm5G', true);

insert into healthy_meet_user (user_id, user_name,email, password, active) values (4, 'krzysztofa_zima', 'k.zima@gmail.com','$2a$12$TwQsp1IusXTDl7LwZqL0qeu49Ypr6vRdEzRq2vAsgb.zvOtrnzm5G', true);
insert into healthy_meet_user (user_id, user_name,email, password, active) values (5, 'jan_chrust','j.chrust@gmail.com', '$2a$12$TwQsp1IusXTDl7LwZqL0qeu49Ypr6vRdEzRq2vAsgb.zvOtrnzm5G', true);
insert into healthy_meet_user (user_id, user_name,email, password, active) values (6, 'albert_opalka','a.opalka@wp.pl', '$2a$12$TwQsp1IusXTDl7LwZqL0qeu49Ypr6vRdEzRq2vAsgb.zvOtrnzm5G', true);

insert into healthy_meet_role (role_id, role) values (1, 'DOCTOR'), (2, 'PATIENT');
insert into healthy_meet_user_role (user_id, role_id) values (1, 1), (2, 1), (3, 1);
insert into healthy_meet_user_role (user_id, role_id) values (4, 2), (5, 2), (6, 2);
