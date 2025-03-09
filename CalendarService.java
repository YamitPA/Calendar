import java.util.Calendar;


/**
 * The CalendarService class is responsible for providing calendar-related utilities.
 * It offers methods to retrieve important information about a specific month and year, 
 * such as the first day of the week and the number of days in the month.
 * This class helps manage the calendar functionality without directly manipulating the UI.
 */
public class CalendarService {
	//Method to get a Calendar object set to the first day of the selected month.
    public Calendar getCalendar(int year, int month) {
    	// Get a Calendar instance.
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1); // Set to the first day of the selected month
        return calendar;
    }

    // Method to get the first day of the week for the selected month and year.
    public int getFirstDayOfWeek(int year, int month) {
    	// Get the calendar for the given month and year.
        Calendar calendar = getCalendar(year, month);
        // Return the day of the week, adjusting for the first day (Sunday).
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    // Method to get the number of days in the selected month and year.
    public int getDaysInMonth(int year, int month) {
    	// Get the calendar for the given month and year.
        Calendar calendar = getCalendar(year, month);
        // Return the maximum number of days in the selected month.
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}
