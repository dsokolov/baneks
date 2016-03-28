package me.ilich.baneks.states;

import android.content.Context;
import android.support.annotation.Nullable;

import me.ilich.baneks.R;
import me.ilich.baneks.data.TopItem;
import me.ilich.baneks.gui.NavigationFragment;
import me.ilich.baneks.gui.ToolbarFragment;
import me.ilich.baneks.gui.TopAnekFragment;
import me.ilich.baneks.gui.TopAneksFragment;
import me.ilich.juggler.gui.JugglerFragment;
import me.ilich.juggler.states.ContentBelowToolbarState;
import me.ilich.juggler.states.ContentToolbarNavigationState;
import me.ilich.juggler.states.State;

public class TopAnekState extends ContentBelowToolbarState<TopAnekState.Params> {

    public TopAnekState(TopItem topItem) {
        super(new Params(topItem));
    }

    @Override
    protected JugglerFragment onCreateContent(Params params) {
        return TopAnekFragment.create(params.topItem);
    }

    @Override
    protected JugglerFragment onCreateToolbar(Params params) {
        return ToolbarFragment.createBack();
    }

    @Nullable
    @Override
    public String getTitle(Context context, Params params) {
        return String.format(context.getString(R.string.title_anek_top), params.topItem.getPos());
    }

    public static class Params extends State.Params {

        private final TopItem topItem;

        public Params(TopItem topItem) {
            this.topItem = topItem;
        }

    }

}
