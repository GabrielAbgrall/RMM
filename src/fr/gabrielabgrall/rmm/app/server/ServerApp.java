package fr.gabrielabgrall.rmm.app.server;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.gabrielabgrall.rmm.app.server.listener.ServerListener;
import fr.gabrielabgrall.rmm.network.Server;
import fr.gabrielabgrall.rmm.network.ServerWorker;
import fr.gabrielabgrall.rmm.network.SocketHandler;
import fr.gabrielabgrall.rmm.utils.Debug;

public class ServerApp {
    
    protected Server server;
    protected Map<String, AuthenticatedWorker> authenticatedWorkers = new HashMap<>();
    protected Map<SocketHandler, String> subscribeMap = new HashMap<>();

    public ServerApp(int port) {
        try {
            this.server = new Server("Server", port);

            server.getEventManager().registerListener(new ServerListener(this));

            server.start();
        } catch (IOException e) {
            Debug.log("Unable to open stream: port already used or permission denied");
        }
    }

    public boolean checkVersion(String version) {
        return SocketHandler.VERSION.split("\\.")[0].equals(version.split("\\.")[0]);
    }

    public AuthenticatedWorker registerAuthenticatedWorker(ServerWorker serverWorker, String login, String password) {
        if(!password.equals("bonjour")) return null;
        AuthenticatedWorker worker = new AuthenticatedWorker(serverWorker, login);
        serverWorker.setName(login);
        authenticatedWorkers.put(login, worker);
        Debug.log("ServerWorker authenticated");
        return worker;
    }

    public void removeAuthenticatedWorker(AuthenticatedWorker worker) {
        if(authenticatedWorkers.containsKey(worker.login)) authenticatedWorkers.remove(worker.login);
    }

    public Map<String, AuthenticatedWorker> getAuthenticatedWorkers() {
        return authenticatedWorkers;
    }

    public List<AuthenticatedWorker> getAuthServerWorkersSortedByName() {
        List<AuthenticatedWorker> cp = List.copyOf(authenticatedWorkers.values());
        cp.sort(new Comparator<AuthenticatedWorker>() {
            public int compare(AuthenticatedWorker o1, AuthenticatedWorker o2) {
                return o1.getLogin().compareTo(o2.getLogin());
            };
        });
        return cp;
    }

    public void subscribe(SocketHandler socketHandler, String client) {
        subscribeMap.put(socketHandler, client);
    }

    public void unsubscribe(SocketHandler socketHandler) {
        subscribeMap.put(socketHandler, null);
    }

    public Map<SocketHandler, String> getSubscribeMap() {
        return subscribeMap;
    }
}
