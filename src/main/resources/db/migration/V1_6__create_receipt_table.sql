CREATE TABLE receipt
(
    receipt_id     serial      not null,
    receipt_number varchar(32) not null,
    date_time      timestamp without time zone not null,
    patient_id     int         not null,
    doctor_id      int         not null,
    unique (receipt_number),
    primary key (receipt_id),
    CONSTRAINT fk_doctor_receipt
        FOREIGN KEY (doctor_id)
            REFERENCES doctor (doctor_id),
    CONSTRAINT fk_patient_receipt
        FOREIGN KEY (patient_id)
            REFERENCES patient (patient_id)

);