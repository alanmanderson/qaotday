package com.qaoftheday;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AchievementsActivity extends Activity {
	Logger log;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log = new Logger();
        setContentView(R.layout.achievement);
        final LinearLayout ll = ((LinearLayout) findViewById(R.id.achievementlinearlayout));
        String uriAppend = "?cmd=getAchievements";
        HttpHelper http = new HttpHelper(uriAppend);
        try {
        	//object format:
        	//{"achievements": [ {JsonObject}, {JsonObject}  ], "user": [  {JsonObject}, {JsonObject }   ]
			JSONObject json = new JSONObject(http.getResult());
			JSONArray achievements = json.getJSONArray("achievements");
			JSONArray userAchievements = json.getJSONArray("user");
			for (int i=0;i<achievements.length();i++){
				final TextView tv = new TextView(this);
				JSONObject achieve = achievements.getJSONObject(i);
				tv.setText(achieve.getString("Name")+": "+achieve.getString("Description"));
				for (int j=0;j<userAchievements.length();j++){
					if (achieve.getString("ID").equals(userAchievements.getJSONObject(j).getString("ID"))){
						tv.setTextColor(this.getResources().getColor(R.color.black_text));
						break;
					}
				}
				
				
				ll.addView(tv);
			}
			
			log.log(json.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final Button btnOK = new Button(this);
		btnOK.setText(R.string.ok);
		ll.addView(btnOK);
		btnOK.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent mainIntent = new Intent(v.getContext(), PushActivity.class);
		  	  	startActivity(mainIntent);
			  	finish();
				
			}
		});
	}
	
	
}
