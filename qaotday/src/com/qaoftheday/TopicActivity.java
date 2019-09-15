package com.qaoftheday;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class TopicActivity extends Activity {
	public ArrayList<String> selectedTopics;
	private Logger log;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		log = new Logger();
		
		final SharedPreferences settings = getSharedPreferences(getString(R.string.pref_file),0);
  	  	final String username = settings.getString(getString(R.string.username_pref), "");
  	  	String strTopics = settings.getString(getString(R.string.topics_pref), "[]");
		log.log("Starting topic selection.");
  	  	
  	  	
  	  	ArrayList<String> myTopics = getArray(strTopics);
  	  	
		//devID = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID); 
		selectedTopics = new ArrayList<String>();
		ScrollView sv = new ScrollView(this);
		LinearLayout ll = new LinearLayout(this);
		ll.setOrientation(LinearLayout.VERTICAL);
		sv.addView(ll);
		
		final TextView tv = new TextView(this);
		tv.setText(R.string.topic_selection_label);
		ll.addView(tv);
		String[] topics = getTopics();
		if (topics.length==myTopics.size()){
			final TextView alreadySubscribed = new TextView(this);
			alreadySubscribed.setText(R.string.already_subscribed_to_all);
			ll.addView(alreadySubscribed);
		} else {
			for (int i=0; i<topics.length; i++){
				if (myTopics.contains(topics[i])){
					continue;
				}
				final CheckBox cb = new CheckBox(this);
				cb.setText(topics[i]);
				cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if(isChecked){
							selectedTopics.add(cb.getText().toString());
						} else {
							selectedTopics.remove(cb.getText().toString());
						}
					}
				});
				ll.addView(cb);
			}
		}
		
		final TextView txtUnsubscribe = new TextView(this);
		txtUnsubscribe.setText(R.string.unsubscribe);
		ll.addView(txtUnsubscribe);

		if (myTopics.size()==0){
			final TextView notSubscribed = new TextView(this);
			notSubscribed.setText(R.string.no_topic_selected);
			ll.addView(notSubscribed);
		} else {
			for (int i=0; i<myTopics.size(); i++){
				final CheckBox cb = new CheckBox(this);
				cb.setText(myTopics.get(i));
				cb.setChecked(true);
				selectedTopics.add(myTopics.get(i));
				cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if(isChecked){
							selectedTopics.add(cb.getText().toString());
						} else {
							selectedTopics.remove(cb.getText().toString());
						}
					}
				});
				ll.addView(cb);
			}
		}
		
		final Button btn = new Button(this);
		btn.setText(R.string.submit);
		btn.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		btn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				String uriAppend = "?cmd=setTopics";
				uriAppend = uriAppend.concat("&un="+username);
				for(int i=0;i<selectedTopics.size();i++){
					uriAppend = uriAppend.concat("&topics[]="+selectedTopics.get(i));
				}
				HttpHelper httpHelper = new HttpHelper(uriAppend);
				if (!httpHelper.equals("OK")){
					
				}
				SharedPreferences.Editor editor = settings.edit();
				editor.putBoolean(getString(R.string.is_registered_pref), true);
				editor.putString(getString(R.string.topics_pref), selectedTopics.toString());
				editor.commit();
				
				Intent mainIntent = new Intent(v.getContext(), PushActivity.class);
  	  			startActivity(mainIntent);
  	  			finish();
			}

		});
		ll.addView(btn);
		setContentView(sv);

	}

	private ArrayList<String> getArray(String strTopics) {
		ArrayList<String> arr = new ArrayList<String>();
		strTopics = strTopics.replace("[", "");
		strTopics = strTopics.replace("]", "");
		String[] topics = strTopics.split(", ");
		for(String topic:topics){
			if (!topic.equals("")){
				arr.add(topic);
			}
		}
		return arr;
	}

	private String[] getTopics() {
		String[] topics = {};
		try{
			String uriAppend = "?cmd=getTopics";
			HttpHelper httpHelper = new HttpHelper(uriAppend);
			
	        String result = httpHelper.getResult();
	        topics = result.split(",");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return topics;
	}
}
