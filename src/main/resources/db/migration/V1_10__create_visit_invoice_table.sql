create table visit_invoice(
    invoice_id serial not null ,
    invoice_nr varchar(32) not null,
    start_date timestamp with time zone not null ,
    patient_id int not null ,
    doctor_id int not null ,
    visit_cost numeric(7,2) not null ,
    primary key (invoice_id),
    unique (invoice_nr),
        CONSTRAINT fk_doctor_visit_invoice
            FOREIGN KEY (doctor_id)
                REFERENCES doctor (doctor_id),
        CONSTRAINT fk_patient_visit_invoice
            FOREIGN KEY (patient_id)
                          REFERENCES patient (patient_id)

);