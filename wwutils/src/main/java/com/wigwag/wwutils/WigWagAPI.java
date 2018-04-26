package com.wigwag.wwutils;

import android.content.Context;
import android.util.Log;

import com.wigwag.wwutils.Database.APIContants;
import com.wigwag.wwutils.Database.Database;
import com.wigwag.wwutils.ErrorManager.WigWagError;
import com.wigwag.wwutils.Interface.HttpStatus;
import com.wigwag.wwutils.Interface.StorageMethods;
import com.wigwag.wwutils.ResponseManager.NetworkResponse;
import com.wigwag.wwutils.ResponseManager.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import static com.wigwag.wwutils.Interface.HttpStatus.WW_INTERNAL_SERVER_ERROR;
import static com.wigwag.wwutils.Interface.HttpStatus.WW_NO_CLOUD;
import static com.wigwag.wwutils.Interface.HttpStatus.WW_OK;


/**
 * Created by WigWag on 2/28/18.
 */

public class WigWagAPI {


    private final String TAG = getClass().getSimpleName();

    private static WigWagAPI wigWagAPI;
    private static Context mContext;
    private String CLOUD_ADDRESS = null;
    private static Response.WWListener<String> successListener;
    private static Response.WWErrorListener errorListener;
    public  WigWagAPI(Context context){
        mContext = context;
    }

    public static WigWagAPI getInstance(Context context,Response.WWListener<String> wwListener,Response.WWErrorListener wwErrorListener) {
        if (wigWagAPI == null)
            wigWagAPI = new WigWagAPI(context);
            successListener = wwListener;
            errorListener = wwErrorListener;

        return wigWagAPI;
    }


    public void doLogin(String grantType,String username,String password){
        postHttpResponse(Request.Login.API_URL,Request.Login.requestMethod,getObject(Request.Login.ObjectKeys, grantType, username, password));
    }


    public void listSites(long limit,long last){

    }

    public void getAccounts(long limit,long last){
        getHttpResponse(APIContants.ACCOUNT,Request.Accounts.requestMethod,getObject(Request.Accounts.ObjectKeys));
    }


    private void requestBuilder(int requestType){

    }

    private JSONObject getObject(String[] keys,String... args){
        JSONObject jsonObject = new JSONObject();
        for(int i=0;i<args.length;i++){
            try {
                jsonObject.put(keys[i],args[i]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

    private void getHttpResponse(String url,String method,JSONObject requestObject) {
        CLOUD_ADDRESS = Database.getInstance(mContext).getStorage(StorageMethods.Type.DCS);
        if(CLOUD_ADDRESS!=null){
            final String baseURL = CLOUD_ADDRESS+url;
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .addHeader("Authorization","Bearer "+Database.getInstance(mContext).getStorage(StorageMethods.Type.ACCESS_TOKEN))
                    .url(baseURL)
                    .get()
                    .build();

            OkHttpClient client = new OkHttpClient();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    errorListener.onWWErrorResponse(new WigWagError(WW_INTERNAL_SERVER_ERROR,e.toString()));
                }

                @Override
                public void onResponse(Call call, final okhttp3.Response response) throws IOException {
                    if (response.isSuccessful()){
                        successListener.onWWResponse(response.body().string());
                    }else{
                        Log.e(TAG,"Loading URL : "+baseURL);
                        errorListener.onWWErrorResponse(new WigWagError(response.code(),response.message()));
                    }

                }
            });
        }else{
            errorListener.onWWErrorResponse(new WigWagError(WW_NO_CLOUD,"No Cloud Selected"));
        }

    }




    private void postHttpResponse(String url,String method,JSONObject requestObject) {
        CLOUD_ADDRESS = Database.getInstance(mContext).getStorage(StorageMethods.Type.DCS);
        if(CLOUD_ADDRESS!=null){
            final String baseURL = CLOUD_ADDRESS+url;
            MediaType CONTENT_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody requestBody = RequestBody.create(CONTENT_TYPE_JSON,requestObject.toString());

            okhttp3.Request request = new okhttp3.Request.Builder()
                    .addHeader("Authorization","Bearer "+Database.getInstance(mContext).getStorage(StorageMethods.Type.ACCESS_TOKEN))
                    .url(baseURL)
                    .post(requestBody)
                    .build();


            OkHttpClient client = new OkHttpClient();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    errorListener.onWWErrorResponse(new WigWagError(WW_INTERNAL_SERVER_ERROR,e.toString()));
                }

                @Override
                public void onResponse(Call call, final okhttp3.Response response) throws IOException {
                    if (response.isSuccessful()){
                        successListener.onWWResponse(response.body().string());
                    }else{
                        Log.e(TAG,"Loading URL : "+baseURL);
                        errorListener.onWWErrorResponse(new WigWagError(response.code(),response.message()));
                    }

                }
            });
        }else{
            errorListener.onWWErrorResponse(new WigWagError(WW_NO_CLOUD,"No Cloud Selected"));
        }

    }


}
