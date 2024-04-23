--- Insert on client ---

INSERT INTO szp.client
(client_id, first_name, second_name, last_name, email, phone_number, bank_account)
values
(1, 'Paweł', NULL, 'Szabla', 'delord12@gmail.com', '192837466', '6755'),
(2, 'Tomasz', 'Jonasz', 'Comasz', 'tocomasz@gov.pl', '112233440', '9998'),
(3, 'Jadwiga', NULL, 'Hymel', 'styrta44@lipinki.net', '112997998', '1111'),
(4, 'Józef', 'Marian', 'Nowak', 'alibabakebabk@naszaklasa.pl', '777777112', '1236'),
(5, 'Karolina', 'Alina', 'Winsdor', 'placeholder@wp.pl', '789654276', '5523'),
(6, 'Kornel', NULL, 'Poziomka', 'ospgodowa@agh.edu.pl', '000123123', '7762'),
(7, 'Michalina', 'Grażyna', 'Kotomiler', 'miskamontis@o2.pl', '665237231', '1111'),
(8, 'Ryszard', 'Dawid', 'Wójcik', 'noreply@agh.pl', '215436235', '0008');

--- Insert on employee ---
INSERT INTO szp.employee
(employee_id, first_name, second_name, last_name, birth_date, pesel, login, "password", "role")
values
(1, 'John', NULL, 'Pork', '04-24-1976', '1234', 'porkjohnson', 'placeholder', 'Szef'),
(2, 'Christopher', NULL, 'Paitner', '01-01-1960', '9990', 'corobidziekan', 'placeholder', 'Malarz-lakiernik'),
(3, 'Kamil', 'Mammon', 'Kowalski', '11-11-1999', '1232', 'amonra', 'placeholder', 'Wulkanizator'),
(4, 'Arkadiusz', NULL, 'Abacki', '09-07-2001', '1231', 'aabacki1', 'placeholder', 'Praktykant'),
(5, 'Aleksandra', 'Olga', 'Babacka', '05-19-1989', '1123', 'ababacka2', 'placeholder', 'Pracownik BOK'),
(6, 'Elżbieta', NULL, 'Cabacka', '12-21-2000', '8782', 'ecabacka3', 'placeholder', 'Informatyk');

--- Insert on vacation ---
INSERT INTO szp.vacation
(vacation_id, employee_id, beginning, ending, vacation_type)
values
(1, 1, '04-23-2024', '04-29-2024', 'Urlop wypocznykowy'),
(2, 1, '05-01-2024', '05-05-2024', 'Urlop wypocznykowy'),
(3, 2, '04-01-2024', '06-01-2024', 'Zwolnienie lekarskie'),
(4, 3, '04-22-2024', '04-25-2024', 'Zwolnienie lekarskie'),
(5, 4, '07-01-2024', '07-06-2024', 'Urlop wypoczynkowy'),
(6, 5, '03-20-2024', '12-11-2024', 'Urlop macierzyński'),
(7, 5, '12-12-2024', '11-10-2026', 'Urlop wychowawczy');

--- Inser on assignment ---
INSERT INTO szp."assignment"
(assignment_id, employee_id, client_id, description, assign_date, "cost", workstation, state)
values
(1, 1, 6, 'Wymiana klocków hamulcowych', '01-04-2024', 300, 'Warsztat 1', 'Zakończone'),
(2, 2, 7, 'Polerowanie', '04-05-2024', 450, 'Warsztat 1', 'Zakończone'),
(3, 2, 1, 'Malowanie', '04-22-2024', 700, 'Stanowisko lakiernicze', 'W trakcie'),
(4, 2, 2, 'Malowanie', '11-10-2023', 800, 'Stanowisko lakiernicze', 'Zakończone'),
(5, 1, 3, 'Diagnostyka', '01-05-2024', 200, 'Warsztat 2', 'Zakończone'),
(6, 3, 6, 'Zmiana opon', '05-02-2024', 340, 'Stanowisko wulkanizacyjne', 'Oczekiwanie na pojazd'),
(7, 2, 4, 'Diagnostyka', '12-30-2023', 200, 'Warsztat 2', 'W trakcie'),
(8, 3, 4, 'Elektryka', '11-24-2023', 650, 'Stanowisko elektryczne', 'Odrzucone'),
(9, 3, 5, 'Wstawienie szyby', '10-10-2023', 100, 'Warsztat 1', 'Zakończone'),
(10, 4, 1, 'Wymiana oleju', '04-17-2024', 150, 'Warstat 1', 'Pojazd czeka na oddanie'),
(11, 2, 2, 'Diagnostyka', '02-29-2024', 250, 'Warsztat 2', 'Zakończone');






