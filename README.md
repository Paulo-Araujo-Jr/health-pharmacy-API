# Health Pharmacy API

API REST completa para o gerenciamento de uma farmácia, abrangendo funcionalidades essenciais para **pedidos, produtos, funcionários, clientes e autenticação de usuários**. Desenvolvida com Spring Boot, esta API oferece uma solução robusta e segura para as operações do dia a dia.

---

## 🚀 Tecnologias Utilizadas

* **Spring Boot:** Framework para desenvolvimento rápido de aplicações Java.
* **Spring Security:** Segurança robusta para autenticação (JWT) e autorização baseada em roles.
* **Spring Data JPA:** Simplifica o acesso a dados e a persistência com Hibernate.
* **PostgreSQL:** Banco de dados relacional robusto e de código aberto.
* **Lombok:** Facilita a escrita de código boilerplate.
* **JWT (JSON Web Tokens):** Para autenticação segura e stateless.
* **SpringDoc OpenAPI / Swagger UI:** Geração automática e interativa da documentação da API.

---

## 💡 Como Executar o Projeto

Para rodar a Health Pharmacy API localmente, siga os passos abaixo:

1.  **Pré-requisitos:**
    * **Java 21** ou superior instalado.
    * **Maven** instalado.
    * **PostgreSQL** rodando (localmente ou via Docker).

2.  **Configuração do Banco de Dados:**
    * No arquivo `src/main/resources/application.properties` (ou `application.yml`), configure as credenciais e o URL de conexão com seu banco de dados PostgreSQL.
    * Exemplo de `application.properties`:
        ```properties
        spring.datasource.url=jdbc:postgresql://localhost:5432/health_pharmacy_db
        spring.datasource.username=seu_usuario
        spring.datasource.password=sua_senha
        spring.jpa.hibernate.ddl-auto=update # ou create, create-drop para desenvolvimento
        spring.jpa.show-sql=true
        ```

3.  **Executar a Aplicação:**
    * Navegue até o diretório raiz do projeto no seu terminal.
    * Execute o comando Maven:
        ```bash
        mvn spring-boot:run
        ```
    * A aplicação estará disponível em `http://localhost:8080` (porta padrão do Spring Boot).

---

## 📄 Documentação da API (Swagger UI)

A API é auto-documentada usando o SpringDoc OpenAPI, que fornece uma interface interativa (Swagger UI) para explorar todos os endpoints.

