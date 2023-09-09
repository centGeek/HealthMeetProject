CREATE TABLE doctor
(
    doctor_id serial not null,
    name varchar(32) not null,
    surname varchar(32) not null,
    email varchar(32) not null,
    specialization varchar(64) not null,
    phone varchar(32) not null,
    clinic_id int,
    earnings_per_visit int not null,
    user_id int,
    primary key (doctor_id),
        CONSTRAINT fk_doctor_clinic
            FOREIGN KEY (clinic_id)
                REFERENCES clinic (clinic_id),
        CONSTRAINT fk_security_doctor
            FOREIGN KEY (user_id)
                REFERENCES healthy_meet_user (user_id)
);