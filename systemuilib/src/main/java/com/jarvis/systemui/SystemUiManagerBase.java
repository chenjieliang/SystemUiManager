package com.jarvis.systemui;

import android.app.Activity;
import android.view.WindowManager;

/**
 * A base implementation of {@link SystemUiHider}. Uses APIs available in all
 * API levels to show and hide the status bar.
 */
public class SystemUiManagerBase extends SystemUiManager {
	/**
	 * Whether or not the system UI is currently visible. This is a cached value
	 * from calls to {@link #hide()} and {@link #show()}.
	 */
	private boolean mVisible = true;

	/**
	 * Constructor not intended to be called by clients. Use
	 * {@link SystemUiManager#getInstance} to obtain an instance.
	 */
	protected SystemUiManagerBase(Activity activity) {
		super(activity);
	}

	@Override
	public void setup() {
		if ((mFlags & FLAG_LAYOUT_IN_SCREEN_OLDER_DEVICES) == 0) {
			mActivity.getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
							| WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
					WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
							| WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
		}
	}

	@Override
	public boolean isVisible() {
		return mVisible;
	}

	@Override
	public void hide() {
		if ((mFlags & FLAG_FULLSCREEN) != 0) {
			mActivity.getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}
		mOnVisibilityChangeListener.onVisibilityChange(false);
		mVisible = false;
	}

	@Override
	public void show() {
		if ((mFlags & FLAG_FULLSCREEN) != 0) {
			mActivity.getWindow().setFlags(0,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}
		mOnVisibilityChangeListener.onVisibilityChange(true);
		mVisible = true;
	}
}
