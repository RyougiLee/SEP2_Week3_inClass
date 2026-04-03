# Shopping Cart Calculator (JavaFX + MariaDB)

A small JavaFX desktop app for entering cart items, calculating totals, switching UI language, and saving cart records to MariaDB.

## What this project does

- Builds a dynamic cart form based on the number of items you enter.
- Calculates total cost from quantity and unit price.
- Supports language switching (English, Finnish, Swedish, Arabic, Japanese).
- Loads UI translations from the database (`localization_strings` table).
- Persists cart summaries and line items (`cart_records` and `cart_items`).

## Tech stack

- Java 21
- JavaFX 23 (`javafx-controls`, `javafx-fxml`)
- Maven
- MariaDB
- JUnit 5
- Docker / Docker Compose
- Jenkins pipeline (Windows agent style using `bat`)

## Project structure

- `src/main/java/com/example/calculatorapp/`
  - `MainApp.java`: JavaFX entrypoint and scene reload for language changes.
  - `Controller.java`: UI event handling.
  - `CalculatorModel.java`: core calculation model.
  - `LocalizationService.java` + `DbResourceBundle.java`: DB-backed localization.
  - `CartService.java`: saves cart records and items to DB.
  - `DBUtil.java`: DB connection config.
- `src/main/resources/com/example/calculatorapp/main-view.fxml`: main UI layout.
- `schema.sql`: DB schema + seed localization/cart data.
- `src/test/java/com/example/calculatorapp/CalculatorModelTest.java`: model unit tests.

## Prerequisites

- JDK 21 installed and available on `PATH`
- Maven 3.9+
- MariaDB running on port `3306`

## Quick start (Windows PowerShell)

### 1) Start MariaDB with Docker Compose

```powershell
docker compose up -d db
```

### 2) Build and test

```powershell
mvn clean test
```

### 3) Package the app

```powershell
mvn clean package -DskipTests
```

### 4) Run

```powershell
java -jar target/app.jar
```

## Running without Docker DB

If you already have MariaDB locally:

1. Create database `shopping_cart_localization`.
2. Execute `schema.sql`.
3. Ensure credentials match `DBUtil.java`.

## Docker notes

- `Dockerfile` builds `target/app.jar` and runs it in a JRE image.
- `docker-compose.yml` includes:
  - `db` service (MariaDB)
  - `app` service (Java app)
- JavaFX in containers often needs host display configuration (especially on Windows). If UI does not open in container, run DB in Docker and run the Java app locally.

## CI (Jenkins)

`Jenkinsfile` stages:

1. Checkout
2. Build/Compile (`mvn clean compile`)
3. Unit tests (`mvn test`)
4. Coverage publishing (`jacoco` step)
5. Package (`mvn package -DskipTests`)
6. Docker image build/tag

## Database schema overview

- `localization_strings`: translations by key and language.
- `cart_records`: total items, total cost, language, timestamp.
- `cart_items`: per-item quantity, price, subtotal linked to `cart_records`.

## Known configuration caveats

- `DBUtil.java` uses:
  - user: `root`
  - password: `114514`
- `docker-compose.yml` sets `MYSQL_ROOT_PASSWORD=root`.

These should match before running app + DB together.

## Useful commands

```powershell
# Run tests
mvn test

# Build shaded jar
mvn clean package -DskipTests

# Start full compose stack
docker compose up -d

# Stop compose stack
docker compose down
```

## Main class

The packaged jar is configured to start:

- `com.example.calculatorapp.Launcher`

