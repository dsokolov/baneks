package me.ilich.baneks.commands;

import me.ilich.baneks.data.Anek;
import me.ilich.baneks.helpers.StringHelper;

public abstract class AnekCommand extends AbstractCommand<Anek> {

    public AnekCommand(Callback callback) {
        super(callback);
    }

    @Override
    protected Anek parseSuccessResponse(String s) {
        String id = StringHelper.substring(s, "<main data-id=\"","\">");
        String title = StringHelper.substring(s, "<meta property=\"og:title\" content=\"", "\"/>");
        String content = StringHelper.substring(s, " <meta property=\"og:description\" content=\"", "\"/>");
        String rating = StringHelper.substring(s, "<span class=\"rating-counter\">", "</span>");
        boolean rated = StringHelper.contains(s, "rating-wrap rate-me rated");
        return new Anek(id, title, content, rating, rated);
    }

    public interface Callback extends AbstractCommand.Callback<Anek> {

    }

}
