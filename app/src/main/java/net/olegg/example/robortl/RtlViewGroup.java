package net.olegg.example.robortl;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Simple LinearLayout-ish ViewGroup
 */
public class RtlViewGroup extends ViewGroup {

    public RtlViewGroup(Context context) {
        super(context);
    }

    public RtlViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RtlViewGroup(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childState = 0;
        int width = 0;
        int height = 0;

        for (int i = 0; i < getChildCount(); ++i) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            }
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            width += child.getMeasuredWidth();
            height = Math.max(height, child.getMeasuredHeight());
            childState = combineMeasuredStates(childState, child.getMeasuredState());
        }

        setMeasuredDimension(
                resolveSizeAndState(width, widthMeasureSpec, childState),
                resolveSizeAndState(height, heightMeasureSpec, childState << MEASURED_HEIGHT_STATE_SHIFT));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        boolean isRtl = ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL;
        int start = 0, end = getChildCount(), step = 1;
        if (isRtl) {
            start = getChildCount() - 1; end = -1; step = -1;
        }

        int left = 0;
        for (int i = start; i != end; i += step) {
            View child = getChildAt(i);
            child.layout(left, 0, left + child.getMeasuredWidth(), child.getMeasuredHeight());
            left += child.getMeasuredWidth();
        }
    }
}
