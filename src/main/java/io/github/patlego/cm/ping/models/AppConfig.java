package io.github.patlego.cm.ping.models;

import java.util.LinkedList;
import java.util.List;

public class AppConfig {

    private final transient long DEFAULT_SLEEP_MS = 5000;

    private List<CMInstance> cmInstances;
    private boolean terminate;
    private long sleep;

    public long getSleep() {
        if (this.sleep <= 0) {
            return DEFAULT_SLEEP_MS;
        }
        return sleep;
    }

    public void setSleep(long sleep) {
        this.sleep = sleep;
    }

    public boolean isTerminated() {
        return terminate;
    }

    public void setTerminate(boolean terminate) {
        this.terminate = terminate;
    }

    public List<CMInstance> getCmInstances() {
        if (cmInstances == null) {
            return new LinkedList<>();
        }
        return cmInstances;
    }

    public void setCmInstances(List<CMInstance> cmInstances) {
        this.cmInstances = cmInstances;
    }

    public void setCmInstance(CMInstance cmInstance) {
        if (this.cmInstances == null) {
            this.cmInstances = new LinkedList<>();
        }

        this.cmInstances.add(cmInstance);
    }
}
