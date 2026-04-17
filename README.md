# 🎫 NextGen Ticket Manager

**NextGen Ticket Manager** is a professional, high-performance Java Swing application designed for IT Helpdesks. It features a modern MVC architecture, real-time data persistence, and a sleek, user-centric interface.

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java: 21](https://img.shields.io/badge/Java-21-blue.svg)](https://www.oracle.com/java/technologies/downloads/)
[![Tests: 28 Passed](https://img.shields.io/badge/Tests-28%20Passed-brightgreen.svg)](https://github.com/amjad-awad-allah/TicketManager)

---

## 📜 Official Project Documentation
For a detailed academic report (IHK/Bfz-Essen standards), please refer to the:
**[PROJEKTDOKUMENTATION.md](docs/PROJEKTDOKUMENTATION.md)**

---

## ✨ Features

- **💎 Premium UI/UX**: Custom-built "NextGen" theme featuring glassmorphism elements, rounded components, and professional color palettes.
- **📊 Interactive Dashboard**: Instant overview of ticket metrics (Total, Open, and High-Priority tickets) with real-time updates.
- **🏗️ MVC Architecture**: Clean code structure ensuring a strict separation between domain logic (Model), UI (View), and mediation (Controller).
- **🔍 Smart Filtering**: A powerful live-search engine to instantly find tickets by ID, Title, Description, or Customer name.
- **🌐 REST API Integration**: Automated customer data import using **Retrofit 2** and **OkHttp**, fetching data from external services like JSONPlaceholder.
- **💾 Fail-Safe Persistence**: Automatic serialization of all tickets and customer data to local storage (`.dat` files) using custom Persistence Management.
- **🔔 Toast System**: Modern, non-intrusive feedback notifications for various user actions.

---

## 🛠️ Tech Stack

- **Core**: Java 21 (LTS)
- **GUI Framework**: Java Swing (Custom UI Extensions)
- **Networking**: Retrofit 2 & OkHttp 4
- **Persistence**: Java Serialization (Custom Manager)
- **Mapping/JSON**: Jackson Databind 2
- **Testing**: JUnit 4
- **Build Management**: Apache Maven

---

## 🚀 Getting Started

### Prerequisites
- **JDK 21** or higher
- **Maven 3.8+**

### Installation & Run

1. Clone the repository.
2. Build the project:
   ```bash
   mvn clean compile
   ```
3. Run directly with Maven:
   ```bash
   mvn exec:java -Dexec.mainClass="Main"
   ```

### ✅ Quick Verification (for Reviewers)
To ensure the project is fully functional and ready for deployment, run the following commands:

```powershell
# 1. Build & Package (Produces JAR)
mvn clean package

# 2. Run Test Suite (28 Unit Tests)
mvn test

# 3. Launch from Executable JAR
java -jar target/TicketManager-1.0-SNAPSHOT.jar
```

---

## 📐 System Design

### Architecture Overview
The system follows a classic **Model-View-Controller (MVC)** pattern:
- **Models**: Plain Old Java Objects (POJOs) representing Tickets, Customers (Kunde), and Users (Benutzer).
- **Views**: Swing-based JFrame and components utilizing a custom UI theme.
- **Controllers**: Mediates between the UI and the data repositories, handling business logic.

### 🧩 Use Case Diagram
Detailed system behavior is documented in [docs/use-case.puml](docs/use-case.puml). It covers roles for Technicians (Admins) and Customers, including ticket lifecycle management and API interactions.

---

## 🗂️ Project Structure

```text
src/main/java/
├── Api/           # REST API client and Retrofit configuration
├── controllers/   # AppController (Busines logic & UI control)
├── dto/           # Data Transfer Objects for API
├── exceptions/    # Custom runtime exceptions
├── gui/           # View layer: MainFrame, Theme, RoundedComponents
├── models/        # Domain Entities: Ticket, Kunde, Priority, Status
└── repositories/  # PersistenceManager and Generic Repository
```

---

## 💡 Support & Notes

> [!IMPORTANT]
> **Bei eventuellen Problemen oder Unklarheiten während der Ausführung des Systems wird empfohlen, die README-Datei zu konsultieren.**

---

*Developed by Amjad Awad Allah – 2026*
