package coop.biantik.traductor.utils.bus;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ErrorAnswerEvent extends BaseAnswerEvent{

    private int errorCode;


    public ErrorAnswerEvent(int errorCode){
        this.errorCode = errorCode;
    }
}