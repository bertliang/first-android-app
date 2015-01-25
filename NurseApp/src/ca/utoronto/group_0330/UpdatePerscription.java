package ca.utoronto.group_0330;

import java.io.File;
import java.text.ParseException;
import backend.Patient;
import backend.Perscription;
import backend.Physician;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

/**
 * This class represents the Activity used to add a new Perscription to a
 * Patient.
 */
public class UpdatePerscription extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_perscription);
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
		getMenuInflater().inflate(R.menu.update_perscription, menu);
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
	 * Takes input form the end user and uses it to add a new Perscription to a
	 * Patient's record.
	 * 
	 * @param view
	 *            The View on the screen when the button was pressed.
	 * @throws ParseException
	 *             Thrown when the end user provides invalid input.
	 */
	@SuppressLint("SimpleDateFormat")
	public void updatePerscription(View view) throws ParseException {
		try {
			/*
			 * get the intent that started this activity. save the
			 * workingPatient as p
			 */
			Intent intent = getIntent();
			// please change the serialise String if we changed
			Patient p = (Patient) intent.getSerializableExtra("patientKey");
			Physician doctor = (Physician) intent
					.getSerializableExtra("nurseKey");

			EditText medication = (EditText) findViewById(R.id.update_medication);
			String meds = medication.getText().toString();

			EditText instruction = (EditText) findViewById(R.id.update_instruction);
			String instr = instruction.getText().toString();

			Perscription rx = new Perscription(meds, instr);
			doctor.getPatient(p.getHealthcard()).addPerscription(rx);

			File file = new File(this.getApplicationContext().getFilesDir(),
					"patients.txt");
			if (!doctor.saveData(file)) {
				Context context = getApplicationContext();
				Toast.makeText(context, R.string.cannot_save,
						Toast.LENGTH_SHORT).show();
			}

			Intent intent1 = new Intent(this, ShowPatient.class);
			intent1.putExtra("patientKey", doctor.getPatient(p.getHealthcard()));
			intent1.putExtra("nurseKey", doctor);
			intent1.putExtra("fileKey", file);
			finish();
			startActivity(intent1);

		} catch (NumberFormatException nfe) {
			Context context = getApplicationContext();
			Toast.makeText(context, R.string.invalid_date, Toast.LENGTH_SHORT)
					.show();
		} catch (ArrayIndexOutOfBoundsException so) {
			Context context = getApplicationContext();
			Toast.makeText(context, R.string.invalid_date, Toast.LENGTH_SHORT)
					.show();
		}

	}

}
