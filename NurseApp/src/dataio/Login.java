package dataio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents an abstract Login object, which must be inherited by
 * all login managers.
 * 
 * @author Muhammad Osama
 */
public abstract class Login implements DataManager {

	/**
	 * The username specified by the end user.
	 */
	private String username;

	/**
	 * The password specified by the end user.
	 */
	private String password;

	/**
	 * Indicates if the end user is logging in as an existing account. A true
	 * value indicates that the end user is logging in as opposed to creating a
	 * new account.
	 */
	private boolean choice;

	/**
	 * Maps usernames to passwords for all Nurses that are either read from the
	 * data file, or added to the program.
	 */
	private Map<String, String> ninput;

	/**
	 * Maps usernames to passwords for all Physicials that are either read from
	 * the data file, or added to the program.
	 */
	private Map<String, String> pinput;

	/**
	 * Specifies the data file where information is stored and saved.
	 */
	private File io;

	/**
	 * Constructs a Login object.
	 * 
	 * @param username
	 *            The username that is entered by the end user.
	 * @param password
	 *            The password that is entered by the end user.
	 * @param choice
	 *            Boolean specifying if the end user is loggin in as an existing
	 *            account.
	 * @param io
	 *            The File that the information is saved to.
	 */
	public Login(String username, String password, boolean choice, File io) {
		this.username = username;
		this.password = password;
		this.choice = choice;
		this.io = io;
		this.ninput = new HashMap<String, String>();
		this.pinput = new HashMap<String, String>();
		fromCSV();
	}

	/**
	 * All subclasses of this object must implement this method. Checks if the
	 * login information stored by this object is valid.
	 * 
	 * @return A boolean specifying if the login information is valid.
	 */
	public abstract boolean isValidLogin();

	/**
	 * Gets the username entered by the end user.
	 * 
	 * @return The username entered by the end user.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Gets the password entered by the end user.
	 * 
	 * @return The password entered by the end user.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Gets the value indicating if this object represents is a new account.
	 * 
	 * @return A boolean indicating if this object represents a new account.
	 */
	public boolean isChoice() {
		return choice;
	}

	/**
	 * Gets a Map of the usernames and passwords associated with Nurse accounts.
	 * 
	 * @return A Map of String to String representing the usernames and
	 *         passwords associated with Nurse accounts.
	 */
	public Map<String, String> getNinput() {
		return ninput;
	}

	/**
	 * Gets a Map of the usernames and passwords associated with Physician
	 * accounts.
	 * 
	 * @return A Map of String to String representing the usernames and
	 *         passwords associated with Physician accounts.
	 */
	public Map<String, String> getPinput() {
		return pinput;
	}

	/**
	 * Indicates if the File associated with this object exists.
	 * 
	 * @return A boolean indicating if the associated File exists.
	 */
	public boolean fileExists() {
		return io.exists();
	}

	/**
	 * Indicates if the given password is valid.
	 * 
	 * @param indicate
	 *            A boolean specifying if Nurse or Physicians are being checked.
	 *            The boolean value is true if Nurse accounts are concered and
	 *            false otherwise.
	 * @return A boolean indicating if the given password is valid.
	 */
	public boolean isValidPassword(boolean indicate) {
		if (indicate)
			return ninput.get(username).equals(password);
		else
			return pinput.get(username).equals(password);
	}

	/**
	 * Saves the stored information and returns true if the save was successful.
	 * 
	 * @param indicate
	 *            A boolean specifying if Nurse or Physicians are being checked.
	 *            The boolean value is true if Nurse accounts are concered and
	 *            false otherwise.
	 * @return A boolean indicating wheter the information was saved
	 *         successfully.
	 */
	public boolean saveInfo(boolean indicate) {
		if (ninput.containsKey(username) || pinput.containsKey(username))
			return false;
		try {
			if (indicate)
				ninput.put(username, password);
			else
				pinput.put(username, password);
			BufferedWriter out = new BufferedWriter(new FileWriter(io));
			out.write(ninput.size() + "\r\n");
			for (Map.Entry<String, String> entries : ninput.entrySet()) {
				out.write(entries.getKey() + "," + entries.getValue() + "\r\n");
			}
			out.write(pinput.size() + "\r\n");
			for (Map.Entry<String, String> entries : pinput.entrySet()) {
				out.write(entries.getKey() + "," + entries.getValue() + "\r\n");
			}
			out.close();
			return true;
		} catch (IOException iox) {
			return false;
		}
	}

	/**
	 * Loads information from the data file, using the File stored by this
	 * object.
	 */
	public void fromCSV() {
		int lines;
		try {
			BufferedReader in = new BufferedReader(new FileReader(io));
			lines = Integer.parseInt(in.readLine());
			for (int c = 0; c < lines; c++) {
				String[] line = in.readLine().split(",");
				ninput.put(line[0], line[1]);
			}
			lines = Integer.parseInt(in.readLine());
			for (int c = 0; c < lines; c++) {
				String[] line = in.readLine().split(",");
				pinput.put(line[0], line[1]);
			}
			in.close();
		} catch (IOException iox) {
		} catch (NumberFormatException nfe) {
		}
	}
}
