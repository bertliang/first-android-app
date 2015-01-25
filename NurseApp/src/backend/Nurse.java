package backend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class defines a Nurse object, which is one of the main users of the
 * application.
 * 
 * @author Muhammad Osama
 */
public class Nurse extends User implements Serializable {

	/**
	 * A unique version identifier that allows this class to be passed between
	 * Activities.
	 */
	private static final long serialVersionUID = 6186946833913578521L;

	/**
	 * Constructs a Nurse object.
	 * 
	 * @param username
	 *            Nurse The username associated with this Nurse's account.
	 * @param password
	 *            The password associated with this Nurse's account.
	 */
	public Nurse(String username, String password) {
		super(username, password);
	}

	/**
	 * Gets a List of Patients who have not been seen by a doctor, organized by
	 * a calculated urgency.
	 * 
	 * @return A List of Patients who have not been seen by a doctor, organized
	 *         by urgency points which have been assigned to each Patient.
	 */
	public List<Patient> getUrgentPatientList() {
		List<Patient> urgencySort = new ArrayList<Patient>();
		List<Patient> high = new ArrayList<Patient>();
		List<Patient> mid = new ArrayList<Patient>();
		List<Patient> low = new ArrayList<Patient>();
		Map<String, Patient> patientMap = super.getPatientMap();
		int size = patientMap.size();
		Patient[] allClients = patientMap.values().toArray(new Patient[size]);
		for (int c = 0; c < size; c++) {
			if (allClients[c].getSeenDoctorBool() == false) {
				if (allClients[c].getUrgentPoint() == 4) {
					urgencySort.add(allClients[c]);
				} else if (allClients[c].getUrgentPoint() == 3) {
					high.add(allClients[c]);
				} else if (allClients[c].getUrgentPoint() == 2) {
					mid.add(allClients[c]);
				} else {
					low.add(allClients[c]);
				}
			}
		}
		urgencySort.addAll(high);
		urgencySort.addAll(mid);
		urgencySort.addAll(low);
		return urgencySort;
	}

}
