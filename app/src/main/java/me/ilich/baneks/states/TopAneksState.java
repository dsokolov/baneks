package me.ilich.baneks.states;

import android.content.Context;

import me.ilich.baneks.R;
import me.ilich.baneks.gui.NavigationFragment;
import me.ilich.baneks.gui.ToolbarFragment;
import me.ilich.baneks.gui.TopAneksFragment;
import me.ilich.juggler.gui.JugglerFragment;
import me.ilich.juggler.states.ContentToolbarNavigationState;
import me.ilich.juggler.states.VoidParams;

public class TopAneksState extends ContentToolbarNavigationState<VoidParams> {

    public TopAneksState() {
        super(null);
    }

    @Override
    protected JugglerFragment onCreateContent(VoidParams params) {
        return TopAneksFragment.create();
    }

    @Override
    protected JugglerFragment onCreateToolbar(VoidParams params) {
        return ToolbarFragment.createNavigation();
    }

    @Override
    protected JugglerFragment onCreateNavigation(VoidParams params) {
        return NavigationFragment.create(R.id.menu_aneks_top);
    }

    @Override
    public int getTitleRes(Context context, VoidParams params) {
        return R.string.title_top_aneks;
    }

}
