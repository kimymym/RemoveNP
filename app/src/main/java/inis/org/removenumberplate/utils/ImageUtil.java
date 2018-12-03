package inis.org.removenumberplate.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;

import inis.org.removenumberplate.C;

public class ImageUtil {
    public static void getPictureFromGallery(Activity activity, @C.RequestCode int requestCode) {
        Intent filter = new Intent(Intent.ACTION_GET_CONTENT).setType("image/*");
        Intent chooser = Intent.createChooser(filter, "Select image");
        activity.startActivityForResult(chooser, requestCode);
    }

    //matrix that changes picture into gray scale
    public static ColorMatrix createGreyMatrix() {
        ColorMatrix matrix = new ColorMatrix(new float[] {
                0.2989f, 0.5870f, 0.1140f, 0, 0,
                0.2989f, 0.5870f, 0.1140f, 0, 0,
                0.2989f, 0.5870f, 0.1140f, 0, 0,
                0, 0, 0, 1, 0
        });
        return matrix;
    }

    // matrix that changes gray scale picture into black and white at given threshold.
    // It works this way:
    // The matrix after multiplying returns negative values for colors darker than threshold
    // and values bigger than 255 for the ones higher.
    // Because the final result is always trimed to bounds (0..255) it will result in bitmap made of black and white pixels only
    public static ColorMatrix createThresholdMatrix(int threshold) {
        ColorMatrix matrix = new ColorMatrix(new float[] {
                85.f, 85.f, 85.f, 0.f, -255.f * threshold,
                85.f, 85.f, 85.f, 0.f, -255.f * threshold,
                85.f, 85.f, 85.f, 0.f, -255.f * threshold,
                0f, 0f, 0f, 1f, 0f
        });
        return matrix;
    }

    public static void a(Bitmap pic) {
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inScaled = false;

        //load source bitmap and prepare destination bitmap
        Bitmap result = Bitmap.createBitmap(pic.getWidth(), pic.getHeight(),  Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(result);

        Paint bitmapPaint = new Paint();
        //first convert bitmap to grey scale:
        bitmapPaint.setColorFilter(new ColorMatrixColorFilter(createGreyMatrix()));
        c.drawBitmap(pic, 0, 0, bitmapPaint);

        //then convert the resulting bitmap to black and white using threshold matrix
        bitmapPaint.setColorFilter(new ColorMatrixColorFilter(createThresholdMatrix(128)));
        c.drawBitmap(result, 0, 0, bitmapPaint);

        //voilà! You can now draw the result bitmap anywhere You want:
        bitmapPaint.setColorFilter(null);
        //otherCanvas.drawBitmap(result, null, new Rect(x, y, x + size, y + size), bitmapPaint);
    }
}
