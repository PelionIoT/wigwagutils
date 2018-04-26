package com.wigwag.wwutils;

import com.wigwag.wwutils.Database.APIContants;

import org.json.JSONObject;

public abstract class Request<T> implements Comparable<Request<T>> {

    public interface Methods {
        String GET = "GET";
        String POST = "POST";
        String PUT = "PUT";
        String DELETE = "DELETE";
        String PATCH = "PATCH";
    }


    public interface Types {
        int JsonRequest = 0;
        int StringRequest = 1;
    }


    public interface Login{
        String API_URL = APIContants.LOGIN;
        String requestMethod = Methods.POST;
        //int requestMethod = Types.JsonRequest;
        String[] ObjectKeys = {"grant_type","username","password"};
    }

    public interface Accounts{
        String API_URL = APIContants.ACCOUNT;
        String requestMethod = Methods.GET;
        //int requestMethod = Types.StringRequest;
        String[] ObjectKeys = {};
    }

    public interface Sites{
        String API_URL = APIContants.SITES;
        String requestMethod = Methods.GET;
        //int requestMethod = Types.StringRequest;
        String[] ObjectKeys = {};
    }
}
