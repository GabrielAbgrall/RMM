package fr.gabrielabgrall.rmm.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import fr.gabrielabgrall.rmm.utils.Debug;

public class SocketHandler extends Thread {

    public final static String VERSION = "0.1";
    public final static String ENTRY_SEPARATOR = " ";
    public final static String ARGS_SEPARATOR = "\n";
    public final static String END_STATEMENT = "\r\n";
    protected Map<String, CommandHandler> commands = new HashMap<>();

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
        String[] split = data.split(ARGS_SEPARATOR);
        String command = split[0];
        Map<String, String> args = new HashMap<>();
        for(String arg : Arrays.copyOfRange(split, 1, split.length)) {
            String k = arg.split(ENTRY_SEPARATOR)[0];
            if(arg.length()<3 || k.length()==0) continue;
            String v = arg.substring(k.length(), arg.length());
            args.put(k, v);
        }
        handleIncomingCommand(command, args);
    }

    public void handleIncomingCommand(String command, Map<String, String> args) {
        if(commands.containsKey(command)) commands.get(command).handleCommand(command, args);
        else Debug.log("Unknown command received from ", socket.getRemoteSocketAddress(), ": " + command);;
    }

    public void sendData(String data) throws IOException {
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        out.write(data + END_STATEMENT);
        out.flush();
    }

    public void sendCommand(String command, Map<String, String> args) throws IOException {
        String data = command.toUpperCase();
        for (Map.Entry<String, String> entry : args.entrySet()) {
            data += ARGS_SEPARATOR + entry.getKey() + ENTRY_SEPARATOR + entry.getValue();
        }
        sendData(data);
    }

    public Socket getSocket() {
        return socket;
    }
}
