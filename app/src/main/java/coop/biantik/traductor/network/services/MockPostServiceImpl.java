package coop.biantik.traductor.network.services;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import coop.biantik.traductor.model.Post;
import coop.biantik.traductor.network.beans.PostCriteria;
import retrofit.RestAdapter;


public class MockPostServiceImpl implements PostService {

    @Override
    public void setRestAdapter(RestAdapter restAdapter) {

    }

    @Override
    public List<Post> findAllByCriteria(PostCriteria criteria) {
        List<Post> posts = new ArrayList<Post>();
        int firstElement = criteria.getPage() *  criteria.getSize();
        int size = firstElement + criteria.getSize().intValue();
        Log.i("firstElement:", firstElement + "");
        Log.i("size" + firstElement, "" + size);
        for (int i = firstElement; i < size ; i++) {
            Post post = new Post();
            post.setTitle("Título" + i);
            post.setDescription("Description" + i);
            post.setImage("http://i.imgur.com/DvpvklR.png");
            posts.add(post);

        }
        return posts;
    }

    @Override
    public Post send(String id) {
        return null;
    }

}