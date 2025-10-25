CREATE TABLE clients (
    code SERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    cpf CHAR(11) NOT NULL,
    email VARCHAR(150),
    phone VARCHAR(20),
    active BOOLEAN DEFAULT TRUE
);

CREATE UNIQUE INDEX idx_clients_cpf ON clients (cpf);
CREATE INDEX idx_clients_email ON clients (email);
