package coop.biantik.traductor.utils.bus;

import coop.biantik.traductor.model.Info;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class LoadInfoAnswerEvent extends BaseAnswerEvent{

    private Info info;


    public LoadInfoAnswerEvent(Info info){
        this.info = info;
    }
}