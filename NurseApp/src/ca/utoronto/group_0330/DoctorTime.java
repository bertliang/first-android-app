package ca.utoronto.group_0330;

import java.io.File;
import java.text.ParseException;
import java.util.Calendar;
import backend.Nurse;
import backend.Patient;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * This class represnts the Activity responsible for recording the time when a
 * Patient was seen by a Physician.
 */
public class DoctorTime extends Activity {

	/**
	 * The Patient that is being updated.
	 */
	private Patient patient;

	/**
	 * The Nurse that is updating the Patient's record.
	 */
	private Nurse nurse;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doctor_time);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.doctor_time, menu);
		return true;
	}

	/**
	 * Takes input from the end user and sets the time when a Patient was seen
	 * by a Physician.
	 * 
	 * @param view
	 *            The View on the screen when the button was pressed.
	 * @throws ParseException
	 *             Thrown when the end user provides invalid input.
	 */
	public void recordDoctime(View view) throws ParseException {
		try {

			Intent intent = getIntent();

			patient = (Patient) intent.getSerializableExtra("patientKey");
			nurse = (Nurse) intent.getSerializableExtra("nurseKey");
			// File patientsFile = (File)intent.getSerializableExtra("fileKey");
			File patientsFile = new File(this.getApplicationContext()
					.getFilesDir(), "patients.txt");
			EditText doctime = (EditText) findViewById(R.id.update_doc_time);
			String[] time = doctime.getText().toString().split("-");

			Calendar docDate = Calendar.getInstance();
			docDate.set(Calendar.YEAR, Integer.parseInt(time[0]));
			docDate.set(Calendar.MONTH, Integer.parseInt(time[1]) - 1);
			docDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(time[2]));
			docDate.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[3]));
			docDate.set(Calendar.MINUTE, Integer.parseInt(time[4]));

			nurse.getPatient(patient.getHealthcard()).updateDoctorTime(docDate);

			if (!nurse.saveData(patientsFile)) {
				Context context = getApplicationContext();
				Toast.makeText(context, R.string.cannot_save,
						Toast.LENGTH_SHORT).show();
			}

			Intent intent1 = new Intent(this, ShowPatient.class);
			intent1.putExtra("patientKey",
					nurse.getPatient(patient.getHealthcard()));
			intent1.putExtra("nurseKey", nurse);
			intent.putExtra("fileKey", patientsFile);

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
