package com.qaoftheday;

import com.qaoftheday.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class QuestionActivity extends Activity{
	Logger log;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    log = new Logger();
	    
	    SharedPreferences settings = getSharedPreferences(getString(R.string.pref_file),0);
	    final String username = settings.getString(getString(R.string.username_pref), ""); 
	    
	    final String questInfo = getIntent().getStringExtra("quest");
	    final String topic = getIntent().getStringExtra("topic");
	    final String quest = questInfo.substring(questInfo.indexOf(":")+1);
		final String questID = questInfo.substring(0, questInfo.indexOf(":"));
		String uriAppend = "?cmd=hasCorrectlyAnswered";
        uriAppend = uriAppend.concat("&un="+username);
        uriAppend = uriAppend.concat("&questID=" + questID);
        log.log("visiting fastqs.com/ajaxtools.php"+uriAppend);
        HttpHelper http = new HttpHelper(uriAppend);
        if (http.getResult().equals("true")){
        	setContentView(R.layout.error);
        	final TextView error = ((TextView) findViewById(R.id.errorText));
        	final Button ok =  ((Button) findViewById(R.id.error_ok_button));
        	error.setText("You have already answered this question correctly!");
        	ok.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent mainIntent = new Intent(v.getContext(), PushActivity.class);
			  	  	startActivity(mainIntent);
				  	finish();
				}
			});
        	return;
        }
		
	    setContentView(R.layout.question);
	    final TextView question = ((TextView) findViewById(R.id.questionText));
	    final EditText answer = ((EditText) findViewById(R.id.answer));
	    
	    question.setText(quest);
	    final Button submit = ((Button) findViewById(R.id.submit_answer_button));
	    submit.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				try  {
					log.log("Submit clicked");
			        //SharedPreferences settings = getSharedPreferences(getString(R.string.pref_file),0);
			  	  	//String username = settings.getString(getString(R.string.username_pref), ""); 
			        String anstxt = answer.getText().toString().replaceAll(" ", "+");
			        log.log("username: "+username);
			        log.log("anstxt:"+anstxt);
			        String uriAppend = "?cmd=answer";
			        uriAppend = uriAppend.concat("&un="+username);
			        uriAppend = uriAppend.concat("&answer=" + anstxt);
			        uriAppend = uriAppend.concat("&topic="+topic);
			        uriAppend = uriAppend.concat("&questID=" + questID);
			        log.log("visiting fastqs.com/ajaxtools.php"+uriAppend);
			        HttpHelper http = new HttpHelper(uriAppend);
		            String result = http.getResult();
			    	log.log("the result is: "+result);
			  	  	Intent resultIntent = new Intent(v.getContext(), ResultsActivity.class);
			  	  	resultIntent.putExtra("response",result);
			  	  	startActivity(resultIntent);
				  	finish();
			    } catch (Exception e) {
			        e.printStackTrace();
			        String test = e.getMessage();
			        System.out.print(test);
			    }
			}
	    	
	    });
	}	
}
