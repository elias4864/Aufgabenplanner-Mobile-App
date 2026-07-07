# Aufgabenplaner Mobile-App Android App

Eine native Android-App zur effizienten und persistenten  überischtlichen Verwaltung von täglichen Aufgaben in eienr Liste  Die App bietet Funktionen zum Erstellen, Abhaken und Löschen von Aufgaben sowie eine persistente Speicherung mittels Room-Datenbank via CRUD-Befele per DAO-Repository-Schnittstelle zugreift. Dabei drüfen maximal 7 Tasks zur Liste hinzugefügt werden können und danach der Emulator  sich  automatisch schliesst.

## 🔗 Design & UI-Screens
Die UI-Prototypen und Screen-Entwürfe für die Aufgabenplanner APP sind auf Visily hinterlegt:
👉 [Visily Board - Aufgabenplaner Mobile-App](https://app.visily.ai/projects/231779b9-ca57-4935-a952-12019f7d8788/boards/2659023)

---

## 💾 Datenbankmodell (`TaskItem` Entity)

Die App nutzt eine lokale SQLite-Datenbank via Room-Dependencies die Tasks persistent in der lokalen DB  gespeichert werden  Die Entität `TaskItem` besteht aus den  folgenden Attributen:



| Feld | Typ | Beschreibung |
| :--- | :--- | :--- |
| `id` | `Int` | Autogenerierter Primärschlüssel zur eindeutigen Identifikation (verhindert Duplikate). |
| `title` | `String` | Titel der Aufgabe (**Pflichtfeld**). |
| `description` | `String` | Beschreibung der Aufgabe (**Pflichtfeld**). |
| `priority` | `String` | Textfeld für die Wichtigkeit/Priorität (z. B. Standardwert "Mittel"). |
| `dueDate` | `String` | Zeitstempel im Kalenderformat (`Jahr-Monat-Tag Stunde:Minute`), wann die Task fällig/erledigt ist. |
| `done` | `Boolean` | Erledigt-Status (`true`/`false`). Steuert die Checkbox und die durchgestrichene UI-Darstellung. |
| `difficulty` | `Enum` | Schwierigkeitsgrad der Task. Mögliche Werte: `LEICHT`, `MITTEL`, `SCHWER` (**Pflichtfeld**). |

---

## 📝 User Stories & Akzeptanzkriterien


### User Story 1: Alle Tasks übersichtlich in einer Liste anzeigen
> **Als** orientierungsloser User  
> **möchte ich** alle meine Tasks übersichtlich in einer Liste angezeigt bekommen,  
> **um** den Überblick über meine Aufgaben zu behalten.

* **Testvoraussetzungen:**
  * `MainActivity` mit `TaskAdapter` zur Verbindung der Activities und Datenquellen ist vorhanden.
  * Die App lässt sich im Emulator erfolgreich starten.
* **Akzeptanzkriterien:**
  * Ist die Datenbank leer, wird eine leere Liste im Emulator angezeigt.
  * Neue Aufgaben werden strukturiert innerhalb der `ListView` gerendert.
  * Ein `TaskAdapter` verbindet die Datenquelle korrekt mit der `ListView`.
  * Aufgaben können nach Attributen (Beschreibung, Name, Schwierigkeit) gefiltert und über einen Reset-Button zurückgesetzt werden.
  * Eingabevalidierung: Bei fehlenden Pflichtfeldern erscheint eine Fehlermeldung/ein Alert-Dialog.

---

### User Story 2: Neue Tasks  auf Liste erfassen
> **Als** gestresster User  
> **möchte ich** einen neuen Task erfassen und zur Liste hinzufügen können,  
> **um** meine To-Dos im Blick zu behalten.

* **Testvoraussetzungen:**
  * `MainActivity` und `TaskActivity` (Formular) sind via `Intent`/`TaskAdapter` miteinander verbunden.
  * Buttons für „+ Add Task“ und „Abbrechen“ sind im XML-Layout und der Activity implementiert.
  * Die `MainActivity` startet fehlerfrei.
* **Akzeptanzkriterien:**
  * Das Formular öffnet sich nach Klick auf den Button „+ Task hinzufügen“.
  * Titel, Beschreibung und Schwierigkeit sind Pflichtfelder. Ein leerer String wird nicht akzeptiert.
  * Im Titelfeld kann ein beliebiger Text eingegeben werden.
  * Über den „Abbrechen“-Button kann das Formular jederzeit geschlossen werden, ohne dass Daten gespeichert werden.
  * Nach erfolgreichem Speichern wird die neue Task sofort in der Liste angezeigt.

---

### User Story 3: Task erledigen und abhaken
> **Als** beschäftigter User  
> **möchte ich** erledigte Tasks abhaken können,  
> **um** meinen aktuellen Fortschritt zu sehen.

* **Testvoraussetzungen:**
  * Jedes Listenelement besitzt eine Checkbox mit einem Click-Listener um die abgeschlossenden Task durchzustreichen
  * Das Interface `OnTaskClickListener` mit den Methoden `onTaskCheckChanged` und `onTaskDeleted` ist implementiert.
  * Im `TaskAdapter` ist die `getView`-Methode sowie die Logik für den `onCheckedChangeListener` und eine `updateStrokeThrough`-Methode integriert.
* **Akzeptanzkriterien:**
  * Beim Klick auf eine Task/Checkbox wird der Text sofort durchgestrichen dargestellt. Ein erneuter Klick setzt den Zustand zurück auf „offen“.
  * Die Daten werden asynchron über das Room-DAO-Objekt in der lokalen Datenbank gespeichert.
  * Nach einem Neustart des Emulators/der VDI bleibt der durchgestrichene Zustand persistent erhalten.

---

### User Story 4: Tasks aus der Liste löschen
> **Als** User  
> **möchte ich** gespeicherte Tasks jederzeit entfernen können,  
> **um** die Taskliste aktuell zu halten.

* **Testvoraussetzungen:**
  * Das Mülleimer-Icon aus dem UI-Entwurf besitzt einen `OnClickListener`, der die Methode `onTaskDeleted` aufruft.
  * Die App läuft stabil im Emulator.
* **Akzeptanzkriterien:**
  * Beim Klick auf den Löschen-Button (Mülleimer-Icon) wird die Task ohne Umwege aus der `ListView` entfernt.
  * Die Task wird dauerhaft aus dem Room-DAO/der Datenbank gelöscht und erscheint nach einem App-Neustart nicht wieder.
  * Der `TaskAdapter` aktualisiert die UI-Daten sofort nach dem Löschen.
  * **Exception-Handling:** Ein Try/Catch-Block fängt Fehler ab. Falls versucht wird, mehr als 10 Tasks hinzuzufügen, erscheint eine Alert-Fehlermeldung.

---

### User Story 5: Persistente Speicherung in der Room-Datenbank
> **Als** gestresster User  
> **möchte ich**, dass meine Tasks nach dem Neustart der App persistent gespeichert sind,  
> **damit** ich sie nicht jedes Mal manuell neu eingeben muss.

* **Testvoraussetzungen:**
  * `RoomDatabase` (`AppDatabase`) und das zugehörige DAO-Objekt mit den erforderlichen Queries (`COUNT`, `SELECT ALL`, `DELETE`, etc.) sind vollständig aufgesetzt.
  * Es existiert eine Liste mit mindestens 3 Beispiel-Tasks, um das Scroll- und Renderverhalten zu testen.
  * `AddTaskActivity` und `task_add.xml` zur Layouterstellung sind vorhanden.
  * Das Verhalten wird laufend über den Logcat in Android Studio überwacht.
* **Akzeptanzkriterien:**
  * Alle Attribute (Beschreibung, Priorität, Taskname, Schwierigkeit) des DAO stimmen exakt mit dem `TaskItem`-Objekt überein.
  * Wird die App komplett geschlossen (Kill-Prozess) und neu gestartet, wird der exakte Zustand (offen/durchgestrichen) aller nicht-gelöschten Aufgaben korrekt aus der SQL-Lite-DB geladen.
  * Statusänderungen (Erledigt/Gelöscht aus Story 3 & 4) werden synchron in der Datenbank aktualisiert.
