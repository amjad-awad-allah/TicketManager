# NextGen Ticket Manager

A professional Java Swing application for managing IT support tickets, featuring a clean MVC architecture, modern UI design, and real-time data persistence.

![Dashboard Preview](https://via.placeholder.com/800x450.png?text=NextGen+Ticket+Manager+Dashboard)

## 🚀 Features

- **Modern UI/UX**: Built with a custom "NextGen" theme featuring rounded components, professional color palettes, and smooth transitions.
- **Smart Dashboard**: Real-time stats cards showing total, open, and high-priority tickets.
- **MVC Architecture**: Clear separation of concerns between Models, Views, and Controllers for maximum maintainability.
- **Live Search**: Instant filtering of tickets by ID, Title, Description, or Customer name.
- **API Integration**: Seamlessly imports customer data from external REST APIs (using Retrofit & OkHttp).
- **Persistent Storage**: Automatic data serialization to local files (`tickets.dat`, `kunden.dat`).
- **Toast Notifications**: Non-intrusive, modern feedback system for user actions.

## 🛠️ Tech Stack

- **Language**: Java 21
- **GUI Framework**: Java Swing
- **Networking**: Retrofit 2, OkHttp
- **JSON Processing**: Jackson Databind
- **Build Tool**: Maven

## 📦 Getting Started

### Prerequisites
- JDK 21 or higher
- Maven 3.8+

### Installation & Run
1. Clone the repository.
2. Build the project:
   ```bash
   mvn clean compile
   ```
3. Run the application:
   ```bash
   mvn exec:java -Dexec.mainClass="Main"
   ```

### ⚡ Quick Verification (before submission)
Run these three commands in order to verify everything works correctly:

```powershell
# 1. Build the project (produces the runnable JAR)
mvn clean package

# 2. Run all JUnit tests (28 tests – must all pass)
mvn test

# 3. Launch the application from the JAR
java -jar target/TicketManager-1.0-SNAPSHOT.jar
```

> ✅ Expected result: **BUILD SUCCESS**, **Tests run: 28, Failures: 0**, and the GUI window opens.

## 📂 Project Structure

- `src/main/java/Main.java`: Application entry point.
- `src/main/java/controllers/`: Core business logic and UI mediation.
- `src/main/java/gui/`: UI components, theme manager, and standard factories.
- `src/main/java/models/`: Domain entities (Ticket, Kunde, etc.).
- `src/main/java/repositories/`: Data access layer and persistence management.
- `src/main/java/Api/`: API definition and networking clients.
