# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**synder-algo7** is a multi-platform e-commerce shopping hub that aggregates data from WooCommerce, Square, and Shopify APIs to provide centralized order/inventory management, refund analysis with AI-powered recommendations, restock prediction, and invoice management.

**Tech Stack:**
- **Backend**: Spring Boot 3.5.7 (Java 21, Maven)
- **Frontend**: React 19 + TypeScript 5 + Vite 7
- **Database**: PostgreSQL 16
- **AI Integration**: Anthropic Claude API (planned)

## Development Commands

### Database Setup (Required First)
```bash
docker run --name synder-algo7-db \
  -e POSTGRES_USER=synder \
  -e POSTGRES_PASSWORD=synder_password \
  -e POSTGRES_DB=synder_algo7 \
  -p 5432:5432 \
  -v postgres_data:/var/lib/postgresql/data \
  -d postgres:16-alpine
```

### Backend (Spring Boot)
```bash
cd backend
mvn spring-boot:run           # Run backend (port 8080)
mvn clean install             # Build
mvn test                      # Run tests
mvn spring-boot:run -Dspring-boot.run.profiles=prod  # Run with prod profile
```

### Frontend (React + Vite)
```bash
cd frontend
npm install                   # Install dependencies
npm run dev                   # Run dev server (port 5173)
npm run build                 # Build for production
npm run lint                  # Run ESLint
npm run preview               # Preview production build
```

## Architecture Overview

### Development Model (Separate Servers)
- **Backend** runs on `http://localhost:8080` as pure REST API (no view rendering)
- **Frontend** runs on `http://localhost:5173` (Vite dev server)
- Frontend makes API calls to backend at `/api/v1/*`
- Backend and frontend developed independently

### Production Deployment
1. Build frontend: `cd frontend && npm run build`
2. Copy `frontend/dist/*` → `backend/src/main/resources/static/`
3. Spring Boot serves everything from single JAR:
   - `/api/**` → REST API endpoints
   - `/*` → Static files (React SPA)

### Backend Architecture (Layered)
**Pattern**: Controller → Service → Repository → Entity

```
backend/src/main/java/algo/backend/
├── BackendApplication.java          # Main Spring Boot application
├── controller/                       # @RestController - REST endpoints
├── service/                          # Business logic layer
├── repository/                       # Spring Data JPA repositories
├── entity/                           # JPA entities (@Entity)
├── dto/                              # Data Transfer Objects
├── config/                           # Spring configuration classes
└── exception/                        # Custom exceptions & @RestControllerAdvice
```

**Key Patterns:**
- Use `@RestController` (not `@Controller`) for all REST endpoints
- Never expose JPA entities directly - always use DTOs
- Controllers should be thin - business logic belongs in services
- Use `@RequiredArgsConstructor` + `@Slf4j` from Lombok
- All endpoints prefixed with `/api/v1/`

### Frontend Architecture (Feature-Driven / Bulletproof React)
**Pattern**: Feature-based organization (not file-type based)

```
frontend/src/
├── app/                              # Application layer
│   ├── routes/                       # Route components (dashboard.tsx, orders.tsx, etc.)
│   ├── provider.tsx                  # Global providers wrapper
│   └── router.tsx                    # Router configuration
├── components/                       # Shared components (used across features)
│   ├── ui/                           # shadcn/ui base components
│   ├── layouts/                      # Layout components
│   ├── common/                       # Common reusable components
│   └── charts/                       # Shared chart components
├── features/                         # Feature modules (CORE OF APPLICATION)
│   ├── auth/
│   ├── orders/
│   ├── refunds/
│   ├── inventory/
│   └── invoices/
├── lib/                              # Third-party library config
├── hooks/                            # Global custom hooks
├── stores/                           # Global state management
├── types/                            # Global TypeScript types
└── utils/                            # Shared utility functions
```

**Each feature module structure:**
```
features/[feature-name]/
├── api/                              # API calls & React Query hooks
├── components/                       # Feature-specific components
├── hooks/                            # Feature-specific hooks
├── stores/                           # Feature-specific state (Zustand)
├── types/                            # Feature-specific TypeScript types
├── utils/                            # Feature-specific utilities
└── index.ts                          # Public API (only exports for external use)
```

## Critical Architectural Principles

