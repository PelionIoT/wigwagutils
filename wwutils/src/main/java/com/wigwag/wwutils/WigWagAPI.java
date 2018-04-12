package com.wigwag.wwutils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import static android.content.ContentValues.TAG;


/**
 * Created by WigWag on 2/28/18.
 */

public class WigWagAPI {


    private final String TAG = getClass().getSimpleName();

    private static WigWagAPI wigWagAPI;
    private static Context mContext;
    private static Response.WWListener<String> listener;
    private static Response.WWErrorListener errorListener;
    public  WigWagAPI(Context context){
        mContext = context;
    }

    public static WigWagAPI getInstance(Context context,Response.WWListener<String> wwListener,Response.WWErrorListener wwErrorListener) {
        if (wigWagAPI == null)
            wigWagAPI = new WigWagAPI(context);
            listener = wwListener;
            errorListener = wwErrorListener;

        return wigWagAPI;
    }


    public void doLogin(String grantType,String username,String password){
        new OkHttpAync(APIContants.LOGIN,getObject(
                com.wigwag.wwutils.Request.Login.ObjectKeys,
                grantType,
                username,
                password)
        ).execute();
    }


    public void listSites(String limit,String last){

    }



    private void requestBuilder(int requestType){

    }

    class OkHttpAync extends AsyncTask<Void, Void, Object> {
        private String TAG = this.getClass().getSimpleName();
        private String API_URL;
        private JSONObject jsonObject;

        public OkHttpAync(String url,JSONObject jsonObject) {
            this.jsonObject = jsonObject;
            API_URL = url;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Void... voids) {
            return getHttpResponse(API_URL,jsonObject);
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            if (result != null) {
                //updateRow(result);
                listener.onWWResponse(result.toString());
            }else{
                errorListener.onWWErrorResponse();
                //onRefresh();
            }
        }
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



    private Object getHttpResponse(String url,JSONObject requestObject) {
        Log.e(TAG,requestObject.toString());
        OkHttpClient httpClient = new OkHttpClient();

        MediaType CONTENT_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(CONTENT_TYPE_JSON,requestObject.toString());

        okhttp3.Request request = new okhttp3.Request.Builder()
                .addHeader("Authorization","Bearer "+Database.getInstance(mContext).getStorage(StorageMethods.Type.ACCESS_TOKEN))
                .url(url)
                .post(requestBody)
                .build();

        okhttp3.Response response = null;
        try {
            response = httpClient.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            Log.e(TAG, "error in getting response get request okhttp");
        }
        return null;
    }
}
