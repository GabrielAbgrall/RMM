package fr.gabrielabgrall.dmst;

import fr.gabrielabgrall.dmst.network.client.Client;
import fr.gabrielabgrall.dmst.network.server.Server;
import fr.gabrielabgrall.dmst.utils.Debug;

public class DSAST {

    public static void main(String[] args) {
        Debug.setDebug(true);

        for (String a : args) {
            switch (a) {
                case "-s":
                case "--server":
                    runServer(args);
                    break;
                case "-c":
                case "--client":
                    runClient(args);
                    break;
                case "-u":
                case "--user-client":
                    runUserClient(args);
                    break;
                case "-h":
                case "--help":
                default:
                    break;
            }
        }
    }

    public static void runServer(String[] args) {
        try {
            if(args.length < 2) {
                Debug.log("Invalid arguments. Format : --server [port]");
                return;
            }
            int port = Integer.parseInt(args[1]);
            Server server = new Server(port);
            server.start();
        } catch (NumberFormatException e) {
            Debug.log("Invalid port number: ", args[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void runClient(String[] args) {
        try {
            if(args.length < 4) {
                Debug.log("Invalid arguments. Format : --client [name] [host] [port]");
                return;
            }
            String name = args[1];
            String host = args[2];
            int port = Integer.parseInt(args[3]);
            Client client = new Client(name, host, port);
            client.start();

            /*String c = "auth";
            Map<String, String> a = new HashMap<>();
            a.put("public_key", "thisisthetestpublickey");
            a.put("private_key", "thisisthetestprivatekey");
            client.sendCommand(c, a);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void runUserClient(String[] args) {
        // TODO
    }
}
