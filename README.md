# LaReserva
Esse é o projeto da **Fase 3** da Especialização em Arquitetura e Desenvolvimento Java da FIAP.
Uma API REST para gestão de reservas e avaliações de restaurantes. A aplicação foi desenvolvida em **Java 17**, 
utilizando **Spring Boot**, **Maven**, um banco de dados **H2** para testes, **Mockito** e **JUnit 5** para testes 
unitários, **Lombok** para facilitar o desenvolvimento, **banco postgres** para produção e documentação gerada pelo **Swagger**.

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
- **Banco de Dados Postgres**
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
Para acessar o banco de dados Postgres e visualizar os registros:
- **SPRING DATASOURCE URL**: jdbc:postgresql://dpg-cvfi46lds78s73fm3vug-a:5432/lareserva
- **SPRING DATASOURCE USERNAME**: lareserva_user
- **SPRING DATASOURCE PASSWORD**: 1VvvT0Ub9efOd2Ml99SmDPihIT1dn6OB
- **POSTGRES PORTS**: 5432
- **POSTGRES DATABASE**: lareserva
- **POSTGRES USER**: lareserva_user
- **POSTGRES PASSWORD**: 1VvvT0Ub9efOd2Ml99SmDPihIT1dn6OB

Os endpoints estão documentados via **Swagger**:
- **Swagger UI**: https://lareserva-9.onrender.com/swagger-ui/index.html
- **Swagger JSON**: https://lareserva-9.onrender.com/v3/api-docs

### Possibilidades de Chamadas da API
1. **Cadastro de Usuário:**
```json
curl --location 'https://lareserva-9.onrender.com/lareserva/user' \
--header 'Content-Type: application/json' \
--data-raw '{
"name": "Gabis",
"cpf": "12312312312",
"phoneNumber": "01234567890",
"email": "gabis@gmail.com",
"password": "123aS!12"
}'
```

2. **Cadastro de Restaurante:**
```json
curl --location 'https://lareserva-9.onrender.com/lareserva/restaurant' \
--header 'Content-Type: application/json' \
--data-raw '{
"restaurantName": "Sabor & Arte",
"cnpj": "12345678000199",
"address": "Rua das Flores, 123",
"city": "São Paulo",
"phoneNumber": "+5511998765432",
"typeOfFood": "Italiana",
"capacity": 50,
"numberOfTables": 20,
"openingHour": [
{
"dayOfWeek": "Monday",
"openTime": "18:00",
"closeTime": "22:00"
},
{
"dayOfWeek": "Tuesday",
"openTime": "11:00",
"closeTime": "22:00"
},
{
"dayOfWeek": "Wednesday",
"openTime": "11:00",
"closeTime": "22:00"
},
{
"dayOfWeek": "Thursday",
"openTime": "11:00",
"closeTime": "22:00"
},
{
"dayOfWeek": "Friday",
"openTime": "11:00",
"closeTime": "23:00"
},
{
"dayOfWeek": "Saturday",
"openTime": "11:00",
"closeTime": "23:00"
},
{
"dayOfWeek": "Sunday",
"openTime": "11:00",
"closeTime": "15:00"
}
],
"email": "contato@saborearte.com",
"password": "Senha@123"
}'
```

3. **Cadastro de Reserva:**
```json
curl --location 'https://lareserva-9.onrender.com/lareserva/reservation' \
--header 'Content-Type: application/json' \
--data '{
"reservationDate": "2025-05-15",
"reservationTime": "19:30",
"numberOfPeople": 2,
"restaurant": {
"id": 3
},
"user": {
"id": 2
}
}'
```

4. **Busca de Restaurante Por CNPJ:**
```json
curl --location 'https://lareserva-9.onrender.com/lareserva/restaurant/12345678000199' \
--header 'Content-Type: application/json'
```

5. **Busca de Restaurantes:**
```json
curl --location 'https://lareserva-9.onrender.com/lareserva/restaurant/search?restaurantName=Sabor%20%26%20Arte&city=S%C3%A3o%20Paulo&typeOfFood=Italiana' \
--header 'Content-Type: application/json'
```

