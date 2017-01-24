package coop.biantik.traductor.network;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.Date;

import javax.inject.Singleton;

import coop.biantik.traductor.R;
import coop.biantik.traductor.network.enums.PostStatusEnum;
import coop.biantik.traductor.network.services.EventService;
import coop.biantik.traductor.network.services.EventServiceImpl;
import coop.biantik.traductor.network.services.GeoService;
import coop.biantik.traductor.network.services.GeoServiceImpl;
import coop.biantik.traductor.network.services.PostService;
import coop.biantik.traductor.network.services.PostServiceImpl;
import coop.biantik.traductor.network.services.UserService;
import coop.biantik.traductor.network.services.UserServiceImpl;
import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

@Module
public class NetworkModule {

    private Context context;

    public NetworkModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public RestAdapter providePublicRestAdapter(Gson gson) {
        RestAdapter restAdapter = new RestAdapter.Builder().setRequestInterceptor(new SessionRequestInterceptor(context)).setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(context.getResources().getString(R.string.server_public_url)).setConverter(new GsonConverter(gson)).build();

        return restAdapter;
    }

    @Provides
    @Singleton
    public PostService providePostService(RestAdapter restAdapter) {
        return new PostServiceImpl(restAdapter);
    }


    @Provides
    @Singleton
    public EventService provideEventService(RestAdapter restAdapter) {
        return new EventServiceImpl(restAdapter);
    }

    @Provides
    @Singleton
    public UserService provideUserService(RestAdapter restAdapter) {
        return new UserServiceImpl(restAdapter);
    }
/*
    @Provides
    @Singleton
    public EventService provideEventService(RestAdapter restAdapter) {
        return new MockEventServiceImpl();
    }
*/


    /*
   @Provides
   @Singleton
   public PostService providePostService() {
       return new MockPostServiceImpl();
   }
   */
    /*
    @Provides
    @Singleton
    public UserService provideUserService(RestAdapter restAdapter) {
        return new MockUserServiceImpl();
        //return new UserServiceImpl(context, restAdapter);
    }
*/
    @Provides
    @Singleton
    public GeoService provideGeoService() {
        return new GeoServiceImpl(context);
    }


    @Provides
    @Singleton
    public Gson provideGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }

        });

        builder.registerTypeAdapter(Date.class, new JsonSerializer<Date>() {
            @Override
            public JsonElement serialize(Date arg0, Type arg1, JsonSerializationContext arg2) {
                long result = arg0.getTime();
                return new JsonPrimitive(result);
            }
        });
        //builder.setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'");
        registerPostStatusEnum(builder);

        return builder.create();
    }


    private static void registerPostStatusEnum(GsonBuilder builder) {
        builder.registerTypeAdapter(PostStatusEnum.class, new JsonDeserializer<PostStatusEnum>() {
            public PostStatusEnum deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return PostStatusEnum.getErrorStatus(json.getAsJsonPrimitive().getAsInt());
            }

        });

    }


}