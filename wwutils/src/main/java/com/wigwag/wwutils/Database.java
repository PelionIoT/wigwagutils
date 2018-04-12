package com.wigwag.wwutils;


import android.content.Context;
import android.content.SharedPreferences;

public class Database {
    private SharedPreferences sharedPreferences;
    private static Database database;
    private static Context context;

    public Database(Context context){
        sharedPreferences = context.getSharedPreferences(APIContants.KEY_STORAGE_NAME,Context.MODE_PRIVATE);
        this.context = context;
    }
    public static Database getInstance(Context context){
        if (database == null)
            database = new Database(context);
        return database;
    }

    public String getStorage(String storageMethods){
        return sharedPreferences.getString(storageMethods,StorageMethods.Type.DEFUALT_VALUE);
    }
}
