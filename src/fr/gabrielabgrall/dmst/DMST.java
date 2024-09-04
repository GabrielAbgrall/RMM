package fr.gabrielabgrall.dmst;

import fr.gabrielabgrall.dmst.network.client.Client;
import fr.gabrielabgrall.dmst.network.server.Server;
import fr.gabrielabgrall.dmst.utils.Debug;

public class DMST {

    public static void main(String[] args) {
        Debug.Log("N1");
        try {
            Debug.Log("N2");
            Server server = new Server(15000);
            server.start();

            Debug.Log("N3");
            Client client = new Client("127.0.0.1", 15000);
            client.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
