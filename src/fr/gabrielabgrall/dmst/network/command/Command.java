package fr.gabrielabgrall.dmst.network.command;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import fr.gabrielabgrall.dmst.network.exception.MalformedCommandException;

public class Command {

    public final static String ENTRY_SEPARATOR = " ";
    public final static String ARGS_SEPARATOR = "\n";

    protected String commandHeader;
    protected Map<String, String> args; 
    
    public Command(String commandHeader, String... args) {
        StringBuilder sb = new StringBuilder();
        for (String arg : args) {
            sb.append(ARGS_SEPARATOR).append(arg);
        }
        try {
            buildCommand(commandHeader + sb.toString());
        } catch (MalformedCommandException e) {
            e.printStackTrace();
        }
    }

    protected Command(String raw) {
        try {
            buildCommand(raw);
        } catch(MalformedCommandException e) {
            e.printStackTrace();
        }
    }

    protected void buildCommand(String raw) throws MalformedCommandException {
        String[] split = raw.split(ARGS_SEPARATOR);

        this.commandHeader = split[0].toUpperCase();
        if(this.commandHeader.length()==0) throw new MalformedCommandException();

        buildArgs(Arrays.copyOfRange(split, 1, split.length));
    }

    protected void buildArgs(String[] rawArgs) {
        args = new HashMap<>();
        for(String arg : rawArgs) {
            arg = arg.trim();
            String k = arg.split(ENTRY_SEPARATOR)[0];
            if(k.length()==0) continue; // Skip empty line
            String v = arg.substring(k.length(), arg.length());
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
}
