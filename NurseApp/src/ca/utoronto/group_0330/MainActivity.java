package ca.utoronto.group_0330;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;

/**
 * This class represents the primary Activity of the program.
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * Takes the end user to the logging in Activity.
	 * 
	 * @param view
	 *            The View on the screen when the button was pressed.
	 */
	public void loginNurse(View view) {
		startActivity(new Intent(this, LoginNurse.class));
	}

	/**
	 * Takes the end user to the Activity used to register a new Nurse.
	 * 
	 * @param view
	 *            The View on the screen when the button was pressed.
	 */
	public void registerNurse(View view) {
		startActivity(new Intent(this, RegisterNurse.class));
	}

	/**
	 * Takes the end user to the Activity used to registe a new Physician.
	 * 
	 * @param view
	 *            The View on the screen when the button was pressed.
	 */
	public void registerDoctor(View view) {
		startActivity(new Intent(this, RegisterDoctor.class));
	}

}
