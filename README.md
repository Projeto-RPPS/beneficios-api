# ServicoBeneficios API

Esta é a API REST do sistema de benefícios do projeto RPPS. Ela permite o gerenciamento de solicitações de benefícios, incluindo criação, listagem, desativação e cálculo de totais por CPF.

---

## 📊 Tecnologias Utilizadas

- Java 21  
- Spring Boot 3.4.4  
- Spring Data JPA  
- PostgreSQL  
- Docker e Docker Compose  
- OpenAPI 3 (via Springdoc)  
- Swagger UI  
- Lombok

---

## 🛠️ Como Executar com Docker

### 1. Clonar o projeto
```bash
git clone https://github.com/Projeto-RPPS/beneficios-api.git
cd beneficios-api
```

### 2. Criar o arquivo `.env`
Crie um arquivo chamado `.env` na raiz do projeto com o seguinte conteúdo:
```env
# Variáveis do PostgreSQL
POSTGRES_USER=beneficios_user
POSTGRES_PASSWORD=beneficios_pass
POSTGRES_DB=beneficios_db

# Nome da aplicação Spring (opcional)
spring.application.name=ServicoBeneficios
```

### 3. Subir a aplicação e banco com Docker Compose
```bash
docker-compose up --build
```

A API estará acessível na porta **8087** e o banco de dados na porta **5434**.


---

## 🔗 Endpoints Disponíveis

### ✨ BenefícioController

- `POST /beneficios`  — Cria um novo benefício
- `GET /beneficios`  — Lista todos os benefícios
- `PATCH /beneficios/{id}`  — Atualiza os dados de um benefício
- `PATCH /beneficios/{id}/desativar`  — Desativa logicamente um benefício

### ✉️ SolicitacaoBeneficioController

- `POST /beneficios/solicitacao`  — Cria uma nova solicitação de benefício (com análise automática)
- `GET /beneficios/solicitacao`  — Lista todas as solicitações ativas
- `PATCH /beneficios/solicitacao/desativar/{id}`  — Desativa logicamente uma solicitação de benefício
- `GET /beneficios/solicitacao/cpf/{cpf}/total`  — Calcula o total de benefícios para um determinado CPF

---

## 📄 Documentação Swagger (OpenAPI)

Após subir a aplicação com sucesso:
- Acesse: [http://localhost:8087/swagger-ui.html](http://localhost:8087/swagger-ui.html)
- Documentação JSON: [http://localhost:8087/v3/api-docs](http://localhost:8087/v3/api-docs)

> Obs: Certifique-se de que a dependência `springdoc-openapi-starter-webmvc-ui` está corretamente adicionada no `pom.xml` com a versão `2.2.0` ou superior.

---

## ✅ Considerações Finais

- Verifique se todas as dependências estão corretamente configuradas.  
- Certifique-se de que os endpoints `/v3/api-docs` e `/swagger-ui.html` estão acessíveis.  
- Em caso de problemas com o Swagger, consulte a [documentação oficial do Springdoc](https://springdoc.org/).



