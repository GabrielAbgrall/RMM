package fr.gabrielabgrall.dmst.network.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import fr.gabrielabgrall.dmst.utils.Debug;

public class ServerWorker extends Thread {

    protected final Socket socket;

    ServerWorker(String name, Socket socket) {
        setName(name);
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            while((line = br.readLine()) != null) {
                Debug.Log(this, " Incoming data: ", line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return getName();
    }
}