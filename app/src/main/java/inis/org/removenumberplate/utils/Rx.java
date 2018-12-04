package inis.org.removenumberplate.utils;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Rx {
    public static Observable start() {
        return Observable.just(new Object());
    }

    public static Scheduler main() {
        return AndroidSchedulers.mainThread();
    }

    public static Scheduler io() {
        return Schedulers.io();
    }
}
