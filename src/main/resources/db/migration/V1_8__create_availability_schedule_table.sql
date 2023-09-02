CREATE TABLE availability_schedule
(
    availability_schedule_id serial                   not null,
    since                    timestamp with time zone not null,
    to_when                  timestamp with time zone not null,
    available                boolean                  not null,
    doctor_id                int                      not null,
    primary key (availability_schedule_id),
    CONSTRAINT fk_doctor_availability_schedule
        FOREIGN KEY (doctor_id)
            REFERENCES doctor (doctor_id)
);