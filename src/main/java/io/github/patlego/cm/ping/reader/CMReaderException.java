package io.github.patlego.cm.ping.reader;

public class CMReaderException extends Exception {
    
    public CMReaderException(String msg, Throwable t) {
        super(msg, t);
    }

    public CMReaderException(String msg) {
        super(msg);
    }
}
