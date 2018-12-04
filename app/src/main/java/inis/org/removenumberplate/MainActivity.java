package inis.org.removenumberplate;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;

import inis.org.removenumberplate.common.RxActivity;
import inis.org.removenumberplate.databinding.ActivityMainBinding;
import inis.org.removenumberplate.utils.ImageUtil;
import inis.org.removenumberplate.utils.Rx;
import io.reactivex.disposables.Disposable;

public class MainActivity extends RxActivity {
    private ActivityMainBinding binding;
    private Handler handler;
    private MainContext mainContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler(Looper.myLooper());
        binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);

        init();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mainContext == null) {
            checkPermission(() -> pickImageFromGallery());
        }
    }

    private void init() {
        binding.btnExit.setOnClickListener(view -> {
            MainActivity.this.finish();
        });

        binding.btnNext.setOnClickListener(view -> {
            processImage();
        });
    }

    private void processImage() {

    }

    private void checkPermission(final Runnable runnable) {
        handler.post(runnable);
//        Dexter.withActivity(this)
//                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
//                .withListener(new PermissionListener() {
//                    @Override
//                    public void onPermissionGranted(PermissionGrantedResponse response) {
//                        Toast.makeText(MainActivity.this, R.string.permission_granted, Toast.LENGTH_SHORT).show();
//                        if (runnable != null) {
//                            handler.post(runnable);
//                        }
//                    }
//
//                    @Override
//                    public void onPermissionDenied(PermissionDeniedResponse response) {
//                        new AlertDialog.Builder(MainActivity.this)
//                                .setMessage(R.string.need_permission)
//                                .setPositiveButton(R.string.show_permission, (dialog, position) -> {
//                                    dialog.dismiss();
//                                    handler.post(() -> checkPermission(runnable));
//                                }).show();
//                    }
//
//                    @Override
//                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
//                        Toast.makeText(MainActivity.this, R.string.exit_by_permission, Toast.LENGTH_SHORT).show();
//                        MainActivity.this.finish();
//                    }
//                })
//                .withErrorListener(new PermissionRequestErrorListener() {
//                    @Override
//                    public void onError(DexterError error) {
//                        Timber.d("PermissionError" + error.toString());
//                    }
//                })
//                .check();
    }

    private void pickImageFromGallery() {
        ImageUtil.getPictureFromGallery(this, C.PICK_IMAGE);
    }

    private void showPreview(Uri imageUri) {
        mainContext = MainContext.create(imageUri);
        Disposable disposable = mainContext.loadImage(MainActivity.this)
                .subscribeOn(Rx.io())
                .observeOn(Rx.main())
                .subscribe(bitmap -> {
                    binding.preview.setImageBitmap(bitmap);
                });

        addDisposable(disposable);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == C.PICK_IMAGE) {
            if (resultCode == RESULT_OK) {
                Uri imageUri = data.getData();
                showPreview(imageUri);
            }

            return ;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
