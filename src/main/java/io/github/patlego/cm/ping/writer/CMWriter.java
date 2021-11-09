package io.github.patlego.cm.ping.writer;

import com.google.gson.JsonObject;

public interface CMWriter {
    
    public void write(JsonObject cmList, String location) throws CMWriterException;
}
