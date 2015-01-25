package dataio;

import java.io.File;
import backend.Nurse;

/**
 * This class represents a NurseManager object, which is used to handle the
 * login process for Nurses.
 * 
 * @author Muhammad Osama
 */
public class NurseManager extends Login {

	/**
	 * Creates an instance of this NurseManager class.
	 * 
	 * @param username
	 *            Username of the Nurse logging in.
	 * @param password
	 *            Password of the Nurse logging in.
	 * @param choice
	 *            Boolean specifying if the Physician is an existing account. A
	 *            true value indicates that the account already exists.
	 * @param io
	 *            The File that the information is saved to.
	 */
	public NurseManager(String username, String password, boolean choice,
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
					&& super.getNinput().containsKey(username)
					&& isValidPassword(true);
		else
			return !super.getNinput().containsKey(username) && saveInfo(true);
	}

	/**
	 * Creates a Nurse using this NurseManager's information.
	 * 
	 * @return The new Nurse that is created.
	 */
	public Nurse createNurse() {
		return new Nurse(super.getUsername(), super.getPassword());
	}
}
