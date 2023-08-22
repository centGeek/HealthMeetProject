CREATE TABLE patient(
    patient_id serial not null,
    name varchar(32) not null,
    surname varchar(32) not null,
    pesel varchar(32) not null,
    email varchar(32) not null,
    phone varchar(32) not null,
    address_id int not null,
    primary key (patient_id),
        CONSTRAINT fk_patient_address
            FOREIGN KEY (address_id)
                REFERENCES address (address_id)
);