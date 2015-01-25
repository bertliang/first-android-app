package ca.utoronto.group_0330;

import java.util.List;
import backend.Nurse;
import backend.Patient;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This class represents the Activity used to view Patients in order of Urgency.
 */
public class UrgencyListActivity extends Activity {

	/**
	 * The Nurse that is accessing the Activity.
	 */
	private Nurse nurse;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_urgency_list);
		TextView textview;
		Intent intent = getIntent();
		nurse = (Nurse) intent.getSerializableExtra("nurseKey");
		List<Patient> urgentList = nurse.getUrgentPatientList();
		String s = new String();
		for (Patient p : urgentList) {
			s += "Health Card ID: " + p.getHealthcard() + "\n" + "Name: "
					+ p.getName() + "\n" + "Urgency Point: "
					+ p.getUrgentPoint() + "\n" + "\n";
		}
		textview = (TextView) this.findViewById(R.id.UrgencyList);
		textview.setMovementMethod(new ScrollingMovementMethod());
		textview.setText(s);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.urgency_list, menu);
		return true;
	}

	/**
	 * Takes input from the end user and searches for a Patient that matches the
	 * inputed healthcard number.
	 * 
	 * @param view
	 *            The View on the screen when the button was pressed.
	 */
	public void searchPatients(View view) {
		String hcard = ((EditText) findViewById(R.id.search_patient_hcard_Urgency))
				.getText().toString();
		System.out.println("a");
		try {
			if (nurse.getPatient(hcard) != null) {
				Intent intent = new Intent(this, ShowPatient.class);
				intent.putExtra("patientKey", nurse.getPatient(hcard));
				intent.putExtra("nurseKey", nurse);
				finish(); // new added
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
