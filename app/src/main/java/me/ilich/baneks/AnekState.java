package me.ilich.baneks;

import me.ilich.juggler.gui.JugglerFragment;
import me.ilich.juggler.states.ContentBelowToolbarState;
import me.ilich.juggler.states.VoidParams;

public class AnekState extends ContentBelowToolbarState<VoidParams> {

    public AnekState() {
        super(null);
    }

    @Override
    protected JugglerFragment onCreateContent(VoidParams params) {
        return AnekFragment.create();
    }

    @Override
    protected JugglerFragment onCreateToolbar(VoidParams params) {
        return ToolbarFragment.create();
    }

/*    @Override
    public int getTitleRes(VoidParams params) {
        return R.string.title_banek;
    }*/

}
