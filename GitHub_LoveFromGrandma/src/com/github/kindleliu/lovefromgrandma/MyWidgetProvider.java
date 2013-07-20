package com.github.kindleliu.lovefromgrandma;

import java.util.Calendar;
import java.util.Date;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class MyWidgetProvider extends AppWidgetProvider {
	private static final String TAG = MyWidgetProvider.class.getSimpleName();
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		Log.i(TAG, "onUpdate");
		final int N = appWidgetIds.length;

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];

            // Create an Intent to launch ExampleActivity
            Intent intent = new Intent(context, SettingActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            // Get the layout for the App Widget and attach an on-click listener
            // to the button
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget_layout);
            views.setOnClickPendingIntent(R.id.iv_app_widget, pendingIntent);

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
            
        }
	}
	
	/** 
	 * 获取两个日期之间的间隔天数 
	 * @return 
	 */  
	public static int getGapCount(Date startDate, Date endDate) {  
	       Calendar fromCalendar = Calendar.getInstance();    
	       fromCalendar.setTime(startDate);    
	       fromCalendar.set(Calendar.HOUR_OF_DAY, 0);    
	       fromCalendar.set(Calendar.MINUTE, 0);    
	       fromCalendar.set(Calendar.SECOND, 0);    
	       fromCalendar.set(Calendar.MILLISECOND, 0);    
	   
	       Calendar toCalendar = Calendar.getInstance();    
	       toCalendar.setTime(endDate);    
	       toCalendar.set(Calendar.HOUR_OF_DAY, 0);    
	       toCalendar.set(Calendar.MINUTE, 0);    
	       toCalendar.set(Calendar.SECOND, 0);    
	       toCalendar.set(Calendar.MILLISECOND, 0);    
	   
	       return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));  
	}
	
}
