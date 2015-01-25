package ca.utoronto.group_0330;

import java.io.File;
import backend.User;
import dataio.PhysicianManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

/**
 * This class represents the Activity used to register a new Physician.
 */
public class RegisterDoctor extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_doctor);
		// Show the Up button in the action bar.
		setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register_doctor, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Uses the input provided by the end user to create a new Physician and add
	 * it to the record.
	 * 
	 * @param view
	 *            The View on the screen when the button was pressed.
	 */
	public void registerLogin(View view) {
		File passwdFile = new File(this.getApplicationContext().getFilesDir(),
				"passwords.txt");
		EditText user = (EditText) findViewById(R.id.register_username_field);
		EditText pass = (EditText) findViewById(R.id.register_password_field);
		PhysicianManager check = new PhysicianManager(
				user.getText().toString(), pass.getText().toString(), false,
				passwdFile);

		if (check.isValidLogin()) {
			User login = check.createPhysician();
			Intent intent = new Intent(this, AllPatients.class);
			intent.putExtra("nurseKey", login);
			finish();
			startActivity(intent);
		} else {
			Context context = getApplicationContext();
			Toast.makeText(context, R.string.cannot_create_doctor,
					Toast.LENGTH_SHORT).show();
		}
	}

}
