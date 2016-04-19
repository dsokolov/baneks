package me.ilich.baneks;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import me.ilich.juggler.Juggler;

public class BAneksApp extends Application {

    private static BAneksApp app;

    public static BAneksApp getApp(){
        return app;
    }

    private Tracker mTracker;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            mTracker = analytics.newTracker(R.xml.global_tracker);
        }
        return mTracker;
    }

}
