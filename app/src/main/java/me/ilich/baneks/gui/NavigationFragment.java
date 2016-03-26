package me.ilich.baneks.gui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Random;

import butterknife.ButterKnife;
import butterknife.OnClick;
import me.ilich.baneks.R;
import me.ilich.baneks.commands.RandomAnekCommand;
import me.ilich.baneks.helpers.GoogleAnalyticsHelper;
import me.ilich.baneks.states.AboutState;
import me.ilich.baneks.states.RandomAnekState;
import me.ilich.juggler.change.Add;
import me.ilich.juggler.change.Remove;
import me.ilich.juggler.gui.JugglerFragment;
import me.ilich.juggler.gui.JugglerNavigationFragment;

public class NavigationFragment extends JugglerNavigationFragment {

    public static JugglerFragment create(int itemIndex) {
        NavigationFragment f = new NavigationFragment();
        Bundle b = new Bundle();
        addSelectedItemToBundle(b, itemIndex);
        f.setArguments(b);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_navigation, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavigationView navigationView = (NavigationView) view.findViewById(R.id.navigation_view);
        navigationView.inflateMenu(R.menu.navigation);
        for (int i = 0; i < navigationView.getMenu().size(); i++) {
            MenuItem menuItem = navigationView.getMenu().getItem(i);
            if (menuItem.getItemId() == getDefaultSelectedItem()) {
                menuItem.setChecked(true);
                break;
            }
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                final boolean r;
                switch (item.getItemId()) {
                    case R.id.menu_random_anek:
                        GoogleAnalyticsHelper.trackNavigationRandomAnek();
                        navigateTo().state(Remove.dig(RandomAnekState.TAG));
                        r = true;
                        break;
                    case R.id.menu_about:
                        GoogleAnalyticsHelper.trackNavigationAbout();
                        navigateTo().state(Remove.dig(RandomAnekState.TAG), Add.deeper(new AboutState()));
                        r = true;
                        break;
                    default:
                        r = false;
                }
                if (r) {
                    close();
                }
                return r;
            }
        });
        navigationView.getHeaderView(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoogleAnalyticsHelper.trackNavigationBanek();
                String[] says = getContext().getResources().getStringArray(R.array.banek_says);
                int i = new Random().nextInt(says.length);
                Toast.makeText(getContext(), says[i], Toast.LENGTH_SHORT).show();
            }
        });
    }

}
