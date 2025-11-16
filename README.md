# 🛍️ EcommerceHM - Customer Microservice

[![Java 21](https://img.shields.io/badge/java-21-blue)](https://openjdk.org/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring--Boot-3.x-brightgreen)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/database-PostgreSQL-blue)](https://www.postgresql.org/)
[![Tests](https://img.shields.io/badge/tests-unit%20%2F%20integration-lightgrey)]()
[![Docker](https://img.shields.io/badge/docker-compose-blue)](https://docs.docker.com/compose/)
[![Kafka](https://img.shields.io/badge/kafka-event%20driven-black)](https://kafka.apache.org/)
[![OpenAPI](https://img.shields.io/badge/api-docs-yellow)](http://localhost:8081/swagger-ui/index.html)

---

## ✨ Sobre o Projeto

Este repositório contém o **microsserviço de Clientes**, parte do ecossistema de microsserviços do **EcommerceHM**.  
O objetivo deste sistema é fornecer:

- Cadastro de clientes  
- Cadastro e gerenciamento de endereços de clientes  
- Comunicação via REST com outros serviços do ecossistema  
- Emissão futura de eventos via **Kafka**

Este serviço foi projetado com foco em:

- Arquitetura limpa  
- Testes unitários e de integração  
- Alta escalabilidade  
- Extensibilidade para comunicação assíncrona  

---

## 🧰 Tecnologias Utilizadas

| Tecnologia      | Versão/Descrição                     |
|-----------------|--------------------------------------|
| **Java**        | 21                                   |
| **Spring Boot** | 3.x                                  |
| **PostgreSQL**  | Banco principal                      |
| **H2**          | Banco in-memory para testes          |
| **JUnit 5**     | Testes unitários                     |
| **MockMvc**     | Testes de integração                 |
| **Mockito**     | Mocks unitários                      |
| **Flyway**      | Migração de banco                    |
| **Docker**      | Provisionamento local                |
| **OpenAPI**     | Documentação da API (Swagger UI)     |

---

## 🚀 Como Executar o Projeto

### 📦 *Pré-requisitos*
- Java 21  
- Maven 3.9+  
- Docker + Docker Compose  

---

### ▶️ Subir o Banco com Docker

```bash
docker-compose up -d
```
O PostgreSQL ficará disponível em:
http://localhost:5555/client

### 🔧 Executar a Aplicação
```bash
./mvnw spring-boot:run
```
Ou:
```bash
mvn clean package
java -jar target/customer-0.0.1-SNAPSHOT.jar
```
A aplicação rodará em:
📍 http://localhost:8081
🔍 Documentação da API

Acesse a documentação completa gerada automaticamente:

📘 Swagger UI:
http://localhost:8081/swagger-ui/index.html

### 🧪 Testes Automatizados

O projeto contém testes:

✔️ Unitários (Mockito + JUnit 5)

✔️ Integração (MockMvc + H2)

Rodar todos os testes:
```bash
./mvnw test
```
Durante os testes:
- O banco H2 é utilizado
- O Flyway é desabilitado automaticamente
- As tabelas são regeneradas com ddl-auto=create-drop

## 🗄️ Estrutura de Domínio
### 👤 Cliente
Campos principais:
- name
- email
- phone
- document
Cada cliente pode ter 0 ou mais endereços.

### 📍 Endereço
Campos principais:
- publicPlace
- number
- zipcode
- city
- state
Relacionamento:
```bash
Client 1 --- * Address
```
### 🗂️ Estrutura de Pastas
```bash
src
├── main
│   ├── java
│   │   └── br.com.herison.ecommercehm.customer
│   │       ├── controller
│   │       ├── controller.documentation
│   │       ├── service
│   │       ├── repository
│   │       ├── mapper
│   │       ├── model
│   │       ├── dtos
│   │       ├── exception
│   │       └── config
│   └── resources
│       ├── application.yml
│       └── db/migration
├── test
│   ├── java
│   │   └── br.com.herison.ecommercehm.customer
│   └── resources
│       └── application-test.yml
```
### 🔄 Migração com Flyway
As tabelas do banco são criadas via scripts localizados em:
```bash
src/main/resources/db/migration/
```
O Flyway é automaticamente executado no perfil prod/dev
e desabilitado no perfil test.

## 📡 Comunicação com outros microsserviços
O ecossistema EcommerceHM será composto por diversos microsserviços:
- Customer
- Order
- Product
- Payment
- Inventory
- Notification
- Shipping
### 🔗 Comunicação:
REST → para interações síncronas:
- Cliente criado
- Endereço alterado
- Cliente desativado
- Etc.

### 🧠 Roadmap
- Adicionar autenticação com JWT
- Implementar testes E2E com Testcontainers
- Criar dashboards com Actuator + Prometheus + Grafana

## 👨‍💻 Autor
### Herison Maciel
📧 Email: herisson2maciel@hotmail.com
🐙 GitHub: Ecommerce-HM

### 📝 Licença
Este projeto é licenciado sob a Apache 2.0 License.
Mais detalhes em:
https://www.apache.org/licenses/LICENSE-2.0.html
