package com.javafwp.game.highscoreSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Einfaches Highscore Sytem zum hinzufügen von Highscores und auch verwalten
 */
public class highscoreSystem {
    private Text displayText;
    private Text infoDisplay;
    private Text congrats;
    private TextField inputName;
    private ArrayList<highscoreEntry> entries;
    private int tempScoreEntry;

    private int maxEntries;
    private int maxNameLength;
    private int textXPos;
    private int textYPos;

    private boolean onlineMode;

    /**
     * Kontruktor für das Highscore System
     *
     * @param textXPos Obere Linke Ecke des Texts
     * @param textYPos Obere Linke Ecke des Texts
     * @param maxEntries Maximale Anzahl an Einträgen
     * @param maxNameLength Maximale Namenslänge
     */
    public highscoreSystem(int textXPos, int textYPos, int maxEntries, int maxNameLength, boolean onlineMode) {
        this.textXPos = textXPos;
        this.textYPos = textYPos;
        this.maxNameLength = maxNameLength;
        this.maxEntries = maxEntries;
        this.onlineMode = onlineMode;

        // display
        displayText = new Text();
        displayText.setTranslateX(this.textXPos);
        displayText.setTranslateY(this.textYPos);
        displayText.setStyle("-fx-font: 25 arial;");
        displayText.setFill(Color.WHITE);

        // info text
        infoDisplay = new Text();
        infoDisplay.setTranslateX(this.textXPos);
        infoDisplay.setStyle("-fx-font: 20 arial;");
        infoDisplay.setFill(Color.RED);
        infoDisplay.setVisible(false);

        // congrats
        congrats = new Text();
        congrats.setTranslateX(this.textXPos);
        congrats.setStyle("-fx-font: 20 arial;");
        congrats.setFill(Color.GOLD);
        congrats.setVisible(false);

        // input
        inputName = new TextField();
        inputName.setTranslateX(textXPos);
        inputName.setVisible(false);
        inputName.setStyle("-fx-font: 15 arial;");
        inputName.setMaxHeight(50);
        inputName.setPromptText("Enter Name");
        inputName.setFocusTraversable(false);

        // other vars
        entries = new ArrayList<highscoreEntry>();
        tempScoreEntry = 0;

        // if user pressed "ENTER" complete addition
        inputName.setOnAction(event -> {
            completeAddingNewEntry();
        });

        // automatically shorten name when to long
        inputName.setOnKeyTyped(even -> {
            if(inputName.isVisible()) {
                String input = inputName.getText();
                int tempPos = inputName.getCaretPosition();
                if(inputName.getLength() > maxNameLength) {
                    infoDisplay.setText("Name can not be longer than " + maxNameLength + " Letters!");
                }
                inputName.setText(input.substring(0, Math.min(input.length(), this.maxNameLength)));
                inputName.positionCaret(tempPos);
            }
        });
    }


    /**
     * Gibt die Möglichkeit frei einen neuen Eintrag hhinzuzufügen wenn dieser noch Platz hat oder besser ist als der letzte
     *
     * @param newScore neue erreichte Punktezahl nach Tod
     */
    public void addEntry(int newScore) {
        entries = dbc.getFromDatabase(onlineMode);
        sort();
        if(entries.isEmpty() || entries.size() < maxEntries || newScore > entries.get(entries.size() - 1).getScore()) {
            // make user able to type in text
            inputName.setVisible(true);
            infoDisplay.setVisible(true);
            congrats.setVisible(true);
            tempScoreEntry = newScore;
            congrats.setText("NEW HIGHSCORE! Enter name here:");
            infoDisplay.setText("");
        }

        // set the input field relative to the text height
        congrats.setTranslateY(displayText.getBoundsInParent().getMinY() + displayText.getBoundsInParent().getHeight() + 100);
        inputName.setTranslateY(congrats.getBoundsInParent().getMinY() + congrats.getBoundsInParent().getHeight() + 10);
        infoDisplay.setTranslateY(inputName.getTranslateY() + inputName.getMaxHeight() + 10);
    }

    /**
     * Beendet das hinzufügen nachdem ENTER im Eingabefeld gedrückt wird
     */
    private void completeAddingNewEntry() {
        if(!inputName.getText().isEmpty()) {
            // longest name is 10 letters
            highscoreEntry entry = new highscoreEntry(inputName.getText(), tempScoreEntry);
            entries.add(entry);
            dbc.writeToDatabase(entry, onlineMode);

            sort(); // sort again to get rid of lowest score in the next step

            if(entries.size() > maxEntries) {
                entries.remove(entries.size() - 1); // remove the last entry
            }

            inputName.clear();
            inputName.setVisible(false);
            infoDisplay.setVisible(false);
            congrats.setVisible(false);
            generateText();
        } else {
            infoDisplay.setText("Name can not be empty!");
        }
    }

    /**
     * Sortiert die Einträge nach Score absteigend
     */
    private void sort() {
        Collections.sort(entries, new Comparator<highscoreEntry>() {
            @Override
            public int compare(highscoreEntry entry1, highscoreEntry entry2)
            {
                return entry2.getScore() - entry1.getScore();
            }
        });
    }

    /**
     * Generiert den Text der den Highscore anzeigt
     */
    public void generateText() {
        entries = dbc.getFromDatabase(onlineMode);
        sort();
        String tempText = "Top " + maxEntries + " Highscores:\n";
        for(highscoreEntry entry: entries) {
            tempText += entry.getName() + ":\t" + entry.getScore() + "\n";
        }
        displayText.setText(tempText);
    }

    /**
     * Gibt die Anfrage die Datenbank zurückzusetzen an die Datenbank weiter
     * 
     * @param pass
     * @return
     */
    public boolean resetScore(String pass, String defaultPassword) {
        return dbc.resetScore(pass, onlineMode, defaultPassword);
    }

    /**
     * Gibt alle Nodes des Systems zurück
     *
     * @return Node[] Node Array um diese zum Pane hinzuzufügen
     */
    public Node[] getEntities() {
        return new Node[]{displayText, inputName, infoDisplay, congrats};
    }
}
