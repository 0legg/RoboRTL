package net.olegg.example.robortl;

import android.app.Activity;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static android.view.View.MeasureSpec.AT_MOST;
import static android.view.View.MeasureSpec.makeMeasureSpec;
import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class RtlViewGroupTest {

    private Activity activity;
    private RtlViewGroup view;

    @Before
    public void setup() {
        activity = Robolectric.setupActivity(Activity.class);
        view = new RtlViewGroup(activity);
    }

    @Test
    public void whenLTR_shouldLayoutLTR() {
        View child1 = createChildView(100, 50);
        View child2 = createChildView(100, 50);
        view.addView(child1);
        view.addView(child2);

        view.measure(makeMeasureSpec(200, AT_MOST), makeMeasureSpec(50, AT_MOST));
        view.layout(0, 0, 200, 50);

        assertEquals(0, child1.getLeft());
        assertEquals(0, child1.getTop());
        assertEquals(100, child1.getRight());
        assertEquals(50, child1.getBottom());

        assertEquals(100, child2.getLeft());
        assertEquals(0, child2.getTop());
        assertEquals(200, child2.getRight());
        assertEquals(50, child2.getBottom());
    }

    @Test
    public void whenRTL_shouldLayoutRTL() {
        view.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        View child1 = createChildView(100, 50);
        View child2 = createChildView(100, 50);
        view.addView(child1);
        view.addView(child2);

        view.measure(makeMeasureSpec(200, AT_MOST), makeMeasureSpec(50, AT_MOST));
        view.layout(0, 0, 200, 50);

        assertEquals(100, child1.getLeft());
        assertEquals(0, child1.getTop());
        assertEquals(200, child1.getRight());
        assertEquals(50, child1.getBottom());

        assertEquals(0, child2.getLeft());
        assertEquals(0, child2.getTop());
        assertEquals(100, child2.getRight());
        assertEquals(50, child2.getBottom());
    }

    @Test
    public void whenSetRTL_shouldSetRTL() {
        view.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        assertEquals(View.LAYOUT_DIRECTION_RTL, view.getLayoutDirection());
    }

    @Test
    public void whenSetCompatRTL_shouldSetCompatRTL() {
        ViewCompat.setLayoutDirection(view, ViewCompat.LAYOUT_DIRECTION_RTL);
        assertEquals(ViewCompat.LAYOUT_DIRECTION_RTL, ViewCompat.getLayoutDirection(view));
    }

    private View createChildView(int width, int height) {
        TextView textView = new TextView(activity);
        textView.setWidth(width);
        textView.setHeight(height);
        return textView;
    }
}