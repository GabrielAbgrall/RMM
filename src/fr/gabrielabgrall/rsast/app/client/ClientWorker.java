package fr.gabrielabgrall.rsast.app.client;

import fr.gabrielabgrall.rsast.command.AuthCommand;
import fr.gabrielabgrall.rsast.network.Client;
import fr.gabrielabgrall.rsast.utils.Debug;

public class ClientWorker extends Thread {

    protected ClientApp clientApp;
    protected Client client;

    public ClientWorker(String name, ClientApp clientaApp) {
        setName(name + "-Worker");
        this.clientApp = clientaApp;
        this.client = clientaApp.getClient();
    }

    @Override
    public void run() {
        while (!clientApp.isAuthenticated()) {
            client.sendCommand(new AuthCommand("user", "bonjour", ClientApp.VERSION));
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                interrupt();
            }
        }
        Debug.log("Auth OK");
    }
}
