package org.apache.cordova.core;

import com.parse.ParsePushBroadcastReceiver;
import com.parse.ParseAnalytics;

import android.app.Activity;
import android.app.TaskStackBuilder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import android.os.Build;
import android.net.Uri;
import android.util.Log;

import org.json.JSONObject;
import org.json.JSONException;

public class ParsePushPluginReceiver extends ParsePushBroadcastReceiver
{	
	public static final String LOGTAG = "ParsePushPluginReceiver";
	
	@Override
	protected void onPushReceive(Context context, Intent intent) {
        
		super.onPushReceive(context, intent);
        
		JSONObject pushData = getPushData(intent);
        
        //
        //
		if(pushData != null) ParsePushPlugin.javascriptECB( pushData );
        //
        //
	}
	
	@Override
    protected void onPushOpen(Context context, Intent intent) {
        
		//
		// Note: preempt a Parse Android SDK bug observed in 1.7.0 and 1.7.1
		// where empty/null uri string causes crash
		//

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                
        //
        //
        
        ParseAnalytics.trackAppOpenedInBackground(intent);

        JSONObject pushData = getPushData(intent);
                
        String pushDataString = pushData.toString();
        String uriString = pushData.optString("uri");
        
        //
        //

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("PUSHDATA", pushDataString); // Storing string
        editor.commit(); // Commit changes
        
        //
        
        Class<? extends Activity> cls = getActivity(context, intent);
        
        Intent activityIntent;
        
        if (!uriString.isEmpty()) {
            activityIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uriString));
        } else {
            activityIntent = new Intent(context, cls);
        }
        
        activityIntent.putExtras(intent.getExtras());
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(cls);
            stackBuilder.addNextIntent(activityIntent);
            stackBuilder.startActivities();
        } else {
            activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(activityIntent);
        }

    }
	
	private static JSONObject getPushData(Intent intent){
        
		JSONObject pushData = null;
		
        try {
            pushData = new JSONObject(intent.getStringExtra("com.parse.Data"));
        } catch (JSONException e) {
            Log.e(LOGTAG, "JSONException while parsing push data:", e);
        } finally{
        	return pushData;
        }
	}
}
