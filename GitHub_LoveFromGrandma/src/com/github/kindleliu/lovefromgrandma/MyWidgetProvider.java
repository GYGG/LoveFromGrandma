package com.github.kindleliu.lovefromgrandma;

import java.util.Date;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.gyliu.core.date.DateTools;
import com.gyliu.core.setting.Setting;

public class MyWidgetProvider extends AppWidgetProvider {
	private static final String TAG = MyWidgetProvider.class.getSimpleName();
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		Log.i(TAG, "onUpdate");
//		final int N = appWidgetIds.length;

        // Perform this loop procedure for each App Widget that belongs to this provider
//        for (int i=0; i<N; i++) {
//            int appWidgetId = appWidgetIds[i];

            // Create an Intent to launch ExampleActivity

            // Get the layout for the App Widget and attach an on-click listener
            // to the button
            UpdateAllWidgetsByCheckDate(context);
            
            // Tell the AppWidgetManager to perform an update on the current app widget
            
//        }
	}
	
	public static void UpdateAllWidgetsByCheckDate(Context context) {
		Log.i(TAG, "UpdateImageByCheckDate");
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget_layout);
		
		int days = getDays(context);
		
		
		views.setTextViewText(R.id.tv_next_checkdate, String.valueOf(days));
		
		Intent intent = new Intent(context, SettingActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
		views.setOnClickPendingIntent(R.id.iv_app_widget, pendingIntent);
		
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        appWidgetManager.updateAppWidget(new ComponentName(context, MyWidgetProvider.class), views);
		
	}
	
	/**
	 * 获取距下一次去外婆家的天数
	 * @param context
	 * @return
	 */
	private static int getDays(Context context) {
		Date currentDate = new Date(System.currentTimeMillis());
		
		Setting mSetting = Setting.getInstance();
    	mSetting.setContext(context);
		Long lastTime = mSetting.getLongSetting(Constants.LAST_CHECK_DATE_NAME);
		
		if(lastTime != 0) {
			Date lastCheckDate = new Date(lastTime);
			return 4 - Math.abs(DateTools.getGapCount(lastCheckDate, currentDate) % 4);
		} else {
			return 0;
		}
	}
	
	/**
	 * 保存新的去外婆家的时间
	 * @param context
	 */
//	private void saveFreshCheckDate(Context context) {
//		Date currentDate = new Date(System.currentTimeMillis());
//		
//		Setting mSetting = Setting.getInstance();
//    	mSetting.setContext(context);
//		mSetting.saveObject(Constants.LAST_CHECK_DATE_NAME, currentDate);
//	}
	
	
	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
	}
	
}
