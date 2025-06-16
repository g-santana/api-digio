# API Digio 🍷

Esse projeto é uma API desenvolvida em Java 21 com Spring Boot 3, 
que disponibiliza dados de compras de vinhos feitas por clientes, 
expondo endpoints do tipo GET. Foi desenvolvido para cumprir as 
especificações de um desafio técnico da act digital. O enunciado 
se encontra [aqui](insumos/teste-backend.pdf). Os demais artefatos 
que serviram de input e apoio para o desenvolvimento se encontram 
na pasta [insumos](insumos/).

---

## 🛠️ Tecnologias utilizadas

- Gradle
- Java 21 + Lombok + Spring Boot 3
- JUnit + SpringBootTest
- H2 (testes unitários) + Testcontainers (testes de integração)
- JPA (Hibernate)
- Flyway (DB migrations)
- PostgreSQL (via Docker)
- Swagger (OpenAPI 3)
- Python 3 (script que gerou as migrations do Flyway a partir dos JSONs)

---

## 🚀 Instruções para execução e testes

#### Aviso: é necessário ter o Git, o Docker e o docker-compose instalados e rodando na máquina.

Execute os comandos a seguir:

```bash
git clone https://github.com/g-santana/api-digio.git
cd api-digio
./gradlew clean build; docker-compose up --build -d
```

Caso esteja usando um computador Windows para rodar o projeto, 
substitua `./gradlew` por `gradlew.bat`

Essa sequência de comandos irá:
1. Clonar o repositório do projeto;
2. Entrar na pasta do projeto;
3. Compilar o projeto e gerar seu JAR;
4. Subir dois containers Docker: um com o banco de dados PostgreSQL 
e outro com a aplicação Spring Boot. A aplicação roda na porta 8080
e o banco de dados na 5432.

Caso queira executar a aplicação de forma nativa, você precisará 
conferir o [.env](.env) e seguir as instruções contidas lá. Depois, 
basta executar `./gradlew bootRun` (ou `gradlew.bat bootRun` no 
Windows).

Com a aplicação rodando, você pode acessar a documentação da API e 
testá-la em [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html). 
Também tem coleção e ambiente criados via Postman anexados na pasta 
[postman](postman/), caso prefira testar a API via Postman ou 
Insomnia. Basta importá-los no software de sua preferência.

Para rodar os testes unitários e de integração, execute 
`./gradlew clean test` (ou `gradlew.bat clean test`).


> Observações: 
> 1. As Flyway migrations (em `src/main/resources/db/migrations`) 
populam o banco de dados no momento em que a aplicação é inicializada 
e a execução delas é fundamental para a obtenção dos resultados esperados. 
Elas foram geradas por meio de um script Python. Ele encontra-se [aqui](insumos/cria_migracoes.py), 
em caso de curiosidade ou interesse em entender o processo que segui.
> 2. O arquivo `sync.sh` garante que o container da aplicação aguarde 
o container do banco de dados terminar de subir, para então iniciar a 
aplicação. A ausência ou alteração desse arquivo pode causar falhas.
> 3. Gostaria de ter implementado uma refatoração para isolar a lógica 
de negócio do código do framework (Clean Architecture), mas por ter 
recebido o desafio somente às 15:42 da sexta-feira (13/06/2025) e 
estar viajando, tive tempo bastante limitado para cumprir o desafio 
e tal refatoração não foi possível. Contudo, organizei o código e o 
histórico de commits da melhor maneira que pude. Um abraço, espero 
que goste do resultado.