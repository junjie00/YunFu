package com.javon.yunfu.wight.adwheel;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 */
public class BGAViewPager extends ViewPager {
    private boolean mScrollable = true;

    public BGAViewPager(Context context) {
        super(context);
    }

    public BGAViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setPageTransformer(boolean reverseDrawingOrder, ViewPager.PageTransformer transformer) {

        Class viewpagerClass = ViewPager.class;

        try {
            boolean hasTransformer = transformer != null;

            Field pageTransformerField = viewpagerClass.getDeclaredField("mPageTransformer");
            pageTransformerField.setAccessible(true);
            PageTransformer mPageTransformer = (PageTransformer) pageTransformerField.get(this);

            boolean needsPopulate = hasTransformer != (mPageTransformer != null);
            pageTransformerField.set(this, transformer);

            Method setChildrenDrawingOrderEnabledCompatMethod = viewpagerClass.getDeclaredMethod("setChildrenDrawingOrderEnabledCompat", boolean.class);
            setChildrenDrawingOrderEnabledCompatMethod.setAccessible(true);
            setChildrenDrawingOrderEnabledCompatMethod.invoke(this, hasTransformer);

            Field drawingOrderField = viewpagerClass.getDeclaredField("mDrawingOrder");
            drawingOrderField.setAccessible(true);
            if (hasTransformer) {
                drawingOrderField.setInt(this, reverseDrawingOrder ? 2 : 1);
            } else {
                drawingOrderField.setInt(this, 0);
            }

            if (needsPopulate) {
                Method populateMethod = viewpagerClass.getDeclaredMethod("populate");
                populateMethod.setAccessible(true);
                populateMethod.invoke(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置调用setCurrentItem(int item, boolean smoothScroll)方法时，page切换的时间长度
     *
     * @param duration page切换的时间长度
     */
    public void setPageChangeDuration(int duration) {
        try {
            Field scrollerField = ViewPager.class.getDeclaredField("mScroller");
            scrollerField.setAccessible(true);
            scrollerField.set(this, new PageChangeDurationScroller(getContext(), duration));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置是否允许用户手指滑动
     *
     * @param scrollable true表示允许跟随用户触摸滑动，false反之
     */
    public void setAllowUserScrollable(boolean scrollable) {
        mScrollable = scrollable;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mScrollable) {
            return super.onInterceptTouchEvent(ev);
        } else {
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mScrollable) {
            return super.onTouchEvent(ev);
        } else {
            return false;
        }
    }
}