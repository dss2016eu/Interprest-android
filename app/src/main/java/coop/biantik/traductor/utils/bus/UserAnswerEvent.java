package coop.biantik.traductor.utils.bus;

import coop.biantik.traductor.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserAnswerEvent extends BaseAnswerEvent {

    private User user;


}