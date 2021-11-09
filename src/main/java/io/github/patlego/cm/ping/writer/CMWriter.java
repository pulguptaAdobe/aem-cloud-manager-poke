package io.github.patlego.cm.ping.writer;

import com.google.gson.JsonArray;

public interface CMWriter {
    
    public void write(JsonArray cmList, String location) throws CMWriterException;
}
