package me.ilich.baneks.commands;

import java.util.ArrayList;
import java.util.List;

import me.ilich.baneks.data.TopItem;
import me.ilich.baneks.helpers.StringHelper;

public class TopCommand extends AbstractCommand<List<TopItem>> {

    public TopCommand(Callback<List<TopItem>> callback) {
        super(callback);
    }

    @Override
    protected String getUrl() {
        return "http://baneks.ru/top";
    }

    @Override
    protected List<TopItem> parseSuccessResponse(String s) {
        List<TopItem> r = new ArrayList<>();
        int index = 0;
        int pos = 1;
        while (index != -1) {
            index = s.indexOf("<a class='golden-anek' href='/", index);
            if (index != -1) {
                String anekStr = StringHelper.substring(s, "<a class='golden-anek' href='", "/a>", index);
                String id = StringHelper.substring(anekStr, "/", "'");
                String title = StringHelper.substring(anekStr, ">", "<");
                TopItem topItem = new TopItem(id, String.valueOf(pos), title);
                r.add(topItem);
                index++;
                pos++;
            }
        }
        return r;
    }

}
