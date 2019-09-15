package com.qaoftheday;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
//import android.widget.TableLayout;
import android.widget.TextView;

public class StatsActivity extends Activity{
	public static int TOTAL_ANSWERS = 0;
	public static int CORRECT_ANSWERS = 1;
	public static int AVERAGE_RANK = 2;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.stats);
	    
	    SharedPreferences settings = getSharedPreferences(getString(R.string.pref_file),0);
	    final String username = settings.getString(getString(R.string.username_pref), null);
	    
	    String uriAppend = "?cmd=getStats&username="+username;
	    HttpHelper httpHelper = new HttpHelper(uriAppend);
	    
	    final LinearLayout ll = ((LinearLayout) findViewById(R.id.stats_linearlayout));
	    //final TableLayout ll = ((TableLayout) findViewById(R.id.stats_table_layout));
	    final TextView userNameText = ((TextView) findViewById(R.id.username_text));
	    final TextView totalAnswers = ((TextView) findViewById(R.id.totalanswers_text));
	    final TextView correctAnswers = ((TextView) findViewById(R.id.correctanswers_text));
	    final TextView averageRank = ((TextView) findViewById(R.id.average_rank_text));
	    
	    userNameText.setText(username);
	    
	    JSONObject json;
		try {
			json = new JSONObject(httpHelper.getResult());
			
			JSONArray totals = json.getJSONArray("Total");
			totalAnswers.setText(""+totals.getInt(TOTAL_ANSWERS));
			correctAnswers.setText(""+totals.getInt(CORRECT_ANSWERS));
			averageRank.setText(""+totals.getDouble(AVERAGE_RANK));
			
			Iterator<String> topics = json.keys();
			while(topics.hasNext()) {
			    String topic = topics.next();
			    if (topic.equals("Total")) continue;
			    TextView lblCurTotal = new TextView(this);
			    TextView lblCurCorrect = new TextView(this);
			    TextView lblCurRank = new TextView(this);
			    lblCurTotal.setText(R.string.total_answers_submitted);
			    lblCurCorrect.setText(R.string.correct_answers_submitted);
			    lblCurRank.setText(R.string.average_rank);
			    
			    TextView curTopic = new TextView(this);
			    TextView curTotal = new TextView(this);
			    TextView curCorrect = new TextView(this);
			    TextView curRank = new TextView(this);
			    curTopic.setTextSize(18);
			    curTopic.setText(topic + ":");
			    JSONArray curStats = json.getJSONArray(topic);
			    curTotal.setText(""+curStats.getInt(TOTAL_ANSWERS));
			    curCorrect.setText(""+curStats.getInt(CORRECT_ANSWERS));
			    curRank.setText(""+curStats.getDouble(AVERAGE_RANK));
			    curTotal.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
			    curCorrect.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
			    curRank.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
			    ll.addView(curTopic);
			    ll.addView(lblCurTotal);
			    ll.addView(curTotal);
			    ll.addView(lblCurCorrect);
			    ll.addView(curCorrect);
			    ll.addView(lblCurRank);
			    ll.addView(curRank);
			} 
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    /*String[] results = httpHelper.getResult().split(":");
	    int totAnswers = Integer.parseInt(results[TOTAL_ANSWERS]);
	    int corAnswers = Integer.parseInt(results[CORRECT_ANSWERS]);
	    double avgRank = Double.parseDouble(results[AVERAGE_RANK]);
	    totalAnswers.setText(totAnswers+"");
	    correctAnswers.setText((totAnswers!=0) ? corAnswers+" ("+100*corAnswers/totAnswers+"%)" : " (0%)");
	    averageRank.setText(avgRank+"");*/
	    final Button done = new Button(this);
	    done.setText(R.string.done);
	    done.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent mainIntent = new Intent(v.getContext(), PushActivity.class);
  	  			startActivity(mainIntent);
  	  			finish();
			}
		});
	    ll.addView(done);
	    
	}
}
