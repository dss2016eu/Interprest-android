package coop.biantik.traductor.utils.bus;

import coop.biantik.traductor.model.Event;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class LoadEventAnswerEvent extends BaseAnswerEvent{

    private Event event;


    public LoadEventAnswerEvent(Event event){
        this.event = event;
    }
}