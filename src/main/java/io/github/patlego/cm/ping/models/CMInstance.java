package io.github.patlego.cm.ping.models;

public class CMInstance {

    private final transient String DEFAULT_PATH = "/content/dam.1.json";

    // Cloud Manager URL to invoke
    private String url;

    // Time in seconds
    private long interval;
    
    // Date Time Representing last invocation
    private String lastInvocation;

    // Authentication credentials used when invoking the CM instance
    private Authentication auth;

    // The path to invoke on the instance, if not defined then it will default to /content/dam.1.json
    // Make sure that the is not blocked by the dispatcher
    private String path;

    public String getPath() {
        if (this.path == null) {
            return DEFAULT_PATH;
        } else {
            return this.path;
        }
    }
    public void setPath(String path) {
        this.path = path;
    }
    public Authentication getAuth() {
        return this.auth;
    }
    public void setAuth(Authentication auth) {
        this.auth = auth;
    }
    public boolean hasAuth() {
        return this.auth != null;
    }

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
