package com.sirmabc.bulkpayments.exceptions;

public class PostMessageException extends Exception {

    public PostMessageException() {}

    public PostMessageException(String message) {
        super(message);
    }

    public PostMessageException(Throwable t) {
        super(t);
    }

    public PostMessageException(String message, Throwable t) {
        super(message, t);
    }

}