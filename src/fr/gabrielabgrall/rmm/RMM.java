package fr.gabrielabgrall.rmm;

import fr.gabrielabgrall.rmm.database.DatabaseConnector;
import fr.gabrielabgrall.rmm.utils.Debug;

public class RMM {

    public static void main(String[] args) {
        Debug.setDebug(true);

        DatabaseConnector dbConnector = new DatabaseConnector("local.db");
        dbConnector.connect();
        dbConnector.close();
    }
}
