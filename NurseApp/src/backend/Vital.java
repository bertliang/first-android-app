package backend;

import java.io.Serializable;
import java.util.Calendar;

/**
 * This class represents a Vital object, used to store information regarding
 * various vital signs that a Patient may have.
 * 
 * @author Jason Zheng
 * @author Muhammad
 */

public class Vital implements Serializable {

	/**
	 * A unique version identifier that allows this class to be passed between
	 * Activities.
	 */
	private static final long serialVersionUID = 7135563197868997627L;
	
	/**
	 * The temperature when this Vital was taken.
	 */
	private double temperature;
	
	/**
	 * The systolic blood pressure when this Vital was taken.
	 */
	private double systolicBloodPressure;
	
	/**
	 * The diastolic blood pressure when this Vital was taken.
	 */
	private double diastolicBloodPressure;
	
	/**
	 * The heart rate when this Vital was taken.
	 */
	private double heartRate;
	
	/**
	 * The time when this Vital was recorded.
	 */
	private Calendar recordTime;

	/**
	 * Constructs a new Vital object.
	 * 
	 * @param temp
	 *            The temperature of the Patient, given as a double.
	 * @param systolicBlood
	 *            The systolic blood pressure of the Patient, given as a double.
	 * @param diatolicblood
	 *            The diastolic blood pressure of the Patient, given as a double
	 * @param heart
	 *            The Patient's heart rate, given as a double.
	 * @param arriveTime
	 *            A Calendar representing the Patient's arrival time.
	 */
	public Vital(double temp, double systolicblood, double diastolicblood,
			double heart, Calendar arriveTime) {
		this.temperature = temp;
		this.heartRate = heart;
		this.systolicBloodPressure = systolicblood;
		this.diastolicBloodPressure = diastolicblood;
		this.recordTime = arriveTime;
	}

	/**
	 * Gets this Vital's temperature
	 * 
	 * @return A double representing this Vital's temperature.
	 */
	public double getTemperature() {
		return temperature;
	}

	/**
	 * Gets this Vital's systolic blood pressure.
	 * 
	 * @return A double representing this Vital's systolic blood pressure.
	 */
	public double getSystolicBloodPressure() {
		return systolicBloodPressure;
	}

	/**
	 * Gets this Vital's heart rate.
	 * 
	 * @return A double representing this Vital's heart rate.
	 */
	public double getHeartRate() {
		return heartRate;
	}

	/**
	 * Gets the time this VItal was recorded.
	 * 
	 * @return A Calendar represnting the time this Vital was recorded.
	 */
	public Calendar getRecordTime() {
		return recordTime;
	}

	/**
	 * Gets this Vital's diastolic blood pressure.
	 * 
	 * @return A double representing this Vital's diastolic blood pressure.
	 */
	public double getDiastolicBloodPressure() {
		return diastolicBloodPressure;
	}

	/**
	 * Gets a formatted String of this Vital's information, ready to be saved to
	 * the data file.
	 * 
	 * @return A string representing this vital's information, with all values
	 *         being separated by '-'.
	 */
	public String toData() {
		String date = recordTime.get(Calendar.YEAR) + "-"
				+ (recordTime.get(Calendar.MONTH) + 1) + "-"
				+ recordTime.get(Calendar.DAY_OF_MONTH) + "-"
				+ recordTime.get(Calendar.HOUR_OF_DAY) + "-"
				+ recordTime.get(Calendar.MINUTE);
		return temperature + "-" + systolicBloodPressure + "-"
				+ diastolicBloodPressure + "-" + heartRate + "-" + date;
	}

	/**
	 * Gets a String representation of this Vital's information.
	 * 
	 * @return A String representing this Vital's information.
	 */
	public String toString() {
		String date = recordTime.get(Calendar.YEAR) + "-"
				+ (recordTime.get(Calendar.MONTH) + 1) + "-"
				+ recordTime.get(Calendar.DAY_OF_MONTH) + " "
				+ recordTime.get(Calendar.HOUR_OF_DAY) + ":"
				+ recordTime.get(Calendar.MINUTE);
		String s = date + "\n" + "Temperature: " + this.getTemperature() + "\n"
				+ "HeartRate: " + this.getHeartRate() + "\n"
				+ "Blood pressure in systolic: "
				+ this.getSystolicBloodPressure() + "\n"
				+ "Blood preessure in diastolic: "
				+ this.getDiastolicBloodPressure();

		return s;
	}

}