import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.List;
import java.util.Arrays;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;


/**
 * This is the controller class for the calendar application.
 * It handles the functionality for displaying a calendar and managing appointments.
 */
public class CalendarController {
	// Create an instance of the CalendarService class to handle calendar-related operations.
	private CalendarService calendarService = new CalendarService();
	// Create an instance of the AppointmentManager class to manage appointments for different dates.
    private AppointmentManager appointmentManager = new AppointmentManager();
    // Define a constant for the range of years to be displayed.
	private static final int YEARS_RANGE = 10;

	// The ComboBox elements for selecting year and month.
    @FXML
    private ComboBox<Integer> yearComboBox;

    @FXML
    private ComboBox<String> monthComboBox;
    
    // The GridPane where the calendar is displayed.
    @FXML
    private GridPane calendarGrid;
    
    // Method to initialize the ComboBoxes and update the calendar.
    public void initialize() {
        initializeComboBoxes(); // Set up the ComboBoxes with year and month values.
        updateCalendar(); // Update the calendar to show the current month.
    }

    //This method sets up the year and month ComboBoxes with values.
    private void initializeComboBoxes() {
    	// Gets the current year using the system's calendar instance.
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        // Adding years to the yearComboBox from current year - YEARS_RANGE to current year + YEARS_RANGE.
        for (int i = currentYear - YEARS_RANGE ; i <= currentYear + YEARS_RANGE ; i++) {
            yearComboBox.getItems().add(i);
        }
        yearComboBox.setValue(currentYear); //Set the current year as default.

        // Adding months to the monthComboBox.
        String[] months = new DateFormatSymbols().getMonths();
        monthComboBox.getItems().addAll(Arrays.asList(months));
        monthComboBox.setValue(months[Calendar.getInstance().get(Calendar.MONTH)]); // Set the current month as default.

        // When the year or month is changed, update the calendar.
        yearComboBox.setOnAction(event -> updateCalendar());
        monthComboBox.setOnAction(event -> updateCalendar());
    }

    
    // Method that updates the calendar display based on the selected year and month.
    private void updateCalendar() {
        int year = yearComboBox.getValue(); // Get the selected year.
        int month = Arrays.asList(new DateFormatSymbols().getMonths()).indexOf(monthComboBox.getValue()); // Get the selected month.
        populateCalendar(year, month); // Call the method to display the calendar.
    }

    
    // Method that populates the calendar grid with the correct days for the selected month.
    private void populateCalendar(int year, int month) {
        calendarGrid.getChildren().clear(); // Clear the calendar grid before adding new days.
        
        String[] daysOfWeek = {"Sun", "Mon", "Tues", "Wed", "Thu", "Fri", "Sat"};
        
        //Add the days of the week as labels to the first row of the grid.
        for (int i = 0; i < daysOfWeek.length; i++) {
            Label dayLabel = new Label(daysOfWeek[i]);
            dayLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;"); //Set the style for the day labels.
            GridPane.setHalignment(dayLabel, javafx.geometry.HPos.CENTER); // Center the labels in the grid.
            calendarGrid.add(dayLabel, i, 0); // Add the label to the grid.
        }

        // Find the first day of the week for the selected month.
        int firstDayOfWeek = calendarService.getFirstDayOfWeek(year, month);
        // Get the number of days in the selected month.
        int daysInMonth = calendarService.getDaysInMonth(year, month);

        int row = 1;
        // Start at the correct column based on the first day of the month.
        int col = firstDayOfWeek;

        // Loop through each day of the month and add a button for each day.
        for (int day = 1; day <= daysInMonth; day++) {
        	// Make the day variable final by defining it within the lambda
            final int currentDay = day;
            Button dayButton = new Button(String.valueOf(day)); // Create a button for the day.
            dayButton.setPrefSize(50, 50); // Set the size of the button.      
            // Style the button.
            dayButton.setStyle(
            		"-fx-font-size: 14px;"
            		+ "-fx-background-color: lightblue;"
            		+ "-fx-border-color: black;" 
            		+ "-fx-border-s: 1;"
            		+ "-fx-border-radius: 5;");
            
            GridPane.setMargin(dayButton, new Insets(2)); // Add margin around the button.
            
            // When the button is clicked, open the dialog for editing appointments.
            dayButton.setOnAction(event -> openDialogForDay(year, month, currentDay));
            calendarGrid.add(dayButton, col, row); // Add the button to the grid.

            col++; // Move to the next column.
            
            // If the end of the week (Saturday) is reached, move to the next row.
            if (col > 6) {
                col = 0; // Reset column to the first day (Sunday).
                row++; // Move to the next row.
            } 
        }
    }

    // Method that opens a dialog for editing appointments for a specific day.
    private void openDialogForDay(int year, int month, int day) {
        // Format the date as a string.
        String dateKey = String.format("%d-%02d-%02d", year, month + 1, day); 
        // Get existing appointments for the day.
        List<String> meetings = appointmentManager.getAppointments(dateKey);

        // Create a TextArea for entering appointments.
        TextArea textArea = new TextArea(String.join("\n", meetings));
        textArea.setWrapText(true); // Enable line wrapping for better readability.
        textArea.setPrefRowCount(5); // Set the preferred row count for the TextArea.

        // Create an Alert for the dialog window.
        Alert alert = new Alert(AlertType.NONE);
        alert.setTitle("Edit Appointments");
        alert.setHeaderText("Appointments for " + day + "/" + (month + 1) + "/" + year);
        
        // Add the TextArea to the dialog window.
        VBox vbox = new VBox(new Label("Enter appointments:"));
        vbox.getChildren().add(textArea);
        alert.getDialogPane().setContent(vbox);

        // Add custom buttons to the alert.
        ButtonType saveButton = new ButtonType("Save");
        ButtonType cancelButton = new ButtonType("Cancel");
        alert.getButtonTypes().setAll(saveButton, cancelButton);

        // Show the dialog and wait for the user to press a button.
        alert.showAndWait().ifPresent(result -> {
            if (result == saveButton) {
                // Split the input into lines and save the appointments.
                List<String> updatedMeetings = Arrays.asList(textArea.getText().split("\n"));
                appointmentManager.saveAppointments(dateKey, updatedMeetings);
            }
        });
    }
}
