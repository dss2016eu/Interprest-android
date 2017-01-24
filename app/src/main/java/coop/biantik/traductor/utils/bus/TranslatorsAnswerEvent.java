package coop.biantik.traductor.utils.bus;

import java.util.List;

import coop.biantik.traductor.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TranslatorsAnswerEvent extends BaseAnswerEvent {

    private List<User> translators;

    public TranslatorsAnswerEvent(List<User> translators){
        this.translators = translators;
    }


}