package coop.biantik.traductor.network;

import android.content.Context;

import coop.biantik.traductor.utils.NetworkUtils;
import retrofit.RequestInterceptor;

public class SessionRequestInterceptor implements RequestInterceptor
{
    Context context;

    public SessionRequestInterceptor(Context context){
        this.context = context;
    }

    @Override
    public void intercept(RequestFacade request)
    {
       if  (NetworkUtils.isNetworkAvailable(context)){
           //UIUtils.showMessage(context, "La cosa va perfecta");
       }
    }
}