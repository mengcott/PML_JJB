package com.jjb.activity;

import java.text.ParseException;
import java.util.Date;

import com.jjb.R;
import com.jjb.util.Constant;
import com.jjb.widget.SignInTask;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

/**
 * App启动时的欢迎Activity
 * @author Robert Peng
 */
public class WelcomeActivity extends Activity {
	private String accessKey;
	private int userId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		
		SharedPreferences settings = getSharedPreferences(Constant.PREF_USER_INFO, Activity.MODE_PRIVATE);
		accessKey = settings.getString(Constant.PREF_ACCESS_KEY, "");
		userId = settings.getInt(Constant.PREF_USERID, 0);
		Date now = new Date();
		Date expiresTime = null;
		try {
			expiresTime = Constant.DATETIME_FORMAT.parse(
					settings.getString(Constant.PREF_EXPIRES_TIME, Constant.DATETIME_FORMAT.format(now)));
		} catch (ParseException e) {
			Log.e("JJB", "Malformed expiresTime in SharedPreferences");
		}
		
		if (expiresTime != null && expiresTime.after(now)) {
			Constant.ACCESS_KEY = accessKey;
			Constant.USER_ID = userId;
			Intent mIntent = new Intent(WelcomeActivity.this, IndexActivity.class);
			startActivity(mIntent);
			WelcomeActivity.this.finish();
		} else {
			Intent mIntent = new Intent(WelcomeActivity.this, SignInActivity.class);
			startActivity(mIntent);
			WelcomeActivity.this.finish();
		}
	}

}
