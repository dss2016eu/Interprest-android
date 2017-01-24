package coop.biantik.traductor.utils.bus;

import coop.biantik.traductor.model.Post;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SendPostAnswerEvent extends BaseAnswerEvent{

    private Post post;


    public SendPostAnswerEvent(Post post){
        this.post = post;
    }
}