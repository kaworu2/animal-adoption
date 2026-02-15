# Animal Adoption Centre (Java)

A Java-based animal adoption centre app demonstrating core business flows:
role-based login (Customer / Manager), adoption rule validation, and safe state transitions.

## Features

### Role-based login
- Customer login via **name + email**
- Manager login via **numeric ID**
- Input validation and unified error handling (ErrorView)

### Manager workflow
- View animals in a table and filter by type (**All / Cat / Dog / Rabbit**)
- Add new animals
- Remove animals with business constraint: **cannot remove adopted animals**

### Customer workflow
- View adoptable animals only
- Adopt an animal with rule validation (e.g., adoption limit per type)
- State updates are applied consistently:
  - animal marked as adopted
  - customer adoption list updated
  - animal removed from adoptable list

## Tech Stack
- Java
- JavaFX (FXML)
- MVC-style structure (`model` / `controller` / `view`)
- Git/GitHub

## Project Structure
- `src/model` – domain model and business rules
- `src/controller` – UI interaction + orchestration of business logic
- `src/view` – JavaFX views (FXML) + CSS
- `Assignment2.jar` – runnable packaged build (for demo/submission)

## How to Run

### Option A: Run the packaged JAR
1. Download `Assignment2.jar`
2. Run:
   ```bash
   java -jar Assignment2.jar
