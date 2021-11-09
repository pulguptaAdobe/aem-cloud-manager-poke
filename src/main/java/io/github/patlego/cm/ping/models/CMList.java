package io.github.patlego.cm.ping.models;

import java.util.List;

public class CMList {

    public List<CMInstance> cmInstances;

    public List<CMInstance> getCmInstances() {
        return cmInstances;
    }

    public void setCmInstances(List<CMInstance> cmInstances) {
        this.cmInstances = cmInstances;
    }
}
