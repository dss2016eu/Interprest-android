package coop.biantik.traductor.utils.bus;

import java.util.List;

import coop.biantik.traductor.model.Post;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class LoadPostsAnswerEvent extends BaseAnswerEvent{

    private List<Post>posts;


    public LoadPostsAnswerEvent(List<Post> posts){
        this.posts = posts;
    }
}