insert into healthy_meet_user (user_name, email, password, active)
values ('tom_shelby', 'tom.shelby@medi.com', '$2a$12$TwQsp1IusXTDl7LwZqL0qeu49Ypr6vRdEzRq2vAsgb.zvOtrnzm5G', true);
insert into healthy_meet_user (user_name, email, password, active)
values ('grace_shelby', 'grace.shelby@medi.com', '$2a$12$TwQsp1IusXTDl7LwZqL0qeu49Ypr6vRdEzRq2vAsgb.zvOtrnzm5G', true);
insert into healthy_meet_user (user_name, email, password, active)
values ('krzysztof_cool', 'k.cool@medi.com', '$2a$12$TwQsp1IusXTDl7LwZqL0qeu49Ypr6vRdEzRq2vAsgb.zvOtrnzm5G', true);

insert into healthy_meet_user (user_name, email, password, active)
values ('walter_white', 'w.white@gmail.com', '$2a$12$TwQsp1IusXTDl7LwZqL0qeu49Ypr6vRdEzRq2vAsgb.zvOtrnzm5G', true);
insert into healthy_meet_user (user_name, email, password, active)
values ('skyler_white', 's.white@gmail.com', '$2a$12$TwQsp1IusXTDl7LwZqL0qeu49Ypr6vRdEzRq2vAsgb.zvOtrnzm5G', true);
insert into healthy_meet_user (user_name, email, password, active)
values ('jesse_pinkman', 'j.pinkman@wp.pl', '$2a$12$TwQsp1IusXTDl7LwZqL0qeu49Ypr6vRdEzRq2vAsgb.zvOtrnzm5G', true);

insert into healthy_meet_role (role_id, role)
values (1, 'DOCTOR'),
       (2, 'PATIENT');
insert into healthy_meet_user_role (user_id, role_id)
values (currval('healthy_meet_user_user_id_seq') - 5, 1),
       (currval('healthy_meet_user_user_id_seq') - 4, 1),
       (currval('healthy_meet_user_user_id_seq') - 3, 1);
insert into healthy_meet_user_role (user_id, role_id)
values (currval('healthy_meet_user_user_id_seq') - 2, 2),
       (currval('healthy_meet_user_user_id_seq') - 1, 2),
       (currval('healthy_meet_user_user_id_seq'), 2);

insert into healthy_meet_role (role_id, role)
values (3, 'REST_API');
insert into healthy_meet_user (user_name, email, password, active)
values ('rest_api', 'rest_api@wp.pl', '$2a$12$TwQsp1IusXTDl7LwZqL0qeu49Ypr6vRdEzRq2vAsgb.zvOtrnzm5G', true);
insert
into healthy_meet_user_role (user_id, role_id)
values (currval('healthy_meet_user_user_id_seq'), 3);
