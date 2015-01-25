package ca.utoronto.group_0330;

import java.io.File;

import backend.Nurse;
import backend.User;
import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This class represents the Activity responsible for allowing the end user to
 * access any Patient.
 */
public class AllPatients extends Activity {

	/**
	 * The User that is currently using the application.
	 */
	private User user;

	/**
	 * Specifies where all Patient information is saved.
	 */
	private File file;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_patients);
		Intent intent = getIntent();
		user = (User) intent.getSerializableExtra("nurseKey");
		file = new File(this.getApplicationContext().getFilesDir(),
				"patients.txt");
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
		getMenuInflater().inflate(R.menu.all_patients, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem select) {
		switch (select.getItemId()) {
		case R.id.action_settings:
			if (user instanceof Nurse) {
				if (!user.loadData(file)) {
					Toast.makeText(getApplicationContext(), R.string.no_load,
							Toast.LENGTH_SHORT).show();
				}
				Intent intent = new Intent(AllPatients.this,
						CreatePatient.class);
				intent.putExtra("nurseKey", user);
				intent.putExtra("fileKey", file);
				startActivity(intent);

			} else {
				Toast.makeText(getApplicationContext(), R.string.access_denied,
						Toast.LENGTH_SHORT).show();
			}
			return true;
		case R.id.action_settings1:
			if (user instanceof Nurse) {
				if (!user.loadData(file)) {
					Toast.makeText(getApplicationContext(), R.string.no_load,
							Toast.LENGTH_SHORT).show();
				} else if (user.allPatientsToArray().length == 0) {
					Toast.makeText(getApplicationContext(),
							R.string.no_patient, Toast.LENGTH_SHORT).show();
				} else {
					try {
						Intent intent1 = new Intent(AllPatients.this,
								UrgencyListActivity.class);
						intent1.putExtra("nurseKey", user);
						intent1.putExtra("fileKey", file);
						startActivity(intent1);
						return true;
					} catch (NullPointerException npe) {
						Context context = getApplicationContext();
						Toast.makeText(context, R.string.no_search_results,
								Toast.LENGTH_SHORT).show();
					}
				}
			} else {
				Toast.makeText(getApplicationContext(), R.string.access_denied,
						Toast.LENGTH_SHORT).show();
			}
			return true;
		default:
			return super.onOptionsItemSelected(select);
		}
	}

	/**
	 * Takes input from the end user and searches for a Patient that matches the
	 * inputed healthcard number.
	 * 
	 * @param view
	 *            The View on the screen when the button was pressed.
	 */
	public void searchPatients(View view) {
		if (!user.loadData(file)) {
			Toast.makeText(getApplicationContext(), R.string.no_load,
					Toast.LENGTH_SHORT).show();
		} else {
			String hcard = ((EditText) findViewById(R.id.search_patient_hcard))
					.getText().toString();
			System.out.println("a");
			try {
				if (user.getPatient(hcard) != null) {
					Intent intent = new Intent(this, ShowPatient.class);
					intent.putExtra("patientKey", user.getPatient(hcard));
					intent.putExtra("nurseKey", user);
					intent.putExtra("fileKey", file);
					startActivity(intent);
				} else {
					Context context = getApplicationContext();
					Toast.makeText(context, R.string.no_search_results,
							Toast.LENGTH_SHORT).show();
				}
			} catch (NullPointerException npe) {
				Context context = getApplicationContext();
				Toast.makeText(context, R.string.no_search_results,
						Toast.LENGTH_SHORT).show();
			}
		}

	}

}
