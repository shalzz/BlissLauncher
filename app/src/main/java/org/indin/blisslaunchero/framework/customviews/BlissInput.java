package org.indin.blisslaunchero.framework.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.DragEvent;

public class BlissInput extends android.support.v7.widget.AppCompatEditText {
    public BlissInput(Context context) {
        super(context);
    }

    public BlissInput(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BlissInput(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onDragEvent(DragEvent event) {
        // Without this drag/drop apps won't work on API <24.
        // EditTexts seem to interfere with drag/drop.
        return false;
    }
}
