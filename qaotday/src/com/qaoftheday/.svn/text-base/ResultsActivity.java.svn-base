package com.qaoftheday;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ResultsActivity extends Activity{
	private final static int RANKING = 0;
	private final static int IS_CORRECT = 1;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.results);
	    final TextView isCorrectBox = ((TextView) findViewById(R.id.isCorrectBox));
	    final TextView resultBox = ((TextView) findViewById(R.id.resultBox));
	    final String result = getIntent().getStringExtra("response");
	    String[] results = result.split(":");
	    String msg;
	    if (results[IS_CORRECT].equals("true")){
	    	isCorrectBox.setText(R.string.correct_answer);
	    	msg = getString(R.string.congratulations_result)+" "+results[RANKING]+" "+getString(R.string.to_answer_result);
	    } else {
	    	isCorrectBox.setText(R.string.incorrect_answer);
	    	msg = results[RANKING]+" "+getString(R.string.correct_answers);
	    }
	     
	    resultBox.setText(msg);
	    final Button ok = ((Button) findViewById(R.id.result_ok_button));
	    ok.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				try  {		        
			  	  	Intent mainIntent = new Intent(v.getContext(), PushActivity.class);
			  	  	//mainIntent.putExtra("response",result);
			  	  	startActivity(mainIntent);
				  	finish();
			        
			    } catch (Exception e) {
			        e.printStackTrace();
			    }
			}
	    	
	    });
	}	
}
