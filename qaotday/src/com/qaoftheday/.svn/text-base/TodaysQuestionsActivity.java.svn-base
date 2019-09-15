package com.qaoftheday;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class TodaysQuestionsActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ScrollView sv = new ScrollView(this);
		LinearLayout ll = new LinearLayout(this);
		ll.setOrientation(LinearLayout.VERTICAL);
		sv.addView(ll);
		
		final SharedPreferences settings = getSharedPreferences(getString(R.string.pref_file),0);
//  	  	final String username = settings.getString(getString(R.string.username_pref), "");
		final String strTopics = settings.getString(getString(R.string.topics_pref), "");
		if (strTopics.equals("[]")){
			TextView noSubscription = new TextView(this);
			noSubscription.setText(getString(R.string.no_topic_selected));
			ll.addView(noSubscription);
			
			final Button selectTopics = new Button(this);
			final Button cancel = new Button(this);
			
			selectTopics.setText(getString(R.string.register_new_topic));
			selectTopics.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			selectTopics.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent topicsIntent = new Intent(v.getContext(), TopicActivity.class);
					startActivity(topicsIntent);
					finish();
				}
			});
			cancel.setText(getString(R.string.cancel));
			cancel.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			cancel.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent mainIntent = new Intent(v.getContext(),PushActivity.class);
					startActivity(mainIntent);
					finish();
				}
			});
			selectTopics.setGravity(1);
			cancel.setGravity(1);
			ll.addView(selectTopics);
			ll.addView(cancel);
			
			setContentView(sv);
			return;
		}
		String[] selectedTopics = getTopicsFromString(strTopics);
		for(final String topic: selectedTopics){
			final TextView tv = new TextView(this);
			tv.setText(topic);
			ll.addView(tv);
			final Button getQuestion = new Button(this);
			getQuestion.setText("Get Question");
	  	  	try {
		  		String uriAppend = "?cmd=isQuestionToday";
		  		uriAppend = uriAppend.concat("&topic="+topic);
		  		HttpHelper httpHelper = new HttpHelper(uriAppend);
	
		  		final String result = httpHelper.getResult();
		  		if (!result.equals("nil")){
		  			getQuestion.setOnClickListener(new OnClickListener() {
					
		  				@Override
		  				public void onClick(View v) {
		  					Intent questionIntent = new Intent(v.getContext(), QuestionActivity.class);
		  					questionIntent.putExtra("quest", result);
		  					questionIntent.putExtra("topic", topic);
		  					startActivity(questionIntent);
		  					finish();
		  				}
		  			});
		  		} else {
		  			getQuestion.setEnabled(false);
		  		}
		  		ll.addView(getQuestion);
	  	    
		  	} catch (Exception e) {
		  		e.printStackTrace();
		  	}

		}//End of For Loop
		
		final Button btn = new Button(this);
		btn.setText(getString(R.string.return_to_main));
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent mainIntent = new Intent(v.getContext(), PushActivity.class);
				startActivity(mainIntent);
				finish();
			}
		});
		ll.addView(btn);
		
		setContentView(sv);

	}

	private String[] getTopicsFromString(String strTopics) {
		strTopics = strTopics.replace("[", "");
		strTopics = strTopics.replace("]", "");
		return strTopics.split(", ");
	}
}
