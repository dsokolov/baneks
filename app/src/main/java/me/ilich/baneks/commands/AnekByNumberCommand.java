package me.ilich.baneks.commands;

public class AnekByNumberCommand extends AnekCommand {

    private final String number;

    public AnekByNumberCommand(Callback callback, String number) {
        super(callback);
        this.number = number;
    }

    @Override
    protected String getUrl() {
        return String.format("http://baneks.ru/%s", number);
    }

}
