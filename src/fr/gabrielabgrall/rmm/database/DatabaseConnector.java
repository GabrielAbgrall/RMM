package fr.gabrielabgrall.rmm.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
            init();
            Debug.log("Database ", url, " successfuly initialized");
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

    public void init() throws SQLException {
        Statement stmt = conn.createStatement();
        /*stmt.execute(
                "CREATE TABLE IF NOT EXISTS test ("
            +   "   id      INTEGER PRIMARY KEY,"
            +   "   name    TEXT NOT NULL"
            +   ")"
        );*/

        /*
        TABLES :
        client (id_client, login, password)
        script (id_script, content)
        script_routine (id_script, id_client, periodicity)
        client_data (cpu, ram, memory)
        */
    }

    public Connection getConn() {
        return conn;
    }
}
