package me.ilich.baneks.gui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.ilich.baneks.R;
import me.ilich.baneks.commands.ErrorResponse;
import me.ilich.baneks.commands.RandomAnekCommand;
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

    private RandomAnekCommand command;
    private Anek currentAnek = null;

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
            doLoad();
        } else {
            showAnek(currentAnek);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.random, menu);
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
                doLoad();
                b = true;
                break;
            default:
                b = super.onOptionsItemSelected(item);
                break;
        }
        return b;
    }

    @OnClick(R.id.b_reaload)
    public void onReloadClick(View v) {
        doLoad();
    }

    private void doLoad() {
        if (command != null) {
            command.cancel();
        }
        RandomAnekCommand.Callback callback = new RandomAnekCommand.Callback() {

            @Override
            public void onStart() {
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

        command = new RandomAnekCommand(callback);
        command.execute();
    }

    private void showAnek(Anek anek) {
        if (anek == null) {
            progressContainer.setVisibility(View.GONE);
            errorContainer.setVisibility(View.VISIBLE);
            successContainer.setVisibility(View.GONE);
        } else {
            progressContainer.setVisibility(View.GONE);
            errorContainer.setVisibility(View.GONE);
            successContainer.setVisibility(View.VISIBLE);
            anekTitleTextView.setText(anek.getTitle());
            anekBodyTextView.setText(anek.getBody());
        }
    }

}
