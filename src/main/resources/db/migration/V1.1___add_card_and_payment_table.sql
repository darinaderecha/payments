CREATE TABLE IF NOT EXISTS card (
    card_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    client_id UUID NOT NULL,
    card_number VARCHAR(16) UNIQUE NOT NULL,
    CONSTRAINT fk_client FOREIGN KEY (client_id) REFERENCES client(client_id)
);

CREATE TABLE IF NOT EXISTS payment (
    payment_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    card_id UUID NOT NULL,
    iban VARCHAR(34) NOT NULL,
    mfo VARCHAR(6)  NOT NULL,
    zkpo VARCHAR(10) NOT NULL,
    receiver_name VARCHAR(255) NOT NULL,
    amount NUMERIC,
    CONSTRAINT fk_card FOREIGN KEY (card_id) REFERENCES card(card_id)
);

CREATE TABLE IF NOT EXISTS charge (
    charge_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    charge_time TIMESTAMP,
    payment_id UUID NOT NULL,
    amount NUMERIC,
    status varchar(10) check (status in ('ACTIVE','SUSPENDED')),
    CONSTRAINT fk_payment FOREIGN KEY (payment_id) REFERENCES payment(payment_id)
);