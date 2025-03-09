# Electronic Calendar in JavaFX

## Description
This JavaFX application implements an electronic calendar that displays a month in a standard table format, with a cell for each day. The calendar includes:

- A 7-column grid (Sunday to Saturday) with 6 rows to accommodate all possible month lengths and starting days.
- A header displaying the selected year and month, adjustable via dropdown menus.
- Interactive day buttons that allow users to view and edit appointments for a specific day in a dialog box.
- Appointment management using free-text input, stored persistently for each date.

The application enables users to select a year and month, click on a day to manage appointments, and save changes, providing a simple yet functional calendar system.

## Features
- **Graphical Interface**: Built with JavaFX, featuring ComboBoxes for year/month selection and a GridPane of buttons for days.
- **Appointment Management**: Users can add, edit, and save free-text appointments for any day via a dialog with a TextArea.
- **Dynamic Calendar**: Adjusts the layout based on the first day of the week and the number of days in the selected month.
- **Data Storage**: Uses a HashMap to store appointments, with dates as keys and lists of strings as values.

## Project Structure
The project adheres to Object-Oriented Programming (OOP) principles with the following key classes:
- `Calendar`: Main entry point, loading the FXML interface.
- `CalendarController`: Manages user interactions, calendar display, and appointment dialogs.
- `CalendarService`: Provides calendar utilities (e.g., first day of the week, days in a month) using `java.util.Calendar`.
- `AppointmentManager`: Handles storage and retrieval of appointments using a HashMap.
