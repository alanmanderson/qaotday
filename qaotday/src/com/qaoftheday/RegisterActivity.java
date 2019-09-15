package com.qaoftheday;

import com.qaoftheday.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends Activity{
	private static final int FIRST = 0;
	private static final int LAST = 1;
	private static final int USERNAME = 2;
	private static final int PASSWORD = 3;
	private static final int ADDRESS = 4;
	private static final int CITY = 5;
	private static final int STATE = 6;
	private static final int ZIPCODE = 7;
	private static final int EMAIL = 8;
	
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
	    addCustomFocusListener(pw, getString(R.string.password));
	    addCustomFocusListener(pwConfirm, getString(R.string.confirm_password));
	    
	    SharedPreferences settings = getSharedPreferences(getString(R.string.pref_file),0);
	    final boolean isRegistered = settings.getBoolean(getString(R.string.is_registered_pref), false);
	    final String username = settings.getString(getString(R.string.username_pref), null);
	    if (isRegistered) {
	    	String uriAppend = "?cmd=getUserInfo";
	    	uriAppend = uriAppend.concat("&un="+username);
	    	HttpHelper httpHelper = new HttpHelper(uriAppend);
	    	String userInfo = httpHelper.getResult();
	    	//Result is given as: First:Last:Username:Address:city:state:ZipCode:email
	    	String[] info = userInfo.split(":");
	    	
	    	first.setTextColor(R.color.edit_text_color);
	    	last.setTextColor(R.color.edit_text_color);
	    	un.setTextColor(R.color.edit_text_color);
	    	pw.setTextColor(R.color.edit_text_color);
	    	pwConfirm.setTextColor(R.color.edit_text_color);
	    	address.setTextColor(R.color.edit_text_color);
	    	city.setTextColor(R.color.edit_text_color);
	    	state.setTextColor(R.color.edit_text_color);
	    	zip.setTextColor(R.color.edit_text_color);
	    	email.setTextColor(R.color.edit_text_color);
	    	
	    	first.setText(info[FIRST]);
	    	last.setText(info[LAST]);
	    	un.setText(info[USERNAME]);
	    	pw.setText(info[PASSWORD]);
	    	pwConfirm.setText(info[PASSWORD]);
	    	address.setText(info[ADDRESS]);
	    	city.setText(info[CITY]);
	    	state.setText(info[STATE]);
	    	zip.setText(info[ZIPCODE]);
	    	email.setText(info[EMAIL]);
	    }
	    
	    
	    
	    final Button submit = ((Button) findViewById(R.id.register_submit_button));
	    final Button cancel = ((Button) findViewById(R.id.register_cancel_button));
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
			    	uriAppend = uriAppend.concat("&update="+isRegistered);
			    	if (isRegistered) uriAppend = uriAppend.concat("&oldUsername="+username);
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
	    cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isRegistered){
					Intent mainIntent = new Intent(v.getContext(), PushActivity.class);
					startActivity(mainIntent);
					finish();
				} else {
					error.setText(getString(R.string.must_register_first));
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
						if (et.getInputType()==129){			//129 is invisible password
							et.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL);
						}
					}
				} else {
					if (et.getText().toString().startsWith(startingValue)){
					//if (et.getText().equals(startingValue)){
						et.setTextColor(R.color.edit_text_color);
						if (startingValue.contains("Password")){
							et.setInputType(129);
						}
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
