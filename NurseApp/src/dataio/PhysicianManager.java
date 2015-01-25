package dataio;

import java.io.File;
import backend.Physician;

/**
 * This class represents a PhysicianManager object, which is used to handle the
 * login process for Physicians.
 * 
 * @author Muhammad Osama
 */
public class PhysicianManager extends Login {

	/**
	 * Creates an instance of this PhysicianManager class.
	 * 
	 * @param username
	 *            Username of the Physician logging in.
	 * @param password
	 *            Password of the Physician logging in.
	 * @param choice
	 *            Boolean specifying if the Physician is an existing account. A
	 *            true value indicates that the account already exists.
	 * @param io
	 *            The File that the information is saved to.
	 */
	public PhysicianManager(String username, String password, boolean choice,
			File io) {
		super(username, password, choice, io);
	}

	/**
	 * Checks if the login information stored by this object is valid.
	 * 
	 * @return A boolean specifying if the login information is valid.
	 */
	public boolean isValidLogin() {
		String username = super.getUsername();
		if (username.equals("") || super.getPassword().equals(""))
			return false;
		else if (super.isChoice())
			return super.fileExists()
					&& super.getPinput().containsKey(username)
					&& isValidPassword(false);
		else
			return !super.getPinput().containsKey(username) && saveInfo(false);
	}

	/**
	 * Creates a Physician using this PhysicianManager's information.
	 * 
	 * @return The new Physician that is created.
	 */
	public Physician createPhysician() {
		return new Physician(super.getUsername(), super.getPassword());
	}
}
