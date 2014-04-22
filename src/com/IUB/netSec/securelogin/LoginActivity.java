package com.IUB.netSec.securelogin;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.spec.PSSParameterSpec;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.IUB.netSec.Database.UserDataSource;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private EditText userId;
	private EditText password;
	private UserDataSource userDataSource;
	private final int REQUEST_ID = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	    userDataSource = new UserDataSource(this);
	    
	}
	public void login (View view){
		userId = (EditText) findViewById(R.id.userId);
		password = (EditText) findViewById(R.id.password);
		Intent intent = new Intent(getApplicationContext(), GeneratorActivity.class);
		intent.putExtra("userId", (String)userId.getText().toString());
		try{
			userDataSource.open();
		if(authenticateUser(((String)userId.getText().toString()), ((String)password.getText().toString()))){
			intent.putExtra("isLoggedIn", true);
			startActivityForResult(intent,REQUEST_ID);
		}
		else
			Toast.makeText(getApplicationContext(), "Please enter correct credentials and try again", Toast.LENGTH_LONG).show();
		}
		catch(Exception e){
			System.out.println("some exception in databse open");
			userDataSource.close();
		}
		finally{
			userDataSource.close();
		}
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public boolean authenticateUser(String user, String password){
		
		String result = null;
		InputStream is = null;
		StringBuilder sb=null;
		String resultSet=null;

		//http post
		List<NameValuePair> list = null;
		try{
			list = new ArrayList<NameValuePair>();
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://10.0.2.2:8080/NetSecWebFiles/usersAndroidAuthenticate.php");
			httppost.setEntity(new UrlEncodedFormEntity(list));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		}catch(Exception e){
			Log.e("log_tag", "Error in http connection"+e.toString()+list.size());
		}
		//convert response to string
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
			sb = new StringBuilder();
			sb.append(reader.readLine() + "\n");
			String line="0";

			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}

			is.close();
			result=sb.toString();

		}catch(Exception e){
			Log.e("log_tag", "Error converting result "+e.toString());
		}

		//paring data
		String fd_id;
		String fd_name;
		JSONArray jArray; 
		try{
			jArray = new JSONArray(result);
			JSONObject json_data=null;

			for(int i=0;i<jArray.length();i++){
				json_data = jArray.getJSONObject(i);
				fd_id=json_data.getString("username");
				fd_name=json_data.getString("password");
				if(user.equals(fd_id) && password.equals(fd_name))
					return true;	
			}
			return false;

		}catch(Exception e1){
			Log.e("log_tag", "Error converting result "+e1.toString());
		}
		return false;
	}

	protected void onActivityResult(int requestCode, int resultCode,
	          Intent data) {
	      if (requestCode == REQUEST_ID) {
	          if (resultCode == RESULT_OK) {
	            userId.setText("");
	            password.setText("");
	            // use 'myValue' return value here
	          }
	      }
	}

}
