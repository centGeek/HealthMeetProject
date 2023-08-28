CREATE TABLE meeting_request
(
    meeting_id             serial                   not null,
    meeting_request_number varchar(32)              not null,
    received_date_time     timestamp with time zone not null,
    completed_date_time    timestamp with time zone not null,
    description            varchar(256)             not null,
    patient_id             int                      not null,
    doctor_id              int                      not null,
    unique (meeting_request_number),
    primary key (meeting_id),
    CONSTRAINT fk_doctor_meeting_request
        FOREIGN KEY (doctor_id)
            REFERENCES doctor (doctor_id),
    CONSTRAINT fk_patient_meeting_request
        FOREIGN KEY (patient_id)
            REFERENCES patient (patient_id)
);