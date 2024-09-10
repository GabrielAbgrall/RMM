package fr.gabrielabgrall.dmst.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;

import fr.gabrielabgrall.dmst.utils.Debug;

public class SocketHandler extends Thread {

    public final static String VERSION = "0.1";
    public final static String ARGS_SEPARATOR = "\n";
    public final static String END_STATEMENT = "\r\n";

    protected final Socket socket;

    public SocketHandler(String name, Socket socket) throws UnknownHostException, IOException {
        setName(name);
        this.socket = socket;
    }

    @Override
    public void run() {
        listen(socket);
    }
    
    public void listen(Socket socket) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            int charId;
            String data = "";
            while((charId = in.read()) != -1) {
                char[] chars = Character.toChars(charId);
                for (char c : chars) {
                    data += c;
                    int l = data.length();
                    if(l>=2 && data.subSequence(l-2, l).equals("\r\n")) {
                        handleIncomingData(socket, data.substring(0, l-2));
                        data = "";
                    }
                }
            }
        } catch (SocketException e) {
            Debug.log("Lost connection to remote ", socket.getRemoteSocketAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleIncomingData(Socket socket, String data) {
        String[] sd = data.split(ARGS_SEPARATOR); // Splitted Data
        String command = sd[0];
        String[] args = Arrays.copyOfRange(sd, 1, sd.length);
        handleIncomingCommand(command, args);
    }

    public void handleIncomingCommand(String command, String[] args){
        Debug.log("Incoming data from ", socket.getRemoteSocketAddress(), ": ", command, " ", args);
    }

    public void sendData(String data) throws IOException {
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        out.write(data + END_STATEMENT);
        out.flush();
    }

    public Socket getSocket() {
        return socket;
    }
}
