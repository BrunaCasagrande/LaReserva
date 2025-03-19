# LaReserva

Este é um projeto da **Fase 3** da Especialização em Arquitetura e Desenvolvimento Java da FIAP.
A API **LaReserva** é um sistema para gestão de reservas e avaliações de restaurantes. A aplicação foi desenvolvida em **Java 17**, utilizando **Spring Boot**, **Maven**, um banco de dados **H2** para testes e documentação gerada pelo **Swagger**.

## Descrição do Projeto

O objetivo da API **LaReserva** é permitir que restaurantes possam gerenciar reservas, registrar avaliações de clientes e armazenar informações sobre seus serviços e disponibilidade.

## Funcionalidades

A API permite:

- **Cadastrar** restaurantes e seus respectivos dados.
- **Cadastrar** usuários e seus respectivos dados.
- **Gerenciar reservas** permitindo que usuários agendem, alterem ou cancelem reservas em restaurantes cadastrados.
- **Registrar avaliações** dos usuários sobre os restaurantes.
- **Buscar restaurantes**, reservas e avaliações.
- **Excluir** reservas quando necessário.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot**
- **Spring Data JPA**
- **Maven**
- **Banco de Dados H2**
- **Mockito** e **JUnit 5**
- **Lombok**
- **Swagger**

## Estrutura do Projeto

O projeto segue uma arquitetura modularizada, organizada nas seguintes camadas:

- `core`: Contém as regras de negócio do sistema.
- `domain`: Define as entidades principais do domínio.
- `dto`: Representa as entradas e saídas de dados para a API.
- `gateway`: Interfaces para interação com o banco de dados.
- `usecase`: Contém os casos de uso do sistema.
- `usecase.exception`: Exceções personalizadas para regras de negócio.
- `infrastructure.configuration`: Configurações do sistema, incluindo tratamento de exceções.
- `infrastructure.controller`: Controladores responsáveis por expor os endpoints da API.
- `infrastructure.gateway`: Implementações das interfaces de gateway.
- `infrastructure.persistence.entity`: Representação das entidades persistidas no banco de dados.
- `infrastructure.persistence.repository`: Interfaces dos repositórios Spring Data JPA.

## Pré-requisitos

- Java 17
- Maven 3.6+
- IDE como IntelliJ IDEA ou Eclipse

## Configuração e Execução

1. **Clone o repositório**:
   ```bash
   git clone https://github.com/BrunaCasagrande/LaReserva.git
   ```
2. **Instale as dependências:**
   ```bash
   mvn clean install
   ```
3. **Execute o projeto:**
   ```bash
   mvn spring-boot:run
   ```

## Uso da API

Para acessar o banco de dados H2 e visualizar os registros:

- **Banco H2**: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- **Driver Class**: org.h2.Driver
- **JDBC URL**: jdbc\:h2\:mem\:LaReserva
- **User Name**: sa
- **Password**: (vazio)

Os endpoints estão documentados via **Swagger**:

- **Swagger UI**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- **Swagger JSON**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

### Ordem de chamadas da API

Para um uso correto da API, as chamadas devem seguir esta ordem:

1. **Cadastro de Usuário:**
```json
curl --location 'localhost:8080/lareserva/user' \
--header 'Content-Type: application/json' \
--data '{
  "name": "Bruna Casagrande",
  "cpf": "12345678900",
  "phoneNumber": "11999999999",
  "email": "bruna@email.com"
}'
```

2. **Cadastro de Restaurante:**
```json
curl --location 'localhost:8080/lareserva/restaurant' \
--header 'Content-Type: application/json' \
--data '{
  "restaurantName": "Mamma Ristorante",
  "cnpj": "12.345.678/0001-99",
  "address": "Avenida Itália, 123",
  "phoneNumber": "11988887777",
  "typeOfFood": "Italiana"
}'
```

3. **Cadastro de Reserva:**
```json
curl --location 'localhost:8080/lareserva/reservation' \
--header 'Content-Type: application/json' \
--data '{
  "restaurantId": 1,
  "userId": 5,
  "reservationDate": "2025-03-20T19:00:00"
}'
```

4. **Registro de Avaliação:**
```json
curl --location 'localhost:8080/lareserva/rating' \
--header 'Content-Type: application/json' \
--data '{
  "userId": 1,
  "restaurantId": 1,
  "stars": 5,
  "comment": "Ótima comida e atendimento!"
}'
```

5. **Busca de Restaurante:**
```json
curl --location 'localhost:8080/lareserva/restaurant/1'
```

6. **Busca de Reservas do Usuário:**
```json
curl --location 'localhost:8080/lareserva/reservation/user/1'
```

7. **Busca de Avaliações do Restaurante:**
```json
curl --location 'localhost:8080/lareserva/rating/restaurant/1'
```

8. **Exclusão de Reserva:**
```json
curl --location --request DELETE 'localhost:8080/lareserva/reservation/1'
```

9. **Exclusão de Usuário:**
```json
curl --location --request DELETE 'localhost:8080/lareserva/user/1'
```

## Testes

Para rodar os testes unitários:

```bash
mvn test
```

O projeto inclui testes unitários para os principais casos de uso, utilizando **Mockito** para mockar dependências e **ArgumentCaptor** para validar os parâmetros passados aos métodos.

## Desenvolvedoras:

- **Bruna Casagrande Zaramela** - RM: 359536
- **Gabriela de Mesquita Ferraz** - RM: 358745

