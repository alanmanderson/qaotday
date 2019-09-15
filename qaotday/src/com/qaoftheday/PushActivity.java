package com.qaoftheday;

import com.qaoftheday.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PushActivity extends Activity {
	private String mDeviceID;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mDeviceID = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);
        Editor editor = getSharedPreferences(PushService.TAG, MODE_PRIVATE).edit();
    	editor.putString(PushService.PREF_DEVICE_ID, mDeviceID);
    	editor.commit();
        
  	  	//PushService.actionStart(getApplicationContext());
        
        SharedPreferences settings = getSharedPreferences(getString(R.string.pref_file),0);
  	  	boolean isRegistered = settings.getBoolean(getString(R.string.is_registered_pref), false);
  	  	
  	  	if (!isRegistered){
  	  		Intent registerIntent = new Intent(this, RegisterActivity.class);
			startActivity(registerIntent);
			finish();
			return;
  	  	}

  	  	final Button todaysQuestionButton = ((Button) findViewById(R.id.todays_question_button));
  	  	final Button getStatsButton = ((Button) findViewById(R.id.view_stats_button));
  	  	final Button registerTopicButton = ((Button) findViewById(R.id.register_topic_button));
//  	  	final Button pastQuestionButton = ((Button) findViewById(R.id.past_questions_button));
  	  	final Button editAccountButton = ((Button) findViewById(R.id.edit_account_button));
  	  	final Button viewAchievementsButton = ((Button) findViewById(R.id.view_acheivements_button));
  	  	todaysQuestionButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent todaysQuestIntent = new Intent(v.getContext(), TodaysQuestionsActivity.class);
				startActivity(todaysQuestIntent);
				finish();
			}
		});
//  	  	try {
//  	  		String uriAppend = "?cmd=isQuestionToday";
//  	  		HttpHelper httpHelper = new HttpHelper(uriAppend);
//
//  	  		final String result = httpHelper.getResult();
//	  	    if (!result.equals("nil")){
//	  	    	todaysQuestionButton.setEnabled(true);
//	  	    	todaysQuestionButton.setOnClickListener(new OnClickListener() {
//					
//					@Override
//					public void onClick(View v) {
//						Intent questionIntent = new Intent(v.getContext(), QuestionActivity.class);
//		  	  			questionIntent.putExtra("quest", result);
//		  	  			startActivity(questionIntent);
//		  	  			finish();
//					}
//				});
//	  	    } else {
//	  	    	todaysQuestionButton.setEnabled(false);
//	  	    }
//	  	    
//  	  	} catch (Exception e) {
//			e.printStackTrace();
//		}
  	  	
  	  	registerTopicButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent topicIntent = new Intent(v.getContext(), TopicActivity.class);
				startActivity((topicIntent));
				//finish();
			}
		});
  	  	
  	  	editAccountButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent registerIntent = new Intent(v.getContext(), RegisterActivity.class);
				startActivity(registerIntent);
				finish();				
			}
		});
  	  	
  	  	getStatsButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent statsIntent = new Intent(v.getContext(), StatsActivity.class);
				startActivity(statsIntent);
			}
		});
  	  	
  	  	viewAchievementsButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent achievementsIntent = new Intent(v.getContext(), AchievementsActivity.class);
				startActivity(achievementsIntent);
			}
		});
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	//PushService.actionStop(getApplicationContext());
    	PushService.actionStart(getApplicationContext());
    }
}