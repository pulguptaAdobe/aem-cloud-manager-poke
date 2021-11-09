package io.github.patlego.cm.ping.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import org.apache.commons.io.IOUtils;

public class FileCMReader implements CMReader {

    @Override
    public JsonArray getCMInstances(String location) throws CMReaderException {
        try {
            if (location == null || location.isEmpty()) {
                throw new CMReaderException("File Location provided is invalid, please provide a valid file");
            }

            FileInputStream cmLocation = new FileInputStream(new File(location));
            return JsonParser.parseString(IOUtils.toString(cmLocation, StandardCharsets.UTF_8)).getAsJsonArray();

        } catch (JsonSyntaxException | IOException e) {
            throw new CMReaderException(e.getMessage(), e);
        }
    }

}
