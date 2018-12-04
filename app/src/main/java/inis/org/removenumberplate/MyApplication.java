package inis.org.removenumberplate;

import android.app.Application;
import android.content.Context;

import timber.log.Timber;

public class MyApplication extends Application {
    private Context context;
    @Override
    public void onCreate() {
        super.onCreate();

        context = this;
        Timber.plant(Timber.asTree());
    }
}
