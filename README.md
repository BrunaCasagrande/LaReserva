# Project-LaReserva
Este é um projeto da Fase 3 da Especialização em Arquitetura e Desenvolvimento Java da FIAP. 

## Descrição do Projeto
O objetivo desta API é 

## Funcionalidades
A API permite:
- **Criar**  
- **Buscar**  
- **Atualizar**  
- **Deletar** 

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
O projeto está organizado nas seguintes camadas:
- `domain`: Define as entidades principais do domínio.
- `dto`: Representa as entradas e saidas de dados para a API.
- `gateway`: Interfaces para interação com o banco de dados.
- `usecase`: Contém os casos de uso com a lógica de negócios.
- `usecase.exception`: Exceções customizadas utilizadas nos casos de uso.
- `entrypoint.config`: Configurações do Controller Exception Handler.
- `entrypoint.controller`: Controladores da API.
- `infrastructure.gateway`: Implementações das interfaces do gateway.
- `infrastructure.persistence.entity`: Representa as entidades de persistência do banco de dados.
- `infrastructure.persistence.repository`: Interfaces dos repositórios Spring Data JPA.
- `presenter`: Representa as saídas de dados para a API.

## Pré-requisitos
- Java 17
- Maven 3.6+
- IDE como IntelliJ IDEA ou Eclipse

## Configuração e Execução
1. **Clone o repositório**:
   ```bash
   url do repositório: https://github.com/BrunaCasagrande/LaReserva
   git clone git@github.com:BrunaCasagrande/LaReserva.git
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
Para visualização dos dados da api no banco de dados, acessar localmente o banco H2 através do endpoint:
- **Banco H2**: http://localhost:8080/h2-console
- **Driver Class**: org.h2.Driver
- **JDBC URL**: jdbc:h2:mem:LaReserva
- **User Name**: gb
- **Password**:

Os endpoints desenvolvidos podem ser acessados através do Swagger:
- **Swagger UI**: http://localhost:8080/swagger-ui/index.html
- **Swagger JSON**: http://localhost:8080/v3/api-docs

- Para o funcionamento correto da aplicação, existe uma ordem nas chamadas dos endpoints. Abaixo deixo a ordem com os curls das chamadas:
- Criação de um usuário:
```json

```

## Testes
Para executar os testes unitários:
```bash
   mvn test
```
O projeto inclui testes unitários para os principais casos de uso, utilizando Mockito
para mockar dependências e ArgumentCaptor para verificar os valores dos parâmetros nos
métodos chamados.

## Desenvolvedores:
- Bruna Casagrande Zaramela - RM: 359536
- Gabriela de Mesquita Ferraz - RM: 358745