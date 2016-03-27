package me.ilich.baneks.commands.errors;

public class ExceptionErrorResponse extends ErrorResponse {

    private Exception exception;

    public ExceptionErrorResponse(Exception exception) {
        this.exception = exception;
    }

}
