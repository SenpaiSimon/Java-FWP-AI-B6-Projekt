package com.javafwp.game.highscoreSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


final class dbc {
    static final String DB_URL = "jdbc:mysql://db.robin-prillwitz.de/k122486_javafwp";
    static final String USER = "k122486_java_user";
    static final String PASS = "JavaFWP123!";

    static final String INSERT_QUERY = "INSERT INTO Highscores(Name, Score) VALUES (?, ?);";
    static final String SELECTION_QUERY = "SELECT Name, Score FROM Highscores ORDER BY Score DESC LIMIT 10;";

    public static void writeToDatabase(highscoreEntry entry)  {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connection.prepareStatement(INSERT_QUERY);
            statement.setString(1, entry.getName());
            statement.setInt(2, entry.getScore());
            statement.execute();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static ArrayList<highscoreEntry> getFromDatabase()  {
        ArrayList<highscoreEntry> entries = new ArrayList<highscoreEntry>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connection.prepareStatement(SELECTION_QUERY);
            ResultSet rs = statement.executeQuery();

            while(rs.next())    {
                System.out.println(rs.getInt("Score"));
                entries.add(new highscoreEntry(rs.getString("Name"), rs.getInt("Score")));
            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return entries;
    }
}
