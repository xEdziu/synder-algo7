# synder-algo7 — lokalny development i CI/CD

Ten README wyjaśnia jak uruchomić aplikacje lokalnie (Postgres, backend, frontend) oraz opisuje przygotowany workflow CI/CD do deploymentu na Raspberry Pi.

## Struktura repo

```
synder-algo7/
├── frontend/        # React + Vite + TypeScript
├── backend/         # Spring Boot 3 (Java 21, Maven)
├── .github/workflows/
└── docker-compose.yml
```

## Wymagania (developer)
- Docker Desktop (do uruchomienia Postgres + opcjonalnie backend)
- Node.js 22 LTS + npm
- (opcjonalnie) Java 21 + Maven, jeśli chcesz uruchamiać backend lokalnie bez Dockera

## Uruchomienie lokalne — szybki przewodnik

1) Uruchomienie bazy i backendu przez Docker Compose (szybkie, izolowane):

```cmd
cd <repo_root>
docker compose up --build
```

To wystawi:
- Postgres na porcie 5432
- Backend (Spring Boot) na porcie 8080

1.1) Uruchomienie tylko bazy danych (Postgres)

```cmd
cd <repo_root>
docker compose up -d db
```

Co robi:
- Uruchamia kontener Postgres zgodnie z definicją w `docker-compose.yml`.
- Użyje wartości z pliku `.env` (POSTGRES_USER/PASSWORD/DB).
- Dane będą przechowywane w volume `postgres_data`.

Sprawdzenie statusu i logów:

```cmd
docker ps
docker compose logs -f db
```

Zatrzymanie / usunięcie kontenera:

```cmd
docker compose stop db
docker compose rm -f db
```

Alternatywa — `docker run` bez Compose:

```cmd
docker run --name synder-algo7-db -e POSTGRES_USER=synder -e POSTGRES_PASSWORD=synder_password -e POSTGRES_DB=synder_algo7 -p 5432:5432 -v postgres_data:/var/lib/postgresql/data -d postgres:16-alpine
```

Wejście do psql w kontenerze lub połączenie z hosta:

```cmd
docker exec -it synder-algo7-db psql -U synder -d synder_algo7
```

1) Uruchomienie frontend (dev server, HMR):

```cmd
cd frontend
npm install
npm run dev
```

Front-end (Vite) domyślnie uruchomi serwer HMR (np. http://localhost:5173). Upewnij się, że `VITE_API_URL` wskazuje na `http://localhost:8080` (możesz skopiować plik `.env` z repo root do `frontend/.env` lub ustawić zmienną środowiskową).

3) Uruchomienie backendu lokalnie (dev, hot-reload):

- Aby korzystać z Spring Boot DevTools (restart po zmianach), dodaj w `backend/pom.xml` zależność `spring-boot-devtools` (scope runtime) i uruchom:

```cmd
cd backend
mvnw.cmd spring-boot:run
```

- Alternatywnie, uruchom z IDE (IntelliJ/VSCode) z włączonym DevTools.

4) Hot-reload w Docker (opcjonalnie):

- Jeśli chcesz uruchamiać backend w kontenerze z hot-reload, rozważ `docker-compose.override.yml` z volume montującym `./backend:/app` i komendą `./mvnw spring-boot:run` w kontenerze.

## Build produkcyjny lokalnie

- Build frontendu i wrzucenie do backendu (tak jak robi workflow):

```cmd
cd frontend
npm ci
npm run build
cd ..
mkdir -p backend/src/main/resources/static
rm -rf backend/src/main/resources/static/*
cp -r frontend/dist/* backend/src/main/resources/static/

cd backend
mvn -B clean package -DskipTests
# wynik: backend/target/*.jar
```

Uruchomienie pliku JAR:

```cmd
java -jar backend/target/your-app.jar
```

## CI/CD — co dodałem

- Plik GitHub Actions: `.github/workflows/deploy.yml`
  - buduje frontend (npm ci, npm run build)
  - kopiuje `frontend/dist/` do `backend/src/main/resources/static/`
  - buduje backend (mvn clean package -DskipTests)
  - przesyła JAR na Raspberry Pi przez SCP i restartuje `synder-algo7.service` (systemd)
  - wykonuje health-check `/actuator/health` na Raspberry Pi

Instrukcje dodania secrets znajdziesz poniżej (sekcja w repo oraz opis w workflow).

Polecenia na Raspberry Pi (przykład):

```bash
sudo mkdir -p /opt/synder-algo7
sudo chown pi:pi /opt/synder-algo7
# Umieść service file
sudo systemctl daemon-reload
sudo systemctl enable --now synder-algo7.service
sudo journalctl -u synder-algo7.service -f
```

## Health check
- Workflow wykona po deployu `curl http://localhost:8080/actuator/health` na Raspberry Pi.
- Alternatywnie możesz sprawdzać endpoint z runnera, ale to wymaga dostępu publicznego do hosta.