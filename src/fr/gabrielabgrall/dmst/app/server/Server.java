package fr.gabrielabgrall.dmst.app.server;

import java.io.IOException;

import fr.gabrielabgrall.dmst.network.NetworkServer;
import fr.gabrielabgrall.dmst.utils.Debug;

public class Server extends Thread{
    
    protected NetworkServer networkServer;

    public Server(int port) {
        try {
            this.networkServer = new NetworkServer(port);
        } catch (IOException e) {
            Debug.log("Unable to open stream: port already used or permission denied");
        }
    }

    @Override
    public void run() {
        while (!interrupted()) {
            networkServer.getServerWorkers().forEach(sw -> {
                if(!sw.isConnected()) return;
                try {
                    sw.sendCommand("UPDATE", null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                interrupt();
            }
        }
    }
}
