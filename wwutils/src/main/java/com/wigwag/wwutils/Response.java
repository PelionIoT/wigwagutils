package com.wigwag.wwutils;

public class Response<T> {

    /**
     * Callback interface for delivering parsed responses.
     */
    public interface WWListener<T> {
        /**
         * Called when a response is received.
         */
        public void onWWResponse(T response);
    }

    /**
     * Callback interface for delivering error responses.
     */
    public interface WWErrorListener {
        /**
         * Callback method that an error has been occurred with the
         * provided error code and optional user-readable message.
         */
        public void onWWErrorResponse();
        //public void onErrorResponse(VolleyError error);
    }

}