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
import me.ilich.baneks.commands.ErrorResponse;
import me.ilich.baneks.commands.RandomAnekCommand;
import me.ilich.baneks.commands.RateCommand;
import me.ilich.baneks.data.Anek;
import me.ilich.baneks.helpers.GoogleAnalyticsHelper;
import me.ilich.juggler.gui.JugglerFragment;

public class RandomAnekFragment extends JugglerFragment {

    private static final String STATE_ANEK = "anek";

    public static RandomAnekFragment create() {
        return new RandomAnekFragment();
    }

    @Bind(R.id.container_progress)
    View progressContainer;
    @Bind(R.id.container_error)
    View errorContainer;
    @Bind(R.id.container_success)
    View successContainer;

    @Bind(R.id.t_anek_title)
    TextView anekTitleTextView;
    @Bind(R.id.t_anek_body)
    TextView anekBodyTextView;
    @Bind(R.id.t_anek_rating)
    TextView anekRatingTextView;
    @Bind(R.id.b_anek_rate_up)
    Button anekRateUpButton;
    @Bind(R.id.b_anek_rate_down)
    Button anekRateDownButton;

    private AnekCommand command;
    private Anek currentAnek = null;
    private MenuItem shareMenuItem;

    private AnekCommand.Callback anekCallback = new AnekCommand.Callback() {

        @Override
        public void onStart() {
            if (shareMenuItem != null) {
                shareMenuItem.setVisible(false);
            }
            progressContainer.setVisibility(View.VISIBLE);
            errorContainer.setVisibility(View.GONE);
            successContainer.setVisibility(View.GONE);
        }

        @Override
        public void onSuccess(Anek anek) {
            currentAnek = anek;
            showAnek(anek);
        }

        @Override
        public void onFail(ErrorResponse errorResponse) {
            errorContainer.setVisibility(View.VISIBLE);
            successContainer.setVisibility(View.GONE);
        }

        @Override
        public void onFinish() {
            progressContainer.setVisibility(View.GONE);
        }

    };

    private RateCommand.Callback<String> rateCallback = new AbstractCommand.Callback<String>() {
        @Override
        public void onStart() {

        }

        @Override
        public void onSuccess(String successResponse) {

        }

        @Override
        public void onFail(ErrorResponse errorResponse) {

        }

        @Override
        public void onFinish() {
            doLoadNumber(currentAnek.getId());
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (savedInstanceState != null) {
            currentAnek = (Anek) savedInstanceState.getSerializable(STATE_ANEK);
        }
        GoogleAnalyticsHelper.track(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_anek, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            doLoadRandom();
        } else {
            showAnek(currentAnek);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.random, menu);
        inflater.inflate(R.menu.share, menu);

        shareMenuItem = menu.findItem(R.id.menu_share);
        if (currentAnek == null) {
            shareMenuItem.setVisible(false);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(STATE_ANEK, currentAnek);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final boolean b;
        switch (item.getItemId()) {
            case R.id.menu_reload_random:
                doLoadRandom();
                b = true;
                break;
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
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, currentAnek.getTitle());
        String text = String.format(getString(R.string.share_text), currentAnek.getBody());
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.dialog_share)));
    }

    @OnClick(R.id.b_reaload)
    public void onReloadClick(View v) {
        doLoadRandom();
    }

    @OnClick(R.id.b_anek_rate_up)
    public void onRateUpClick(View v) {
        RateCommand rateCommand = new RateCommand(rateCallback, currentAnek.getId());
        rateCommand.execute();
    }

    @OnClick(R.id.b_anek_rate_down)
    public void onRateDownClick(View v) {
        RateCommand rateCommand = new RateCommand(rateCallback, currentAnek.getId());
        rateCommand.execute();
    }

    private void doLoadRandom() {
        if (command != null) {
            command.cancel();
        }
        command = new RandomAnekCommand(anekCallback);
        //command = new AnekByNumberCommand(anekCallback, "735");
        command.execute();
    }

    private void doLoadNumber(String number) {
        if (command != null) {
            command.cancel();
        }
        command = new AnekByNumberCommand(anekCallback, number);
        command.execute();
    }

    private void showAnek(Anek anek) {
        if (anek == null) {
            progressContainer.setVisibility(View.GONE);
            errorContainer.setVisibility(View.VISIBLE);
            successContainer.setVisibility(View.GONE);
            shareMenuItem.setVisible(false);
        } else {
            progressContainer.setVisibility(View.GONE);
            errorContainer.setVisibility(View.GONE);
            successContainer.setVisibility(View.VISIBLE);
            anekTitleTextView.setText(anek.getTitle());
            anekBodyTextView.setText(anek.getBody());
            String rating = String.format(getString(R.string.anek_rating), anek.getRating());
            anekRatingTextView.setText(rating);
            shareMenuItem.setVisible(true);
            if (anek.isRated()) {
                anekRateUpButton.setVisibility(View.GONE);
                anekRateDownButton.setVisibility(View.VISIBLE);
            } else {
                anekRateUpButton.setVisibility(View.VISIBLE);
                anekRateDownButton.setVisibility(View.GONE);
            }
        }
    }

}
