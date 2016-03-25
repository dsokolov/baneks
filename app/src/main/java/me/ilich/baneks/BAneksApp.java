package me.ilich.baneks;

import android.app.Application;

import me.ilich.juggler.Juggler;

public class BAneksApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Juggler.init();
    }

}
