package backend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * This class defines a Patient object, which is used to store information for
 * the clients of the hospital.
 * 
 * @author Ryan
 * @author Muhammad
 */

public class Patient extends Person implements Serializable {
	
	/**
	 * A unique version identifier that allows this class to be passed between
	 * Activities.
	 */
	private static final long serialVersionUID = 8043750151134034737L;
	
	/**
	 * The healthcard number of this Patient.
	 */
	private String healthcard;
	
	/**
	 * The time when this Patient arrived at the hospital.
	 */
	private Calendar arriveTime;
	
	/**
	 * A List of all Vitals taken for this Patient by a Nurse.
	 */
	private List<Vital> vitalSigns;
	
	/**
	 * A List of all Perscriptions given to this patient by a Physician.
	 */
	private List<Perscription> perscriptions;
	
	/**
	 * Indicates this Patient's urgency, as per hospital policy.
	 */
	private int urgentPoint;
	
	/**
	 * The time indicating when this Patient was seen by a Physician.
	 */
	private Calendar doctorTime;
	
	/**
	 * Indicates whether this Patient hss been seen by a Physician.
	 */
	private boolean seenDoctorBool;
	
	/**
	 * Indicates whether this Patient's condition is improving.
	 */
	private boolean improvement;

	/**
	 * This is used to calculate the how urgent this Patient's condition is.
	 */
	private static Urgency urgency = new Urgency();

	/**
	 * Contructs a Patient object.
	 * 
	 * @param name
	 *            The name of this Patient.
	 * @param dob
	 *            The date of birth of this Patient.
	 * @param healthcard
	 *            The healthcard number of this patient.
	 * @param arriveTime
	 *            The time when this Patient arrived at the hospital, given as a
	 *            Calendar.
	 * 
	 */
	public Patient(String lastname, String firstname, Calendar dob,
			String healthcard, Calendar arriveTime) {
		super(lastname, firstname, dob);
		this.healthcard = healthcard;
		this.arriveTime = arriveTime;
		this.vitalSigns = new ArrayList<Vital>();
		this.perscriptions = new ArrayList<Perscription>();
		this.seenDoctorBool = false;
		this.improvement = false;
		this.urgentPoint = 0;
		this.doctorTime = null;
	}

	/**
	 * Adds a new Vital to this Patient's List of Vitals.
	 * 
	 * @param temperature
	 *            The temperature of this patient, given as double.
	 * @param bloodPressure
	 *            The blood pressure of this patient, given as integer.
	 * @param heartRate
	 *            The heart beat rate of this patient, given as integer.
	 * @param time
	 *            The time of this record, given as an integer.
	 */
	public void updateVitalSigns(double temperature, double sbloodPressure,
			double dbloodPressure, double heartRate, Calendar time) {
		Vital v = new Vital(temperature, sbloodPressure, dbloodPressure,
				heartRate, time);
		this.vitalSigns.add(v);
		this.updateUrgencyPoint();
	}

	/**
	 * Adds a new Vital to this Patient's List of Vitals.
	 * 
	 * @param vital
	 *            This Patient's vital signs.
	 */
	public void updateVitalSigns(Vital vital) {
		this.vitalSigns.add(vital);
		this.updateUrgencyPoint();
	}

	/**
	 * Updates this Patient's urgency points through recalculation.
	 */
	private void updateUrgencyPoint() {
		this.urgentPoint = urgency.calculateUrgencyPoint(this.getDob(),
				this.vitalSigns.get(this.vitalSigns.size() - 1));
	}

	/**
	 * Sets the time when this Patient has been seen by a doctor.
	 * 
	 * @param time
	 *            A Calendar specifying the time when the Patient has been seen
	 *            by a doctor.
	 */
	public void updateDoctorTime(Calendar time) {
		this.doctorTime = time;
		this.updateSeenDoctorBool(true);
	}

	/**
	 * Sets whether this Patient has been seen by a doctor.
	 * 
	 * @param bool
	 *            A boolean value specifying if the Patient has been seen by a
	 *            doctor. A true value indicates that the Patient has been seen.
	 */
	public void updateSeenDoctorBool(boolean bool) {
		this.seenDoctorBool = bool;
	}

	/**
	 * Sets whether this Patient is improving
	 * 
	 * @param bool
	 *            A boolean specifying whether this Patient is getting better. A
	 *            true value indicates that this Patient is getting better.
	 */
	public void updateImprovement(boolean bool) {
		this.improvement = bool;
	}

	/**
	 * Returns a formatted string containing all of this Patient's information.
	 * This information is formatted to be saved to a data file.
	 * 
	 * @return A String containing all information relating to this Patient.
	 *         Different values are separated by commas.
	 */
	public String toData() {
		String dateofbirth = super.getDob().get(Calendar.YEAR) + "-"
				+ (super.getDob().get(Calendar.MONTH) + 1) + "-"
				+ super.getDob().get(Calendar.DAY_OF_MONTH);
		String arrival = arriveTime.get(Calendar.YEAR) + "-"
				+ (arriveTime.get(Calendar.MONTH) + 1) + "-"
				+ arriveTime.get(Calendar.DAY_OF_MONTH) + "-"
				+ arriveTime.get(Calendar.HOUR_OF_DAY) + "-"
				+ arriveTime.get(Calendar.MINUTE);
		String output = super.getFirstName() + "," + super.getLastName() + ","
				+ dateofbirth + "," + healthcard + "," + arrival + ",";
		Object[] vitalToArray = vitalSigns.toArray();
		Vital[] vitals = new Vital[vitalToArray.length];
		for (int c = 0; c < vitalToArray.length; c++) {
			vitals[c] = (Vital) vitalToArray[c];
		}

		for (int c = 0; c < vitals.length; c++) {
			output += vitals[c].toData();
			if (c != vitals.length - 1)
				output += "=";
		}

		output += ",";
		if (this.getSeenDoctorBool() == false) {
			output += "false";
		} else {
			String doctorTime = this.getDoctorTime().get(Calendar.YEAR) + "-"
					+ (this.getDoctorTime().get(Calendar.MONTH) + 1) + "-"
					+ this.getDoctorTime().get(Calendar.DAY_OF_MONTH) + "-"
					+ this.getDoctorTime().get(Calendar.HOUR_OF_DAY) + "-"
					+ this.getDoctorTime().get(Calendar.MINUTE);
			output += doctorTime;
		}

		output += ",";
		Object[] rx = perscriptions.toArray();
		for (int c = 0; c < rx.length; c++) {
			output += ((Perscription) rx[c]).toData();
			if (c != rx.length - 1)
				output += "=";
		}
		return output;
	}

