package io.github.patlego.cm.ping.reader;

import com.google.gson.JsonArray;

public interface CMReader {
    
    public JsonArray getCMInstances(String location) throws CMReaderException;

}
