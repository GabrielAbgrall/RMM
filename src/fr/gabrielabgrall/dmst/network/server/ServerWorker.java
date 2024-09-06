package fr.gabrielabgrall.dmst.network.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;

import fr.gabrielabgrall.dmst.utils.Debug;

public class ServerWorker extends Thread {

    protected final Socket socket;

    ServerWorker(String name, Socket socket) {
        setName(name);
        this.socket = socket;
    }

    @Override
    public void run() {
        new Thread((Runnable)this::listen, getName() + "-listener").start();
        sendData("TEST\ndata");
    }

    protected void listen() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            int data;
            String message = "";
            while((data = in.read()) != -1) {
                char[] chars = Character.toChars(data);
                for (char c : chars) {
                    message += c;
                    int l = message.length();
                    if(l>=2 && message.subSequence(l-2, l).equals("\r\n")) {
                        handleIncomingData(message.substring(0, l-2));
                        message = "";
                    }
                }
            }
        } catch (SocketException e) {
            Debug.log(this, "Client ", socket.getRemoteSocketAddress(), " lost connection.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void handleIncomingData(String data) {
        StringBuilder s = new StringBuilder();
        for (String d : data.split("\n")) {
            s.append("\n\t" + d);
        }
        Debug.log(this, "Incoming data from ", socket.getRemoteSocketAddress(), ": ", s.toString());
    }

    public void sendData(String data) {
        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            out.write(data + "\r\n");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return getName();
    }
}