CREATE TABLE IF NOT EXISTS compras (
    id SERIAL PRIMARY KEY,
    cliente_id INTEGER NOT NULL REFERENCES clientes(id),
    produto_codigo INTEGER NOT NULL REFERENCES produtos(codigo),
    quantidade INTEGER NOT NULL,
    valor_total NUMERIC(10,2) NOT NULL
);