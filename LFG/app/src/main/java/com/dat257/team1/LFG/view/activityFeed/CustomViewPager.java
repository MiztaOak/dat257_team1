package com.dat257.team1.LFG.view.activityFeed;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class CustomViewPager extends ViewPager {

    public CustomViewPager(@NonNull Context context) {
        super(context);
    }

    public CustomViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event){
        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            // Always return false to disable user swipes
            return false;
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            // Always return false to disable user swipes
            return false;
        }
        //return false;
        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }
}
