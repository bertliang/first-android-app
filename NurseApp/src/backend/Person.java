package backend;

import java.io.Serializable;
import java.util.Calendar;

/**
 * This class defines a Person object. This class is abstract and must be
 * inherited.
 * 
 * @author Ryan
 */
public abstract class Person implements Serializable {

	/**
	 * A unique version identifier that allows this class to be passed between
	 * Activities.
	 */
	private static final long serialVersionUID = 3089244499800880925L;

	/**
	 * This Person's last name.
	 */
	private String lastName;

	/**
	 * This Person's first name.
	 */
	private String firstName;

	/**
	 * This person's date of birth.
	 */
	private Calendar dob;

	/**
	 * Constructs a Person object given
	 * 
	 * @param lastname
	 *            The Person's last name.
	 * @param firstname
	 *            The Person's first name.
	 * @param dob
	 *            The date of birth of the Person, as a Calendar.
	 */
	public Person(String lastname, String firstname, Calendar dob) {
		this.lastName = lastname;
		this.firstName = firstname;
		this.dob = dob;
	}

	/**
	 * Gets this Person's last name
	 * 
	 * @return This Person's last name.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Gets this Person's first name.
	 * 
	 * @return This Person's first name.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Gets this Person's date of birth.
	 * 
	 * @return A Calendar specifying this Person's date of birth.
	 */
	public Calendar getDob() {
		return dob;
	}

}
