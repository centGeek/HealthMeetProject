CREATE TABLE medicine
(
    availability_schedule_id serial              not null,
    since                    time with time zone not null,
    to_when                  time with time zone not null,
    doctor_id                int                 not null,
    primary key (availability_schedule_id),
    CONSTRAINT fk_doctor_availability_schedule
        FOREIGN KEY (doctor_id)
            REFERENCES doctor (doctor_id)
);