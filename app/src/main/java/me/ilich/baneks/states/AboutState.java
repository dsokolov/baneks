package me.ilich.baneks.states;

import android.content.Context;

import me.ilich.baneks.R;
import me.ilich.baneks.gui.AboutFragment;
import me.ilich.baneks.gui.NavigationFragment;
import me.ilich.baneks.gui.ToolbarFragment;
import me.ilich.juggler.gui.JugglerFragment;
import me.ilich.juggler.states.ContentToolbarNavigationState;
import me.ilich.juggler.states.VoidParams;

public class AboutState extends ContentToolbarNavigationState<VoidParams> {

    public AboutState() {
        super(null);
    }

    @Override
    protected JugglerFragment onCreateContent(VoidParams params) {
        return AboutFragment.create();
    }

    @Override
    protected JugglerFragment onCreateToolbar(VoidParams params) {
        return ToolbarFragment.createNavigation();
    }

    @Override
    protected JugglerFragment onCreateNavigation(VoidParams params) {
        return NavigationFragment.create(R.id.menu_about);
    }

    @Override
    public String getTitle(Context context, VoidParams params) {
        return context.getString(R.string.title_about);
    }

}