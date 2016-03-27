package me.ilich.baneks.states;

import me.ilich.baneks.gui.NavigationFragment;
import me.ilich.juggler.gui.JugglerFragment;
import me.ilich.juggler.states.ContentToolbarNavigationState;
import me.ilich.juggler.states.State;

public class NumberAnekState extends ContentToolbarNavigationState<NumberAnekState.Params> {

    public NumberAnekState(String id) {
        super(new Params(id));
    }

    @Override
    protected JugglerFragment onCreateContent(Params params) {
        return null;
    }

    @Override
    protected JugglerFragment onCreateToolbar(Params params) {
        return null;
    }

    @Override
    protected JugglerFragment onCreateNavigation(Params params) {
        return NavigationFragment.create(-1);
    }

    public static class Params extends State.Params {

        private final String id;

        public Params(String id) {
            this.id = id;
        }

    }

}
