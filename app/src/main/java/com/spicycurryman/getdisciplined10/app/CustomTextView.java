package com.spicycurryman.getdisciplined10.app;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Spicycurryman on 8/30/14.
 */
public class CustomTextView extends TextView {
    private static Typeface customFont = null;

    public CustomTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (isInEditMode()) {
            return;
        }

        if (customFont == null) {
            customFont = Typeface.createFromAsset(context.getApplicationContext().getAssets(),
                    "fonts/robotocondensed-light.ttf");
        }
        setTypeface(customFont);
    }

}
