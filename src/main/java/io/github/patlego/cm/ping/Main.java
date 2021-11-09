package io.github.patlego.cm.ping;

import java.util.List;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import io.github.patlego.cm.ping.models.CMList;
import io.github.patlego.cm.ping.reader.FileCMReader;
import io.github.patlego.cm.ping.reader.CMReader;
import io.github.patlego.cm.ping.reader.CMReaderException;

public class Main {
    
    public static void main(String[] args) {
        try {
            List<String> arguments = getArgs(args);
            CMReader fileReader = new FileCMReader();
    
            JsonObject cmInstances = fileReader.getCMInstances(arguments.get(1));
            CMList cmList = new GsonBuilder().create().fromJson(cmInstances, CMList.class);
        } catch (CMReaderException e) {

        }
        
    }

    public static List<String> getArgs(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Invalid number of CLI arguments provided, please make sure to execute the cli with --location <location>");
        }

        String locationStr = args[0];
        String locationPath = args[1];

        if (!locationStr.equals("--location")) {
            throw new IllegalArgumentException("Invalid command, please make sure to execute the cli with --location <location>");
        }

        return List.of(locationStr, locationPath);
    }
}
