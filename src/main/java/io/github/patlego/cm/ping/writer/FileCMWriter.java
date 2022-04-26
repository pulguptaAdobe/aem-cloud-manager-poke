package io.github.patlego.cm.ping.writer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileCMWriter implements CMWriter {

    @Override
    public void write(String appConfig, String location) throws CMWriterException {
        try (FileOutputStream out = new FileOutputStream(new File(location))) {
    
            out.write(appConfig.getBytes());
        } catch (IOException e) {
            throw new CMWriterException(e.getMessage(), e);
        }

    }

}
