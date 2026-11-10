# Banking System: Spring Boot Backend + Web Frontend

REST API version of the Java Swing Banking System (accounts, deposit, withdraw, transaction history, JWT login).

**Stack:** Java 17, Spring Boot 3.3, Spring Security + JWT, Spring Data JPA, PostgreSQL (prod) / H2 (local), Docker.

## API

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| POST | /api/auth/login | no | `{"username","password"}` -> JWT token |
| POST | /api/accounts | yes | `{"holderName","type":"SAVINGS|CURRENT|STUDENT","openingBalance"}` |
| GET | /api/accounts?search= | yes | List / search by name or number |
| GET | /api/accounts/{no} | yes | Account details |
| POST | /api/accounts/{no}/deposit | yes | `{"amount"}` |
| POST | /api/accounts/{no}/withdraw | yes | `{"amount"}` |
| GET | /api/accounts/{no}/transactions | yes | Transaction history (newest first) |
| GET | /actuator/health | no | Health check |

Send the token as `Authorization: Bearer <token>`.

**Business rules (edit in `AccountType.java`):**
Savings max balance 10,00,000 / max withdraw 50,000; Current 50,00,000 / 2,00,000; Student 50,000 / 5,000.

## Run locally
Needs Java 17+ and Maven.
```
mvn spring-boot:run
```
Uses a local H2 file database (`./data`). Login: `admin` / `admin`.
Then open `frontend/index.html` in a browser (API_URL defaults to http://localhost:8080).

Quick test:
```
TOKEN=$(curl -s -X POST localhost:8080/api/auth/login -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin"}' | sed 's/.*"token":"\([^"]*\)".*/\1/')
curl -X POST localhost:8080/api/accounts -H "Authorization: Bearer $TOKEN" -H "Content-Type: application/json" \
  -d '{"holderName":"Rama","type":"STUDENT","openingBalance":4000}'
curl -X POST localhost:8080/api/accounts/1001/deposit -H "Authorization: Bearer $TOKEN" -H "Content-Type: application/json" -d '{"amount":500}'
```

## Run with Docker
```
docker build -t banking-backend .
docker run -p 8080:8080 banking-backend
```

## Deploy on Render (backend + PostgreSQL)
1. Push this folder to a GitHub repo (the repo root must contain `pom.xml`, `Dockerfile`, `render.yaml`).
2. Render dashboard -> **New** -> **Blueprint** -> select the repo.
3. When prompted, enter a strong `ADMIN_PASSWORD`. The database, `JWT_SECRET` and DB variables are created automatically.
4. Wait for the deploy. Check `https://<your-service>.onrender.com/actuator/health` shows `UP`.
5. Free-tier services sleep when idle, so the first request can take a while. Check Render's current free-plan limits.

## Deploy the frontend
1. Edit `API_URL` at the top of the script in `frontend/index.html` to your Render backend URL.
2. Host the single file on Netlify (drag and drop), GitHub Pages, or Render Static Site.
3. In Render, set `CORS_ORIGINS` to your frontend URL (e.g. `https://my-bank.netlify.app`) and redeploy.

## Environment variables
| Variable | Purpose | Default |
|---|---|---|
| SPRING_PROFILES_ACTIVE | `prod` = use PostgreSQL | (H2 locally) |
| DB_HOST, DB_PORT, DB_NAME, DB_USER, DB_PASSWORD | PostgreSQL connection (prod profile) | none |
| ADMIN_USERNAME / ADMIN_PASSWORD | Login credentials | admin / admin |
| JWT_SECRET | Token signing key (32+ chars) | dev-only value |
| JWT_TTL_MINUTES | Token lifetime | 120 |
| CORS_ORIGINS | Allowed frontend origins (comma-separated) | * |

## Notes
- Money uses `BigDecimal`; deposits/withdrawals take a row lock so concurrent requests can't overdraw an account.
- This is a learning project with a single admin user, not for real banking use.
