CREATE TABLE addresses (
    id SERIAL PRIMARY KEY,
    city VARCHAR(100),
    neighborhood VARCHAR(100),
    public_place VARCHAR(100),
    number VARCHAR(10),
    zipcode VARCHAR(20),
    client_id BIGINT NOT NULL,
    CONSTRAINT fk_address_client
        FOREIGN KEY (client_id)
        REFERENCES clients (code)
        ON DELETE CASCADE
);

CREATE INDEX idx_address_client ON addresses (client_id);