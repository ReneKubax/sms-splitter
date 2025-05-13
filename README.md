# SMS Splitter API
A full-stack application that splits long SMS messages into 160-character parts.

## 📋 Overview
This project is a comprehensive SMS splitting solution featuring a React TypeScript frontend and Java Spring Boot backend. It automatically splits long text messages into multiple SMS parts while respecting the 160-character limit and adding appropriate suffixes.

## ✨ Features
- **SMS Splitting** - Automatically splits messages over 160 characters
- **Smart Word Boundaries** - Avoids splitting words in the middle
- **Part Numbering** - Adds "Part X of Y" suffix to multi-part messages
- **Character Counter** - Real-time character count display
- **API Health Check** - Monitor backend connection status
- **Error Handling** - Comprehensive error messages and validation
- **Responsive Design** - Works on desktop and mobile devices

## 🖼️ Screenshots
### SMS Form
![image](https://github.com/user-attachments/assets/678caede-a1f1-4596-b6ef-3766625aff25)

![image](https://github.com/user-attachments/assets/7b033245-e5c2-45b8-a10c-8e9c1fbe7e53)



## 🛠️ Technology Stack
### Frontend
- React 18
- TypeScript
- Vite
- Material-UI
- Axios for API calls

### Backend
- Java 17+
- Spring Boot 3.2
- Maven
- Jakarta Validation
- Lombok

## 🚀 Getting Started

### Prerequisites
- Node.js (v16.x or higher)
- Java JDK 17+
- Maven 3.6+

### Backend Setup
```bash
# Navigate to backend directory
cd backend

# Run the application
./mvnw spring-boot:run

# The API will be available at http://localhost:8080
```

### Frontend Setup
```bash
# Navigate to frontend directory
cd frontend

# Install dependencies
npm install

# Start development server
npm run dev

# The app will be available at http://localhost:3000
```

### Running Tests
```bash
# Backend tests
cd backend
./mvnw test

# Frontend tests (if implemented)
cd frontend
npm test
```

## 📝 API Documentation

### Endpoints

#### Health Check
```
GET /api/v1/sms/health
```

Response:
```json
{
  "status": "UP",
  "service": "SMS Splitter API",
  "version": "1.0.0"
}
```

#### Send SMS
```
POST /api/v1/sms/send
Content-Type: application/json

{
  "message": "Your long message here",
  "phoneNumber": "1234567890" (optional)
}
```

Response:
```json
{
  "originalMessage": "Your long message here",
  "totalParts": 2,
  "parts": [
    "Your long message... - Part 1 of 2",
    "...continuation - Part 2 of 2"
  ],
  "totalCharacters": 250
}
```

## 💭 Design & Analytical Questions

### 1. Architecture Decisions
**What were your main considerations when structuring your code?**

I followed a layered architecture pattern with clear separation of concerns:
- **Controller Layer**: Handles HTTP requests and responses
- **Service Layer**: Contains business logic for SMS splitting
- **DTO Layer**: Data transfer objects for API contracts
- **Exception Layer**: Centralized error handling

Key considerations:
- Modularity for easy testing and maintenance
- Single Responsibility Principle
- Dependency injection for loose coupling
- Clear package structure for scalability

### 2. API Design
**How did you decide what the API should look like? Did you consider versioning or error formats?**

API design decisions:
- **RESTful principles**: POST for operations, GET for queries
- **Versioning**: Implemented URL versioning (`/api/v1/`) for future compatibility
- **Consistent error format**: Standardized JSON error responses with status codes, messages, and timestamps
- **Validation**: Input validation using Jakarta Bean Validation
- **Clear response structure**: Intuitive response objects with all necessary information

### 3. State Management (Frontend)
**How is state handled on the frontend? Why did you choose that approach?**

State management approach:
- **Local component state**: Used React hooks (useState) for form inputs and results
- **Prop drilling**: Simple parent-to-child communication
- **No global state**: Project scope didn't require Redux/Context API

Reasoning:
- Simple application with limited state
- Direct parent-child relationships
- Easier to understand and maintain
- Avoids over-engineering

### 4. Security (Not Implemented)
**How would you handle token storage and validation?**

If implemented, security would include:
- **JWT tokens**: For stateless authentication
- **Secure storage**: HttpOnly cookies or secure localStorage
- **Token refresh**: Automatic token renewal
- **CORS configuration**: Restrictive origin policies
- **Input sanitization**: Prevent XSS attacks
- **Rate limiting**: Prevent API abuse
- **HTTPS**: Encrypted communication

### 5. Scalability & Maintainability
**If this project grew to support teams and thousands of users, what changes would you make?**

Scalability improvements:
- **Message Queue**: Async processing with RabbitMQ/Kafka
- **Database**: PostgreSQL for message history and analytics
- **Caching**: Redis for frequently accessed data
- **Microservices**: Separate SMS, User, and Analytics services
- **Load Balancing**: Multiple backend instances
- **API Gateway**: For routing and rate limiting
- **Monitoring**: Prometheus/Grafana for metrics
- **CI/CD**: Automated testing and deployment
- **Documentation**: OpenAPI/Swagger specifications
- **Logging**: Centralized logging with ELK stack

### 6. Time Constraints
**What features did you intentionally skip due to time constraints?**

Features skipped:
- User authentication and authorization
- Database persistence
- Comprehensive integration tests
- Frontend unit tests
- API documentation (Swagger)
- Internationalization (i18n)
- SMS provider integration
- Message templates
- Batch processing
- Admin dashboard
- Performance optimizations
- Docker containerization

## 📁 Project Structure
```
sms-splitter-test/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   └── resources/
│   │   └── test/
│   ├── pom.xml
│   └── README.md
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   ├── services/
│   │   ├── types/
│   │   └── App.tsx
│   ├── package.json
│   └── README.md
└── README.md
```

⏱️ Time Estimation & Task Breakdown

Total Estimated Time: 4-6 hours
<details>
<summary>Click to see detailed task breakdown</summary>

  
1. Analysis & Design (30-45 min)

 Read and understand requirements - 10 min
 Design solution architecture - 15 min
 Define project structure - 10 min
 Plan edge cases and splitting algorithm - 10 min

2. Initial Setup (15-20 min)

 Create Spring Boot project with Spring Initializr - 5 min
 Configure folder structure - 5 min
 Configure pom.xml with dependencies - 5 min
 Initial Git setup - 5 min

3. Backend Development (1.5-2 hours)
  DTOs and Models (15 min)

 Create SmsRequest DTO - 5 min
 Create SmsResponse DTO - 5 min
 Configure validations - 5 min

Service Layer (45-60 min)

 Implement basic splitting algorithm - 20 min
 Handle edge cases (word boundaries) - 15 min
 Optimize algorithm for dynamic suffixes - 15 min
 Add logging and console output - 5 min

Controller Layer (20 min)

 Create SmsController - 10 min
 Implement endpoints (send, health) - 10 min

Exception Handling (15 min)

 Create custom exceptions - 5 min
 Implement GlobalExceptionHandler - 10 min

Backend Testing (30-40 min)

   Unit tests for SmsService - 20 min
   Integration tests for Controller - 15 min
   Test edge cases - 5 min

4. Frontend Development (1.5-2 hours)
React + TypeScript Setup (20 min)

   Create project with Vite - 5 min
   Install dependencies (MUI, Axios) - 5 min
   Configure TypeScript - 5 min
   Folder structure - 5 min

UI Components (40-50 min)

   Create TypeScript types - 10 min
   Implement API service - 10 min
   Develop SmsForm component - 15 min
   Develop SmsResults component - 15 min

Integration & Styling (30 min)

   Integrate components in App.tsx - 10 min
   Implement health check - 10 min
   Style with Material-UI - 10 min

5. Documentation (30-45 min)

   Main README with structure - 15 min
   Answer design questions - 20 min
   Document API endpoints - 5 min
   Setup instructions - 5 min

6. Testing & Refinement (30 min)

   Manual end-to-end testing - 15 min
   Fix bugs found - 10 min
   Clean and format code - 5 min

7. Git & Deployment (15 min)

   Organize logical commits - 5 min
   Create GitHub repository - 5 min
   Final push and verification - 5 min

## 📄 License
This project was created as part of a technical assessment.
