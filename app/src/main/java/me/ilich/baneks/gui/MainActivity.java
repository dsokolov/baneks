package me.ilich.baneks.gui;

import android.os.Bundle;

import me.ilich.baneks.states.RandomAnekState;
import me.ilich.juggler.gui.JugglerActivity;

public class MainActivity extends JugglerActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            navigateTo().clearState(new RandomAnekState());
        } else {
            navigateTo().restore();
        }
    }

}
