CREATE TABLE clinic
(
    clinic_id   SERIAL PRIMARY KEY,
    clinic_name VARCHAR(32),
    country     VARCHAR(32),
    postal_code VARCHAR(32),
    address     VARCHAR(64)
);