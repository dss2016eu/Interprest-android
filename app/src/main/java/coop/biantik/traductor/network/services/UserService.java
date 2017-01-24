package coop.biantik.traductor.network.services;

import java.util.List;

import coop.biantik.traductor.model.User;
import retrofit.RestAdapter;

public interface UserService {

	void setRestAdapter(RestAdapter restAdapter);

    User findMe();

	User findOne(String id);

	User login(String username);

	List<User> findTranslators();


}