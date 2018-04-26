package com.wigwag.wwutils.Database;


import android.content.Context;
import android.content.SharedPreferences;

import com.wigwag.wwutils.Interface.StorageMethods;

public class Database {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor spfeditor;
    private static Database database;
    private static Context context;

    public Database(Context context){
        sharedPreferences = context.getSharedPreferences(APIContants.KEY_STORAGE_NAME,Context.MODE_PRIVATE);
        spfeditor = sharedPreferences.edit();
        this.context = context;
    }
    public static Database getInstance(Context context){
        if (database == null)
            database = new Database(context);
        return database;
    }


    //getters...
    public String getStorage(String storageMethods){ return sharedPreferences.getString(storageMethods, StorageMethods.Type.DEFUALT_VALUE); }


    //setters...
    public void setDCS(String dcs){
        spfeditor.putString(StorageMethods.Type.DCS,dcs);
        spfeditor.commit();
    }

    public void setAccessToken(String token){
        spfeditor.putString(StorageMethods.Type.ACCESS_TOKEN,token);
        spfeditor.commit();
    }

    public void setAccountId(String id){
        spfeditor.putString(StorageMethods.Type.AccountID,id);
        spfeditor.commit();
    }


}
