# Beneficios - Containerized API com Docker Compose


Esta versão da aplicação `SBeneficios` está preparada para ser executada completamente em **containers Docker**, facilitando o deploy e o ambiente de desenvolvimento. Inclui dois serviços principais: a **API de Benefícios** e o **banco de dados PostgreSQL**.


---


## 🧱 Tecnologias Utilizadas


- Java 21
- Spring Boot 3.4.4
- Spring Data JPA
- PostgreSQL
- Docker e Docker Compose
- Springdoc OpenAPI (Swagger)
- Lombok


---


## 🐳 Estrutura Docker Compose


O arquivo `docker-compose.yml` define dois serviços:


### `postgres`
- Imagem: `postgres`
- Porta exposta: `5434:5432`
- Variáveis de ambiente:
  - `POSTGRES_USER=beneficios_user`
  - `POSTGRES_PASSWORD=beneficios_pass`
  - `POSTGRES_DB=beneficios_db`
- Volume persistente: `postgres_data`


### `api`
- Build do Dockerfile local
- Porta exposta: `8087:8087`
- Depende do serviço `postgres`
- Variáveis de ambiente para conexão com o banco:
  - `DATASOURCE_URL=jdbc:postgresql://postgres:5432/beneficios_db`
  - `POSTGRES_USER=beneficios_user`
  - `POSTGRES_PASSWORD=beneficios_pass`
  - `SERVER_PORT=8087`


---


## ▶️ Como Executar com Docker Compose


1. **Clone o repositório**
```bash
git clone https://github.com/Projeto-RPPS/beneficios-api
cd beneficios-api
```


2. **Suba os containers com build**
```bash
docker-compose up --build
```


3. A API estará disponível em: [http://localhost:8087](http://localhost:8087)  
   A documentação Swagger estará em: [http://localhost:8087/swagger-ui.html](http://localhost:8087/swagger-ui.html)


---


## 📘 Endpoints Principais


### Solicitações de Benefício


- `POST /beneficios/solicitacao` - Cria nova solicitação e analisa
- `PATCH /beneficios/solicitacao/desativar/{id}` - Desativa logicamente uma solicitação
- `GET /beneficios/solicitacao` - Lista solicitações ativas
- `GET /beneficios/solicitacao/cpf/{cpf}/total` - Retorna total de benefícios de um CPF


### Benefícios


- `POST /beneficios` - Cria novo benefício
- `GET /beneficios` - Lista todos os benefícios
- `PATCH /beneficios/{id}` - Atualiza um benefício
- `PATCH /beneficios/{id}/desativar` - Desativa logicamente um benefício


---


## 📌 Considerações Finais


- A aplicação já vem pronta para uso com Docker Compose.
- Certifique-se de que as portas `5434` (PostgreSQL) e `8087` (API) estejam livres em sua máquina.
- A URL de acesso à API e à interface Swagger pode variar caso esteja usando WSL ou Docker Desktop em outro SO.


---


Para dúvidas ou melhorias, abra uma issue no repositório ou entre em contato com o time de desenvolvimento do Projeto RPPS.
