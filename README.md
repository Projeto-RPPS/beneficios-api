ServicoBeneficios API


Esta é a API REST do sistema de benefícios do projeto RPPS. Ela permite o gerenciamento de solicitações de benefícios, incluindo criação, listagem, desativação e cálculo de totais por CPF.


## Tecnologias Utilizadas


- Java 21
- Spring Boot 3.4.4
- Spring Data JPA
- PostgreSQL
- Docker
- OpenAPI 3 (via Springdoc)
- Swagger UI
- Lombok


## Configuração do Projeto


Certifique-se de que o PostgreSQL esteja em execução e configurado com as credenciais especificadas no `application.properties`.


## Dependências Swagger no `pom.xml`


```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.2.0</version>
</dependency>
```


> **Nota:** Certifique-se de que a versão do springdoc-openapi seja compatível com Java 21 e Spring Boot 3.4.4. Versões como 2.2.0 são compatíveis.


## Executando a Aplicação


1. Construa o projeto:


```bash
mvn clean install
```


2. Execute a aplicação:


```bash
mvn spring-boot:run
```


## Acessando a Documentação Swagger


- Swagger UI: [http://localhost:8087/swagger-ui.html](http://localhost:8087/swagger-ui.html)
- OpenAPI JSON: [http://localhost:8087/v3/api-docs](http://localhost:8087/v3/api-docs)


## Endpoints Principais


### SolicitacaoBeneficioController


- `POST /beneficios/solicitacao` - Cria uma nova solicitação de benefício.
- `PATCH /beneficios/solicitacao/desativar/{id}` - Desativa logicamente uma solicitação de benefício pelo ID.
- `GET /beneficios/solicitacao/cpf/{cpf}/total` - Calcula o total de benefícios por CPF.
- `GET /beneficios/solicitacao` - Lista todas as solicitações ativas.


### BeneficioController


- `POST /beneficios` - Cria um novo tipo de benefício.
- `GET /beneficios` - Lista todos os tipos de benefícios.
- `PATCH /beneficios/{id}` - Atualiza um benefício existente.
- `PATCH /beneficios/{id}/desativar` - Desativa logicamente um benefício existente.


## Considerações Finais


Certifique-se de que todas as dependências estejam corretamente configuradas e que não haja conflitos de versões. Em caso de problemas com o Swagger, verifique se os endpoints `/v3/api-docs` e `/swagger-ui.html` estão acessíveis. Além disso, consulte a documentação oficial do Springdoc para informações sobre compatibilidade de versões.
