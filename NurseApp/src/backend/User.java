package backend;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * This class defines a User, which must be inherited by all users of the
 * application.
 * 
 * @author Muhammad Osama
 */
public abstract class User implements Serializable {

	/**
	 * A unique version identifier that allows this class to be passed between
	 * Activities.
	 */
	private static final long serialVersionUID = 3556701568344037718L;

	/**
	 * The username of this User.
	 */
	private String username;

	/**
	 * The password of this user.
	 */
	private String password;

	/**
	 * A Map which maps Patient healthcard numbers to the Patients themselves.
	 */
	private Map<String, Patient> patientMap;

	/**
	 * Constructs a User object.
	 * 
	 * @param username
	 *            The username associated with this User's account.
	 * @param password
	 *            The password associated with this User's account.
	 */
	public User(String username, String password) {
		this.username = username;
		this.password = password;
		patientMap = new HashMap<String, Patient>();
	}

	/**
	 * Gets the Patients held by this user, as a map of their healthcard numbers
	 * and the Patients themselves.
	 * 
	 * @return A Map of healthcard numbers and Patients held by this object.
	 */
	public Map<String, Patient> getPatientMap() {
		return this.patientMap;
	}

	/**
	 * Sets a new password for this User.
	 * 
	 * @param password
	 *            The new password for this User.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets an array of all of the Patients held by this User. These Patients
	 * are extracted from this User's Map of healthcard numbers and Patients.
	 * 
	 * @return An array of Objects containing all of the Patients.
	 */
	public Object[] allPatientsToArray() {
		return patientMap.values().toArray();
	}

	/**
	 * Checks if the given string is the correct password for this User. There
	 * is no getter for the password for security reasons.
	 * 
	 * @param password
	 *            The password to be checked.
	 * @return Boolean indicating whether the given password matches the stored
	 *         password.
	 */
	public boolean isPassword(String password) {
		return password.equals(this.password);
	}

	/**
	 * Adds a Patient to this User's Map of Patients.
	 * 
	 * @param client
	 *            The Patient to be added.
	 */
	public void addPatient(Patient client) {
		patientMap.put(client.getHealthcard(), client);
	}

	/**
	 * Gets a Patient based on their health card number.
	 * 
	 * @param healthcard
	 *            The health card number of the desired Patient.
	 * @return The Patient which matches the given health card number. If there
	 *         is no matching Patient, a null pointer is returned.
	 */
	public Patient getPatient(String healthcard) {
		return patientMap.get(healthcard);
	}

	/**
	 * Loads data from a save file into the Map of Patients.
	 * 
	 * @param file
	 *            The File containing information regarding the save file.
	 * @return Boolean value indicating whether the load was successful.
	 */
	public boolean loadData(File file) {
		if (file.exists()) {
			try {
				BufferedReader in = new BufferedReader(new FileReader(file));
				int lines = Integer.parseInt(in.readLine());
				for (int c = 0; c < lines; c++) {
					String input = in.readLine();
					if (input != null) {
						String[] patient = input.split(",");
						Calendar dob = Calendar.getInstance();
						String[] dobparse = patient[2].split("-");
						dob.set(Calendar.YEAR, Integer.parseInt(dobparse[0]));
						dob.set(Calendar.MONTH, Integer.parseInt(dobparse[1]));
						dob.set(Calendar.DAY_OF_MONTH,
								Integer.parseInt(dobparse[2]));
						Calendar arrival = Calendar.getInstance();
						String[] arrivalparse = patient[4].split("-");
						arrival.set(Calendar.YEAR,
								Integer.parseInt(arrivalparse[0]));
						arrival.set(Calendar.MONTH,
								Integer.parseInt(arrivalparse[1]) - 1);
						arrival.set(Calendar.DAY_OF_MONTH,
								Integer.parseInt(arrivalparse[2]));
						arrival.set(Calendar.HOUR_OF_DAY,
								Integer.parseInt(arrivalparse[3]));
						arrival.set(Calendar.MINUTE,
								Integer.parseInt(arrivalparse[4]));

						Patient newPatient = new Patient(patient[0],
								patient[1], dob, patient[3], arrival);
						addPatient(newPatient);

						if (patient.length >= 6) {
							try {
								String[] vitals = patient[5].split("=");
								for (int d = 0; d < vitals.length; d++) {
									String[] uparse = vitals[d].split("-");
									Calendar uptime = Calendar.getInstance();
									uptime.set(Calendar.YEAR,
											Integer.parseInt(uparse[4]));
									uptime.set(Calendar.MONTH,
											Integer.parseInt(uparse[5]));
									uptime.set(Calendar.DAY_OF_MONTH,
											Integer.parseInt(uparse[6]));
									uptime.set(Calendar.HOUR_OF_DAY,
											Integer.parseInt(uparse[7]));
									uptime.set(Calendar.MINUTE,
											Integer.parseInt(uparse[8]));
									Vital add = new Vital(
											Double.parseDouble(uparse[0]),
											Double.parseDouble(uparse[1]),
											Double.parseDouble(uparse[2]),
											Double.parseDouble(uparse[3]),
											uptime);
									this.getPatient(patient[3])
											.updateVitalSigns(add);
								}
							} catch (ArrayIndexOutOfBoundsException ao) {
							}
						}
						if (patient.length >= 7) {
							try {
								if (patient[6].equals("false")) {
									this.getPatient(patient[3])
											.updateSeenDoctorBool(false);

								} else {
									String[] doc = patient[6].split("-");
									Calendar doctime = Calendar.getInstance();
									doctime.set(Calendar.YEAR,
											Integer.parseInt(doc[0]));
									doctime.set(Calendar.MONTH,
											Integer.parseInt(doc[1]));
									doctime.set(Calendar.DAY_OF_MONTH,
											Integer.parseInt(doc[2]));
									doctime.set(Calendar.HOUR_OF_DAY,
											Integer.parseInt(doc[3]));
									doctime.set(Calendar.MINUTE,
											Integer.parseInt(doc[4]));
									this.getPatient(patient[3])
											.updateDoctorTime(doctime);
								}

							} catch (ArrayIndexOutOfBoundsException ao) {
							}
						}

						if (patient.length == 8) {
							try {
								this.getPatient(patient[3]).addPerscription(
										patient[7].split("="));
							} catch (ArrayIndexOutOfBoundsException axe) {
							}
						}
					} else {
						in.close();
						return false;
					}
				}
				in.close();
				return true;
			} catch (IOException iox) {
				return false;
			} catch (NumberFormatException nfe) {
				return false;
			} catch (ArrayIndexOutOfBoundsException ao) {
				return false;
			} catch (StringIndexOutOfBoundsException so) {
				return false;
			} catch (NullPointerException npe) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Saves the data in the Map of all Patients to a save file.
	 * 
	 * @param file
	 *            The File containing information regarding the save file.
	 * @return Boolean specifying whether the save was successful.
	 */
	public boolean saveData(File file) {
		try {
			int size = patientMap.size();
			Patient[] allClients = patientMap.values().toArray(
					new Patient[size]);
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			out.write(size + "\r\n");
			for (int c = 0; c < size; c++) {
				out.write(allClients[c].toData() + "\r\n");
			}
			out.close();
			return true;
		} catch (IOException iox) {
			return false;
		}
	}

}
