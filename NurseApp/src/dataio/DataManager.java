package dataio;

/**
 * This class represents a DataManager interface.
 * 
 * @author Muhammad Osama
 */
public interface DataManager {

	/**
	 * Requires all classes implementing this interface to have fileExists()
	 * method with the return true if it does exit and false if it doesn't.
	 * 
	 * @return Returns true if the file exists, false if not.
	 */
	public boolean fileExists();

}
