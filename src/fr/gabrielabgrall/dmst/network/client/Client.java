package fr.gabrielabgrall.dmst.network.client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import fr.gabrielabgrall.dmst.utils.Debug;

public class Client extends Thread {
    
    protected final Socket socket;

    public Client(String host, int port) throws UnknownHostException, IOException {
        this.socket = new Socket(host, port);

        Debug.Log(this, " Client connected to ", host, ":", port);
    }

    @Override
    public void run() {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bw.write("Blou");
            bw.newLine();
            Debug.Log(this, " Data sent");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}