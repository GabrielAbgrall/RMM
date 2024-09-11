package fr.gabrielabgrall.dmst.app.client;

import fr.gabrielabgrall.dmst.network.ConnectionException;
import fr.gabrielabgrall.dmst.network.SocketHandler;
import fr.gabrielabgrall.dmst.network.event.ConnectionEvent;
import fr.gabrielabgrall.dmst.network.event.DisconnectionEvent;
import fr.gabrielabgrall.dmst.network.event.EventHandler;
import fr.gabrielabgrall.dmst.network.event.NetworkEvent;
import fr.gabrielabgrall.dmst.network.event.NetworkEventListener;
import fr.gabrielabgrall.dmst.utils.Debug;

public class Client extends Thread {
    
    protected SocketHandler socketHandler;

    public Client(String name, String host, int port) {
        this.socketHandler = new SocketHandler(name, host, port);

        socketHandler.registerListener(new NetworkEventListener() {
            @EventHandler
            public void onDisconnect(DisconnectionEvent e) {
                Debug.log("Disconnection event caught !");
            }
            
            @EventHandler
            public void onConnect(ConnectionEvent e) {
                Debug.log("Connection event caught !");
            }
            
            @EventHandler
            public void onDisconnect(NetworkEvent e) {
                Debug.log("Network event caught !");
            }
        });
    }

    @Override
    public void run() {
        try {
            socketHandler.connect();
        } catch (ConnectionException e) {
            e.printStackTrace();
        }
        while (!interrupted()) {
            try {
                Thread.sleep(1000);
                socketHandler.disconnect();
            } catch (InterruptedException e) {
                interrupt();
            }         
        }
    }
}
