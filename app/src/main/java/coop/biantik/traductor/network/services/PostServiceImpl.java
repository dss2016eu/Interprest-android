package coop.biantik.traductor.network.services;

import java.util.List;

import coop.biantik.traductor.model.Post;
import coop.biantik.traductor.network.beans.PostCriteria;
import coop.biantik.traductor.network.daos.PostDao;
import retrofit.RestAdapter;

public class PostServiceImpl implements PostService {

    private PostDao postDao;

    public PostServiceImpl(RestAdapter restAdapter) {

        postDao = restAdapter.create(PostDao.class);
    }

    public void setRestAdapter(RestAdapter restAdapter){
        postDao = restAdapter.create(PostDao.class);
    }

    @Override
    public List<Post> findAllByCriteria(PostCriteria criteria) {
        return postDao.findPosts(criteria);
    }

    @Override
    public Post send(String id) {
        return postDao.sendPost(id);
    }

}