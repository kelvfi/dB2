package org.example;

import java.sql.*;

public class App
{

    public static void main( String[] args )
    {
        showEverything();
        System.out.printf("%n");
        insertData("Dominik Neuner", 34);
        System.out.printf("%n");
        showEverything();
    }

    /**
     * Hier baut man einfach eine
     * Verbindung zur Datenbank auf diese habe ich extra hier geschrieben
     * damit ich sie nicht immer neu machen muss.
     *
     * @return = Gibt den 'DriverManager' zurück zum Verbinden
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

    /**
     * Diese Funktion zeigt alles an was in der Datenbank
     * drin ist.
     */
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
            System.out.println("Fehler beim Abfragen der Daten: "+e.getMessage());
        }
    }

    /**
     * Diese Funktion kann neue Werte in die Datenbank
     * eingeben
     *
     * @param name = Namen den man eingeben möchte
     * @param aS = Arbeitsstunden die man eingaben möchte
     */
    public static void insertData(String name, int aS) {

        try {

            PreparedStatement preparedStatement = connect().prepareStatement(
                    "INSERT INTO `lehrer` (`id`, `name`, `arbeitsstunden`) VALUES (NULL, ?, ?)");

            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, aS);
            int rowAffected = preparedStatement.executeUpdate();
            System.out.println(rowAffected+"x Datensätze eingefügt!");

        } catch (SQLException e) {
            System.out.println("Fehler beim SQL-INSERT Befehl: "+e.getMessage());
        }

    }

    /**
     * Diese Funktion kann schon vorhandene Datensätze umändern
     *
     * @param id = Eingabe von der ID die man ändern möchte
     * @param name = Eingabe vom Namen den man eingeben möchte
     * @param aS = Eingabe vom den Arbeitsstunden die man eingeben möchte
     */
    public static void updateData(int id, String name, int aS) {

        try {

            PreparedStatement preparedStatement = connect().prepareStatement(
                    "UPDATE `lehrer` SET `name` = ?, `arbeitsstunden` = ? WHERE `student`.`id` = id");

            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, aS);
            int rowAffected = preparedStatement.executeUpdate();
            System.out.println("Anzahl der Aktuallisierten Datensätze: "+rowAffected);

        } catch (SQLException e) {
            System.out.println("Fehler beim SQL-INSERT Befehl:"+e.getMessage());
        }
    }

    /**
     * Diese Funktion kann Datensätze löschen
     *
     * @param id = Eingabe von ID die man löschen möchte
     */
    public static void deleteData(int id) {

        try {

            PreparedStatement preparedStatement = connect().prepareStatement(
                    "DELETE FROM student WHERE `student`.`id` = ?");

            preparedStatement.setInt(1, id);
            int rowAffected = preparedStatement.executeUpdate();
            System.out.println("Anzahl der Gelöschten Datensätze: "+rowAffected);

        } catch (SQLException e) {
            System.out.println("Fehler beim SQL-DELETE Befehl: "+e.getMessage());
        }
    }
}
