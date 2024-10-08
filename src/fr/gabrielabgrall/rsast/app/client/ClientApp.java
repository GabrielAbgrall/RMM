package fr.gabrielabgrall.rsast.app.client;

import fr.gabrielabgrall.rsast.app.client.listener.ClientListener;
import fr.gabrielabgrall.rsast.network.Client;
import fr.gabrielabgrall.rsast.utils.Debug;

public class ClientApp {

    public static final String VERSION = "0.1";
    
    protected Client client;
    protected boolean isAuthenticated = false;

    public ClientApp(String name, String host, int port) {
        this.client = new Client(name, host, port);

        client.getEventManager().registerListener(new ClientListener(this));
        client.start();

        ClientWorker clientWorker = new ClientWorker(name, this);
        clientWorker.start();

        Debug.log("Client started, waiting for connection to the server");
    }

    public Client getClient() {
        return client;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }
}
