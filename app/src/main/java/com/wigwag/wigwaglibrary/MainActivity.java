package com.wigwag.wigwaglibrary;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.wigwag.wwutils.Response;
import com.wigwag.wwutils.WigWagAPI;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        WigWagAPI.getInstance(getApplicationContext()).doLogin(new Response.WWListener<String>() {
            @Override
            public void onWWResponse(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }, new Response.WWErrorListener() {
            @Override
            public void onWWErrorResponse() {

            }
        });

    }
}
