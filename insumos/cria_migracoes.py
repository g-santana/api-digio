import json


tabela_clientes = """
CREATE TABLE IF NOT EXISTS clientes (
    id SERIAL PRIMARY KEY,
    nome TEXT NOT NULL,
    cpf VARCHAR(11) UNIQUE NOT NULL
);
"""

tabela_produtos = """
CREATE TABLE IF NOT EXISTS produtos (
    codigo INTEGER PRIMARY KEY,
    tipo_vinho TEXT NOT NULL,
    preco NUMERIC(10,2) NOT NULL,
    safra VARCHAR(4),
    ano_compra VARCHAR(4)
);
"""

tabela_compras = """
CREATE TABLE IF NOT EXISTS compras (
    id SERIAL PRIMARY KEY,
    cliente_id INTEGER NOT NULL REFERENCES clientes(id),
    produto_codigo INTEGER NOT NULL REFERENCES produtos(codigo),
    quantidade INTEGER NOT NULL,
    valor_total NUMERIC(10,2) NOT NULL
);
"""

with open("../src/main/resources/db/migrations/V1__create_table_clientes.sql", "w", encoding="utf-8") as f:
    f.write(tabela_clientes.strip())
    f.close()

with open("../src/main/resources/db/migrations/V2__create_table_produtos.sql", "w", encoding="utf-8") as f:
    f.write(tabela_produtos.strip())
    f.close()

with open("../src/main/resources/db/migrations/V3__create_table_compras.sql", "w", encoding="utf-8") as f:
    f.write(tabela_compras.strip())
    f.close()

with open("produtos.json", encoding="utf-8") as f:
    produtos = {str(p["codigo"]): p for p in json.load(f)}
    f.close()

with open("clientes.json", encoding="utf-8") as f:
    clientes = json.load(f)
    f.close()

insert_produtos = []
for p in produtos.values():
    insert_produtos.append(
        f"INSERT INTO produtos (codigo, tipo_vinho, preco, safra, ano_compra) "
        f"VALUES ({p['codigo']}, '{p['tipo_vinho']}', {p['preco']:.2f}, '{p['safra']}', '{p['ano_compra']}');"
    )

with open("../src/main/resources/db/migrations/V4__insert_produtos.sql", "w", encoding="utf-8") as f:
    f.write("\n".join(insert_produtos))
    f.close()

insert_clientes = []
cliente_id_map = {}  # Map CPF -> ID para uso posterior em compras
next_cliente_id = 1

for cliente in clientes:
    nome = cliente["nome"].replace("'", "''")
    cpf = cliente["cpf"]
    insert_clientes.append(
        f"INSERT INTO clientes (id, nome, cpf) VALUES ({next_cliente_id}, '{nome}', '{cpf}');"
    )
    cliente_id_map[cpf] = next_cliente_id
    next_cliente_id += 1

with open("../src/main/resources/db/migrations/V5__insert_clientes.sql", "w", encoding="utf-8") as f:
    f.write("\n".join(insert_clientes))
    f.close()

insert_compras = []
for cliente in clientes:
    cliente_id = cliente_id_map[cliente["cpf"]]
    for compra in cliente["compras"]:
        codigo = compra["codigo"]
        qtd = compra["quantidade"]
        preco_unit = produtos[codigo]["preco"]
        valor_total = round(qtd * preco_unit, 2)
        insert_compras.append(
            f"INSERT INTO compras (cliente_id, produto_codigo, quantidade, valor_total) "
            f"VALUES ({cliente_id}, {codigo}, {qtd}, {valor_total:.2f});"
        )

with open("../src/main/resources/db/migrations/V6__insert_compras.sql", "w", encoding="utf-8") as f:
    f.write("\n".join(insert_compras))
    f.close()
