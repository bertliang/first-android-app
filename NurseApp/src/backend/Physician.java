package backend;

import java.io.Serializable;

/**
 * This class defines a Physician object, which is a User of the program.
 * 
 * @author Jason Zheng
 */
public class Physician extends User implements Serializable {

	/**
	 * A unique version identifier that allows this class to be passed between
	 * Activities.
	 */
	private static final long serialVersionUID = -1251026866479515323L;

	/**
	 * Constructs a Physician using the given username and password.
	 * 
	 * @param username
	 *            Nurse The username associated with this Physician's account.
	 * @param password
	 *            The password associated with this Physician's account.
	 */
	public Physician(String username, String password) {
		super(username, password);
	}

}
