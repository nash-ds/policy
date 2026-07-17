# Policy Proposal Processing API

A Spring Boot REST API that simulates an insurance policy proposal processing system.

The application allows customers to be created, policy proposals to be submitted, and audit records to be generated. All data is stored in-memory using Java Collections without any database.

---

## Technology Stack

- Java 21
- Spring Boot 4.x
- Maven
- Lombok
- Spring Validation
- Swagger / OpenAPI
- JUnit 5

---

## Features

### Customer Management

- Create Customer
- Update Customer
- Get Customer by ID
- Get All Customers

### Reference Master

Provides static master data for:

- Policy Terms
- Payment Frequencies

### Policy Proposal

- Create Proposal
- Validate Proposal
- Submit Proposal
- Generate Policy Number
- Calculate Maturity Date

### Audit

- Log proposal submission
- Retrieve audit history

---

## Business Rules

### Customer

- Age must be between 18 and 65 years.
- Phone number must be unique.
- Email must be unique.
- PAN must be unique if provided.

---

### Policy

- Customer must exist.
- Policy Term must be one of:
    - 10
    - 15
    - 20
    - 25
    - 30 years
- Payment Frequency must exist in Reference Master.
- Minimum Premium: ₹5,000.
- Sum Assured:
    - Minimum ₹1,00,000
    - Maximum ₹5,00,00,000
- PAN is mandatory if Annual Premium exceeds ₹50,000.
- Nominee is mandatory.
- Nominee cannot be the same as the customer.

---

## Project Structure

```
src
 ├── controller
 ├── service
 │     ├── interfaces
 │     └── implementations
 ├── model
 ├── exception
 └── test
```

---

## API Endpoints

### Reference Master

| Method | Endpoint |
|---------|----------|
| GET | /reference-master/{category} |

---

### Customer

| Method | Endpoint |
|---------|----------|
| POST | /customers |
| GET | /customers |
| GET | /customers/{id} |
| PUT | /customers/{id} |

---

### Proposal

| Method | Endpoint |
|---------|----------|
| POST | /proposals |
| GET | /proposals/{id} |
| POST | /proposals/{id}/submit |

---

### Audit

| Method | Endpoint |
|---------|----------|
| GET | /audits |

---

## Running the Project

Clone the repository

```
git clone <repository-url>
```

Navigate into the project

```
cd policy
```

Run

```
mvn spring-boot:run
```

Application starts at

```
http://localhost:8080
```

---

## Swagger

Open

```
http://localhost:8080/swagger-ui/index.html
```

---

## Running Tests

```
mvn test
```

---

## Sample Flow

1. Fetch Reference Master
2. Create Customer
3. Create Policy Proposal
4. Submit Proposal
5. View Audit Logs

---

## Sample Customer

```json
{
  "name":"Nash",
  "age":22,
  "phno":"9876543210",
  "email":"nash@gmail.com",
  "pan":"ABCDE1234F",
  "address":"Mumbai",
  "gender":"Male"
}
```

---

## Sample Proposal

```json
{
  "customerId":"<customer-id>",
  "policyName":"Pension",
  "policyTerm":20,
  "policyFreq":"Quarterly",
  "policyPremium":65000,
  "sum":1000000,
  "nominee":{
      "name":"John",
      "relation":"Brother",
      "age":30
  }
}
```

---

## Testing

Unit tests cover

- Customer validations
- Proposal validations
- Proposal submission flow
- Audit logging

An integration test verifies the complete application workflow.

---

## Notes

- No database is used.
- Data is maintained using Java Collections.
- IDs are generated using UUID.
- Policy Numbers are generated during proposal submission.
- Audit entries are automatically created after successful submission.