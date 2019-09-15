package com.qaoftheday;

import java.io.IOException;

import android.util.Log;

public class Logger {
	public static final String		TAG = "QAotDay";
	
	// Connection log for the push service. Good for debugging.
	private ConnectionLog 			mLog;
	
	public Logger(){
		try {
			mLog = new ConnectionLog();
			Log.i(TAG, "Opened log at " + mLog.getPath());
		} catch (IOException e) {
			Log.e(TAG, "Failed to open log", e);
		}
	}
	
	// log helper function
	public void log(String message) {
		log(message, null);
	}
	public void log(String message, Throwable e) {
		if (e != null) {
			Log.e(TAG, message, e);
			
		} else {
			Log.i(TAG, message);			
		}
		
		if (mLog != null)
		{
			try {
				mLog.println(message);
			} catch (IOException ex) {}
		}		
	}
}
