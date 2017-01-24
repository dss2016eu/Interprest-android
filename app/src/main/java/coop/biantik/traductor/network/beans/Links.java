
package coop.biantik.traductor.network.beans;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class Links implements Serializable {

    @Expose
    private Self self;

    /**
     * 
     * @return
     *     The self
     */
    public Self getSelf() {
        return self;
    }

    /**
     * 
     * @param self
     *     The self
     */
    public void setSelf(Self self) {
        this.self = self;
    }

}
