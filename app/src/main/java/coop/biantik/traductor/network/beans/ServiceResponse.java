package coop.biantik.traductor.network.beans;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ServiceResponse implements Serializable{

    int errorCode;
    Object response;
}
