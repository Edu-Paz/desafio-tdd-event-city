# Desafio TDD Event City - DevSuperior

Este projeto foi desenvolvido como parte do desafio TDD (Test-Driven Development) do curso DevSuperior. O objetivo era implementar uma API REST para gerenciamento de Cidades e Eventos, utilizando a abordagem TDD onde os testes de integração já foram fornecidos e a implementação deveria fazer com que todos os testes passassem.

## 📋 Descrição do Desafio

O desafio consistia em implementar uma aplicação Spring Boot com as seguintes características:

- **Entidades fornecidas**: `City` e `Event` com relacionamento One-to-Many
- **DTOs fornecidos**: `CityDTO` e `EventDTO`
- **Testes de integração fornecidos**: Testes para `CityController` e `EventController`
- **Implementação necessária**: Repositories, Services, Controllers e Exception Handlers

## 🏗️ Tecnologias Utilizadas

- Java 25
- Spring Boot 4.0.6
- Spring Data JPA
- H2 Database (banco em memória para testes)
- Maven

## 📁 Estrutura do Projeto

```
src/main/java/com/devsuperior/bds02/
├── controllers/
│   ├── CityController.java           # Controller para endpoints de cidades
│   ├── EventController.java          # Controller para endpoints de eventos
│   └── exceptions/
│       ├── ControllerExceptionHandler.java  # Tratamento global de exceções
│       └── StandardError.java              # DTO padrão para erros
├── dto/
│   ├── CityDTO.java                  # DTO de Cidade (fornecido)
│   └── EventDTO.java                 # DTO de Evento (fornecido)
├── entities/
│   ├── City.java                     # Entidade Cidade (fornecida)
│   └── Event.java                    # Entidade Evento (fornecida)
├── repositories/
│   ├── CityRepository.java           # Repository JPA de Cidade
│   └── EventRepository.java          # Repository JPA de Evento
└── services/
    ├── CityService.java              # Service de Cidade
    ├── EventService.java             # Service de Evento
    └── exceptions/
        ├── DatabaseException.java    # Exceção para erros de banco
        └── ResourceNotFoundException.java  # Exceção para recurso não encontrado
```

## ✨ Funcionalidades Implementadas

### CityController
- **GET /cities** - Lista todas as cidades ordenadas por nome
- **POST /cities** - Insere uma nova cidade
- **DELETE /cities/{id}** - Deleta uma cidade por ID
  - Retorna 404 se a cidade não existir
  - Retorna 400 se a cidade estiver associada a eventos (integridade referencial)
  - Retorna 204 se deletada com sucesso

### EventController
- **PUT /events/{id}** - Atualiza um evento existente
  - Retorna 200 se atualizado com sucesso
  - Retorna 404 se o evento não existir
  - Retorna 404 se a cidade informada não existir

## 🔧 Implementações Realizadas

### 1. Repositories
- `CityRepository`: Interface JPA Repository com métodos básicos CRUD
- `EventRepository`: Interface JPA Repository com método customizado `existsByCityId()` para verificar integridade referencial

### 2. Services
- **CityService**:
  - `findAll()`: Busca todas as cidades ordenadas por nome
  - `insert()`: Insere nova cidade
  - `delete()`: Deleta cidade com verificação de integridade referencial
  
- **EventService**:
  - `update()`: Atualiza evento com validação de existência de evento e cidade
  - `copyDtoToEntity()`: Método auxiliar para copiar dados do DTO para entidade

### 3. Controllers
- **CityController**: Endpoints REST para operações CRUD de cidades
- **EventController**: Endpoint REST para atualização de eventos

### 4. Exception Handling
- **ControllerExceptionHandler**: Tratamento global de exceções
  - `ResourceNotFoundException` → HTTP 404
  - `DatabaseException` → HTTP 400

### 5. Exceções Customizadas
- **ResourceNotFoundException**: Lançada quando recurso não é encontrado
- **DatabaseException**: Lançada para violações de integridade referencial

## 🧪 Testes de Integração

Os testes fornecidos cobrem:

### CityControllerIT
- `findAllShouldReturnAllResourcesSortedByName`: Verifica listagem ordenada
- `insertShouldInsertResource`: Verifica inserção de nova cidade
- `deleteShouldReturnNoContentWhenIndependentId`: Verifica deleção de cidade sem eventos
- `deleteShouldReturnNotFoundWhenNonExistingId`: Verifica deleção de ID inexistente
- `deleteShouldReturnBadRequestWhenDependentId`: Verifica deleção de cidade com eventos

### EventControllerIT
- `updateShouldUpdateResourceWhenIdExists`: Verifica atualização de evento existente
- `updateShouldReturnNotFoundWhenIdDoesNotExist`: Verifica atualização de ID inexistente

## 🚀 Como Executar

### Pré-requisitos
- Java25 ou superior
- Maven 3.6+

### Executar os testes
```bash
./mvnw test
```

### Executar a aplicação
```bash
./mvnw spring-boot:run
```

## 📝 Pontos Importantes da Implementação

1. **Integridade Referencial**: Antes de deletar uma cidade, verificamos se existem eventos associados usando o método `existsByCityId()` do `EventRepository`.

2. **Transações**: Métodos de escrita nos services são anotados com `@Transactional` para garantir consistência.

3. **Validação**: O service de eventos valida tanto a existência do evento quanto da cidade antes de atualizar.

4. **Tratamento de Exceções**: Exceções são tratadas globalmente pelo `ControllerExceptionHandler`, retornando res HTTP apropriados.

5. **Spring Data JPA**: Utilização de métodos derivados (query methods) para consultas simples, mantendo o código limpo.

## 🎯 Aprendizados

Este projeto demonstra a abordagem TDD onde:
- Os testes guiam a implementação
- O código é desenvolvido para fazer os testes passarem
- A refatoração é constante para manter código limpo
- Testes de integração garantem que o sistema funciona como um todo
