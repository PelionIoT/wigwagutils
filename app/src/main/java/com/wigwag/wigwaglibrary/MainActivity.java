package com.wigwag.wigwaglibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.wigwag.wwutils.Database.Database;
import com.wigwag.wwutils.ErrorManager.WigWagError;
import com.wigwag.wwutils.ResponseManager.Response;
import com.wigwag.wwutils.WigWagAPI;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Database.getInstance(this).setDCS("https://devcloud.wigwag.io");
        Database.getInstance(this).setAccountId("d55fa29dbad3418883374168ba4791fd");
        WigWagAPI.getInstance(this, new Response.WWListener<String>() {
            @Override
            public void onWWResponse(final String response) {

                try {
                    Database.getInstance(getApplicationContext()).setAccessToken(new JSONObject(response).getString("access_token"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                showMessage(response);

            }
        }, new Response.WWErrorListener() {
            @Override
            public void onWWErrorResponse(WigWagError wigWagError) {
                showMessage(Integer.toString(wigWagError.statusCode));
            }
        }).getAccounts(0,0);

/*
        }).doLogin("password","ygoyal+wwdev1@wigwag.com","yash123");
*/
    }


    private void showMessage(final String message){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
