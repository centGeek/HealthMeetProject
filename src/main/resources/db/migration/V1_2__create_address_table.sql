CREATE TABLE address(
    address_id serial not null,
    country varchar(32) not null,
    city varchar(32) not null,
    postal_code varchar(32) not null,
    address varchar(32) not null,
    primary key (address_id)
);