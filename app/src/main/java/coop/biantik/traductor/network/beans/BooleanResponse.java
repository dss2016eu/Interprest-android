package coop.biantik.traductor.network.beans;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class BooleanResponse implements Serializable {

    private static final long serialVersionUID = 389362879619437181L;

    boolean ok;
}
