package backend;

import java.io.Serializable;
import java.util.Calendar;

/**
 * This class represents Urgency, which is used to store values indicating a
 * Patient's urgency at the hospital.
 * 
 * @author zhi
 */

public class Urgency implements Serializable {

	/**
	 * A unique version identifier that allows this class to be passed between
	 * Activities.
	 */
	private static final long serialVersionUID = -6578496124234972714L;

	/**
	 * Represents the level of urgency as a String, as per hospital policy.
	 */
	private String Urgency;

	/**
	 * Represents the level of urgency as a number.
	 */
	private int intUrgency;

	/**
	 * Constructs an Urgency object, not initializing any values.
	 */
	public Urgency() {
	}

	/**
	 * Calculates the urgency level of the patient, relying on the method
	 * calculateUrgencyPoint.
	 * 
	 * @param urgency
	 *            An integer representation of urgency points.
	 * @return A String specifying the relevant category.
	 */
	public String categoryUrgency(int urgency) {
		if (urgency >= 3) {
			this.Urgency = "Urgent";
			return this.Urgency;
		} else if (urgency == 2) {
			this.Urgency = "Less Urgent";
			return this.Urgency;
		} else {
			this.Urgency = "Non Urgent";
			return this.Urgency;
		}
	}

	/**
	 * Calculates how urgent a Patient's condition is.
	 * 
	 * @param dob
	 *            The Patient's birthdate.
	 * @param vital
	 *            The most recent Vital of the Patient.
	 * @return An integer representing how urgent a Patient's condition is. This
	 *         follows the hospital's policy.
	 */
	public int calculateUrgencyPoint(Calendar dob, Vital vital) {
		this.intUrgency = 0;
		Calendar yearNow = Calendar.getInstance();

		if ((yearNow.get(Calendar.YEAR) - dob.get(Calendar.YEAR) < 2)
				|| yearNow.get(Calendar.YEAR) - dob.get(Calendar.YEAR) == 2
				&& yearNow.get(Calendar.YEAR) <= dob.get(Calendar.YEAR)
				&& yearNow.get(Calendar.DAY_OF_MONTH) <= dob
						.get(Calendar.DAY_OF_MONTH)) {
			this.intUrgency += 1;
		}
		if (vital.getTemperature() >= 39) {
			this.intUrgency += 1;
		}
		if (vital.getSystolicBloodPressure() >= 140
				|| vital.getDiastolicBloodPressure() >= 90) {
			this.intUrgency += 1;
		}
		if (vital.getHeartRate() >= 100 || vital.getHeartRate() <= 50) {
			this.intUrgency += 1;
		}
		return this.intUrgency;
	}
}
