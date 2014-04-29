/*
 * Copyright 2014 Luuk Willemsen (Twinone)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.twinone.locker.lock;

import java.io.Serializable;

import android.content.Context;
import android.util.Log;

import com.twinone.locker.R;
import com.twinone.locker.pro.ProUtils;
import com.twinone.locker.util.PrefUtils;

public class LockPreferences implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2334826883469805015L;

	public static final int TYPE_PASSWORD = 1 << 0; // 1
	public static final int TYPE_PATTERN = 1 << 1; // 2

	// Common
	public int type;
	public String orientation;
	public Boolean vibration;
	public String message;
	public int patternSize;

	// Pro only
	public String background;
	public int showAnimationResId;
	public int hideAnimationResId;
	public int showAnimationMillis;
	public int hideAnimationMillis;

	// Password only
	public String password;
	public boolean passwordSwitchButtons;

	// Pattern only
	public String pattern;
	public boolean patternStealth;
	public boolean patternHideError;

	// Pro & pattern only
	public int patternCircleResId;

	private LockPreferences() {
	}

	/**
	 * You should use this constructor which loads all properties into the
	 * object automatically
	 * 
	 * @param c
	 * @return
	 */
	public LockPreferences(Context c) {
		boolean pro = new ProUtils(c).proFeaturesEnabled();
		PrefUtils prefs = new PrefUtils(c);
		// Common
		type = getLockType(c, prefs.getString(R.string.pref_key_lock_type,
				R.string.pref_def_lock_type));
		orientation = prefs.getString(R.string.pref_key_orientation);
		vibration = prefs.getBoolean(R.string.pref_key_vibrate);
		message = prefs.getString(R.string.pref_key_lock_message);
		if (pro) {
			// Pro only
			background = prefs.getString(R.string.pref_key_background,
					R.string.pref_def_background);
			// Show animation
			final String showAnim = prefs.getString(
					R.string.pref_key_anim_show_type,
					R.string.pref_def_anim_show_type);
			showAnimationResId = getAnimationResId(c, showAnim, true);
			showAnimationMillis = prefs.parseInt(
					R.string.pref_key_anim_show_millis,
					R.string.pref_def_anim_show_millis);

			// Hide animation
			final String hideAnim = prefs.getString(
					R.string.pref_key_anim_hide_type,
					R.string.pref_def_anim_hide_type);
			hideAnimationResId = getAnimationResId(c, hideAnim, false);
			hideAnimationMillis = prefs.parseInt(
					R.string.pref_key_anim_hide_millis,
					R.string.pref_def_anim_hide_millis);
		}
		if (c.getString(R.string.pref_val_lock_type_password).equals(type)) {
			// Passwd only
			password = prefs.getString(R.string.pref_key_passwd);
			passwordSwitchButtons = prefs
					.getBoolean(R.string.pref_key_switch_buttons);
		} else {
			// // Pattern only
			pattern = prefs.getString(R.string.pref_key_pattern);
			patternStealth = prefs
					.getBoolean(R.string.pref_key_pattern_stealth);
			patternHideError = prefs
					.getBoolean(R.string.pref_key_pattern_hide_error);
			patternSize = prefs.parseInt(R.string.pref_key_pattern_size,
					R.string.pref_def_pattern_size);

			// // Pro && pattern only
			if (pro) {
				patternCircleResId = getPatternCircleResId(c,
						prefs.getString(R.string.pref_key_pattern_color));
			}
		}
	}

	/**
	 * @param animType
	 *            the input animtype
	 * @param show
	 *            true if show animation, false if hide animation
	 * @return the resid to be applied
	 */
	private static int getAnimationResId(Context c, String type, boolean show) {
		if (type.equals(c.getString(R.string.pref_val_anim_slide_left)))
			return show ? R.anim.slide_in_left : R.anim.slide_out_left;
		else if (type.equals(c.getString(R.string.pref_val_anim_slide_right)))
			return show ? R.anim.slide_in_right : R.anim.slide_out_right;
		else if (type.equals(c.getString(R.string.pref_val_anim_fade)))
			return show ? R.anim.fade_in : R.anim.fade_out;
		return 0;
	}

	public static int getLockType(Context c, String type) {
		if (type == null) {
			throw new IllegalArgumentException("Null type");
		}
		Log.d("", "getLockType (type=" + type + ")");
		if (type.equals(c.getString(R.string.pref_val_lock_type_password))) {
			return TYPE_PASSWORD;
		} else if (type
				.equals(c.getString(R.string.pref_val_lock_type_pattern))) {
			return TYPE_PATTERN;
		} else {
			return 0;
		}
	}

	private static int getPatternCircleResId(Context c, String setting) {
		if (setting.equals(c.getString(R.string.pref_val_pattern_color_blue)))
			return R.drawable.pattern_circle_blue;
		if (setting.equals(c.getString(R.string.pref_val_pattern_color_green)))
			return R.drawable.pattern_circle_green;
		else
			return R.drawable.pattern_circle_white;
	}

}