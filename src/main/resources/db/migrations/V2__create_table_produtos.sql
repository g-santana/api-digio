CREATE TABLE IF NOT EXISTS produtos (
    codigo INTEGER PRIMARY KEY,
    tipo_vinho TEXT NOT NULL,
    preco NUMERIC(10,2) NOT NULL,
    safra VARCHAR(4),
    ano_compra VARCHAR(4)
);