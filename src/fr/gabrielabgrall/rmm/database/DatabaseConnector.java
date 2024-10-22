package fr.gabrielabgrall.rmm.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import fr.gabrielabgrall.rmm.utils.Debug;

public class DatabaseConnector {

    protected Connection conn;
    protected String url;

    public DatabaseConnector(String url) {
        this.url = "jdbc:sqlite:" + url;
    }

    public void connect() {
        try {
            conn = DriverManager.getConnection(url);
            Debug.log("Connection to database ", url, " successful");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            conn.close();
            Debug.log("Connection to database ", url, " closed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void init() {
        //todo
    }
}