### Backend (Spring Boot)
1. **REST API only** - No view templates, no server-side rendering, pure JSON API
2. **Use @RestController** - Always return JSON via ResponseEntity
3. **DTOs for all responses** - Never expose JPA entities through endpoints
4. **Layered architecture** - Keep controllers thin, business logic in services
5. **Lombok annotations** - Use `@Data`, `@Builder`, `@Slf4j` to reduce boilerplate
6. **Validation** - Use `@Valid` and Bean Validation annotations on DTOs
7. **Global exception handling** - Use @RestControllerAdvice for consistent error responses
8. **Database config** - Connection details in `application.properties`, currently set to `spring.jpa.hibernate.ddl-auto=create-drop` (recreates schema on restart)

### Frontend (React + TypeScript)
1. **Feature-driven organization** - Organize by feature, not file type
2. **Strict feature boundaries** - Features cannot import from other features' internals
3. **Use absolute imports** - Always use `@/` prefix instead of relative paths (`../../../`)
4. **No `any` types** - Always use proper TypeScript types
5. **Use shadcn/ui components** - Don't create custom UI when shadcn/ui equivalents exist
6. **React Query for data fetching** - All API calls should use React Query hooks (when implemented)
7. **Handle loading/error states** - Every data-fetching component must handle loading and errors
8. **Public APIs via index.ts** - Each feature exports only what other features need via `index.ts`

### Import Rules (Frontend)
```typescript
// ✅ Allowed: Features importing from shared modules
import { Button } from '@/components/ui';
import { useAuth } from '@/hooks';

// ✅ Allowed: Features importing from other features via public API
import { OrderList } from '@/features/orders';  // Via index.ts

// ❌ Forbidden: Direct import from feature internals
import { OrderList } from '@/features/orders/components/order-list';

// ❌ Forbidden: Shared modules importing from features
import { useOrders } from '@/features/orders';  // In shared components
```

## Database Configuration

**Connection Details** (in `backend/src/main/resources/application.properties`):
- URL: `jdbc:postgresql://localhost:5432/synder_algo7`
- Username: `synder`
- Password: `synder_password`
- Hibernate DDL: `create-drop` (recreates schema on restart - change for production)

## Project Context from Copilot Instructions

The project is designed as a unified shopping hub following these principles:

1. **Multi-platform integration** - Keep WooCommerce/Square/Shopify logic isolated in service layer
2. **AI-powered features** - Claude API for refund analysis and restock predictions
3. **Multi-shop support** - Always filter data by `shopId` parameter
4. **Colocation** - Keep related files together (components, hooks, types in same feature)
5. **Unidirectional architecture** - Dependencies flow one direction: features → shared → lib

## Testing Notes

- **Backend tests**: Located in `backend/src/test/java/algo/backend/`
- **Frontend tests**: Not yet configured (will use Vitest + React Testing Library)
- Use `@MockBean` for service layer mocking in backend tests
- Test features in isolation - mock external dependencies

## Common Anti-Patterns to Avoid

**Backend:**
- ❌ Using `@Controller` instead of `@RestController` for API endpoints
- ❌ Exposing JPA entities directly (always use DTOs)
- ❌ Hardcoding configuration (use `application.properties` or environment variables)
- ❌ Business logic in controllers (belongs in service layer)

**Frontend:**
- ❌ Importing from feature internals (use public API via index.ts)
- ❌ Relative imports like `../../../components` (use `@/components`)
- ❌ Using `any` type (always define proper types)
- ❌ Creating custom UI when shadcn/ui has equivalent component

## API Design Conventions

- RESTful endpoints under `/api/v1/{resource}`
- Proper HTTP methods (GET, POST, PUT, DELETE)
- Consistent response format using ResponseEntity
- HTTP status codes: 200 (OK), 201 (Created), 204 (No Content), 400 (Bad Request), 404 (Not Found)
- Input validation with `@Valid` annotation
- Error responses handled by @RestControllerAdvice

## Notes

- Database schema is recreated on every backend restart (ddl-auto=create-drop)
- Frontend vite.config.ts does not yet have path aliases configured for `@/` imports
- Project structure follows the comprehensive Agents.md guidance in `.github/copilot-instructions.md`
- Current state: Basic scaffolding, no features implemented yet
