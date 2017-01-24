package coop.biantik.traductor.utils.bus;

import coop.biantik.traductor.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class LoginAnswerEvent extends BaseAnswerEvent {

    private User user;


    public LoginAnswerEvent(User user){
        this.user = user;
    }

}