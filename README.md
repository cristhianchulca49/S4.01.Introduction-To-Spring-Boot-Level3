
# LEVEL 3 — Layered Architecture Refactor
---

## 📚 Applied Principles

- SOLID
- Single Responsibility Principle
- Dependency Inversion Principle
- Layered Architecture
- Test Driven Development
- Dependency Injection
- Spring Beans
  
---

## 🎯 Objective
Refactor the application following a clean layered architecture and SOLID principles.

Clear separation of responsibilities:
- Controller
- Service
- Repository

---

## 📌 1️⃣ Integration Test (End-to-End)

Replace:
@WebMvcTest

With:
@SpringBootTest  
@AutoConfigureMockMvc

Objective:
Verify full integration between all layers.

---

## 📌 2️⃣ Repository Pattern

### UserRepository Interface

Defines:
- save
- findAll
- findById
- searchByName
- existsByEmail

### InMemoryUserRepository Implementation

- Uses internal list
- Annotated with @Repository

Benefits:
- Dependency Inversion Principle
- Easily replaceable with real database

Unit tests for repository required.

---

## 📌 3️⃣ Service Layer

### UserService Interface
Defines application use cases.

### UserServiceImpl Implementation
- Injects UserRepository
- Contains business logic
- No direct list access
- Annotated with @Service

Controller now delegates to service.

Benefits:
- Single Responsibility Principle
- Reusability
- Better testability

---

## 📌 4️⃣ Unit Testing with Mockito

Objective:
Test service logic in isolation.

Annotations:
@ExtendWith(MockitoExtension.class)  
@Mock  
@InjectMocks  

Key Use Case:
Unique email validation

Rule:
- If email exists → throw EmailAlreadyExistsException
- Do not call save()

TDD Approach:
1. Write failing test
2. Implement logic
3. Make test pass

---

## 🛠 Technologies Used

- Java 17 / 21
- Spring Boot
- Spring Web
- Spring Boot Test
- JUnit 5
- Mockito
- MockMvc
- Jackson
- Maven
- Git
- IntelliJ IDEA

---

## 🛠️ Installation

Clone this repository:
```git
git clone https://github.com/cristhianchulca49/S4.01.Introduction-To-Spring-Boot-Level3.git
```
---

## 🤝 Contributions are welcome! 
Please follow these steps to contribute:
  
- Fork the repository Create a new branch: git checkout -b feature/NewFeature 
- Make your changes and commit them: git commit -m 'Add New Feature' 
- Push the changes to your branch: git push origin feature/NewFeature 
- Open a Pull Request

