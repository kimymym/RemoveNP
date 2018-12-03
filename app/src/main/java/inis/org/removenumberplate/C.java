package inis.org.removenumberplate;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class C {
    public static final int PICK_IMAGE = 1001;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({PICK_IMAGE})
    public @interface RequestCode {};
}
