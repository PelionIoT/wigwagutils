package com.wigwag.wwutils;

import org.json.JSONObject;

public abstract class Request<T> implements Comparable<Request<T>> {

    public interface Methods {
        int GET = 0;
        int POST = 1;
        int PUT = 2;
        int DELETE = 3;
        int HEAD = 4;
        int OPTIONS = 5;
        int TRACE = 6;
        int PATCH = 7;
    }


    public interface Types {
        int JsonRequest = 0;
    }


    public interface Login{
        int requestType = Methods.POST;
        int requestMethod = Types.JsonRequest;
        String[] ObjectKeys = {"grant_type","username","password"};
    }
}
