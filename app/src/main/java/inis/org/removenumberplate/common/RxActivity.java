package inis.org.removenumberplate.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class RxActivity extends AppCompatActivity {
    List<Disposable> disposableOnDestroy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        disposableOnDestroy = new ArrayList<>();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        if (disposableOnDestroy.size() > 0) {
            for (Disposable disposable : disposableOnDestroy) {
                if (!disposable.isDisposed()) {
                    disposable.dispose();
                }
            }

            disposableOnDestroy.clear();
        }

        super.onDestroy();
    }

    protected void addDisposable(Disposable disposable) {
        if (disposable == null) {
            return ;
        }

        disposableOnDestroy.add(disposable);
    }
}
