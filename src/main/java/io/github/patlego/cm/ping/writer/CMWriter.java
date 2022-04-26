package io.github.patlego.cm.ping.writer;

public interface CMWriter {
    
    public void write(String appConfig, String location) throws CMWriterException;
}
