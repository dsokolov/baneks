package me.ilich.baneks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import me.ilich.juggler.gui.JugglerFragment;
import me.ilich.juggler.gui.JugglerToolbarFragment;

public class ToolbarFragment extends JugglerToolbarFragment {

    public static JugglerFragment create() {
        ToolbarFragment f = new ToolbarFragment();
        Bundle b = addDisplayOptionsToBundle(null, ActionBar.DISPLAY_SHOW_TITLE);
        f.setArguments(b);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_toolbar, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    protected int getToolbarId() {
        return R.id.toolbar;
    }

}
