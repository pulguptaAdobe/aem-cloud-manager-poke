package io.github.patlego.cm.ping.reader;

import com.google.gson.JsonObject;

public interface CMReader {
    
    public JsonObject getCMInstances(String location) throws CMReaderException;

}
