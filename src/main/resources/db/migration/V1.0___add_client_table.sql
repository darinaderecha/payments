CREATE TABLE IF NOT EXISTS client (
    client_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    gender CHAR(1) CHECK (gender IN ('M', 'F')),
    birthdate DATE NOT NULL,
    nationality VARCHAR(100),
    email VARCHAR(255) UNIQUE NOT NULL,
    phone_number VARCHAR(15),
    passport_id VARCHAR(15) UNIQUE NOT NULL,
    tin VARCHAR(10) UNIQUE NOT NULL
);
