
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
                title VARCHAR NOT NULL,
                CONSTRAINT employee_pk PRIMARY KEY (employee_id)
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
                employee_id SERIAL NOT NULL,
                client_id SERIAL NOT NULL,
                description VARCHAR NOT NULL,
                assign_date DATE NOT NULL,
                cost INTEGER NOT NULL,
                workstation VARCHAR NOT NULL,
                state VARCHAR NOT NULL,
                CONSTRAINT assignment_id PRIMARY KEY (assignment_id)
);

CREATE TABLE szp.refresh_token (
               id SERIAL NOT NULL,
               expiry_date TIMESTAMP(6) WITH TIME ZONE,
               token       VARCHAR(255),
               employee_id INTEGER,
               CONSTRAINT employee_id PRIMARY KEY (employee_id)
);


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

ALTER TABLE szp.refresh_token ADD CONSTRAINT refresh_token_employee_fk
FOREIGN KEY (employee_id)
REFERENCES szp.employee (employee_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;