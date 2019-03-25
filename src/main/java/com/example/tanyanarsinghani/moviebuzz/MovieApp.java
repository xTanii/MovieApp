package com.example.tanyanarsinghani.moviebuzz;

import android.app.Application;
import com.facebook.stetho.Stetho;

public class MovieApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
