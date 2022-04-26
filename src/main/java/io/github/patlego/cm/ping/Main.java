package io.github.patlego.cm.ping;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.Gson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.patlego.cm.ping.models.CMInstance;
import io.github.patlego.cm.ping.models.AppConfig;
import io.github.patlego.cm.ping.reader.FileCMReader;
import io.github.patlego.cm.ping.writer.CMWriter;
import io.github.patlego.cm.ping.writer.CMWriterException;
import io.github.patlego.cm.ping.writer.FileCMWriter;
import io.github.patlego.cm.ping.reader.CMReader;
import io.github.patlego.cm.ping.reader.CMReaderException;

/**
 * https://howtodoinjava.com/java/multi-threading/callable-future-example/
 */
public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) {
        logger.info("Starting up Cloud Manager poke utility");
        
        AppConfig appConfig = null;

        // Run the program
        do {
            try {
                // Load the JSON config into memory
                List<String> arguments = getArgs(args);
                CMReader fileReader = new FileCMReader();

                JsonObject appConfigJson = fileReader.getCMInstances(arguments.get(1));
                appConfig = gson.fromJson(appConfigJson, AppConfig.class);

                ExecutorService executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
                List<Future<CMInstance>> resultList = new LinkedList<>();
                for (CMInstance instance : appConfig.getCmInstances()) {
                    Callable<CMInstance> cmPoke = new CMPing(instance);
                    Future<CMInstance> result = executor.submit(cmPoke);
                    resultList.add(result);
                }

                for (Future<CMInstance> futureCM : resultList) {
                    AppConfig list = new AppConfig();
                    // This is a blocking call
                    CMInstance instance = futureCM.get();
                    list.setCmInstance(instance);
                }

                executor.shutdown();

                CMWriter writer = new FileCMWriter();
                writer.write(gson.toJson(appConfig), arguments.get(1));

                logger.info(String.format("About to sleep for %d seconds", appConfig.getSleep()));
                Thread.sleep(appConfig.getSleep() * 1000);
            } catch (CMReaderException e) {
                logger.error(e.getMessage(), e);
                System.exit(1);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            } catch (ExecutionException e) {
                logger.error(e.getMessage(), e);
            } catch (JsonSyntaxException e) {
                logger.error(e.getMessage(), e);
                System.exit(1);
            } catch (CMWriterException e) {
                logger.error(e.getMessage(), e);
                System.exit(1);
            }
        } while(Boolean.FALSE.equals(appConfig.isTerminated()));

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
