package com.jjb.activity;

import com.baidu.voicerecognition.android.ui.BaiduASRDigitalDialog;
import com.baidu.voicerecognition.android.ui.DialogRecognitionListener;
import com.jjb.R;
import com.jjb.util.Constant;
import com.jjb.widget.SinkingView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 主页Activity，包含一个波浪球和一个百度云语音
 * @author Robert Peng
 */
public class IndexActivity extends BaseActivity {
	private BaiduASRDigitalDialog mDialog = null;

	private DialogRecognitionListener mRecognitionListener;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index);
		
		final Button recordButton = (Button) findViewById(R.id.addRecord);
		final Button textButton = (Button) findViewById(R.id.addTextRecord);
		
		final SinkingView sinkingView = (SinkingView) findViewById(R.id.sinking);
		// TODO 设定小球的百分比
		sinkingView.setPercent(0.56f);
		
		// 百度云语音的回调Listener
		mRecognitionListener = new DialogRecognitionListener() {
			public void onResults(Bundle bundle) {
				Bundle startBundle = new Bundle();
				Intent intent = new Intent(IndexActivity.this, MainActivity.class);
				startBundle
						.putStringArrayList(
								"resultList",
								bundle != null ? bundle
										.getStringArrayList(RESULTS_RECOGNITION)
										: null);
				intent.putExtras(startBundle);
				startActivity(intent);
			}
		};
		
		textButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IndexActivity.this, MainActivity.class);
				startActivity(intent);
			}
		});
		
		recordButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// 打开百度云语音
				if (mDialog != null) {
					mDialog.dismiss();
				}
				Bundle params = new Bundle();
				
				// API Key
				params.putString(BaiduASRDigitalDialog.PARAM_API_KEY,
						Constant.BAIDU_API_KEY);
				params.putString(BaiduASRDigitalDialog.PARAM_SECRET_KEY,
						Constant.BAIDU_SECRET_KEY);
				// 设定对话框主题
				params.putInt(BaiduASRDigitalDialog.PARAM_DIALOG_THEME,
						Constant.BAIDU_DIALOG_THEME);
				// 设定识别领域
				params.putInt(BaiduASRDigitalDialog.PARAM_PROP,
						Constant.BAIDU_PROP);
				// 设定语言
				params.putString(
						BaiduASRDigitalDialog.PARAM_LANGUAGE,
						Constant.BAIDU_LANGUAGE);
				// 设定提示音播放
				params.putBoolean(
						BaiduASRDigitalDialog.PARAM_START_TONE_ENABLE,
						Constant.BAIDU_START_TONE_ENABLE);
				params.putBoolean(
						BaiduASRDigitalDialog.PARAM_END_TONE_ENABLE,
						Constant.BAIDU_END_TONE_ENABLE);
				params.putBoolean(
						BaiduASRDigitalDialog.PARAM_TIPS_TONE_ENABLE,
						Constant.BAIDU_TIP_TONE_ENABLE);
				// 设定语义识别
				params.putBoolean(
						BaiduASRDigitalDialog.PARAM_NLU_ENABLE,
						Constant.BAIDU_NLU_ENABLE);

				// 生成对话框对象，设置回调函数
				mDialog = new BaiduASRDigitalDialog(IndexActivity.this, params);
				mDialog.setDialogRecognitionListener(mRecognitionListener);
				// 启动对话框
				mDialog.show();
			}
		});
		
	}
	
}
