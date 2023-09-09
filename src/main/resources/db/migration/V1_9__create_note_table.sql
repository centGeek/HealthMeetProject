CREATE TABLE note
(
    note_id             serial              not null,
    description         varchar(256)        not null,
    illness             varchar(256)        not null,
    start_date          timestamp with time zone not null,
    end_date            timestamp with time zone not null,
    patient_id          int                 not null,
    doctor_id           int                 not null,
    primary key (note_id),
    CONSTRAINT fk_doctor_note
        FOREIGN KEY (doctor_id)
            REFERENCES doctor (doctor_id),
    CONSTRAINT  fk_patient_note
        FOREIGN KEY (patient_id)
            REFERENCES patient (patient_id)
);