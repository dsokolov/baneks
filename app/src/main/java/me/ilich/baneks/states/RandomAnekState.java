package me.ilich.baneks.states;

import android.content.Context;

import me.ilich.baneks.R;
import me.ilich.baneks.gui.RandomAnekFragment;
import me.ilich.baneks.gui.NavigationFragment;
import me.ilich.baneks.gui.ToolbarFragment;
import me.ilich.juggler.gui.JugglerFragment;
import me.ilich.juggler.states.ContentToolbarNavigationState;
import me.ilich.juggler.states.VoidParams;

public class RandomAnekState extends ContentToolbarNavigationState<VoidParams> {

    public static final String TAG = "state_random_anek";

    public RandomAnekState() {
        super(null);
    }

    @Override
    protected JugglerFragment onCreateContent(VoidParams params) {
        return RandomAnekFragment.create();
    }

    @Override
    protected JugglerFragment onCreateNavigation(VoidParams params) {
        return NavigationFragment.create(R.id.menu_random_anek);
    }

    @Override
    protected JugglerFragment onCreateToolbar(VoidParams params) {
        return ToolbarFragment.createNavigation();
    }

    @Override
    public String getTitle(Context context, VoidParams params) {
        return context.getString(R.string.title_banek_random);
    }

    @Override
    public String getTag() {
        return TAG;
    }

}
