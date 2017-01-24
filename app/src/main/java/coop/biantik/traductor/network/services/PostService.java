package coop.biantik.traductor.network.services;

import java.util.List;

import coop.biantik.traductor.model.Post;
import coop.biantik.traductor.network.beans.PostCriteria;
import retrofit.RestAdapter;


public interface PostService {

	void setRestAdapter(RestAdapter restAdapter);

	List<Post> findAllByCriteria(PostCriteria criteria);

	Post send(String id);

}