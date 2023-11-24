package org.example;

import java.sql.*;

public class App
{

    public static void main( String[] args )
    {
        showEverything();
    }

    /**
     * Hier baut man einfach eine
     * Verbindung zur Datenbank auf diese habe ich extra hier geschrieben
     * damit ich sie nicht immer neu machen muss.
     */
    public static Connection connect() {
        final String CONNECTION_URL = "jdbc:mysql://localhost:3306/db2"; // jdbc:mysql://localhost:3306/NAME | Das ist immer Standardmäßig und hinten kommt der Name
        final String USER = "root";
        final String PSWRD = "";
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(CONNECTION_URL, USER, PSWRD);
            System.out.println("Verbindung zur Datenbank war ERFOLGREICH!");
        } catch (SQLException e) {
            System.out.println("FEHLER beim Verbinden zur Datenbank! "+e.getMessage());
        }
        return  conn;
    }

    public static void showEverything() {

        try {

            PreparedStatement preparedStatement = connect().prepareStatement("SELECT * FROM `lehrer`");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int aS = rs.getInt("arbeitsstunden");

                System.out.println("LEHRER | ID: "+id+" NAME: "+name+" ARBEITSSTUNDEN: "+aS);
            }

        } catch (SQLException e) {
            System.out.println("Fehler beim Verbinden der Datenbank: "+e.getMessage());
        }
    }
}
