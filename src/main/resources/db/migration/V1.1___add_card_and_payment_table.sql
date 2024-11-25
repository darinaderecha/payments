CREATE TABLE IF NOT EXISTS card (
    card_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    client_id UUID NOT NULL,
    card_number VARCHAR(16) UNIQUE NOT NULL,
    cvv VARCHAR(3) NOT NULL,
    expiration_date TIMESTAMP NOT NULL,
    iban VARCHAR(29) UNIQUE NOT NULL,
    is_active BOOLEAN,
    balance DOUBLE PRECISION,
    CONSTRAINT fk_client FOREIGN KEY (client_id) REFERENCES client(client_id)
);

CREATE TABLE IF NOT EXISTS regular_payment (
    regular_payment_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    card_id UUID NOT NULL,
    iban VARCHAR(29) NOT NULL,
    mfo VARCHAR(6)  NOT NULL,
    zkpo VARCHAR(10) NOT NULL,
    receiver_name VARCHAR(255) NOT NULL,
    is_active BOOLEAN,
    amount DOUBLE PRECISION,
    CONSTRAINT fk_card FOREIGN KEY (card_id) REFERENCES card(card_id)
);

CREATE TABLE IF NOT EXISTS charge (
    charge_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    charge_time TIMESTAMP,
    regular_payment_id UUID NOT NULL,
    amount DOUBLE PRECISION,
    status CHAR(1) CHECK (status IN ('A', 'S')),
    CONSTRAINT fk_regular_payment FOREIGN KEY (regular_payment_id) REFERENCES regular_payment(regular_payment_id)
);