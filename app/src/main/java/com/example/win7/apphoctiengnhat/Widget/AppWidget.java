package com.example.win7.apphoctiengnhat.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.win7.apphoctiengnhat.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * Implementation of App Widget functionality.
 */
public class AppWidget extends AppWidgetProvider {

    static String btnNextWidget_CLICK_ACTION = "CLICKED";

    public static ArrayList<DataWidget> arrayList = new ArrayList<>();

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        //------------------------------------------------------------------------------------------
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);

        //------------------------------------------------------------------------------------------
        Intent intent = new Intent(context,AppWidget.class);
        intent.setAction(btnNextWidget_CLICK_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intent,0);
        //------------------------------------------------------------------------------------------

        Random random = new Random();
//        int i = random.nextInt(ManHinhChinh.arrayDataWidget.size());
//
//        views.setTextViewText(R.id.appwidget_txtTiengNhat,ManHinhChinh.arrayDataWidget.get(i).TiengNhat);
//        views.setTextViewText(R.id.appwidget_txtYNghia, ManHinhChinh.arrayDataWidget.get(i).YNghia);
        views.setOnClickPendingIntent(R.id.btnNextWidget,pendingIntent);

        //------------------------------------------------------------------------------------------
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        //------------------------------------------------------------------------------------------
        if(intent.getAction().equals(btnNextWidget_CLICK_ACTION))
        {
            try {
                Toast.makeText(context,"CLICK",Toast.LENGTH_SHORT).show();
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

                RemoteViews remoteViews;
                ComponentName watchWidget;

                remoteViews = new RemoteViews(context.getPackageName(), R.layout.app_widget);
                watchWidget = new ComponentName(context, AppWidget.class);

                Random random = new Random();
//                int i = random.nextInt(ManHinhChinh.arrayDataWidget.size());
//
//                remoteViews.setTextViewText(R.id.appwidget_txtTiengNhat, ManHinhChinh.arrayDataWidget.get(i).TiengNhat);
//                remoteViews.setTextViewText(R.id.appwidget_txtYNghia, ManHinhChinh.arrayDataWidget.get(i).YNghia);

                appWidgetManager.updateAppWidget(watchWidget, remoteViews);
            }
            catch (Exception ex)
            {
//                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
//                ComponentName watchWidget;
//                RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.app_widget);
//                watchWidget = new ComponentName(context, AppWidget.class);
//                Intent configIntent = new Intent(context, ManHinhChinh.class);
//
//                PendingIntent configPendingIntent = PendingIntent.getActivity(context, 0, configIntent, 0);
//
//                remoteViews.setOnClickPendingIntent(R.id.btnNextWidget, configPendingIntent);
//                appWidgetManager.updateAppWidget(watchWidget, remoteViews);
            }
        }
        //------------------------------------------------------------------------------------------
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

//        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.app_widget);
//        Intent configIntent = new Intent(context, ManHinhChinh.class);
//
//        PendingIntent configPendingIntent = PendingIntent.getActivity(context, 0, configIntent, 0);
//
//        remoteViews.setOnClickPendingIntent(R.id.btnNextWidget, configPendingIntent);
//        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created

    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled

    }
}

