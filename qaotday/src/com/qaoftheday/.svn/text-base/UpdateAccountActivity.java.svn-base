package com.qaoftheday;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UpdateAccountActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.register);
	    
	    final EditText first = ((EditText) findViewById(R.id.firstName));
	    final EditText last = ((EditText) findViewById(R.id.lastName));
	    final EditText un = ((EditText) findViewById(R.id.username));
	    final EditText pw = ((EditText) findViewById(R.id.password));
	    final EditText pwConfirm = ((EditText) findViewById(R.id.passwordConfirm));
	    final EditText address = ((EditText) findViewById(R.id.address));
	    final EditText city = ((EditText) findViewById(R.id.city));
	    final EditText state = ((EditText) findViewById(R.id.state));
	    final EditText zip = ((EditText) findViewById(R.id.zipCode));
	    final EditText email = ((EditText) findViewById(R.id.email));
	    final TextView error = ((TextView) findViewById(R.id.error_label));
	    addCustomFocusListener(first, getString(R.string.first_name));
	    addCustomFocusListener(last, getString(R.string.last_name));
	    addCustomFocusListener(un, getString(R.string.username));
	    addCustomFocusListener(address, getString(R.string.address));
	    addCustomFocusListener(city, getString(R.string.city));
	    addCustomFocusListener(state, getString(R.string.state));
	    addCustomFocusListener(zip, getString(R.string.zip_code));
	    addCustomFocusListener(email, getString(R.string.email));
	    
	    final Button submit = ((Button) findViewById(R.id.register_submit_button));
	    submit.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				try  {
					if (!getURLString(pwConfirm).equals(getURLString(pw))){
						//TODO: insert a popup window here
						pw.setText("");
						pwConfirm.setText("");
						return;
					}
					String uriAppend = "?cmd=register";
			    	uriAppend = uriAppend.concat("&first=" + getURLString(first));
			    	uriAppend = uriAppend.concat("&last=" + getURLString(last));
			    	uriAppend = uriAppend.concat("&un=" + getURLString(un));
			    	uriAppend = uriAppend.concat("&pw=" + getURLString(pw));
			    	uriAppend = uriAppend.concat("&address=" + getURLString(address));
			    	uriAppend = uriAppend.concat("&city=" + getURLString(city));
			    	uriAppend = uriAppend.concat("&state=" + getURLString(state));
			    	uriAppend = uriAppend.concat("&zipcode=" + getURLString(zip));
			    	uriAppend = uriAppend.concat("&email=" + getURLString(email));
			    	HttpHelper http =  new HttpHelper(uriAppend);
			    	
		            //result from buffered stream
		            String result = http.getResult();
			        
		            if (result.equals("username_in_use")){
		            	error.setText(R.string.un_in_use);
		            } else {
		            	SharedPreferences settings = getSharedPreferences(getString(R.string.pref_file),0);
		            	SharedPreferences.Editor editor = settings.edit();
		            	editor.putBoolean(getString(R.string.is_registered_pref), true);
		            	editor.putString(getString(R.string.username_pref), un.getText().toString());
		            	editor.commit();
						Intent mainIntent = new Intent(v.getContext(), PushActivity.class);
		  	  			startActivity(mainIntent);
		  	  			finish();
		            }
			    } catch (Exception e) {
			        e.printStackTrace();
			    }
			}
	    	
	    });
	}
		
		private void addCustomFocusListener(final EditText et, final String startingValue){
			et.setOnFocusChangeListener(new OnFocusChangeListener() {
				
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					if (!hasFocus){
						if (et.getText().toString().equals("")){
							et.setText(startingValue);
							et.setTextColor(R.color.label_color);
						}
					} else {
						if (et.getText().toString().startsWith(startingValue)){
							et.setTextColor(R.color.text_color);
							et.selectAll();
						}	
					}
				}
			});
		}
		
		private String getURLString(final EditText et){
			return et.getText().toString().replaceAll(" ", "+");
		}
}
