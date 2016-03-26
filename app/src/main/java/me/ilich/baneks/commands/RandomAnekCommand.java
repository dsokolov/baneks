package me.ilich.baneks.commands;

import me.ilich.baneks.helpers.StringHelper;
import me.ilich.baneks.data.Anek;

public class RandomAnekCommand extends AbstractCommand<Anek> {

    public RandomAnekCommand(Callback callback) {
        super(callback);
    }

    @Override
    protected String getUrl() {
        return "http://baneks.ru/random";
    }

    @Override
    protected Anek parseSuccessResponse(String s) {
        String title = StringHelper.substring(s, "<meta property=\"og:title\" content=\"", "\"/>");
        String content = StringHelper.substring(s, " <meta property=\"og:description\" content=\"", "\"/>");
        String rating = StringHelper.substring(s, "<span class=\"rating-counter\">", "</span>");
        return new Anek(title, content, rating);
    }

    public interface Callback extends AbstractCommand.Callback<Anek> {

    }

}
