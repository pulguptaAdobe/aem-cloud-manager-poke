package io.github.patlego.cm.ping.writer;

public class CMWriterException extends Exception {

    public CMWriterException(String msg, Throwable t) {
        super(msg, t);
    }

    public CMWriterException(String msg) {
        super(msg);
    }
    
}
