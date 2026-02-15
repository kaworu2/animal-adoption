<!-- Copilot / AI instructions tailored to this JavaFX Adoption Centre project -->
# Copilot instructions (project-specific)

Summary
- This is a small JavaFX-based Adoption Centre app. The app uses an application model (AdoptionCentre) passed into controllers via a lightweight ViewLoader, JavaFX FXML views in `/src/view`, controller classes in `/src/controller`, and model classes under `/src/model`.

Quick start (how devs run / test)
- A runnable JAR is present at the repo root: `Assignment2.jar` — try `java -jar Assignment2.jar` from the workspace root to run the app quickly.
- Sources live under `src/`. The main entry is `Prog2AdoptionCentreApp` which creates seed data and opens the login view.

Big-picture architecture (what to know)
- Startup: `Prog2AdoptionCentreApp.start()` creates `Animals` and `Users` (seed data) then constructs `AdoptionCentre` and calls `ViewLoader.showStage(..., "/view/LoginView.fxml", ...)`.
- View/Controller wiring: `ViewLoader` creates controller instances using reflection, sets `controller.model` and `controller.stage`, and then loads the FXML. Controllers extend `au.edu.uts.ap.javafx.Controller<T>` and expect their `model` to be pre-set.
- Models: `model.Animals.Animals` wraps an ObservableList<Animal>. `Animal` exposes JavaFX properties (`nameProperty`, `ageProperty`, `isAdoptedProperty`) for direct binding to UI controls.
- User/login: `model.Users.Users` contains validation helpers `validateCustomer(...)` and `validateManager(...)` which set the logged-in user via `AdoptionCentre.setLoggedInUser(...)`. The logged-in user is stored in a static field on `AdoptionCentre` (global-ish pattern used across controllers).

Patterns & conventions (concrete examples)
- Controllers are in package `controller` and annotated FXML fields are used (e.g., `LoginController` has `@FXML TextField usernameField`). Example: `LoginController` binds button disabled state using BooleanBinding and reads/writes model via `getCentre()` which returns `model`.
- View loading: Use `ViewLoader.showStage(model, "/view/SomeView.fxml", "Title", new Stage())`. Note: sometimes non-AdoptionCentre models are passed (e.g., the error view is opened with an Exception instance).
- Observable collections & properties: TableViews/Lists bind directly to `Animals.getAnimals()` (an ObservableList). When adding/removing, call `animals.add(...)` / `animals.remove(...)` so UI updates.

Error handling and exceptions
- Project defines `model.Exceptions.UnauthorizedAccessException` and `InvalidOperationException`. Controllers catch those and present `ErrorView.fxml` via `ViewLoader.showStage(exception, "/view/ErrorView.fxml", "Error", new Stage())`.

Files and locations to inspect first (high-value)
- `src/Prog2AdoptionCentreApp.java` — entry point and seed data.
- `src/au/edu/uts/ap/javafx/ViewLoader.java` — how controllers are instantiated and model injected.
- `src/controller/*.java` — controller patterns (FXML wiring, bindings, stage management).
- `src/model/Animals/*` and `src/model/Users/*` — data structures, validation, and how 'logged-in' is tracked.
- `src/view/*.fxml` — UI structure and fx:controller wiring; controllers are often created via ViewLoader, not by the FXML loader’s default controller factory.

What NOT to change without caution
- `ViewLoader` reflection: controllers are instantiated with `type.newInstance()` and `model` is injected. Changing the controller constructor signature or lifecycle without updating ViewLoader will break injection.
- `AdoptionCentre.loggedInUser` is a static field used app-wide; refactoring it into instance-scoped storage will require updating many controllers.

Useful heuristics for making edits/fixes
- When changing a model property name, update both the model's property method (e.g., `nameProperty()`) and any FXML bindings or controller code that references it.
- Prefer mutating ObservableList (add/remove) rather than replacing the list instance — UI bindings rely on the same ObservableList instance.

If you need to run or recompile sources
- Fast run: `java -jar Assignment2.jar` (from repo root).
- Source compile/run: no buildfiles provided. The main class is `Prog2AdoptionCentreApp`. If compiling from source, ensure JavaFX is on the module-path/classpath and compile `src` into `bin`, preserving package paths. (This project uses JavaFX APIs across controllers and models.)

If something in these instructions is unclear, tell me which area to expand (run/compile examples for your OS, deeper controller examples, or tests).  

-- end of file
