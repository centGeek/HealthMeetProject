INSERT INTO doctor (name, surname, email, specialization, phone, earnings_per_visit, user_id)
VALUES ('Tommy', 'Shelby', 'tom.shelby@medi.com', 'CARDIOLOGIST', '+44 323 321 312', 320.00, 1);

INSERT INTO doctor (name, surname, email, specialization, phone, earnings_per_visit, user_id)
VALUES ('Grace', 'Shelby', 'grace.shelby@medi.com', 'NEUROLOGIST', '+48 321 321 212', 320.00, 2);

INSERT INTO doctor (name, surname, email, specialization, phone, earnings_per_visit, user_id)
VALUES ('Krzysztof', 'Columb', 'k.cool@medi.com', 'UROLOGIST', '+48 212 267 217', 150.00, 3);

INSERT INTO address(country, city, postal_code, address)
VALUES ('Poland', 'Poscien Zamion', '96-323', 'ul.Mickiewicza 32');

INSERT INTO address(country, city, postal_code, address)
VALUES ('USA', 'Washington', '98001', 'ul. Polski 25');

INSERT INTO address(country, city, postal_code, address)
VALUES ('Poland', 'Warsaw', '00-120', 'ul. Polska 2115');

INSERT INTO patient (name, surname, email, pesel, phone, address_id, user_id)
VALUES ('Walter', 'White', 'w.white@gmail.com', '80332456791', '+48 212 267 217', currval('address_address_id_seq') - 2,
        4);

INSERT INTO patient (name, surname, email, pesel, phone, address_id, user_id)
VALUES ('Skyler', 'White', 's.white@gmail.com', '90234381912', '+48 318 126 891', currval('address_address_id_seq') - 1,
        5);

INSERT INTO patient (name, surname, email, pesel, phone, address_id, user_id)
VALUES ('Jesse', 'Pinkman ', 'j.pinkman@wp.pl', '02324202812', '+48 213 327 212', currval('address_address_id_seq'), 6);


