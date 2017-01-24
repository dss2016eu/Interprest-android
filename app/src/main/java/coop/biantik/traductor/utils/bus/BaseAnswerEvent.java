package coop.biantik.traductor.utils.bus;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class BaseAnswerEvent {

    private String message;


}