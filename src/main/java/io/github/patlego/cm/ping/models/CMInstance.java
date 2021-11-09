package io.github.patlego.cm.ping.models;

public class CMInstance {

    // Cloud Manager URL to invoke
    private String url;

    // Time in seconds
    private long interval;
    
    // Date Time Representing last invocation
    private String lastInvocation;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public String getLastInvocation() {
        return lastInvocation;
    }

    public void setLastInvocation(String lastInvocation) {
        this.lastInvocation = lastInvocation;
    }

    
    
}
