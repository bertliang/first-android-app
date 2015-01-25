package ca.utoronto.group_0330;

import java.io.File;
import backend.Nurse;
import backend.Patient;
import backend.Physician;
import backend.User;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This class represents the Acitivty used to show a Patient's information.
 */
public class ShowPatient extends Activity {

	/**
	 * The Patient whose information is being displayed.
	 */
	private Patient patient;

	/**
	 * The User that is currently accessing the Patient's information.
	 */
	private User user;

	/**
	 * The File where all Patient information is stored.
	 */
	private File patientsFile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_patient);
		TextView textview;
		Intent intent = getIntent();
		patient = (Patient) intent.getSerializableExtra("patientKey");
		user = (User) intent.getSerializableExtra("nurseKey");
		patientsFile = (File) intent.getSerializableExtra("fileKey");
		String s = patient.toString();
		textview = (TextView) this.findViewById(R.id.textView1);
		textview.setMovementMethod(new ScrollingMovementMethod());
		textview.setText(s);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_patient, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem select) {
		switch (select.getItemId()) {
		case R.id.action_updatevitals:
			if (user instanceof Nurse) {
				// File patientsFile = new File
				// (this.getApplicationContext().getFilesDir(), "patients.txt");
				// Intent intent = new Intent(ShowPatient.this,
				// UpdateVitals.class);

				Intent intent = new Intent(this, UpdateVitals.class);
				intent.putExtra("patientKey", patient);
				intent.putExtra("nurseKey", user);
				intent.putExtra("fileKey", patientsFile);
				finish();
				startActivity(intent);
			} else {
				Toast.makeText(getApplicationContext(), R.string.access_denied,
						Toast.LENGTH_SHORT).show();
			}
			return true;
		case R.id.action_newperscription:
			if (user instanceof Physician) {
				Intent intent = new Intent(this, UpdatePerscription.class);
				intent.putExtra("patientKey", patient);
				intent.putExtra("nurseKey", user);
				intent.putExtra("fileKey", patientsFile);
				finish();
				startActivity(intent);
			} else {
				Toast.makeText(getApplicationContext(), R.string.access_denied,
						Toast.LENGTH_SHORT).show();
			}
			return true;
		case R.id.action_recordDocTime:
			if (user instanceof Nurse) {
				// File patientsFile = new File
				// (this.getApplicationContext().getFilesDir(), "patients.txt");
				Intent intent1 = new Intent(this, DoctorTime.class);
				intent1.putExtra("patientKey", patient);
				intent1.putExtra("nurseKey", user);
				intent1.putExtra("fileKey", patientsFile);
				finish();
				startActivity(intent1);
			} else {
				Toast.makeText(getApplicationContext(), R.string.access_denied,
						Toast.LENGTH_SHORT).show();
			}
			return true;
		default:
			return super.onOptionsItemSelected(select);
		}
	}

}
