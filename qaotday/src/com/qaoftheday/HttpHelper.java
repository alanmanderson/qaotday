package com.qaoftheday;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpHelper {
	private String result;
	
	public HttpHelper(String uriAppend){
		try{
			HttpClient client = new DefaultHttpClient();  
	        String getURL = "http://fastqs.com/ajaxtools.php";
	    	
	        StringBuilder uriBuilder = new StringBuilder(getURL);
  	  		uriBuilder.append(uriAppend);
	        
	    	HttpGet request = new HttpGet(uriBuilder.toString());
	    	HttpResponse response = client.execute(request);
	        
	    	// Check if server response is valid
	        StatusLine status = response.getStatusLine();
	        if (status.getStatusCode() != 200) {
	            throw new Exception("Invalid response from server: " +
	                    status.toString());
	        }
	
	        // Pull content stream from response
	        HttpEntity entity = response.getEntity();
	        InputStream inputStream = entity.getContent();
	
	        ByteArrayOutputStream content = new ByteArrayOutputStream();
	
	        // Read response into a buffered stream
	        int readBytes = 0;
	        byte[] sBuffer = new byte[512];
	        while ((readBytes = inputStream.read(sBuffer)) != -1) {
	            content.write(sBuffer, 0, readBytes);
	        }
	
	        //result from buffered stream
	        result = new String(content.toByteArray());
		} catch(IOException e){
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getResult(){
		return result;
	}
}
