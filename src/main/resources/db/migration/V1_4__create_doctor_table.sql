CREATE TABLE doctor
(
    doctor_id serial not null,
    name varchar(32) not null,
    surname varchar(32) not null,
    specialization varchar(64) not null,
    email varchar(32) not null,
    phone varchar(32) not null,
    clinic_id int not null,
    salary_for_15min_meet int not null,
    primary key (doctor_id),
        CONSTRAINT fk_doctor_clinic
            FOREIGN KEY (clinic_id)
                REFERENCES address (address_id)
);