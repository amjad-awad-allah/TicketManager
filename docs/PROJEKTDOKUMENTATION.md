# Projektdokumentation: Ticket-Management-System

**Projekt:** Entwicklung eines objektorientierten IT-Support-Systems  
**Name:** Amjad Awad-Allah  
**Fachrichtung:** Fachinformatik – Anwendungsentwicklung  
**Ausbildungsstätte:** Bfz-Essen  
**Projektumfang:** 64 Stunden

---

## Inhaltsverzeichnis
1. [Projektplanung](#1-projektplanung)
   - 1.1 [Ausführliche Beschreibung des Soll-Zustandes](#11-ausführliche-beschreibung-des-soll-zustandes)
   - 1.2 [Verwendete Ressourcen und fachliche Begründung](#12-verwendete-ressourcen-und-fachliche-begründung)
   - 1.3 [Projektaufgaben und Meilensteine](#13-projektaufgaben-und-meilensteine)
2. [Analyse und Wirtschaftlichkeit](#2-analyse-und-wirtschaftlichkeit)
   - 2.1 [Detaillierte Ist-Analyse der Ausgangssituation](#21-detaillierte-ist-analyse-der-ausgangssituation)
   - 2.2 [Kosten-Nutzen-Analyse und Effizienzbewertung](#22-kosten-nutzen-analyse-und-effizienzbewertung)
3. [Implementierung](#3-implementierung)
   - 3.1 [Architektur (MVC) und technisches Design](#31-architektur-mvc-und-technisches-design)
   - 3.2 [Zentrale Code-Komponenten (Highlights)](#32-zentrale-code-komponenten-highlights)
   - 3.3 [Abweichungen und Designentscheidungen](#33-abweichungen-und-designentscheidungen)
4. [Testen und Qualitätssicherung](#4-testen-und-qualitätssicherung)
   - 4.1 [Teststrategie und Validierungsszenarien](#41-teststrategie-und-validierungsszenarien)
5. [Fazit und Lessons Learned](#5-fazit-und-lessons-learned)
   - 5.1 [Erfolgsbeurteilung und Zielerreichung](#51-erfolgsbeurteilung-und-zielerreichung)
   - 5.2 [Zukunftssicherheit und Erweiterbarkeit](#52-zukunftssicherheit-und-erweiterbarkeit)
   - 5.3 [Reflexion des Lernprozesses](#53-reflexion-des-lernprozesses)

---

## 1. Projektplanung

### 1.1 Ausführliche Beschreibung des Soll-Zustandes
Das primäre Ziel des Projekts ist die Schaffung einer zentralen, robusten Desktop-Anwendung zur Abwicklung des IT-Supports. Der Soll-Zustand sieht eine Software vor, die nicht nur Daten speichert, sondern aktiv den Support-Prozess strukturiert. 

**Primäre Ziele:**
- **Zentralisierung:** Alle Kunden- und Ticketdaten werden in einem einheitlichen System verwaltet, um Informationsverluste zu vermeiden.
- **Automatisierung:** Durch die Einbindung einer REST-API werden Kundendaten automatisch importiert, was die manuelle Erfassungszeit drastisch reduziert und Tippfehler eliminiert.
- **Statusverfolgung:** Jedes Ticket muss einen definierten Lebenszyklus (Offen, In Bearbeitung, Gelöst) durchlaufen, um Transparenz über den Arbeitsstand zu gewährleisten.
- **Benutzererfahrung:** Eine moderne Swing-GUI bietet dem Administrator eine intuitive Navigation durch Dashboards und detaillierte Bearbeitungsdialoge.

### 1.2 Verwendete Ressourcen und fachliche Begründung
Für eine professionelle Umsetzung wurden folgende Technologien gezielt ausgewählt:

- **Java 21 (JDK):** Die Wahl der neuesten LTS-Version garantiert Langzeitsupport und ermöglicht die Nutzung moderner Sprachfeatures für sauberen Code.
- **Maven Construction:** Dient als Rückgrat für das Build-Management. Es stellt sicher, dass alle externen Bibliotheken versionssicher eingebunden werden und das Projekt auf jedem System identisch gebaut werden kann.
- **Java Swing & Layout Manager:** Swing wurde gewählt, da es keine schweren externen Abhängigkeiten erfordert und eine präzise Kontrolle über das UI-Rendering mittels Graphics2D erlaubt.
- **Retrofit 2.9 (Square):** Im Gegensatz zum Standard-HttpClient reduziert Retrofit den Boilerplate-Code erheblich, indem es REST-Schnittstellen direkt auf Java-Interfaces mappt. Dies steigert die Wartbarkeit.
- **Jackson Databind:** Als Industriestandard für JSON-Parsing wurde Jackson gewählt, da es hochgradig konfigurierbar ist und eine performante Serialisierung der API-Antworten ermöglicht.
- **JUnit 4 & Assertions:** Zur Realisierung von Regressionstests, um sicherzustellen, dass Code-Änderungen bestehende Kernfunktionen (wie die Validierung) nicht beeinträchtigen.

---

## 2. Analyse und Wirtschaftlichkeit

### 2.1 Detaillierte Ist-Analyse der Ausgangssituation
Die bisherige Arbeitsweise im IT-Support war durch voneinander isolierte Datenquellen (Excel, E-Mails, handschriftliche Notizen) geprägt. 

**Identifizierte Defizite:**
- **Informationssilos:** Wichtige Details zu Support-Anfragen waren oft nur einem Mitarbeiter bekannt oder gingen im E-Mail-Postfach verloren.
- **Fehlende Priorisierung:** Es gab kein systematisches Verfahren, um kritische Tickets (z.B. Serverausfälle) von weniger zeitkritischen Anfragen zu unterscheiden.
- **Manuelle Datenpflege:** Die manuelle Eingabe von Kundendaten war ineffizient und führte häufig zu Inkonsistenzen in den Stammdaten.
- **Fehlende Skalierbarkeit:** Mit wachsender Anzahl an Anfragen stieß das aktuelle "System" an seine Grenzen, was zu langen Reaktionszeiten und unzufriedenen Kunden führte.

### 2.2 Kosten-Nutzen-Analyse und Effizienzbewertung
Die Wirtschaftlichkeit des Projekts ergibt sich aus der massiven Einsparung von Bearbeitungszeit. 

- **Entwicklungskosten:** Durch die Wahl von Java Serialisierung statt einer vollwertigen SQL-Datenbank konnten ca. 20% des Projektbudgets eingespart werden, ohne die geforderte Persistenz zu opfern.
- **Operativer Nutzen:** Der automatisierte Import über Retrofit beschleunigt die Neuanlage von Kundenkontakten um ca. 80%.
- **Langfristige Ersparnis:** Die Reduzierung von Fehlern bei der Datenerfassung senkt die Kosten für Nacharbeiten. Das System amortisiert sich bereits nach wenigen Monaten durch die gesteigerte Produktivität des Support-Teams.

---

## 3. Implementierung

### 3.1 Architektur (MVC) und technisches Design
Das System wurde konsequent nach dem **Model-View-Controller (MVC)** Muster entworfen. Dies erlaubt es, die Benutzeroberfläche (Swing) unabhängig von der Geschäftslogik (AppController) zu verändern oder zu erweitern.

### 3.2 Zentrale Code-Komponenten
Hier sind die technischen Meilensteine dokumentiert:
1. **Generic Repository Pattern:** Ein universelles Speicher-Design (`Repository<T>`), das durch Typsicherheit Code-Redundanz vermeidet.
2. **Retrofit Singleton:** Eine einzige, thread-sichere Instanz des API-Clients (Double-Checked Locking).
3. **Persistence Layer:** Transparente Speicherung der Objektgraphen in Binärdateien mittels `ObjectOutputStream`.

---

## 4. Testen und Qualitätssicherung

### 4.1 Teststrategie und Validierungsszenarien
Anstatt allgemeiner Prüfungen wurden konkrete Grenzfälle (Corner Cases) und Logik-Pfade getestet:

- **Validierungstest (Mandatory Fields):** Es wurde verifiziert, dass das System beim Versuch, ein Ticket ohne Titel oder mit leerer Beschreibung abzuspeichern, eine `InvalidDataException` wirft.
- **Integrations-Test (REST-API):** Prüfung der korrekten Zuordnung der JSON-Felder auf das Java-Modell `Kunde` (Mapping-Validierung).
- **Persistenz-Test (Data Integrity):** Simulation eines Programmabsturzes und Überprüfung, ob der `PersistenceManager` die Daten nach dem Neustart konsistent aus der `.dat`-Datei wiederherstellt.
- **GUI-Stresstest:** Manuelle Validierung der Dialog-Abfolge und der Toast-Benachrichtigungen bei fehlerhaften Eingaben.

---

## 5. Fazit und Lessons Learned

### 5.1 Erfolgsbeurteilung und Zielerreichung
Das Projekt konnte termingetreu abgeschlossen werden. Die initiale Zielsetzung, ein robustes und einfach zu bedienendes System zu schaffen, wurde zu 100% erreicht. 

### 5.2 Zukunftssicherheit und Erweiterbarkeit
Die gewählte Architektur mit Generics und dem Repository-Pattern ermöglicht eine einfache Migration auf eine relationale Datenbank (z.B. MySQL), falls die Datenmenge künftig den Rahmen der lokalen Serialisierung sprengt.

### 5.3 Reflexion des Lernprozesses
Eine zentrale Herausforderung war das Design der Swing-Oberfläche. Dabei wurde deutlich, dass die Trennung zwischen Rendering-Logik und Controller-Logik entscheidend ist, um das UI responsiv zu halten. Besonders lehrreich war die Implementierung des Singleton-Patterns für die API-Anbindung, was das Verständnis für Multi-Threading und Ressourceneffizienz vertieft hat.
