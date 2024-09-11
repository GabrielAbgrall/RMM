package fr.gabrielabgrall.dmst.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.gabrielabgrall.dmst.network.event.ConnectionEvent;
import fr.gabrielabgrall.dmst.network.event.DisconnectionEvent;
import fr.gabrielabgrall.dmst.network.event.EventHandler;
import fr.gabrielabgrall.dmst.network.event.NetworkEvent;
import fr.gabrielabgrall.dmst.network.event.NetworkEventListener;
import fr.gabrielabgrall.dmst.utils.Debug;

public class SocketHandler extends Thread {

    public final static String VERSION = "0.1";
    public final static String ENTRY_SEPARATOR = " ";
    public final static String ARGS_SEPARATOR = "\n";
    public final static String END_STATEMENT = "\r\n";

    protected Map<String, CommandHandler> commands = new HashMap<>();

    protected List<NetworkEventListener> listeners = new ArrayList<>();

    protected Socket socket;
    protected String host;
    protected int port;

    public SocketHandler(String name, Socket socket) {
        setName(name);
        this.socket = socket;
        start();
    }

    public SocketHandler(String name, String host, int port) {
        setName(name);
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        listen(socket);
    }

    public boolean connect() throws ConnectionException {
        if(isConnected()) {
            throw new ConnectionException();
        }
        try {
            this.socket = new Socket(host, port);
            start();
            Debug.log("Connected to ", host, ":", port);
            triggerEvent(new ConnectionEvent());
            return true;
        } catch (IOException e) {
            Debug.log("Connection attempt. Unable to open stream.");
            return false;
        }
    }

    public void disconnect() {
        if(!isConnected()) return;
        try {
            this.socket.close();
            interrupt();
            Debug.log("Disconnected from ", socket.getRemoteSocketAddress());
            triggerEvent(new DisconnectionEvent());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        return this.socket != null && this.socket.isConnected() && !this.socket.isClosed();
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
            interrupt();
        } catch (IOException e) {
            e.printStackTrace();
            interrupt();
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
        if(args != null) {
            for (Map.Entry<String, String> entry : args.entrySet()) {
                data += ARGS_SEPARATOR + entry.getKey() + ENTRY_SEPARATOR + entry.getValue();
            }
        }
        sendData(data);
    }

    public void registerCommand(String command, CommandHandler commandHandler) {
        this.commands.put(command, commandHandler);
    }

    public Socket getSocket() {
        return socket;
    }

    public void triggerEvent(NetworkEvent e) {
        for (NetworkEventListener l : listeners) {
            for(Method m : l.getClass().getMethods()) {
                boolean isAnnotated = m.isAnnotationPresent(EventHandler.class);
                boolean canHandleEvent = Arrays.asList(m.getParameterTypes()).stream().map(t -> t.isInstance(e)).toList().contains(true);
                boolean hasSingleParam = m.getParameterCount()==1;
                if(isAnnotated && canHandleEvent && hasSingleParam) {            
                    m.setAccessible(true);
                    try {
                        m.invoke(l, e);
                    } catch (IllegalAccessException | InvocationTargetException err) {
                        err.printStackTrace();
                    }

                }
            }
        }
    }

    public void registerListener(NetworkEventListener listener) {
        this.listeners.add(listener);
    }
}
