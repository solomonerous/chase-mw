package com.obulex.jpos;

public class InvalidMessageTypeException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public InvalidMessageTypeException()
    {
        super();
    }

    public InvalidMessageTypeException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Invalid ISO Message type";
    }
}
