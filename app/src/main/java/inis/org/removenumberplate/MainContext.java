package inis.org.removenumberplate;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.IntDef;

import inis.org.removenumberplate.utils.Rx;
import io.reactivex.Observable;

public class MainContext {
    public static final int STATE_NONE = 0;
    public static final int STATE_LOADED = STATE_NONE + 1;              //이미지 로딩상태
    public static final int STATE_GRAYSCALE = STATE_LOADED + 1;         //그레이스케일처리
    public static final int STATE_BINARY_IMAGE = STATE_GRAYSCALE + 1;   //Threshold 적용하여 바이너리 이미지

    private Uri sourceImage;
    private Bitmap workingBitmap;
    private int state = -1;

    private MainContext(Uri uri) {
        this.sourceImage = uri;
    }

    public static MainContext create(Uri uri) {
        MainContext context = new MainContext(uri);
        return context;
    }

    public int getState() {
        return state;
    }

    public Uri getImageUri() {
        return sourceImage;
    }

    public Observable<Bitmap> loadImage(Context context) {
        return Observable.create(emitter -> {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), sourceImage);
            if (bitmap != null) {
                workingBitmap = Bitmap.createBitmap(bitmap);
                emitter.onNext(workingBitmap);
                emitter.onComplete();
            } else {
                emitter.onError(new Exception("Cannot get bitmap from uri"));
            }
        });
    }
}