	/**
	 * Gets the name of this Patient.
	 * 
	 * @return A String containing the Patient's first and
	 */
	public String getName() {
		return this.getFirstName() + " " + this.getLastName();
	}

	/**
	 * Gets the healthcard number associated with this Patient.
	 * 
	 * @return A String containing this Patient's healthcard number.
	 */
	public String getHealthcard() {
		return healthcard;
	}

	/**
	 * Gets this Patient's arrival time at the hospital.
	 * 
	 * @return A Calendar representing this Patient's arrival time.
	 */
	public Calendar getArriveTime() {
		return arriveTime;
	}

	/**
	 * Gets a List of this Patient's Vitals.
	 * 
	 * @return A List of Vitals associated with this Patient.
	 */
	public List<Vital> getVitalSigns() {
		return vitalSigns;
	}

	/**
	 * Gets this Patient's urgency point.
	 * 
	 * @return An int representing this Patient's urgency point.
	 */
	public int getUrgentPoint() {
		return urgentPoint;
	}

	/**
	 * Gets the time this Patientw as seen by a doctor.
	 * 
	 * @return A Calendar representing the time when this Patient was seen by a
	 *         doctor.
	 */
	public Calendar getDoctorTime() {
		return doctorTime;
	}

	/**
	 * Gets a boolean specifying whether this Patient was seen by a doctor.
	 * 
	 * @return Returns true if this patient has seen a doctor and false
	 *         otherwise.
	 */
	public Boolean getSeenDoctorBool() {
		return seenDoctorBool;
	}

	/**
	 * Gets a boolean indicating whether this Patient is improving.
	 * 
	 * @return Returns a boolean indicating if this Patient is improving.
	 */
	public Boolean getImprovement() {
		return improvement;
	}

	public String toString() {
		this.updateUrgencyPoint();
		String dateofbirth = super.getDob().get(Calendar.YEAR) + "-"
				+ (super.getDob().get(Calendar.MONTH) + 1) + "-"
				+ super.getDob().get(Calendar.DAY_OF_MONTH);
		String arrival = arriveTime.get(Calendar.YEAR) + "-"
				+ (arriveTime.get(Calendar.MONTH) + 1) + "-"
				+ arriveTime.get(Calendar.DAY_OF_MONTH) + " "
				+ arriveTime.get(Calendar.HOUR_OF_DAY) + ":"
				+ arriveTime.get(Calendar.MINUTE);
		String docTime;
		if (this.getSeenDoctorBool() == false) {
			docTime = "N/A";
		} else {
			docTime = this.getDoctorTime().get(Calendar.YEAR) + "-"
					+ (this.getDoctorTime().get(Calendar.MONTH) + 1) + "-"
					+ this.getDoctorTime().get(Calendar.DAY_OF_MONTH) + " "
					+ this.getDoctorTime().get(Calendar.HOUR_OF_DAY) + ":"
					+ this.getDoctorTime().get(Calendar.MINUTE);

		}
		String s = "Name: " + this.getName() + "\n" + "HealthCardNumber: "
				+ this.getHealthcard() + "\n" + "Date of Birth: " + dateofbirth
				+ "\n\n" + "Arrive Time: " + arrival + "\n"
				+ "Doctor assessment time: " + docTime + "\n" + "Improvement: "
				+ this.getImprovement() + "\n" + "Urgency: "
				+ Patient.urgency.categoryUrgency(this.urgentPoint) + "\n\n";

		for (Vital v : this.vitalSigns) {
			String vs = "Vital signs at " + v.toString();
			s += vs + "\n\n";
		}

		if (perscriptions.size() > 0) {
			s += "Prescriptions:\n";
			for (Perscription p : this.perscriptions) {
				String ps = p.getMedication() + ": " + p.getInstruction();
				s += ps + "\n\n";
			}
		} else {
			s += "No prescriptions on record.";
		}
		return s;

	}

	/**
	 * Adds a Prescription to this Patient's List of Prescriptions.
	 * 
	 * @param rx
	 *            The Prescription to be added.
	 */
	public void addPerscription(Perscription rx) {
		perscriptions.add(rx);
	}

	/**
	 * Adds a Perscription to this Patient's List of Perscriptions.
	 * 
	 * @param input
	 *            The array containing the data read from the file.
	 * @throws ArrayIndexOutOfBoundsException
	 *             Thrown if the inputed data is invalid in terms of format.
	 */
	public void addPerscription(String[] input)
			throws ArrayIndexOutOfBoundsException {
		this.perscriptions = Perscription.listPerscriptions(input);
	}

}
