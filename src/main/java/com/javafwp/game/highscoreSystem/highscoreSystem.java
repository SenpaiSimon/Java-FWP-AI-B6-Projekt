package com.javafwp.game.highscoreSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class highscoreSystem {
    private Text displayText;
    private TextField inputName;
    private ArrayList<highscoreEntry> entries;
    private int maxEntries;
    private int tempScoreEntry;

    private int maxNameLength;

    /**
     * Kontruktor für das Highscore System
     * 
     * @param maxEntries
     */
    public highscoreSystem(int maxEntries, int maxNameLength) {
        this.maxNameLength = maxNameLength;
        this.maxEntries = maxEntries;

        // display
        displayText = new Text();
        displayText.setTranslateX(100);
        displayText.setTranslateY(100);
        displayText.setStyle("-fx-font: 15 arial;");
        displayText.setFill(Color.WHITE);

        // input
        inputName = new TextField();
        inputName.setTranslateX(400);
        inputName.setTranslateY(200);

        // other vars
        entries = new ArrayList<highscoreEntry>();
        inputName.setVisible(false);
        tempScoreEntry = 0;

        inputName.setOnAction(event -> { // if user pressed "ENTER"
            completeAddingNewEntry();  
        }); 

        // automatically shorten name when to long
        inputName.setOnKeyTyped(even -> {
            if(inputName.isVisible()) {
                String input = inputName.getText();
                inputName.setText(input.substring(0, Math.min(input.length(), this.maxNameLength)));
                inputName.end();
            }
        });
    }

    public void addEntry(int newScore) {
        sort();
        if(entries.isEmpty() || entries.size() < maxEntries) {
            // make user able to type in text
            inputName.setVisible(true);
            tempScoreEntry = newScore;
        } else if (newScore > entries.get(entries.size() - 1).getScore()) {
            // make user able to type in text
            inputName.setVisible(true);
            tempScoreEntry = newScore;
        }
    }

    private void completeAddingNewEntry() {
        if(!inputName.getText().isEmpty()) {
            // longest name is 10 letters
            entries.add(new highscoreEntry(inputName.getText(), tempScoreEntry));

            sort(); // sort again to get rid of lowest score in the next step

            if(entries.size() > maxEntries) {
                entries.remove(entries.size() - 1); // remove the last entry
            }

            inputName.clear();
            inputName.setVisible(false);
            generateText();
        }
    }

    private void sort() {
        Collections.sort(entries, new Comparator<highscoreEntry>() {
            @Override
            public int compare(highscoreEntry entry1, highscoreEntry entry2)
            {
                return entry2.getScore() - entry1.getScore();
            }
        });
    }

    public void generateText() {
        sort();
        String tempText = "Top " + maxEntries + " Highscores:\n";
        for(highscoreEntry entry: entries) {
            tempText += entry.getName() + ":\t" + entry.getScore() + "\n";
        }
        displayText.setText(tempText);
    }
    
    /** 
     * @return Text
     */
    public Text getDisplayText() {
        return displayText;
    }

    public TextField getInputFied() {
        return inputName;
    }
}
