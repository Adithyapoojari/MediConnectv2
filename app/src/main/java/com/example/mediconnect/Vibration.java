package com.example.mediconnect;

import android.content.Context;
import android.os.Vibrator;

public class Vibration {
    private static Vibrator vibrator;

    // Initialize the Vibrator service
    public static void init(Context context) {
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    // Trigger vibration with a default duration (500 milliseconds)
    public static void vibrate() {
        if (vibrator != null && vibrator.hasVibrator()) {
            vibrator.vibrate(200);
        }
    }

    // Trigger vibration with a custom duration
    public static void vibrate(long milliseconds) {
        if (vibrator != null && vibrator.hasVibrator()) {
            vibrator.vibrate(milliseconds);
        }
    }
}
