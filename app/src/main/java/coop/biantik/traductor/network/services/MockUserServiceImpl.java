package coop.biantik.traductor.network.services;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import coop.biantik.traductor.Constants;
import coop.biantik.traductor.model.Language;
import coop.biantik.traductor.model.User;
import retrofit.RestAdapter;


public class MockUserServiceImpl implements UserService {

    @Override
    public void setRestAdapter(RestAdapter restAdapter) {

    }

    @Override
    public User findMe() {
        return findOne("translator1");
    }

    @Override
    public User findOne(String username) {
        return getUser(username);
    }

    @Override
    public User login(String username) {

        User user = getUser(username);
        if (!(username.equals(Constants.MOCK_ADMIN_USER) || username.equals(Constants.MOCK_TRANSLATOR_USER1) || username.equals(Constants.MOCK_TRANSLATOR_USER2)))
            user = null;
        return user;
    }

    @Override
    public List<User> findTranslators() {

        List<User> translators = new ArrayList<>();
        User user = new User();
        user.setUsername("admin2");
        user.setAdmin(true);
        Language lang = new Language();
        lang.setCode("es");
        lang.setName("Castellano");
        user.setTranslationLanguage("es");
        user.setTranslator(true);
        translators.add(user);
        User user1 = new User();
        user1.setUsername("translator1");
        user1.setAdmin(false);
        lang = new Language();
        lang.setCode("eu");
        lang.setName("Euskera");
        user.setTranslationLanguage("eu");
        user1.setTranslator(true);
        translators.add(user1);
        User user2 = new User();
        user2.setUsername("translator2");
        user2.setAdmin(false);
        lang = new Language();
        lang.setCode("fr");
        lang.setName("Frances");
        user.setTranslationLanguage("fr");
        user2.setTranslator(true);
        translators.add(user2);
        return translators;
    }


    @Nullable
    private User getUser(String username) {
        User user = new User();
        user.setUsername(username);


        if (username.equals(Constants.MOCK_ADMIN_USER)) {
            user.setAdmin(true);
            user.setTranslator(false);
        } else if (username.equals(Constants.MOCK_TRANSLATOR_USER1)) {
            user.setAdmin(false);
            user.setTranslator(true);
            Language lang = new Language();
            lang.setCode("es");
            lang.setName("Castellano");
            user.setTranslationLanguage("es");
        } else if (username.equals(Constants.MOCK_TRANSLATOR_USER2)) {
            user.setAdmin(false);
            user.setTranslator(true);
            Language lang = new Language();
            lang.setCode("eu");
            lang.setName("Euskera");
            user.setTranslationLanguage("eu");
        } else user = null;
        return user;
    }

}