package backend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class defines a Perscription, which contains the name of a medication
 * and associated instructions that a Physician may assign to a Patient.
 * 
 * @author Muhammad Osama
 */
public class Perscription implements Serializable {

	/**
	 * A unique version identifier that allows this class to be passed between
	 * Activities.
	 */
	private static final long serialVersionUID = 12406203928787522L;

	/**
	 * The medication that is the subject of this Perscription.
	 */
	private String medication;

	/**
	 * The instructions associated with this Perscription.
	 */
	private String instruction;

	/**
	 * Constructs a Perscription object with the given medication and
	 * instructions.
	 * 
	 * @param med
	 *            The name of the medication.
	 * @param inst
	 *            The instructions associated with the medication.
	 */
	public Perscription(String med, String inst) {
		this.medication = med;
		this.instruction = inst;
	}

	/**
	 * Constructs a Perscription object given raw data from a save file.
	 * <p>
	 * The data consists of the name of the medication and the associated
	 * instructions, separated by a '-'.
	 * 
	 * @param data
	 *            The raw data, consisting of the name of the medication and the
	 *            associated instructions, separated by a '-'
	 * @throws ArrayIndexOutOfBoundsException
	 *             Throws an exception if the data is invalid.
	 */
	public Perscription(String data) throws ArrayIndexOutOfBoundsException {
		String[] input = data.split("-");
		this.medication = input[0];
		this.instruction = input[1];
	}

	/**
	 * Gets the name of the medication stored in this Perscription.
	 * 
	 * @return The name of the medication.
	 */
	public String getMedication() {
		return medication;
	}

	/**
	 * Returns the Medication Instruction of this Prescription.
	 * 
	 * @return The instructions for the medication.
	 */
	public String getInstruction() {
		return instruction;
	}

	/**
	 * Returns the medication and the instruction of this Prescription in a
	 * conjoined form separated by a + sign.
	 * 
	 * @return The conjoined form of both the medication name and instruction.
	 */
	public String toData() {
		return medication + "-" + instruction;
	}

	/**
	 * Creates a List of Perscriptions from an array containing read data.
	 * 
	 * @param input
	 *            The array containing the data read from the file.
	 * @return A List of Perscriptions created from the inputed array.
	 * @throws ArrayIndexOutOfBoundsException
	 *             Thrown if the inputed data is invalid in terms of format.
	 */
	public static List<Perscription> listPerscriptions(String[] input)
			throws ArrayIndexOutOfBoundsException {
		List<Perscription> output = new ArrayList<Perscription>();
		for (int c = 0; c < input.length; c++) {
			output.add(new Perscription(input[c]));
		}
		return output;
	}
}
