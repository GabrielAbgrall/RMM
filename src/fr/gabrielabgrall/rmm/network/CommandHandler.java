package fr.gabrielabgrall.rmm.network;

import java.util.Map;

public interface CommandHandler {
    
    void handleCommand(String command, Map<String, String> args);
}
