package fr.gabrielabgrall.rsast.app.server;

import java.io.IOException;

import fr.gabrielabgrall.rsast.app.server.listener.ServerListener;
import fr.gabrielabgrall.rsast.network.Server;
import fr.gabrielabgrall.rsast.utils.Debug;

public class ServerApp {

    public static final String VERSION = "0.1";
    
    protected Server server;

    public ServerApp(int port) {
        try {
            this.server = new Server("Server", port);

            server.getEventManager().registerListener(new ServerListener(this));

            server.start();
        } catch (IOException e) {
            Debug.log("Unable to open stream: port already used or permission denied");
        }
    }

    public boolean checkVersion(String version) {
        return VERSION.split("\\.")[0].equals(version.split("\\.")[0]);
    }
}
