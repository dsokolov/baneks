package me.ilich.baneks.commands;

import me.ilich.baneks.helpers.StringHelper;
import me.ilich.baneks.data.Anek;

public class RandomAnekCommand extends AnekCommand {

    public RandomAnekCommand(Callback callback) {
        super(callback);
    }

    @Override
    protected String getUrl() {
        return "http://baneks.ru/random";
    }

}
