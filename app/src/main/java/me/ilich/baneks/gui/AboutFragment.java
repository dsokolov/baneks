package me.ilich.baneks.gui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.ilich.baneks.BuildConfig;
import me.ilich.baneks.R;
import me.ilich.baneks.helpers.GoogleAnalyticsHelper;
import me.ilich.juggler.gui.JugglerFragment;

public class AboutFragment extends JugglerFragment {

    public static strictfp AboutFragment create() {
        return new AboutFragment();
    }

    @Bind(R.id.t_version)
    TextView versionTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GoogleAnalyticsHelper.track(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_about, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String s = String.format(getString(R.string.about_version), BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE);
        versionTextView.setText(s);
    }

    @OnClick(R.id.b_vk)
    public void onVkClick(View v) {
        String url = "https://vk.com/baneks";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    @OnClick(R.id.b_web)
    public void onWebClick(View v) {
        String url = "http://baneks.ru";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

}
