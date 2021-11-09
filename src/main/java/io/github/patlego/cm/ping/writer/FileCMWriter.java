package io.github.patlego.cm.ping.writer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.google.gson.JsonObject;

public class FileCMWriter implements CMWriter {

    @Override
    public void write(JsonObject cmList, String location) throws CMWriterException {
        try (FileOutputStream out = new FileOutputStream(new File(location))) {
            out.write(cmList.toString().getBytes());
        } catch (IOException e) {
            throw new CMWriterException(e.getMessage(), e);
        }

    }

}
