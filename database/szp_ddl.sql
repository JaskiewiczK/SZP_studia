CREATE SCHEMA szp;
CREATE TABLE szp.workstation (
                workstation_id SERIAL NOT NULL,
                workstation_name VARCHAR NOT NULL,
                CONSTRAINT workstation_pk PRIMARY KEY (workstation_id)
);


CREATE TABLE szp.client (
                client_id SERIAL NOT NULL,
                first_name VARCHAR NOT NULL,
                second_name VARCHAR,
                last_name VARCHAR NOT NULL,
                email VARCHAR NOT NULL,
                phone_number VARCHAR NOT NULL,
                bank_account VARCHAR NOT NULL,
                CONSTRAINT client_pk PRIMARY KEY (client_id)
);


CREATE TABLE szp.employee (
                employee_id SERIAL NOT NULL,
                first_name VARCHAR NOT NULL,
                second_name VARCHAR,
                last_name VARCHAR NOT NULL,
                birth_date DATE NOT NULL,
                pesel VARCHAR NOT NULL,
                login VARCHAR NOT NULL,
                password VARCHAR NOT NULL,
                role VARCHAR NOT NULL,
                CONSTRAINT employee_pk PRIMARY KEY (employee_id)
);


CREATE TABLE szp.refresh_token (
                employee_id SERIAL NOT NULL,
                id INTEGER NOT NULL,
                expire_date DATE NOT NULL,
                token VARCHAR NOT NULL,
                CONSTRAINT refresh_token_pk PRIMARY KEY (employee_id)
);


CREATE TABLE szp.vacation (
                vacation_id SERIAL NOT NULL,
                employee_id SERIAL NOT NULL,
                beginning DATE NOT NULL,
                ending DATE NOT NULL,
                vacation_type VARCHAR NOT NULL,
                CONSTRAINT vacation_pk PRIMARY KEY (vacation_id)
);


CREATE TABLE szp.assignment (
                assignment_id SERIAL NOT NULL,
                workstation_id SERIAL NOT NULL,
                employee_id SERIAL NOT NULL,
                client_id SERIAL NOT NULL,
                description VARCHAR NOT NULL,
                assign_date DATE NOT NULL,
                cost INTEGER NOT NULL,
                state VARCHAR NOT NULL,
                CONSTRAINT assignment_id PRIMARY KEY (assignment_id)
);


ALTER TABLE szp.assignment ADD CONSTRAINT workstation_assignment_fk
FOREIGN KEY (workstation_id)
REFERENCES szp.workstation (workstation_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE szp.assignment ADD CONSTRAINT klient_zlecenie_fk
FOREIGN KEY (client_id)
REFERENCES szp.client (client_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE szp.assignment ADD CONSTRAINT pracownik_zlecenie_fk
FOREIGN KEY (employee_id)
REFERENCES szp.employee (employee_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE szp.vacation ADD CONSTRAINT pracownik_urlop_fk
FOREIGN KEY (employee_id)
REFERENCES szp.employee (employee_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE szp.refresh_token ADD CONSTRAINT employee_refresh_token_fk
FOREIGN KEY (employee_id)
REFERENCES szp.employee (employee_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;