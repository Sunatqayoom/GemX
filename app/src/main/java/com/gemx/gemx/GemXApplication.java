
package com.gemx.gemx;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.Window;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

public class GemXApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        registerActivityLifecycleCallbacks(
                new ActivityLifecycleCallbacks() {

                    @Override
                    public void onActivityCreated(
                            Activity activity,
                            Bundle savedInstanceState) {
                        applySystemBarInsets(activity);
                    }

                    @Override
                    public void onActivityStarted(Activity activity) {}

                    @Override
                    public void onActivityResumed(Activity activity) {}

                    @Override
                    public void onActivityPaused(Activity activity) {}

                    @Override
                    public void onActivityStopped(Activity activity) {}

                    @Override
                    public void onActivitySaveInstanceState(
                            Activity activity,
                            Bundle outState) {}

                    @Override
                    public void onActivityDestroyed(Activity activity) {}
                }
        );
    }

    private void applySystemBarInsets(Activity activity) {

        Window window = activity.getWindow();

        // Handle Android 16 edge-to-edge centrally.
        WindowCompat.setDecorFitsSystemWindows(window, false);

        // Draw the existing GemX background behind system bars.
        window.setBackgroundDrawableResource(R.color.background);
        window.setStatusBarColor(
                activity.getColor(R.color.background)
        );
        window.setNavigationBarColor(
                activity.getColor(R.color.background)
        );

        // Keep system-bar icons visible on the dark background.
        WindowInsetsControllerCompat controller =
                WindowCompat.getInsetsController(
                        window,
                        window.getDecorView()
                );

        controller.setAppearanceLightStatusBars(false);
        controller.setAppearanceLightNavigationBars(false);

        // Get the Activity's content container.
        android.view.View content =
                activity.findViewById(android.R.id.content);

        if (content == null) {
            return;
        }

        // Prevent white from showing through the background areas.
        content.setBackgroundResource(R.color.background);

        // Preserve the content's existing padding.
        final int initialLeft = content.getPaddingLeft();
        final int initialTop = content.getPaddingTop();
        final int initialRight = content.getPaddingRight();
        final int initialBottom = content.getPaddingBottom();

        final int insetTypes =
                WindowInsetsCompat.Type.systemBars()
                        | WindowInsetsCompat.Type.displayCutout();

        ViewCompat.setOnApplyWindowInsetsListener(
                content,
                (view, windowInsets) -> {

                    Insets insets =
                            windowInsets.getInsets(insetTypes);

                    view.setPadding(
                            initialLeft + insets.left,
                            initialTop + insets.top,
                            initialRight + insets.right,
                            initialBottom + insets.bottom
                    );

                    // Avoid applying the same system-bar insets
                    // again to child views.
                    return new WindowInsetsCompat.Builder(windowInsets)
                            .setInsets(insetTypes, Insets.NONE)
                            .build();
                }
        );

        ViewCompat.requestApplyInsets(content);
    }
}
