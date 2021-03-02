package com.example.tp3_exo6;

import android.content.Context;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public class Utilisation implements LifecycleObserver {

    private Context context;
    private int counter = 0;

    public Utilisation(Context context) {
        this.context = context;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void nombreUtilisation () {
        this.counter++;
    }

    public int getCounter() {
        return this.counter;
    }

    @Override
    public String toString() {
        return context.getResources().getString(R.string.utilisation_counter_txt).toUpperCase() + ": " + this.getCounter();
    }
}
