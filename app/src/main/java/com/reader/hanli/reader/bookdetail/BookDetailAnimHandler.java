//package com.reader.hanli.reader.bookdetail;
//
//import android.animation.ValueAnimator;
//import android.content.Context;
//import android.os.Bundle;
//import android.view.View;
//import android.view.animation.AccelerateDecelerateInterpolator;
//import android.widget.FrameLayout;
//import android.widget.TextView;
//
//import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
//import com.github.ksoichiro.android.observablescrollview.ScrollState;
//import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
//import com.github.ksoichiro.android.observablescrollview.Scrollable;
//import com.nineoldandroids.view.ViewHelper;
//import com.nineoldandroids.view.ViewPropertyAnimator;
//import com.reader.hanli.reader.R;
//
///**
// * Created by hanli on 2018/2/28.
// */
//
//public class BookDetailAnimHandler implements ObservableScrollViewCallbacks {
//
//    protected View mHeader;
//    protected int mFlexibleSpaceImageHeight;
//    protected View mHeaderBar;
//    protected View mListBackgroundView;
//    protected int mActionBarSize;
//    protected int mIntersectionHeight;
//
//    private View mImage;
//    private View mHeaderBackground;
//    private int mPrevScrollY;
//    private boolean mGapIsChanging;
//    private boolean mGapHidden;
//    private boolean mReady;
//
//    private Context mContext;
//    private View mRootView;
//
//    public BookDetailAnimHandler(Context context , View bookDetailView) {
//        this.mContext = context;
//        mFlexibleSpaceImageHeight = mContext.getResources().getDimensionPixelSize(R.dimen.flexible_space_image_height);
//        mActionBarSize = 0;
//
//        // Even when the top gap has began to change, header bar still can move
//        // within mIntersectionHeight.
//        mIntersectionHeight = mContext.getResources().getDimensionPixelSize(R.dimen.intersection_height);
//
//        mImage = bookDetailView.findViewById(R.id.image);
//        mHeader = bookDetailView.findViewById(R.id.header);
//        mHeaderBar = bookDetailView.findViewById(R.id.header_bar);
//        mHeaderBackground = bookDetailView.findViewById(R.id.header_background);
//        mListBackgroundView = bookDetailView.findViewById(R.id.list_background);
//
//        final Scrollable scrollable = (Scrollable) bookDetailView.findViewById(R.id.lv);
//
//        ((TextView) bookDetailView.findViewById(R.id.title)).setText("测试");
//
//
//        mReady = true;
//        updateViews(scrollable.getCurrentScrollY(), false);
////        ScrollUtils.addOnGlobalLayoutListener((View) scrollable, new Runnable() {
////            @Override
////            public void run() {
////            }
////        });
//    }
//
//
//    @Override
//    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
//        updateViews(scrollY, true);
//    }
//
//    @Override
//    public void onDownMotionEvent() {
//    }
//
//    @Override
//    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
//    }
//
//    protected void updateViews(int scrollY, boolean animated) {
//        // If it's ListView, onScrollChanged is called before ListView is laid out (onGlobalLayout).
//        // This causes weird animation when onRestoreInstanceState occurred,
//        // so we check if it's laid out already.
//        if (!mReady) {
//            return;
//        }
//        // Translate image
//        ViewHelper.setTranslationY(mImage, -scrollY / 2);
//
//        // Translate header
//        ViewHelper.setTranslationY(mHeader, getHeaderTranslationY(scrollY));
//
//        // Show/hide gap
//        final int headerHeight = mHeaderBar.getHeight();
//        boolean scrollUp = mPrevScrollY < scrollY;
//        if (scrollUp) {
//            if (mFlexibleSpaceImageHeight - headerHeight - mActionBarSize <= scrollY) {
//                changeHeaderBackgroundHeightAnimated(false, animated);
//            }
//        } else {
//            if (scrollY <= mFlexibleSpaceImageHeight - headerHeight - mActionBarSize) {
//                changeHeaderBackgroundHeightAnimated(true, animated);
//            }
//        }
//        mPrevScrollY = scrollY;
//    }
//
//    protected float getHeaderTranslationY(int scrollY) {
//        final int headerHeight = mHeaderBar.getHeight();
//        int headerTranslationY = mActionBarSize - mIntersectionHeight;
//        if (0 <= -scrollY + mFlexibleSpaceImageHeight - headerHeight - mActionBarSize + mIntersectionHeight) {
//            headerTranslationY = -scrollY + mFlexibleSpaceImageHeight - headerHeight;
//        }
//        return headerTranslationY;
//    }
//
//    private void changeHeaderBackgroundHeightAnimated(boolean shouldShowGap, boolean animated) {
//        if (mGapIsChanging) {
//            return;
//        }
//        final int heightOnGapShown = mHeaderBar.getHeight();
//        final int heightOnGapHidden = mHeaderBar.getHeight() + mActionBarSize;
//        final float from = mHeaderBackground.getLayoutParams().height;
//        final float to;
//        if (shouldShowGap) {
//            if (!mGapHidden) {
//                // Already shown
//                return;
//            }
//            to = heightOnGapShown;
//        } else {
//            if (mGapHidden) {
//                // Already hidden
//                return;
//            }
//            to = heightOnGapHidden;
//        }
//        if (animated) {
//            ViewPropertyAnimator.animate(mHeaderBackground).cancel();
//            ValueAnimator a = ValueAnimator.ofFloat(from, to);
//            a.setDuration(100);
//            a.setInterpolator(new AccelerateDecelerateInterpolator());
//            a.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator animation) {
//                    float height = (float) animation.getAnimatedValue();
//                    changeHeaderBackgroundHeight(height, to, heightOnGapHidden);
//                }
//            });
//            a.start();
//        } else {
//            changeHeaderBackgroundHeight(to, to, heightOnGapHidden);
//        }
//    }
//
//    private void changeHeaderBackgroundHeight(float height, float to, float heightOnGapHidden) {
//        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mHeaderBackground.getLayoutParams();
//        lp.height = (int) height;
//        lp.topMargin = (int) (mHeaderBar.getHeight() - height);
//        mHeaderBackground.requestLayout();
//        mGapIsChanging = (height != to);
//        if (!mGapIsChanging) {
//            mGapHidden = (height == heightOnGapHidden);
//        }
//    }
//}
