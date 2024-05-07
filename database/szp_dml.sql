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

INSERT INTO szp.vacation
(vacation_id, employee_id, beginning, ending, vacation_type)
values
(1, 1, '04-23-2024', '04-29-2024', 'Urlop wypocznykowy'),
(2, 1, '05-01-2024', '05-05-2024', 'Urlop wypocznykowy'),
(3, 2, '04-01-2024', '06-01-2024', 'Zwolnienie lekarskie'),
(4, 3, '04-22-2024', '04-25-2024', 'Zwolnienie lekarskie');


INSERT INTO szp.workstation
(workstation_id, workstation_name)
values
(1, 'Podnośnik'),
(2, 'Stacja wulkanizacji'),
(3, 'Stacja lakiernicza');


INSERT INTO szp."assignment"
(assignment_id, employee_id, client_id, description, assign_date, "cost", workstation_id, state)
values
(1, 1, 6, 'Wymiana klocków hamulcowych', '01-04-2024', 300, 1, 'Zakończone'),
(2, 2, 7, 'Polerowanie', '04-05-2024', 450, 2, 'Zakończone'),
(3, 2, 1, 'Malowanie', '04-22-2024', 700, 1, 'W trakcie'),
(4, 2, 2, 'Malowanie', '11-10-2023', 800, 2, 'Zakończone'),
(5, 1, 3, 'Diagnostyka', '01-05-2024', 200, 3, 'Zakończone'),
(6, 3, 6, 'Zmiana opon', '05-02-2024', 340, 1, 'Oczekiwanie na pojazd'),
(7, 2, 4, 'Diagnostyka', '12-30-2023', 200, 2, 'W trakcie'),
(8, 3, 4, 'Elektryka', '11-24-2023', 650, 1, 'Odrzucone'),
(9, 3, 5, 'Wstawienie szyby', '10-10-2023', 100, 3, 'Zakończone'),
(10, 2, 2, 'Diagnostyka', '02-29-2024', 250, 3, 'Zakończone');






