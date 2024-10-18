package fr.gabrielabgrall.rmm.app.client;

import fr.gabrielabgrall.rmm.command.AuthCommand;
import fr.gabrielabgrall.rmm.network.Client;
import fr.gabrielabgrall.rmm.utils.Debug;

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
            client.sendCommand(new AuthCommand(client.getName(), "motdepasseincorrect", ClientApp.VERSION));
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                interrupt();
            }
        }
        Debug.log("Auth OK");
    }
}
