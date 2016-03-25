package me.ilich.baneks;

import android.os.Bundle;

import me.ilich.juggler.gui.JugglerActivity;

public class MainActivity extends JugglerActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            navigateTo().clearState(new AnekState());
        } else {
            navigateTo().restore();
        }
    }

}
