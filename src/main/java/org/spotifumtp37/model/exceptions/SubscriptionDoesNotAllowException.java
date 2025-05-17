package org.spotifumtp37.model.exceptions;

public class SubscriptionDoesNotAllowException extends Exception {
    public SubscriptionDoesNotAllowException(String message) {
        super(message);
    }
}
