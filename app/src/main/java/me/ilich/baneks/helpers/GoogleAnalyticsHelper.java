package me.ilich.baneks.helpers;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import me.ilich.baneks.BAneksApp;

public class GoogleAnalyticsHelper {

    public static void track(Fragment fragment) {
        Tracker tracker = BAneksApp.getApp().getDefaultTracker();
        tracker.setScreenName((String) fragment.getActivity().getTitle());
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    private static void track(String category, String action) {
        Tracker tracker = BAneksApp.getApp().getDefaultTracker();
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory(category)
                .setAction(action)
                .build());
    }

    private static void trackNavigation(String action) {
        track("navigation", action);
    }

    public static void trackNavigationRandomAnek(){
        trackNavigation("random_anek");
    }

    public static void trackNavigationAbout(){
        trackNavigation("about");
    }

    public static void trackNavigationBanek(){
        trackNavigation("banek");
    }

}
