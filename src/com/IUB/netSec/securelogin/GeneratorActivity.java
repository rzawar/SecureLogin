package com.IUB.netSec.securelogin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.IUB.netSec.Database.UserDataSource;



public class GeneratorActivity extends Activity{
	private TextView welcomeText;
	private TextView otpTextView;
	private Intent intent;
	private Button generate;
	private UserDataSource userDataSource;
	private boolean isLoggedIn = false;
	private String passphrase;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_generator_new);

		userDataSource = new UserDataSource(this);
		intent  = getIntent();
		if(intent.getBooleanExtra("isLoggedIn",false)!= true)
			super.onBackPressed();
		welcomeText =(TextView)findViewById(R.id.user);
		otpTextView = (TextView)findViewById(R.id.otpTextview);
		generate = (Button)findViewById(R.id.generate);
		if(welcomeText != null)
			welcomeText.setText("Welcome "+intent.getStringExtra("userId"));
		/***************/
		getPassphrase(intent.getStringExtra("userId"));
		//Log.e("Syso", passphrase);
		/*String result = null;
		InputStream is = null;
		StringBuilder sb=null;
		String resultSet=null;

		//http post
		List<NameValuePair> list = null;
		try{
			list = new ArrayList<NameValuePair>();
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://10.0.2.2:8080/users.php");
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
				Log.e("syo",fd_id+" "+fd_name);

			}

		}catch(Exception e1){
			Log.e("log_tag", "Error converting result "+e1.toString());
		}

		 *//**********************/
	}
	public void getPassphrase(String username) {
		// TODO Auto-generated method stub
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httppost = new HttpGet("http://10.0.2.2:8080/NetSecWebFiles/GetPassPhraseAndroid.php");
		JSONObject json = new JSONObject();
		try {           
			json.put("userId", username);//place each of the strings as you did in postData method
			JSONArray postjson=new JSONArray();
			postjson.put(json);
			httppost.setHeader("json",json.toString());
			httppost.getParams().setParameter("jsonpost",postjson);     
			HttpResponse response = httpclient.execute(httppost);

			// for JSON retrieval:
			if(response != null)
			{ 
				InputStream is = response.getEntity().getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				StringBuilder sb = new StringBuilder();
				String line = null;
				try {
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				String jsonStr = sb.toString(); //take the string you built place in a string
				JSONArray jArray; 
				try{
					jArray = new JSONArray(jsonStr);
					JSONObject json_data=null;

					for(int i=0;i<jArray.length();i++){
						json_data = jArray.getJSONObject(i);
						Log.e("Syso", json_data.getString("passphrase"));
						passphrase=json_data.getString("passphrase");
					}
				}
				catch(Exception e){
					Log.e("Syso", jsonStr);
					e.printStackTrace();
				}

			}
		}
		catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void changePassword (View view){


		final AlertDialog.Builder alert = new AlertDialog.Builder(this);
		final EditText input = new EditText(this);
		alert.setView(input);
		alert.setMessage("Enter the New password");

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String value = (String)input.getText().toString().trim();
				try {
					userDataSource.open();
					boolean check = changePasword(intent.getStringExtra("userId").trim(), value);
					if(check)
						Toast.makeText(getApplicationContext(), "Password changed succesfully",
								Toast.LENGTH_SHORT).show();
					else
						Toast.makeText(getApplicationContext(), "Password change UNSUCCESFULL,Please try again",
								Toast.LENGTH_SHORT).show();

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					Toast.makeText(getApplicationContext(), "Password change exception UNSUCCESFULL,Please try again",
							Toast.LENGTH_SHORT).show();
					userDataSource.close();
				}
				finally{
					userDataSource.close();
				}
			}

		});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.cancel();
			}
		});
		alert.show();


	}
	public void logOff(View view){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		// set title
		alertDialogBuilder.setTitle("Do you really wanna log off");

		// set dialog message
		alertDialogBuilder
		.setMessage("Click yes to log off!")
		.setCancelable(false)
		.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				// if this button is clicked, close
				// current activity
				Intent resultData = new Intent();
				resultData.putExtra("valueName", "valueData");
				setResult(Activity.RESULT_OK, resultData);
				finish();
				//GeneratorActivity.super.onBackPressed();
			}
		})
		.setNegativeButton("No",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				// if this button is clicked, just close
				// the dialog box and do nothing
				dialog.cancel();
			}
		});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();

	}
	@Override
	public void onBackPressed() {
		///isLoggedIn = false;
		Toast.makeText(getApplicationContext(), "Please log off to go back ", Toast.LENGTH_LONG).show();
	}
	private boolean changePasword(String user, String password) {
		// TODO Auto-generated method stub
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httppost = new HttpGet("http://10.0.2.2:8080/NetSecWebFiles/changePasswordAndroid.php");
		JSONObject json = new JSONObject();
		try {           
			json.put("userId", user);//place each of the strings as you did in postData method
			json.put("password", password);

			JSONArray postjson=new JSONArray();
			postjson.put(json);
			httppost.setHeader("json",json.toString());
			httppost.getParams().setParameter("jsonpost",postjson);     
			HttpResponse response = httpclient.execute(httppost);

			// for JSON retrieval:
			if(response != null)
			{ 
				InputStream is = response.getEntity().getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				StringBuilder sb = new StringBuilder();
				String line = null;
				try {
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				//String jsonStr = sb.toString(); //take the string you built place in a string
				//Log.e("log_tag", "Error converting result "+jsonStr.toString());
				return  true;
				//JSONObject rec = new JSONObject(jsonStr);
				//String longitudecord = rec.getString("lon");
				//String latitudecord = rec.getString("lat");
				// ...
			}
		}
		catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
	public void generate(View view){
		final String username = intent.getStringExtra("userId");
		Long timeStamp = System.currentTimeMillis()/30000l;
		String otp = "";
		try {
			otpTextView.setText("before clicked");
			otpTextView.post(new Runnable() {
				@Override
				public void run() {
					otpTextView.setText("Expired!!! Click again");
					generate.setEnabled(true);
					//setOtp(0l, username);
					try {
						Thread.sleep(30000);
						//setOtp("0", username);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("log_tag", "Error in thread "+e.toString());
			Toast.makeText(getApplicationContext(),"Some exception in thread", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		otp = getOTP(username+timeStamp , passphrase);
		String numberFromOTP  = getNumbersFromOTP(otp.toString());
		otpTextView.setText(numberFromOTP);
		//generate.setClickable(false);
		generate.setEnabled(false);
		//setOtp(otp,username);
		//Toast.makeText(getApplicationContext(), System.currentTimeMillis()+" generate clicked for "+username, Toast.LENGTH_LONG).show();

	}
	public String getNumbersFromOTP(String otp) {
		// TODO Auto-generated method stub
		StringBuffer numbers = new StringBuffer();
		char[] charArray = otp.toCharArray();
		int count = 0;
		for(char c : charArray){
			if(c >= '0' && c<= '9'){
				numbers.append(c);
				count++;
			}
			if(count == 6)
				break;
		}
		return numbers.toString();
	}
	public String getOTP(String usernametimestamp, String passphraseDB) {
		// TODO Auto-generated method stub

		/*StringBuilder sb = new StringBuilder();
		//System.out.println(username.toCharArray());
		for (char c : username.toCharArray())
			sb.append((int)c);
		Long toReturn = timeStamp % Long.parseLong(sb.toString());
		return (toReturn);*/
		String otp = hash_HMAC("md5",usernametimestamp, passphraseDB);
		return otp;
	}
	public String hash_HMAC(String string, String data, String passphrase_local) {
		// TODO Auto-generated method stub
		SecretKeySpec keySpec = new SecretKeySpec(passphrase_local.getBytes(), "HmacMD5");
		StringBuilder sb=null;
		try{
			Mac mac = Mac.getInstance("HmacMD5");
			mac.init(keySpec);
			byte[] digest = mac.doFinal(data.getBytes());
			sb = new StringBuilder(digest.length*2);
			String s;
			for (byte b : digest){
				s = Integer.toHexString(0xFF & b);
				if(s.length() == 1) sb.append('0');
				sb.append(s);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return sb.toString();
	}
	/*public void setOtp(String otp, String user) {
		// TODO Auto-generated method stub
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httppost = new HttpGet("http://10.0.2.2:8080/changeOtp.php");
		JSONObject json = new JSONObject();
		try {           
			json.put("userId", user);//place each of the strings as you did in postData method
			json.put("otp", otp);//place each of the strings as you did in postData method
			JSONArray postjson=new JSONArray();
			postjson.put(json);
			httppost.setHeader("json",json.toString());
			httppost.getParams().setParameter("jsonpost",postjson);     
			HttpResponse response = httpclient.execute(httppost);

			// for JSON retrieval:
			if(response != null)
			{ 
				InputStream is = response.getEntity().getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				StringBuilder sb = new StringBuilder();
				String line = null;
				try {
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				Log.e("log_tag check ", sb.toString());

				//String jsonStr = sb.toString(); //take the string you built place in a string
				//Log.e("log_tag", "Error converting result "+jsonStr.toString());
				//JSONObject rec = new JSONObject(jsonStr);
				//String longitudecord = rec.getString("lon");
				//String latitudecord = rec.getString("lat");
				// ...
			}
		}
		catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}*/

}
