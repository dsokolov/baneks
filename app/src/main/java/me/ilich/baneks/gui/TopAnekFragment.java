package me.ilich.baneks.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.ilich.baneks.R;
import me.ilich.baneks.commands.AbstractCommand;
import me.ilich.baneks.commands.AnekByNumberCommand;
import me.ilich.baneks.commands.AnekCommand;
import me.ilich.baneks.commands.RandomAnekCommand;
import me.ilich.baneks.commands.RateCommand;
import me.ilich.baneks.commands.errors.ErrorResponse;
import me.ilich.baneks.data.Anek;
import me.ilich.baneks.data.TopItem;
import me.ilich.baneks.helpers.GoogleAnalyticsHelper;
import me.ilich.juggler.gui.JugglerFragment;

public class TopAnekFragment extends JugglerFragment {

    private static final String ARG_ITEM = "item";

    public static TopAnekFragment create(TopItem topItem) {
        TopAnekFragment topAnekFragment = new TopAnekFragment();
        Bundle b = new Bundle();
        b.putSerializable(ARG_ITEM, topItem);
        topAnekFragment.setArguments(b);
        return topAnekFragment;
    }

    @Bind(R.id.t_anek_body)
    TextView anekBodyTextView;

    private AnekCommand command;
    private TopItem topItem = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        topItem = (TopItem) getArguments().getSerializable(ARG_ITEM);
        GoogleAnalyticsHelper.track(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_top_anek, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showAnek(topItem);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.share, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final boolean b;
        switch (item.getItemId()) {
            case R.id.menu_share:
                doShare();
                b = true;
                break;
            default:
                b = super.onOptionsItemSelected(item);
                break;
        }
        return b;
    }

    public void doShare() {
        GoogleAnalyticsHelper.trackShare(topItem.getId());
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String text = String.format(getString(R.string.share_text), topItem.getTitle());
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.dialog_share)));
    }

    private void showAnek(TopItem anek) {
        anekBodyTextView.setText(anek.getTitle());
    }

}
