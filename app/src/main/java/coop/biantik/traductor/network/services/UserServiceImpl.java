package coop.biantik.traductor.network.services;

import java.util.List;

import coop.biantik.traductor.model.User;
import coop.biantik.traductor.network.beans.Login;
import coop.biantik.traductor.network.daos.UserDao;
import retrofit.RestAdapter;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public UserServiceImpl(RestAdapter restAdapter) {
        this.userDao = restAdapter.create(UserDao.class);
    }

    public void setRestAdapter(RestAdapter restAdapter){
        userDao = restAdapter.create(UserDao.class);
    }

    @Override
    public User findOne(String id) {
        return userDao.findOne(id);
    }


    @Override
    public User login(String username) {
        User user = null;
        Login login = new Login();
        login.setUsername(username);
        user = userDao.login(login);
        return user;
    }

    @Override
    public User findMe()
    {
        return userDao.findMe();
    }

    @Override
    public List<User> findTranslators() {
        return userDao.findTranslators();
    }



}