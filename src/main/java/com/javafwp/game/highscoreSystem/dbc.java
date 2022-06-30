package com.javafwp.game.highscoreSystem;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/*
 * Klasse zur Kommunikation mit einer Datenbank
 */
final class dbc {
    static final String HIGHSCORES_FILE = "highscore.csv";

    /* These are online required for the online database */
    static final String DB_URL = "jdbc:mysql://db.robin-prillwitz.de/k122486_javafwp";
    static final String USER = "k122486_java_user";
    static final String PASS = "JavaFWP123!";
    static final String ADMIN_USER = "k122486_java_admin";

    static final String INSERT_QUERY = "INSERT INTO Highscores(Name, Score) VALUES (?, ?);";
    static final String SELECTION_QUERY = "SELECT Name, Score FROM Highscores ORDER BY Score DESC LIMIT 10;";
    static final String DELETE_QUERY = "DELETE FROM Highscores;";

    /*
     * Schreibt einen highscoreEntry durch den INSERT_QUERY in die Datenbank
     *
     * @param entry highscoreEntry der geschrieben werden soll
     * @param connectOnline soll die Online verbindung benutzt werden
     */
    public static void writeToDatabase(HighscoreEntry entry, boolean connectOnline) {
        if(connectOnline)   {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement statement = connection.prepareStatement(INSERT_QUERY);
                statement.setString(1, entry.getName());
                statement.setInt(2, entry.getScore());
                statement.execute();

            // we'll hope no error occurs :)
            } catch (ClassNotFoundException e) {
                //e.printStackTrace();
            } catch (SQLException e) {
                //e.printStackTrace();
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }   else    {
            // convert the new entry
            Map<String, String> ENTRY_MAP = new HashMap<>() {
            {
                put(entry.getName(), Integer.toString(entry.getScore()));
            }
            };

            // append to the old file
            FileWriter out;
            try {
                out = new FileWriter(HIGHSCORES_FILE, true);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            
            // create a CSV Printer to write the output stream
            CSVPrinter printer;
            try {
                printer = new CSVPrinter(out, CSVFormat.DEFAULT);
                
                ENTRY_MAP.forEach((player, highscore) -> {
                    try {
                        printer.printRecord(player, highscore);
                    } catch (IOException e) {
                        //e.printStackTrace();
                    }
                }); 
            } catch (IOException e) {
                //e.printStackTrace();
                return;
            } 
            
            // try and close the printer after we're done
            try {
                printer.close();
            }   catch(Exception e)  {
                //e.printStackTrace();
            }
        }
    }

    /**
     * Löscht ALLE Highscore Einträge nach Eingabe des Passworts
     * 
     * @param pass
     * @param connectOnline soll die Online verbindung benutzt werden
     * @param defaultPass standard Passwort für die Lokale Datenbank
     * @return boolean True wenn es erfolgreich war
     */
    public static boolean resetScore(String pass, boolean connectOnline, String defaultPass) {
        if (connectOnline) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection(DB_URL, ADMIN_USER, pass);
                if (connection != null) {
                    PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);
                    statement.execute();
                    return true;
                } else {
                    return false;
                }

            } catch (ClassNotFoundException e) {
                // e.printStackTrace();
                return false;
            } catch (SQLException e) {
                // e.printStackTrace();
                return false;
            } catch (Exception e) {
                // e.printStackTrace();
                return false;
            }
        } else {
            if(!pass.equals(defaultPass)) {
                return false;
            } else {
                File highscoresFile = new File(HIGHSCORES_FILE);
                highscoresFile.delete();

                return true;
            }
        }
    }

    /*
     * Führt den SELECTION_QUERY aus und gibt die resultate zurück
     *
     * @param connectOnline soll die Online verbindung benutzt werden
     * @return Array list an highscoreEntry aus dem result set der Abfrage
     */
    public static ArrayList<HighscoreEntry> getFromDatabase(boolean connectOnline) {
        ArrayList<HighscoreEntry> entries = new ArrayList<HighscoreEntry>();

        if (connectOnline) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement statement = connection.prepareStatement(SELECTION_QUERY);
                ResultSet rs = statement.executeQuery();

                while (rs.next()) {
                    // System.out.println(rs.getInt("Score"));
                    entries.add(new HighscoreEntry(rs.getString("Name"), rs.getInt("Score")));
                }

            } catch (ClassNotFoundException e) {
                // e.printStackTrace();
            } catch (SQLException e) {
                // e.printStackTrace();
            } catch (Exception e) {
                // e.printStackTrace();
            }
        } else {

            FileReader in;
            try {
                in = new FileReader(HIGHSCORES_FILE);
            } catch (IOException e) {
                //e.printStackTrace();
                return entries;
            }

            try {
                Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(in);
                for (CSVRecord record : records) {
                    String name = record.get(0);
                    int score = Integer.parseInt(record.get(1));

                    entries.add(new HighscoreEntry(name, score));
                }
            }   catch(Exception e)  {
                e.printStackTrace();
            }
        }

        return entries;
    }
}
