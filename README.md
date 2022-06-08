
# Java-FWP-AI-B6-Projekt

![Pizzahut 2077](src/main/java/com/javafwp/sprites/splashscreen.png)

> Domino's Pizza® dominiert die Dystopie Düsburgs.
> Kann Pizza Hut® den Ruf der runden Fressalien retten?
> Begieb dich zur Auslieferung an die Front und beweise deine italienischen Wurzeln!

---

## Kompilieren

Das Projekt benutzt `Java 18`, `maven` und `JavaFX`.

Zum Ausführen der Applikation aus dem root Verzeichniss:
```bash
$ mvn exec:java
```

Zum Erstellen einer `JAR` Datei:
```bash
$ mvn package
```

Die resultiernde `JAR` ist unter `./target` mit dem Suffix `-jar-with-dependencies.jar` anzufinden.

Das Projekt wurde mit `microsoft visualstudio code` erstellt. Die Ausführung und Bearbeitung unter Eclipse oder IntelliJ wurde nicht getestet.

## Steuerung

| Taste | Funktion |
| --- | --- |
| `Q` | Spiel Starten |
| `E` | Shop betreten/verlassen |
| `esc` | Spiel schließen |
| `A` | Links bewegen |
| `D` | Rechts bewegen |
| `Leertaste` | Springen |
| `Maus` | Schießen |

## Autoren

| Name | Matr. Nr. |
| --- | --- |
|Robin Prillwitz | `00805291`|
|Simon Obermeier | `00800498`|
|Anton Kraus | `00804697`|

---

## Requirements

> Eine Java-Anwendung für ein kleines Anwedungszenario

*Notwendig*:
- [x] Vererbung
- [x] Interface
- [ ] Javadoc Kommentierung

*Besondere Herrausforderung*:
- [x] GUI mit `JavaSwing` oder `JavaFX`
- [x] Lauffähge `JAR` Datei
- [ ] relationale Datenbank (Gewünscht)
