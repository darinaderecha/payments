CREATE TABLE IF NOT EXISTS client (
    client_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    birthdate DATE NOT NULL,
    phone_number VARCHAR(15),
    tin VARCHAR(10) UNIQUE NOT NULL
);
