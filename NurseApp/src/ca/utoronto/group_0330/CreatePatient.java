package ca.utoronto.group_0330;

import java.io.File;
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
 * This class represents the Activity responsible for entering a new Patient
 * into the record.
 */
public class CreatePatient extends Activity {

	/**
	 * The Nurse that is currently creating a new Patient.
	 */
	private Nurse nurse;

	/**
	 * The File where all Patient information is saved.
	 */
	private File file;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_patient);
		Intent intent = getIntent();
		nurse = (Nurse) intent.getSerializableExtra("nurseKey");
		file = (File) intent.getSerializableExtra("fileKey");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_patient, menu);
		return true;
	}

	/**
	 * Takes the fields given from the user(date of birth, first name, last
	 * name,and health card) and creates a new patient object with these fields
	 * and then proceeds to update patient's preliminary vital signs
	 * 
	 * @param view
	 *            The view of the activity.
	 */
	public void createNewPatient(View view) {
		try {

			// File patientsFile = new File
			// (this.getApplicationContext().getFilesDir(), "patients.txt");
			EditText firstName = (EditText) findViewById(R.id.register_fname_field);
			EditText lastName = (EditText) findViewById(R.id.register_lname_field);
			EditText dob = (EditText) findViewById(R.id.register_dob_field);
			EditText h_card = (EditText) findViewById(R.id.register_hcard_field);
			Calendar arrivalTime = Calendar.getInstance();
			String[] dobsplit = dob.getText().toString().split("-");
			Calendar dobcal = Calendar.getInstance();
			dobcal.set(Calendar.MONTH, Integer.parseInt(dobsplit[1]) - 1);
			dobcal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dobsplit[2]));
			dobcal.set(Calendar.YEAR, Integer.parseInt(dobsplit[0]));
			Patient patient = new Patient(firstName.getText().toString(),
					lastName.getText().toString(), dobcal, h_card.getText()
							.toString(), arrivalTime);
			nurse.addPatient(patient);
			Intent intent = new Intent(this, UpdateVitals.class);
			intent.putExtra("nurseKey", nurse);
			intent.putExtra("patientKey", patient);
			intent.putExtra("fileKey", file);
			finish();
			startActivity(intent);
		} catch (NumberFormatException nfe) {
			Context context = getApplicationContext();
			Toast.makeText(context, R.string.invalid_input, Toast.LENGTH_SHORT)
					.show();
		} catch (ArrayIndexOutOfBoundsException afe) {
			Context context = getApplicationContext();
			Toast.makeText(context, R.string.invalid_input, Toast.LENGTH_SHORT)
					.show();
		} catch (NullPointerException npe){
			Context context = getApplicationContext();
			Toast.makeText(context, R.string.invalid_input, Toast.LENGTH_SHORT)
					.show();
		}

	}

}
