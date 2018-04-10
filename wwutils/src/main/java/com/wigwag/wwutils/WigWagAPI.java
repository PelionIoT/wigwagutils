package com.wigwag.wwutils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

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

    private static WigWagAPI wigWagAPI;

    private Response.WWListener<String> wwListener;
    private Response.WWErrorListener wwErrorListener;

    private Context context;
    public  WigWagAPI(Context context){
        this.context = context;
    }

    public static WigWagAPI getInstance(Context context) {
        if (wigWagAPI == null)
            wigWagAPI = new WigWagAPI(context.getApplicationContext());

        return wigWagAPI;
    }
    public void doLogin(final Response.WWListener<String> listener, final Response.WWErrorListener wwErrorListener){
        String url = "http://ui2n.com/test2.php";
        wwListener = listener;
        this.wwErrorListener = wwErrorListener;
        new OkHttpAync().execute();
    }



    class OkHttpAync extends AsyncTask<Void, Void, Object> {
        private String TAG = this.getClass().getSimpleName();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Void... voids) {
            return getHttpResponse();
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            if (result != null) {
                //updateRow(result);
                wwListener.onWWResponse(result.toString());
            }else{
                wwErrorListener.onWWErrorResponse();
                //onRefresh();
            }
        }
    }




    public Object getHttpResponse() {
        OkHttpClient httpClient = new OkHttpClient();

        JSONObject commentObject = null;
        try {
            commentObject = new JSONObject().put("dfsdfs","fsdfsfsd");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        MediaType CONTENT_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
        String url = "http://ui2n.com/test2.php";
        RequestBody requestBody = RequestBody.create(CONTENT_TYPE_JSON,commentObject.toString());

        okhttp3.Request request = new okhttp3.Request.Builder()
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
