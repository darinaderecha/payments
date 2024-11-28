INSERT INTO client (client_id, name,  itn) VALUES
    ('11111111-1111-1111-1111-111111111111', 'Симоненко Василь Андрійович',             '1234567890'),
    ('22222222-2222-2222-2222-222222222221', 'Костенко Ліна Василівна',                 '1876543210'),
    ('22222222-2222-2222-2222-222222222222', 'Сковорода Григорій Савич',                '2876543210'),
    ('22222222-2222-2222-2222-222222222223', 'Шевченко Тарас Григорович',               '3876543210'),
    ('22222222-2222-2222-2222-222222222224', 'Підмогильний Валерʼян Петович',           '4876543210'),
    ('33333333-3333-3333-3333-333333333333', 'Косач Олена Петрівна',                    '5556667777');

    INSERT INTO card (card_id, client_id, card_number) VALUES
        ('aaaa1111-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '11111111-1111-1111-1111-111111111111', '1111222233334444'),
        ('bbbb2222-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '22222222-2222-2222-2222-222222222222', '1255666677778888'),
        ('bbbb3333-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '22222222-2222-2222-2222-222222222221', '1355666677778888'),
        ('eeee2222-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '22222222-2222-2222-2222-222222222221', '1455666677778888'),
        ('aaaa4444-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '22222222-2222-2222-2222-222222222223', '1555666677778888'),
        ('bbbb1616-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '22222222-2222-2222-2222-222222222224', '1655666677778888'),
        ('aaaa4321-cccc-cccc-cccc-cccccccccccc', '33333333-3333-3333-3333-333333333333', '1799000011112222'),
        ('cccc1234-cccc-cccc-cccc-cccccccccccc', '33333333-3333-3333-3333-333333333333', '1899000011112222'),
        ('cccc3333-cccc-cccc-cccc-cccccccccccc', '33333333-3333-3333-3333-333333333333', '1999000011112222');

INSERT INTO payment(payment_id, card_id, iban, mfo, zkpo, receiver_name, amount, withdrawal_period) VALUES
	('11111111-1111-1111-1111-111111111111', 'bbbb2222-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'UA11111111111111111111', '111111', '1111111111', 'Пилипів Марія', 100, 30),
	('21111111-1111-1111-1111-111111111111', 'bbbb2222-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'UA33333333333333333333', '333333', '3333333333', 'Григоренко Марія', 36, 10),
	('31111111-1111-1111-1111-111111111111', 'bbbb2222-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'UA44444444444444444444', '444444', '4444444444', 'Віт Віталій', 7000, 50),
	('41111111-1111-1111-1111-111111111111', 'cccc3333-cccc-cccc-cccc-cccccccccccc', 'UA11111111111111111111', '111111', '1111111111', 'Пилипів Марія', 200, 5),
	('5111111-1111-1111-1111-1111111111111',  'bbbb1616-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'UA11111111111111111111', '111111', '1111111111', 'Пилипів Марія', 500, 2),
	('61111111-1111-1111-1111-111111111111', 'bbbb1616-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'UA22222222222222222222', '222222', '2222222222', 'Андрієнко Андрій', 800, 14440);