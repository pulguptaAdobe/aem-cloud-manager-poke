package io.github.patlego.cm.ping.models;

import java.util.LinkedList;
import java.util.List;

public class CMList {

    public List<CMInstance> cmInstances;

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
