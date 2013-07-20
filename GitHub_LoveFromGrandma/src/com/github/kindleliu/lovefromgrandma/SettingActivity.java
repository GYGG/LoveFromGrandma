package com.github.kindleliu.lovefromgrandma;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RemoteViews;

import com.gyliu.core.date.DateTools;
import com.gyliu.core.setting.Setting;

public class SettingActivity extends Activity {

	private int mAppWidgetId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
		checkTheDefaultRadioButton();


		//获取调起activity的widgetID
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		if (extras != null) {
		    mAppWidgetId = extras.getInt(
		            AppWidgetManager.EXTRA_APPWIDGET_ID, 
		            AppWidgetManager.INVALID_APPWIDGET_ID);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.activity_setting, menu);
		return false;
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	

	/**
	 * 响应RadioButton变更上次去外婆家的时间
	 * @param view
	 */
	public void onRadioButtonClicked(View view) {
		// Is the button now checked?
	    boolean checked = ((RadioButton) view).isChecked();
	    Date lastCheckDate = null ;
	    
	    // Check which radio button was clicked
	    switch(view.getId()) {
	        case R.id.rb_three_days_before:
	        	lastCheckDate = DateTools.getDateBefore(new Date(System.currentTimeMillis()), 3);
	        	break;
	        case R.id.rb_two_days_before:
	        	lastCheckDate = DateTools.getDateBefore(new Date(System.currentTimeMillis()), 2);
	            break;
	        case R.id.rb_one_day_before:
	        	lastCheckDate = DateTools.getDateBefore(new Date(System.currentTimeMillis()), 1);
	            break;
	        case R.id.rb_today:
	        	lastCheckDate = new Date(System.currentTimeMillis());
	            break;
	    }
	    
	    if(lastCheckDate != null) {
	    	Setting mSetting = Setting.getInstance();
	    	mSetting.setContext(getApplicationContext());
	    	mSetting.setSetting(Constants.LAST_CHECK_DATE_NAME, lastCheckDate.getTime());
	    }
	    
	    MyWidgetProvider.UpdateAllWidgetsByCheckDate(getApplicationContext());
	    
	}
	
//	private void updateWidgetsAndFinish() {
//		Context context = getApplicationContext();
//		
//		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
//	    RemoteViews views = new RemoteViews(getApplicationContext().getPackageName(),
//	    		R.layout.app_widget_layout);
//	    appWidgetManager.updateAppWidget(mAppWidgetId, views);
//	    		
//		Intent resultValue = new Intent();
//		resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
//		setResult(RESULT_OK, resultValue);
//		
////		finish();
//		
//		AppWidgetManager gm = AppWidgetManager.getInstance(context);
//        ArrayList<Integer> appWidgetIds = new ArrayList<Integer>();
//        ArrayList<String> texts = new ArrayList<String>();
//
//        this.loadAllTitlePrefs(context, appWidgetIds, texts);
//
//        final int N = appWidgetIds.size();
//        for (int i=0; i<N; i++) {
//            ExampleAppWidgetProvider.updateAppWidget(context, gm, appWidgetIds.get(i), texts.get(i));
//        }
//	}
	
	/**
	 * 设置默认的上次去外婆家的天数
	 */
	private void checkTheDefaultRadioButton() {
		Date lastCheckDate  = null;
		Date currentDate = new Date(System.currentTimeMillis());
		
		Setting mSetting = Setting.getInstance();
    	mSetting.setContext(getApplicationContext());
		Long time = mSetting.getLongSetting(Constants.LAST_CHECK_DATE_NAME);
		
		int days;
		//求上一次去外婆家的时间
		if(time != 0) {
			lastCheckDate = new Date(time);
			days = Math.abs(DateTools.getGapCount(lastCheckDate, currentDate) % 4);
		} else {
			days = 0;
			mSetting.saveObject(Constants.LAST_CHECK_DATE_NAME, currentDate);
		}
		
		RadioGroup mRadioGroup = (RadioGroup) findViewById(R.id.rg_radios);
		
		switch (days) {
		case 0:
			mRadioGroup.check(R.id.rb_today);
			break;
		case 1:
			mRadioGroup.check(R.id.rb_one_day_before);
			break;
		case 2:
			mRadioGroup.check(R.id.rb_two_days_before);
			break;
		case 3:
			mRadioGroup.check(R.id.rb_three_days_before);
			break;
		}
	}

}
