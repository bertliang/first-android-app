package ca.utoronto.group_0330;

import java.io.File;
import java.text.ParseException;
import java.util.Calendar;
import backend.Nurse;
import backend.Patient;
import backend.Vital;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * This class represents the Activity used to update a Patient's Vitals.
 */
public class UpdateVitals extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_vitals);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.update_vitals, menu);
		return true;
	}

	/**
	 * Takes input form the end user and uses it to add a new Vital to a
	 * Patient's record.
	 * 
	 * @param view
	 *            The View on the screen when the button was pressed.
	 * @throws ParseException
	 *             Thrown when the end user provides invalid input.
	 */
	@SuppressLint("SimpleDateFormat")
	public void updateVitalSigns(View view) throws ParseException {
		try {
			/*
			 * Get the intent that started this activity. Save the
			 * workingPatient as p.
			 */
			Intent intent = getIntent();
			// please change the serialise String if we changed
			Patient p = (Patient) intent.getSerializableExtra("patientKey");
			Nurse nurse = (Nurse) intent.getSerializableExtra("nurseKey");

			EditText temperature = (EditText) findViewById(R.id.update_temp_field);
			double temp = Double.parseDouble(temperature.getText().toString());

			EditText sbloodPressure = (EditText) findViewById(R.id.update_systolic_field);
			double sblood = Double.parseDouble(sbloodPressure.getText()
					.toString());

			EditText dbloodPressure = (EditText) findViewById(R.id.update_diastolic_field);
			double dblood = Double.parseDouble(dbloodPressure.getText()
					.toString());

			EditText heartRate = (EditText) findViewById(R.id.update_heartrate_field);
			double heart = Double.parseDouble(heartRate.getText().toString());

			EditText datetime = (EditText) findViewById(R.id.update_time_field);
			String[] arrivalparse = datetime.getText().toString().split("-");

			// use calendar for date

			Calendar arrival = Calendar.getInstance();
			arrival.set(Calendar.YEAR, Integer.parseInt(arrivalparse[0]));
			arrival.set(Calendar.MONTH, Integer.parseInt(arrivalparse[1]) - 1);
			arrival.set(Calendar.DAY_OF_MONTH,
					Integer.parseInt(arrivalparse[2]));
			arrival.set(Calendar.HOUR_OF_DAY, Integer.parseInt(arrivalparse[3]));
			arrival.set(Calendar.MINUTE, Integer.parseInt(arrivalparse[4]));

			// Calendar date_format = new
			// SimpleCalendarFormat("yyyyMMdd HH:mm").parse(date_string);
			// Date date_format = new
			// SimpleDateFormat("yyyyMMdd HH:mm").parse("20131104 08:30");
			// will output Mon November 04 08:30:00 EST 2013
			Vital v = new Vital(temp, sblood, dblood, heart, arrival);
			nurse.getPatient(p.getHealthcard()).updateVitalSigns(v);
			File file = new File(this.getApplicationContext().getFilesDir(),
					"patients.txt");
			if (!nurse.saveData(file)) {
				Context context = getApplicationContext();
				Toast.makeText(context, R.string.cannot_save,
						Toast.LENGTH_SHORT).show();
			}

			Intent intent1 = new Intent(this, ShowPatient.class);
			intent1.putExtra("patientKey", nurse.getPatient(p.getHealthcard()));
			intent1.putExtra("nurseKey", nurse);
			intent1.putExtra("fileKey", file);
			finish();
			startActivity(intent1);

		} catch (NumberFormatException nfe) {
			Context context = getApplicationContext();
			Toast.makeText(context, R.string.invalid_input, Toast.LENGTH_SHORT)
					.show();
		} catch (ArrayIndexOutOfBoundsException so) {
			Context context = getApplicationContext();
			Toast.makeText(context, R.string.invalid_input, Toast.LENGTH_SHORT)
					.show();
		}

	}
}
