package helpers;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

public class DateChecker {

	// Create a map to map month abbreviations to month numbers
	private static final Map<String, Month> monthMap = new HashMap<>();

	// Static block to initialize the map
	static {
		monthMap.put("Jan", Month.JANUARY);
		monthMap.put("Feb", Month.FEBRUARY);
		monthMap.put("Mar", Month.MARCH);
		monthMap.put("Apr", Month.APRIL);
		monthMap.put("May", Month.MAY);
		monthMap.put("Jun", Month.JUNE);
		monthMap.put("Jul", Month.JULY);
		monthMap.put("Aug", Month.AUGUST);
		monthMap.put("Sep", Month.SEPTEMBER);
		monthMap.put("Oct", Month.OCTOBER);
		monthMap.put("Nov", Month.NOVEMBER);
		monthMap.put("Dec", Month.DECEMBER);
	}

	// Method to check if the given date string is within one week of the current
	// date
	public static boolean isWithinOneWeek(String dateString) {
		dateString = dateString.toLowerCase().trim(); // Convert to lowercase for consistent handling

		// Handle "hrs ago", "days ago" cases
		if (dateString.contains("hrs") || dateString.contains("hr")) {
			return true; // Consider as within one week if it's "hours ago"
		}
		if (dateString.contains("days ago")) {
			int daysAgo = Integer.parseInt(dateString.split(" ")[0]);
			LocalDate currentDate = LocalDate.now();
			LocalDate targetDate = currentDate.minusDays(daysAgo);
			return targetDate.isAfter(currentDate.minusWeeks(1)) && targetDate.isBefore(currentDate);
		}

		// Handle normal date format (e.g., "Sep 6")
		String[] parts = dateString.split(" ");
		if (parts.length != 2) {
			System.out.println("Invalid date format");
			return false; // Handle invalid date format gracefully
		}

		String monthStr = parts[0].substring(0, 1).toUpperCase() + parts[0].substring(1).toLowerCase(); // Normalize
																										// month case
		int day = Integer.parseInt(parts[1]); // e.g., 6

		// Get the current date
		LocalDate currentDate = LocalDate.now();

		// Get the month as a number from the abbreviation
		Month month = monthMap.get(monthStr);

		// Check if the month abbreviation is valid
		if (month == null) {
			System.out.println("Invalid month abbreviation: " + monthStr);
			return false; // Gracefully handle the case when the month is invalid
		}

		// Create a LocalDate for the given input date
		LocalDate givenDate = LocalDate.of(currentDate.getYear(), month, day);

		// Calculate the date one week before the current date
		LocalDate oneWeekBefore = currentDate.minusWeeks(1);

		// Check if the given date is after or on the one-week-before date
		return !givenDate.isBefore(oneWeekBefore) && givenDate.isBefore(currentDate);
	}

	// Method to check if the given date string is within two weeks of the current
	// date
	public static boolean isWithinTwoWeeks(String dateString) {
		// Split the date string (e.g., "Sep 6")
		String[] parts = dateString.split(" ");

		// Check if the date string has valid parts
		if (parts.length != 2) {
			System.out.println("Invalid date format");
			return false; // Handle invalid date format gracefully
		}

		String monthStr = parts[0]; // e.g., "Sep"
		int day = Integer.parseInt(parts[1]); // e.g., 6

		// Get the current date
		LocalDate currentDate = LocalDate.now();

		// Get the month as a number from the abbreviation
		Month month = monthMap.get(monthStr);

		// Check if the month abbreviation is valid
		if (month == null) {
			System.out.println("Invalid month abbreviation: " + monthStr);
			return false; // Gracefully handle the case when the month is invalid
		}

		// Create a LocalDate for the given input date
		LocalDate givenDate = LocalDate.of(currentDate.getYear(), month, day);

		// Calculate the date two weeks before the current date
		LocalDate twoWeeksBefore = currentDate.minusWeeks(2);

		// Check if the given date is after or on the two-weeks-before date
		return !givenDate.isBefore(twoWeeksBefore) && givenDate.isBefore(currentDate.plusDays(1)); // Include current
																									// day
	}

	public static boolean isWithinSevenDays(String dateString) {
		dateString = dateString.toLowerCase().trim(); // Convert to lowercase for consistent handling

		// Handle "hrs ago", "hr ago" cases
		if (dateString.contains("hrs") || dateString.contains("hr")) {
			return true; // Consider as within 7 days if it's hours ago
		}

		// Handle "days ago" case
		if (dateString.contains("days ago")) {
			int daysAgo = Integer.parseInt(dateString.split(" ")[0]);
			LocalDate currentDate = LocalDate.now();
			LocalDate targetDate = currentDate.minusDays(daysAgo);
			return !targetDate.isBefore(currentDate.minusDays(7)); // Check within last 7 days
		}

		// Handle normal date format (e.g., "Sep 6")
		String[] parts = dateString.split(" ");
		if (parts.length != 2) {
			System.out.println("Invalid date format");
			return false; // Handle invalid date format gracefully
		}

		String monthStr = parts[0].substring(0, 1).toUpperCase() + parts[0].substring(1).toLowerCase(); // Normalize
																										// month case
		int day = Integer.parseInt(parts[1]); // e.g., 6

		// Get the current date
		LocalDate currentDate = LocalDate.now();

		// Get the month as a number from the abbreviation
		Month month = monthMap.get(monthStr);

		// Check if the month abbreviation is valid
		if (month == null) {
			System.out.println("Invalid month abbreviation: " + monthStr);
			return false; // Gracefully handle the case when the month is invalid
		}

		// Create a LocalDate for the given input date
		LocalDate givenDate = LocalDate.of(currentDate.getYear(), month, day);

		// Calculate the date 7 days before the current date
		LocalDate sevenDaysBefore = currentDate.minusDays(7);

		// Check if the given date is after or on the seven-days-before date
		return !givenDate.isBefore(sevenDaysBefore) && givenDate.isBefore(currentDate.plusDays(1)); // Include current
																									// day
	}

}