6. **Busca de Usuário Por CPF:**
```json
curl --location 'https://lareserva-9.onrender.com/lareserva/user/12312312312'
```

7. **Busca de Reserva por Id:**
```json
curl --location 'https://lareserva-9.onrender.com/lareserva/reservation/1'
```

8. **Busca de Avaliações por Restaurante_Id:**
```json
curl --location 'https://lareserva-9.onrender.com/lareserva/rating/restaurant/3'
```

9. **Busca de Avaliações por User_Id:**
```json
curl --location 'https://lareserva-9.onrender.com/lareserva/rating/user/2'
```

10. **Atualização do Usuário por CPF:**
```json
curl --location --request PUT 'https://lareserva-9.onrender.com/lareserva/user/12312312312' \
--header 'Content-Type: application/json' \
--data-raw '{
"name": "Bruna Casagrande",
"phoneNumber": "11999999133",
"email": "nova@email.com",
"password": "NewPassword123!"
}'
```

11. **Atualização do Restaurante por CNPJ:**
```json
curl --location --request PUT 'https://lareserva-9.onrender.com/lareserva/restaurant/12345678000199' \
--header 'Content-Type: application/json' \
--data '{
"address": "Rua das Castanhas, 123"
}'
```

12. **Atualização dos Horários do Restaurante pelo Restaurant_Id:**
```json
curl --location --request PUT 'https://lareserva-9.onrender.com/lareserva/opening-hours/3' \
--header 'Content-Type: application/json' \
--data '[
{
"id": 34,
"dayOfWeek": "Monday",
"openTime": "18:00",
"closeTime": "22:00"
},
{
"id": 35,
"dayOfWeek": "Tuesday",
"openTime": "11:00",
"closeTime": "22:00"
},
{
"id": 36,
"dayOfWeek": "Wednesday",
"openTime": "11:00",
"closeTime": "22:00"
},
{
"id": 37,
"dayOfWeek": "Thursday",
"openTime": "11:00",
"closeTime": "22:00"
},
{
"id": 38,
"dayOfWeek": "Friday",
"openTime": "11:00",
"closeTime": "23:00"
},
{
"id": 39,
"dayOfWeek": "Saturday",
"openTime": "11:00",
"closeTime": "23:00"
},
{
"id": 40,
"dayOfWeek": "Sunday",
"openTime": "11:00",
"closeTime": "15:00"
}
]'
```

13. **Atualização da Reserva por Id:**
```json
curl --location --request PUT 'https://lareserva-9.onrender.com/lareserva/reservation/1' \
--header 'Content-Type: application/json' \
--data '{
"reservationDate": "2025-05-15",
"reservationTime": "20:30",
"numberOfPeople": 2
}'
```

14. **Exclusão de Reserva por Id:**
```json
curl --location --request DELETE 'https://lareserva-9.onrender.com/lareserva/reservation/1'
```

15. **Cadastrar uma Avaliação:**
```json
curl --location 'https://lareserva-9.onrender.com/lareserva/rating' \
--header 'Content-Type: application/json' \
--data '{
"stars": 5,
"comment": "Excellent food and service!",
"date": "2025-05-15T00:00:00.000+00:00",
"restaurant": {
"id": 3
},
"user": {
"id": 2
}
}'
```

16. **Exclusão de Usuário por CPF:**
```json
curl --location --request DELETE 'https://lareserva-9.onrender.com/lareserva/user/12312312312'
```

17. **Exclusão do Restaurante por CNPJ:**
```json
curl --location --request DELETE 'https://lareserva-9.onrender.com/lareserva/restaurant/12345678000199' \
--header 'Content-Type: application/json'
```

## Testes
Para rodar os testes unitários:
```bash
mvn test
```
Lembrando que o docker precisa estar rodando para os testes funcionarem: docker-compose up -d

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
