package me.ilich.baneks.commands;

public class ExceptionErrorResponse extends ErrorResponse {

    private Exception exception;

    public ExceptionErrorResponse(Exception exception) {
        this.exception = exception;
    }

}
