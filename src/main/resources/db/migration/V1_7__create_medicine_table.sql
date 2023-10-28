CREATE TABLE medicine
(
    medicine_id  serial        not null,
    name         varchar(32)   not null,
    quantity     int           not null,
    approx_price NUMERIC(7, 2) not null,
    receipt_id   int           not null,
    primary key (medicine_id),
    CONSTRAINT fk_medicine_receipt
        FOREIGN KEY (receipt_id)
            REFERENCES receipt (receipt_id)
);