import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;


/**
 * The AppointmentManager class handles the storage and retrieval of appointments for specific dates.
 * It uses a HashMap to store appointments, with the date as the key and a list of appointments as the value.
 * This class provides methods to get the existing appointments for a date and to save updated appointments.
 */
public class AppointmentManager {
	// A HashMap to store appointments by date, where the key is the date and the value is a list of appointments.
    private HashMap<String, List<String>> appointments = new HashMap<>();

    // Method to get the list of appointments for a specific date.
    public List<String> getAppointments(String dateKey) {
    	// If there are no appointments for the given date, return an empty list.
        return appointments.getOrDefault(dateKey, new ArrayList<>());
    }

    // Method to save a list of appointments for a specific date.
    public void saveAppointments(String dateKey, List<String> updatedAppointments) {
    	// Save the updated appointments for the given date in the HashMap.
        appointments.put(dateKey, new ArrayList<>(updatedAppointments));
    }
}