* Acesse a documentação interativa em:
    [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

### Autenticação no Swagger UI

Para testar endpoints protegidos no Swagger UI, você precisará obter um **Bearer Token (JWT)**:

1.  Faça uma requisição `POST` para `/api/v1/auth/login` com suas credenciais de usuário/senha.
2.  Copie o token JWT retornado na resposta (geralmente começa com `Bearer `).
3.  No Swagger UI, clique no botão **"Authorize"** (ou no ícone de cadeado) no canto superior direito.
4.  No campo `Value`, insira o token copiado (incluindo o prefixo `Bearer `, ex: `Bearer eyJhbGciOi...`).
5.  Clique em "Authorize" e depois "Close". Agora você pode testar os endpoints protegidos.

---

## 🔒 Segurança da API (Spring Security)

A API utiliza Spring Security para gerenciamento de autenticação e autorização, com as seguintes características:

* **Autenticação JWT:** A comunicação é baseada em tokens JWT para sessões stateless.
* **CSRF Desabilitado:** Adequado para APIs RESTful.
* **Tratamento de Exceções:** Handlers customizados para autenticação (`CustomAuthenticationEntryPoint`) e acesso negado (`CustomAccessDeniedHandler`).

### Regras de Acesso por Role e Endpoint

A tabela abaixo detalha as permissões de acesso para cada endpoint com base nas roles (`ROLE_ADMIN`, `ROLE_EMPLOYEE`, `ROLE_CUSTOMER`) ou se o acesso é público (`permitAll`):

| Categoria | Método | Endpoint                           | Roles Autorizadas             |
| :-------- | :----- | :--------------------------------- | :---------------------------- |
| **Auth** | `POST` | `/api/v1/auth/login`               | `Public`                      |
| **Clientes** | `POST` | `/api/v1/customers/registration`   | `Public`                      |
|           | `GET`  | `/api/v1/customers/customers/{cpf}`| `ROLE_ADMIN`, `ROLE_CUSTOMER` |
|           | `DELETE`| `/api/v1/customers/{id}`          | `ROLE_CUSTOMER`               |
| **Funcionários** | `POST` | `/api/v1/employee/registration`  | `ROLE_ADMIN`                  |
|           | `GET`  | `/api/v1/employee/{cpf}`           | `ROLE_ADMIN`                  |
|           | `DELETE`| `/api/v1/employee/{cpf}`          | `ROLE_ADMIN`                  |
| **Produtos** | `POST` | `/api/v1/products/supplements`     | `ROLE_EMPLOYEE`               |
|           | `POST` | `/api/v1/products/medications`     | `ROLE_EMPLOYEE`               |
|           | `POST` | `/api/v1/products/hygieneProducts` | `ROLE_EMPLOYEE`               |
|           | `POST` | `/api/v1/products/cosmetics`       | `ROLE_EMPLOYEE`               |
|           | `DELETE`| `/api/v1/products/{barcode}`      | `ROLE_EMPLOYEE`               |
|           | `PATCH`| `/api/v1/products/{barcode}`      | `ROLE_EMPLOYEE`               |
|           | `GET`  | `/api/v1/products`                 | `Public`                      |
|           | `GET`  | `/api/v1/products/barcode/{barcode}` | `Public`                  |
| **Pedidos** | `PUT`  | `/api/v1/orders/cart/decrease`     | `ROLE_CUSTOMER`               |
|           | `POST` | `/api/v1/orders/checkout`          | `ROLE_CUSTOMER`               |
|           | `POST` | `/api/v1/orders/cart/add`          | `ROLE_CUSTOMER`               |
|           | `POST` | `/api/v1/orders/buy-now`           | `ROLE_CUSTOMER`               |
|           | `GET`  | `/api/v1/orders`                   | `ROLE_CUSTOMER`               |
|           | `GET`  | `/api/v1/orders/my-orders`         | `ROLE_CUSTOMER`               |
|           | `GET`  | `/api/v1/orders/cart`              | `ROLE_CUSTOMER`               |
|           | `DELETE`| `/api/v1/orders/cart/remove`      | `ROLE_CUSTOMER`               |
| **Documentação**| `GET`| `/swagger-ui/**`, `/v3/api-docs/**`| `Public`                      |
| **Outros**| `TODOS`| `Qualquer outro endpoint`          | Requer Autenticação           |

---

## 🔧 Estrutura do Projeto (Visão Geral)

* `com.healthPharmacy.demo.controllers`: Contém os controladores REST que expõem os endpoints da API.
* `com.healthPharmacy.demo.services`: Lógica de negócio e comunicação com o repositório.
* `com.healthPharmacy.demo.repository`: Interfaces para acesso a dados (Spring Data JPA).
* `com.healthPharmacy.demo.models`: Entidades de banco de dados.
* `com.healthPharmacy.demo.dtos`: Objetos de Transferência de Dados para requisições e respostas.
* `com.healthPharmacy.demo.infra.security`: Configurações de segurança (Spring Security, JWT).
* `com.healthPharmacy.demo.infra.exception`: Classes de exceção e handlers globais de erro.
* `com.healthPharmacy.demo.api`: (Opcional, mas recomendado) Interfaces para documentação Swagger/OpenAPI.

---

## Contribuição

Sinta-se à vontade para contribuir com melhorias, correções de bugs ou novas funcionalidades. Abra um *issue* ou *pull request* no repositório.

---

Se tiver alguma dúvida ou sugestão, por favor, me avise!
