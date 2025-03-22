# LaReserva
Esse é o projeto da **Fase 3** da Especialização em Arquitetura e Desenvolvimento Java da FIAP.
Uma API REST para gestão de reservas e avaliações de restaurantes. A aplicação foi desenvolvida em **Java 17**, 
utilizando **Spring Boot**, **Maven**, um banco de dados **H2** para testes, **Mockito** e **JUnit 5** para testes 
unitários, **Lombok** para facilitar o desenvolvimento, **banco mysql** para produção e documentação gerada pelo **Swagger**.

## Descrição do Projeto
O objetivo desta API é permitir que restaurantes possam gerenciar reservas, registrar avaliações de clientes e armazenar 
informações sobre seus serviços e disponibilidade.

## Funcionalidades
A API permite:
- **Cadastrar** restaurantes e seus respectivos dados.
- **Cadastrar** usuários e seus respectivos dados.
- **Gerenciar reservas** permitindo que usuários agendem, alterem ou cancelem reservas em restaurantes cadastrados.
- **Registrar avaliações** dos usuários sobre os restaurantes.
- **Buscar restaurantes**, usuários,reservas e avaliações.
- **Excluir** reservas, restaurantes e usuários quando necessário.

## Tecnologias Utilizadas
- **Java 17**
- **Spring Boot**
- **Spring Data JPA**
- **Maven**
- **Banco de Dados H2**
- **Banco de Dados MySQL**
- **Mockito** e **JUnit 5**
- **Lombok**
- **Swagger**
- **Docker Compose**
- **Spotless**
- **Jacoco**

## Estrutura do Projeto
O projeto segue uma arquitetura modularizada, organizada nas seguintes camadas:
- `core`: Contém as regras de negócio do sistema.
- `domain`: Define as entidades principais do domínio.
- `dto`: Representa as entradas e saídas de dados para a API.
- `gateway`: Interfaces para interação com o banco de dados.
- `usecase`: Contém os casos de uso do sistema.
- `usecase.exception`: Exceções personalizadas para regras de negócio.
- `entrypoint.configuration`: Configurações do sistema, incluindo tratamento de exceções.
- `entrypoint.controller`: Controladores responsáveis por expor os endpoints da API.
- `infrastructure.gateway`: Implementações das interfaces de gateway.
- `infrastructure.persistence.entity`: Representação das entidades persistidas no banco de dados.
- `infrastructure.persistence.repository`: Interfaces dos repositórios Spring Data JPA.
- `presenter`: Representação dos dados de saída para a API.

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
Para acessar o banco de dados MySQL e visualizar os registros:
- **SPRING DATASOURCE URL**: jdbc:mysql://mysql:3306/dbname?useSSL=false&serverTimezone=UTC
- **SPRING DATASOURCE USERNAME**: user
- **SPRING DATASOURCE PASSWORD**: password
- **SPRING PROFILES ACTIVE**: mysql
- **MYSQL PORTS**: 3306
- **MYSQL DATABASE**: dbname
- **MYSQL ROOT PASSWORD**: rootpassword
- **MYSQL USER**: user
- **MYSQL PASSWORD**: password

Os endpoints estão documentados via **Swagger**:
- **Swagger UI**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- **Swagger JSON**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

### Possibilidades de Chamadas da API
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

4. **Busca de Restaurante Por CNPJ:**
```json
curl --location 'localhost:8080/lareserva/restaurant/12345678000199' \
--header 'Content-Type: application/json'
```

5. **Busca de Restaurantes:**
```json
curl --location 'localhost:8080/lareserva/restaurant/search?restaurantName=Sabor%20%26%20Arte&city=S%C3%A3o%20Paulo&typeOfFood=Italiana' \
--header 'Content-Type: application/json'
```

6. **Busca de Usuário Por CPF:**
```json
curl --location 'localhost:8080/lareserva/user/12345678900'
```

7. **Busca de Reserva por Id:**
```json
curl --location 'localhost:8080/lareserva/reservation/1'
```

8. **Busca de Avaliações por Restaurante_Id:**
```json
curl --location 'localhost:8080/lareserva/rating/restaurant/1'
```

9. **Busca de Avaliações por User_Id:**
```json
curl --location 'http://localhost:8080/lareserva/rating/user/5' 
```

10. **Atualização do Usuário por CPF:**
```json
curl --location --request PUT 'http://localhost:8080/lareserva/user/12345678900' \
--header 'Content-Type: application/json' \
--data-raw '{
"name": "Bruna Casagrande",
"cpf": "12345678900",
"phoneNumber": "11999999133",
"email": "nova@email.com",
"password": "NewPassword123!"
}
'
```

11. **Atualização do Restaurante por CNPJ:**
```json
curl --location --request PUT 'localhost:8080/lareserva/restaurant/12345678000199' \
--header 'Content-Type: application/json' \
--data '{
"address": "Rua das Castanhas, 123"
}' 
```

12. **Atualização dos Horários do Restaurante pelo Restaurant_Id:**
```json
curl --location --request PUT 'localhost:8080/lareserva/opening-hours/1' \
--header 'Content-Type: application/json' \
--data '[
{
"id": 1,
"dayOfWeek": "Monday",
"openTime": "12:00",
"closeTime": "21:00"
},
{
"id": 2,
"dayOfWeek": "Tuesday",
"openTime": "12:00",
"closeTime": "21:00"
}
]'
```

13. **Atualização da Reserva por Id:**
```json
curl --location --request PUT 'http://localhost:8080/lareserva/reservation/1' \
--header 'Content-Type: application/json' \
--data '{
"reservationDate": "2025-05-15",
"reservationTime": "19:30",
"numberOfPeople": 2
}'
```

14. **Exclusão de Reserva por Id:**
```json
curl --location --request DELETE 'localhost:8080/lareserva/reservation/1'
```

15. **Registro de Avaliação:**
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

16. **Exclusão de Usuário:**
```json
curl --location --request DELETE 'localhost:8080/lareserva/user/1'
```

17. **Exclusão do Restaurante por CNPJ:**
```json
curl --location --request DELETE 'localhost:8080/lareserva/restaurant/12345678000199' \
--header 'Content-Type: application/json'
```

## Testes
Para rodar os testes unitários:
```bash
mvn test
```
Lembrando que o docker precisa estar rodando para os testes funcionarem.

**Rodar o coverage:**
   ```bash
   mvn clean package
   ```
   Depois acessar pasta target/site/jacoco/index.html

O projeto inclui testes unitários, testes de integração e testes de arquitetura para garantir a qualidade e 
confiabilidade da API.

## Desenvolvedoras:
- **Bruna Casagrande Zaramela** - RM: 359536
- **Gabriela de Mesquita Ferraz** - RM: 358745
