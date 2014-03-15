package com.IUB.netSec.securelogin;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends Activity {

	private EditText userId;
	private EditText password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}
	public void login (View view){

		userId = (EditText) findViewById(R.id.userId);
		password = (EditText) findViewById(R.id.password);
		Intent intent = new Intent(getApplicationContext(), GeneratorActivity.class);
		intent.putExtra("userId", (String)userId.getText().toString());
		intent.putExtra("password", (String)password.getText().toString());
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
