package io.github.patlego.cm.ping;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.patlego.cm.ping.models.CMInstance;
import io.github.patlego.cm.ping.models.CMList;
import io.github.patlego.cm.ping.reader.FileCMReader;
import io.github.patlego.cm.ping.reader.CMReader;
import io.github.patlego.cm.ping.reader.CMReaderException;

/**
 * https://howtodoinjava.com/java/multi-threading/callable-future-example/
 */
public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Starting up Cloud Manager poke utility");
        while (Boolean.TRUE.equals(true)) {

            try {
                List<String> arguments = getArgs(args);
                CMReader fileReader = new FileCMReader();

                JsonObject cmInstances = fileReader.getCMInstances(arguments.get(1));
                CMList cmList = new GsonBuilder().create().fromJson(cmInstances, CMList.class);
                List<Callable> cmCallablePokes = new LinkedList<>();
                for (CMInstance instance : cmList.cmInstances) {
                    cmCallablePokes.add(new CMPing(instance));
                }

                Thread.sleep(60000);
            } catch (CMReaderException e) {
                logger.error(e.getMessage(), e);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        }

    }

    public static List<String> getArgs(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException(
                    "Invalid number of CLI arguments provided, please make sure to execute the cli with --location <location>");
        }

        String locationStr = args[0];
        String locationPath = args[1];

        if (!locationStr.equals("--location")) {
            throw new IllegalArgumentException(
                    "Invalid command, please make sure to execute the cli with --location <location>");
        }

        return List.of(locationStr, locationPath);
    }
}
