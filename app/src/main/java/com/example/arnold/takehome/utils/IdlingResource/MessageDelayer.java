package com.example.arnold.takehome.utils.IdlingResource;

import android.os.Handler;
import android.support.annotation.Nullable;

/**
 * Created by ArnoldGoncharenko on 2018-06-06.
 */

public class MessageDelayer {

    private static final int DELAY_MILLIS = 2000;

    public interface DelayerCallback {
        void onDone(String text);
    }

    /**
     * Takes a String and returns it after {@link #DELAY_MILLIS} via a {@link DelayerCallback}.
     * @param message the String that will be returned via the callback
     * @param callback used to notify the caller asynchronously
     */
    public static void processMessage(final String message, final DelayerCallback callback,
                                      @Nullable final SimpleIdlingResource idlingResource) {
        // The IdlingResource is null in production.
        if (idlingResource != null) {
            idlingResource.setIdleState(false);
        }

        // Delay the execution, return message via callback.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onDone(message);
                    if (idlingResource != null) {
                        idlingResource.setIdleState(true);
                    }
                }
            }
        }, DELAY_MILLIS);
    }
}