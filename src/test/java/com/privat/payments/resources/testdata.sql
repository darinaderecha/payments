INSERT INTO client (client_id, name,  tin) VALUES
    ('11111111-1111-1111-1111-111111111111', 'Симоненко Василь Андрійович',             '1234567890'),
    ('22222222-2222-2222-2222-222222222221', 'Костенко Ліна Василівна',                 '1876543210'),
    ('22222222-2222-2222-2222-222222222222', 'Сковорода Григорій Савич',                '2876543210'),
    ('22222222-2222-2222-2222-222222222223', 'Шевченко Тарас Григорович',               '3876543210'),
    ('22222222-2222-2222-2222-222222222224', 'Підмогильний Валерʼян Петович',           '4876543210'),
    ('33333333-3333-3333-3333-333333333333', 'Косач Олена Петрівна',                    '5556667777');

    INSERT INTO card (card_id, client_id, card_number, cvv, expiration_date, iban, is_active, balance) VALUES
        ('aaaa1111-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '11111111-1111-1111-1111-111111111111', '1111222233334444', '123', '2026-12-31 23:59:59', 'UA123456789012345678901234567', TRUE, 1000.50),
        ('bbbb2222-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '22222222-2222-2222-2222-222222222222', '1255666677778888', '456', '2025-08-15 23:59:59', 'UA227654321098765432109876543', TRUE, 12673.75),
        ('bbbb3333-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '22222222-2222-2222-2222-222222222221', '1355666677778888', '456', '2025-08-15 23:59:59', 'UA337654321098765432109876543', TRUE, 200001.00),
        ('eeee2222-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '22222222-2222-2222-2222-222222222221', '1455666677778888', '456', '2025-08-15 23:59:59', 'UA447654321098765432109876543', TRUE, 25.43),
        ('aaaa4444-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '22222222-2222-2222-2222-222222222223', '1555666677778888', '456', '2025-08-15 23:59:59', 'UA557654321098765432109876543', TRUE, 45863.75),
        ('bbbb1616-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '22222222-2222-2222-2222-222222222224', '1655666677778888', '456', '2025-08-15 23:59:59', 'UA667654321098765432109876543', TRUE, 4.75),
        ('aaaa4321-cccc-cccc-cccc-cccccccccccc', '33333333-3333-3333-3333-333333333333', '1799000011112222', '789', '2027-04-10 23:59:59', 'UA7755555555555555555555555', TRUE, 0.00),
        ('cccc1234-cccc-cccc-cccc-cccccccccccc', '33333333-3333-3333-3333-333333333333', '1899000011112222', '789', '2024-11-21 23:59:59', 'UA8855555555555555555555555', TRUE, 12345.00),
        ('cccc3333-cccc-cccc-cccc-cccccccccccc', '33333333-3333-3333-3333-333333333333', '1999000011112222', '789', '2027-04-10 23:59:59', 'UA9955555555555555555555555', FALSE, 50000.00);