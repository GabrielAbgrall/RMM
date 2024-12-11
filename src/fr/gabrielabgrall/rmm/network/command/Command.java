package fr.gabrielabgrall.rmm.network.command;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import fr.gabrielabgrall.rmm.InvalidArgumentException;

public class Command {

    public final static String INTERNAL_COMMAND_PREFIX = "!";
    public final static String ENTRY_SEPARATOR = " ";
    public final static String ARGS_SEPARATOR = "\n";

    protected String commandHeader;
    protected Map<String, String> args; 
    
    public Command(String commandHeader, String... args) {
        StringBuilder sb = new StringBuilder();
        for (String arg : args) {
            sb.append(ARGS_SEPARATOR).append(arg);
        }
        buildCommand(commandHeader + sb.toString());
    }

    public Command(String raw) {
        buildCommand(raw);
    }

    protected void buildCommand(String raw) {
        String[] split = raw.split(ARGS_SEPARATOR);
        this.commandHeader = split[0].toUpperCase();
        buildArgs(Arrays.copyOfRange(split, 1, split.length));
    }

    protected void buildArgs(String[] rawArgs) {
        args = new HashMap<>();
        for(String arg : rawArgs) {
            arg = arg.trim();
            String k = arg.split(ENTRY_SEPARATOR)[0];
            if(k.length()==0) continue; // Skip empty line
            String v = null;
            if(arg.length() > k.length()+1) v = arg.substring(k.length()+1, arg.length());
            args.put(k, v);
        }
    }

    public String getCommandHeader() {
        return commandHeader;
    }

    public Map<String, String> getArgs() {
        return args;
    }

    @Override
    public String toString() {
        String raw = commandHeader;
        for (Map.Entry<String, String> entry : args.entrySet()) {
            raw += ARGS_SEPARATOR + entry.getKey() + ENTRY_SEPARATOR + entry.getValue();
        }
        return raw;
    }

    public void checkForArgs(String... args) throws InvalidArgumentException {
        for (String a : args) {
            if(!this.getArgs().containsKey(a)) throw new InvalidArgumentException("Missing arguments");
        }
    }
}
