
# Java-FWP-AI-B6-Projekt

![Pizzahut 2077](src/main/java/com/javafwp/sprites/splashscreen.png)

> Domino's Pizza® dominiert die Dystopie Düsburgs.
> Kann Pizza Hut® den Ruf der runden Fressalien retten?
> In einer Welt, in der Domino's Pizza® die Kontrolle über die Stadt Düsburg hat, ist es an dir, die Bevölkerung mit Pizza Hut®-Pizza zu versorgen.
> Als Lieferant für die letzte verbliebene Pizza-Hut-Filiale in der Stadt musst du dich durch eine von Dominos beherrschte Welt kämpfen, um die Pizza zu den Menschen zu bringen.
> Begib dich zur Auslieferung an die Front und beweise deine italienischen Wurzeln!

---

## JavaDoc

Die aktuelle Dokumentation kann [hier gefunden werden](https://htmlpreview.github.io/?https://github.com/SenpaiSimon/Java-FWP-AI-B6-Projekt/blob/main/javadoc/apidocs/index.html).

javadocs generieren:
```bash
$ mvn javadoc:javadoc
```

## Kompilieren

Das Projekt benutzt [`Java 18`](https://www.oracle.com/java/technologies/javase/jdk18-archive-downloads.html), [`maven`](https://maven.apache.org) und `JavaFX`.

`JAR` Kompilieren und ausführen
```bash
$ mvn package
$ java -jar ./target/SidescrollerGame-jar-with-dependencies.jar
```
oder nur kompilieren und ausführen
```bash
$ mvn exec:java
```

Die resultierende `JAR` ist unter `./target` mit dem Suffix `-jar-with-dependencies.jar` anzufinden.

Das Projekt wurde mit [microsoft visualstudio code](https://code.visualstudio.com) erstellt. Die Ausführung und Bearbeitung unter Eclipse wurde nicht getestet.
Die UML Diagramme wurden mit IntelliJ erstellt.

## Steuerung

| Funktion              | Taste             |
| ---                   | ---               |
| Spiel Starten         | `Q` / `Leertaste` |
| Credits               | `E`               |
| Schließen / Zurück    | `esc`             |
| Links bewegen         | `A`               |
| Rechts bewegen        | `D`               |
| Springen              | `Leertaste`       |
| Schießen              | `Maus`            |

## Autoren

| Name              | Matr. Nr.     |
| ---               | ---           |
|Robin Prillwitz    | `00805291`    |
|Simon Obermeier    | `00800498`    |
|Anton Kraus        | `00804697`    |

## Requirements

> Eine Java-Anwendung für ein kleines Anwedungszenario

*Notwendig*:
- [x] Vererbung (sämtliche `gameObjects`)
- [x] Interface (`globals`, `gameObject`)
- [x] Javadoc Kommentierung (in `./javadoc`)

*Besondere Herrausforderung*:
- [x] GUI mit `JavaSwing` oder `JavaFX`
- [x] Lauffähge `JAR` Datei
- [x] relationale Datenbank (Gewünscht)

---

Das Projekt wird nicht von Pizza Hut, LLC oder Domino's Pizza, Inc. unterstützt.
