package com.wigwag.wwutils.ErrorManager;

import com.wigwag.wwutils.ResponseManager.NetworkResponse;

@SuppressWarnings("serial")
public class WigWagError extends Exception {
    //private long networkTimeMs;

    public final int statusCode;
    public final String errorMessage;


    public WigWagError(int statusCode,String errorMessage) {
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }

    /*public WigWagError(String exceptionMessage) {
       super(exceptionMessage);
       networkResponse = null;
    }

    public WigWagError(String exceptionMessage, Throwable reason) {
        super(exceptionMessage, reason);
        networkResponse = null;
    }

    public WigWagError(Throwable cause) {
        super(cause);
        networkResponse = null;
    }

    public long getNetworkTimeMs() {
       return networkTimeMs;
    }*/
}
