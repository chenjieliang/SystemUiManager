package com.jarvis.systemui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author chenjieliang
 */
public abstract class SystemUiManager {

    /**
     * When this flag is set, the
     * {@link WindowManager.LayoutParams#FLAG_LAYOUT_IN_SCREEN}
     * flag will be set on older devices, making the status bar "float" on top
     * of the activity layout. This is most useful when there are no controls at
     * the top of the activity layout.
     * <p>
     * This flag isn't used on newer devices because the <a
     * href="http://developer.android.com/design/patterns/actionbar.html">action
     * bar</a>, the most important structural element of an Android app, should
     * be visible and not obscured by the system UI.
     */
    public static final int FLAG_LAYOUT_IN_SCREEN_OLDER_DEVICES = 0x1;

    /**
     * When this flag is set, {@link #show()} and {@link #hide()} will toggle
     * the visibility of the status bar. If there is a navigation bar, show and
     * hide will toggle low profile mode.
     */
    public static final int FLAG_FULLSCREEN = 0x4;

    /**
     * When this flag is set, {@link #show()} and {@link #hide()} will toggle
     * the visibility of the navigation bar, if it's present on the device and
     * the device allows hiding it. In cases where the navigation bar is present
     * but cannot be hidden, show and hide will toggle low profile mode.
     */
    public static final int FLAG_HIDE_NAVIGATION = 0x2;

    public static final int FLAG_HIDE_STATUS_BAR = 0x3;

    /**
     * The activity associated with this UI hider object.
     */
    protected Activity mActivity;

    /**
     * The view on which {@link View#setSystemUiVisibility(int)} will be called.
     */
    protected View mAnchorView;

    /**
     * The current UI hider flags.
     *
     * @see #FLAG_FULLSCREEN
     * @see #FLAG_HIDE_NAVIGATION
     * @see #FLAG_LAYOUT_IN_SCREEN_OLDER_DEVICES
     */
    protected int mFlags = FLAG_FULLSCREEN;

    /**
     * The current visibility callback.
     */
    protected SystemUiManager.OnVisibilityChangeListener mOnVisibilityChangeListener = sDummyListener;


    /**
     * Creates and returns an instance of {@link SystemUiHider} that is
     * appropriate for this device. The object will be either a
     * {@link SystemUiHiderBase} or {@link SystemUiHiderHoneycomb} depending on
     * the device.
     *
     * @param activity
     *            The activity whose window's system UI should be controlled by
     *            this class.
     */
    public static SystemUiManager getInstance(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return new SystemUiManagerHoneycomb(activity);
        } else {
            return new SystemUiManagerBase(activity);
        }
    }

    protected SystemUiManager(Activity activity) {
        mActivity = activity;
        mAnchorView = mActivity.getWindow().getDecorView();
    }

    public void setStatusBarTranslucent(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = mActivity.getWindow();
                window.setStatusBarColor(Color.TRANSPARENT);
            } else {
                Window window = mActivity.getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.flags |= flagTranslucentStatus;
                window.setAttributes(attributes);
            }
        }
    }

    public void setNavigationTranslucent(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
            Window window = mActivity.getWindow();
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.flags |= flagTranslucentNavigation;
            window.setAttributes(attributes);
        }
    }

    /*
     * @param flags
     *            Either 0 or any combination of {@link #FLAG_FULLSCREEN},
     *            {@link #FLAG_HIDE_NAVIGATION}, and
     *            {@link #FLAG_LAYOUT_IN_SCREEN_OLDER_DEVICES}.
     */
    public void setFlags(int flags){
        this.mFlags = flags;
        setup();
    }

    /*
     * @param flags
     *            Either 0 or any combination of {@link #FLAG_FULLSCREEN},
     *            {@link #FLAG_HIDE_NAVIGATION}, and
     *            {@link #FLAG_LAYOUT_IN_SCREEN_OLDER_DEVICES}.
     */
    public void show(int flags){
        setFlags(flags);
        setup();
    }

    /**
     * Sets up the system UI hider
     */
    protected abstract void setup();

    /**
     * Returns whether or not the system UI is visible.
     */
    public abstract boolean isVisible();

    /**
     * Hide the system UI.
     */
    public abstract void hide();

    /**
     * Show the system UI.
     */
    public abstract void show();

    /**
     * Toggle the visibility of the system UI.
     */
    public void toggle() {
        if (isVisible()) {
            hide();
        } else {
            show();
        }
    }

    /**
     * Registers a callback, to be triggered when the system UI visibility
     * changes.
     */
    public void setOnVisibilityChangeListener(
            SystemUiManager.OnVisibilityChangeListener listener) {
        if (listener == null) {
            listener = sDummyListener;
        }

        mOnVisibilityChangeListener = listener;
    }

    /**
     * A dummy no-op callback for use when there is no other listener set.
     */
    private static SystemUiManager.OnVisibilityChangeListener sDummyListener = new SystemUiManager.OnVisibilityChangeListener() {
        @Override
        public void onVisibilityChange(boolean visible) {
        }
    };

    /**
     * A callback interface used to listen for system UI visibility changes.
     */
    public interface OnVisibilityChangeListener {
        /**
         * Called when the system UI visibility has changed.
         *
         * @param visible
         *            True if the system UI is visible.
         */
        public void onVisibilityChange(boolean visible);
    }


}
