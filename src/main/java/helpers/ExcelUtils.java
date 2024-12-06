package helpers;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;

public class ExcelUtils {
	/**
	 * Saves the given URL to the specified Excel file if it doesn't already exist.
	 *
	 * @param filePath The path to the Excel file.
	 * @param url      The URL to save.
	 */
	public static void saveUrlIfNotExists(String filePath, String url) {
		FileInputStream fileInputStream = null;
		FileOutputStream fileOutputStream = null;
		Workbook workbook = null;
		try {
			// Load the existing Excel file
			File file = new File(System.getProperty("user.dir") + filePath);
			if (!file.exists()) {
				System.out.println("File not found: " + file.getAbsolutePath());
				return;
			}

			fileInputStream = new FileInputStream(file);
			workbook = new XSSFWorkbook(fileInputStream);

			// Get the first sheet (or specify the sheet by name)
			Sheet sheet = workbook.getSheet("Link"); // or workbook.getSheet("Sheet1");

			// Check if the URL already exists in the sheet
			boolean urlExists = false;
			for (Row row : sheet) {
				Cell cell = row.getCell(0); // Assuming URLs are in the first column (index 0)
				if (cell != null && url.equals(cell.getStringCellValue())) {
					urlExists = true;
					break;
				}
			}

			// If the URL does not exist, find the next empty row and save the URL
			if (!urlExists) {
				int nextEmptyRowIndex = sheet.getLastRowNum() + 1;
				Row newRow = sheet.createRow(nextEmptyRowIndex);
				Cell newCell = newRow.createCell(0);
				newCell.setCellValue(url);

				System.out.println("URL not found, saving to row " + nextEmptyRowIndex);

				// Write the updated workbook back to the file
				fileOutputStream = new FileOutputStream(file);
				workbook.write(fileOutputStream);
			} else {
				System.out.println("URL already exists in the sheet.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fileInputStream != null) {
					fileInputStream.close();
				}
				if (fileOutputStream != null) {
					fileOutputStream.close();
				}
				if (workbook != null) {
					workbook.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Saves a list of URLs into an Excel sheet.
	 *
	 * @param urls     The list of URLs to save.
	 * @param fileName The name of the Excel file to save the URLs in.
	 */
	public static void saveUrlsToExcel(List<String> urls, String fileName) {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Job URLs");

		for (int i = 0; i < urls.size(); i++) {
			Row row = sheet.createRow(i);
			Cell cell = row.createCell(0);
			cell.setCellValue(urls.get(i));
		}

		try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
			workbook.write(fileOut);
			workbook.close();
			System.out.println("URLs saved to Excel file: " + fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeToExcel(String fileName, String category, String jobTitle, String location, int jobCount, int screenshotCount) throws IOException {
        // Get the current date in "YYYY-MM-DD" format
        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        // Get the working directory; if null, use a default path
        String projectDir = System.getProperty("user.dir");
        if (projectDir == null) {
            throw new IOException("The working directory could not be determined.");
        }

        // Define the folder name and path
        Path reportDir = Paths.get(projectDir, "Report");

        // Ensure the directory exists
        Files.createDirectories(reportDir);

        // Construct the file path dynamically with the date in the name
        String filePath = reportDir.toString() + "/" + fileName + "_JobReport_" + currentDate + ".xlsx";

        File file = new File(filePath);
        Workbook workbook;
        Sheet sheet;

        // Check if the Excel file for the current date already exists
        if (file.exists()) {
            // Open the existing workbook
            try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
                workbook = new XSSFWorkbook(fileInputStream);
            }
        } else {
            // Create a new workbook if the file doesn't exist
            workbook = new XSSFWorkbook();
        }

        // Check if a sheet with the same name as the current date exists
        sheet = workbook.getSheet(currentDate);
        if (sheet == null) {
            // Create a new sheet named with the current date
            sheet = workbook.createSheet(currentDate);

            // Create a bold font for the header
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);

            // Create a cell style with the bold font
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            // Add headers to the new sheet and set them to bold
            Row headerRow = sheet.createRow(0);
            Cell categoryHeader = headerRow.createCell(0);
            categoryHeader.setCellValue("Category");
            categoryHeader.setCellStyle(headerCellStyle);

            Cell jobTitleHeader = headerRow.createCell(1);
            jobTitleHeader.setCellValue("Job Title");
            jobTitleHeader.setCellStyle(headerCellStyle);

            Cell locationHeader = headerRow.createCell(2);
            locationHeader.setCellValue("Location");
            locationHeader.setCellStyle(headerCellStyle);

            Cell jobCountHeader = headerRow.createCell(3);
            jobCountHeader.setCellValue("Job Count");
            jobCountHeader.setCellStyle(headerCellStyle);

            Cell screenshotCountHeader = headerRow.createCell(4);
            screenshotCountHeader.setCellValue("Screenshot Count");
            screenshotCountHeader.setCellStyle(headerCellStyle);
        }

        // Find the next empty row
        int lastRowNum = sheet.getLastRowNum();
        Row newRow = sheet.createRow(lastRowNum + 1);

        // Write the data
        newRow.createCell(0).setCellValue(category);
        newRow.createCell(1).setCellValue(jobTitle);
        newRow.createCell(2).setCellValue(location);
        newRow.createCell(3).setCellValue(jobCount);
        newRow.createCell(4).setCellValue(screenshotCount);

        // Write changes to the file
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
            workbook.write(fileOutputStream);
        }
        workbook.close();
    }
	
	
	public static void writeUrlToExcel(String fileName, String jobTitle, String jobUrl) throws IOException {
	    String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	    Path reportDir = Paths.get(System.getProperty("user.dir"), "Report");
	    Files.createDirectories(reportDir);

		String filePath = reportDir.toString() + "/" + fileName + "_JobReport_" + currentDate + ".xlsx";
	    File file = new File(filePath);
	    Workbook workbook;
	    Sheet sheet;

	    if (file.exists()) {
	        FileInputStream fileInputStream = new FileInputStream(filePath);
	        workbook = new XSSFWorkbook(fileInputStream);
	        fileInputStream.close();
	    } else {
	        workbook = new XSSFWorkbook();
	    }

	    sheet = workbook.getSheet(currentDate + "_Url");
	    if (sheet == null) {
	        sheet = workbook.createSheet(currentDate + "_Url");

	        // Create a font for bold text
	        Font headerFont = workbook.createFont();
	        headerFont.setBold(true);

	        // Create a cell style and apply the bold font
	        CellStyle headerCellStyle = workbook.createCellStyle();
	        headerCellStyle.setFont(headerFont);

	        // Create header row
	        Row headerRow = sheet.createRow(0);

	        // Set headers with bold style
	        Cell jobTitleHeader = headerRow.createCell(0);
	        jobTitleHeader.setCellValue("Job Title");
	        jobTitleHeader.setCellStyle(headerCellStyle);

	        Cell jobUrlHeader = headerRow.createCell(1);
	        jobUrlHeader.setCellValue("Job URL");
	        jobUrlHeader.setCellStyle(headerCellStyle);
	    }

	    // Find the next empty row
	    int lastRowNum = sheet.getLastRowNum();
	    Row newRow = sheet.createRow(lastRowNum + 1);
	    newRow.createCell(0).setCellValue(jobTitle);

	    // Create a hyperlink for the URL
	    CreationHelper creationHelper = workbook.getCreationHelper();
	    Hyperlink link = creationHelper.createHyperlink(HyperlinkType.URL);
	    link.setAddress(jobUrl);

	    // Set the URL in the Excel sheet as a hyperlink
	    Cell urlCell = newRow.createCell(1);
	    urlCell.setCellValue(jobUrl); // Display URL
	    urlCell.setHyperlink(link);   // Apply hyperlink

	    // Style the hyperlink cell (optional)
	    CellStyle hlinkStyle = workbook.createCellStyle();
	    Font hlinkFont = workbook.createFont();
	    hlinkFont.setUnderline(Font.U_SINGLE);
	    hlinkFont.setColor(IndexedColors.BLUE.getIndex());
	    hlinkStyle.setFont(hlinkFont);
	    urlCell.setCellStyle(hlinkStyle);

	    // Make sure to write and close the workbook correctly
	    try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
	        workbook.write(fileOutputStream);
	    }
	    workbook.close();
	}
	
	public static void writeToExcel(String fileName, String category, String jobTitle, int jobCount, int screenshotCount) throws IOException {
        // Get the current date in "YYYY-MM-DD" format
        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        // Get the working directory; if null, use a default path
        String projectDir = System.getProperty("user.dir");
        if (projectDir == null) {
            throw new IOException("The working directory could not be determined.");
        }

        // Define the folder name and path
        Path reportDir = Paths.get(projectDir, "Report");

        // Ensure the directory exists
        Files.createDirectories(reportDir);

        // Construct the file path dynamically with the date in the name
        String filePath = reportDir.toString() + "/" + fileName + "_JobReport_" + currentDate + ".xlsx";

        File file = new File(filePath);
        Workbook workbook;
        Sheet sheet;

        // Check if the Excel file for the current date already exists
        if (file.exists()) {
            // Open the existing workbook
            try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
                workbook = new XSSFWorkbook(fileInputStream);
            }
        } else {
            // Create a new workbook if the file doesn't exist
            workbook = new XSSFWorkbook();
        }

        // Check if a sheet with the same name as the current date exists
        sheet = workbook.getSheet(currentDate);
        if (sheet == null) {
            // Create a new sheet named with the current date
            sheet = workbook.createSheet(currentDate);

            // Create a bold font for the header
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);

            // Create a cell style with the bold font
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            // Add headers to the new sheet and set them to bold
            Row headerRow = sheet.createRow(0);
            Cell categoryHeader = headerRow.createCell(0);
            categoryHeader.setCellValue("Category");
            categoryHeader.setCellStyle(headerCellStyle);

            Cell jobTitleHeader = headerRow.createCell(1);
            jobTitleHeader.setCellValue("Job Title");
            jobTitleHeader.setCellStyle(headerCellStyle);

            Cell jobCountHeader = headerRow.createCell(2);
            jobCountHeader.setCellValue("Job Count");
            jobCountHeader.setCellStyle(headerCellStyle);

            Cell screenshotCountHeader = headerRow.createCell(3);
            screenshotCountHeader.setCellValue("Screenshot Count");
            screenshotCountHeader.setCellStyle(headerCellStyle);
        }

        // Find the next empty row
        int lastRowNum = sheet.getLastRowNum();
        Row newRow = sheet.createRow(lastRowNum + 1);

        // Write the data
        newRow.createCell(0).setCellValue(category);
        newRow.createCell(1).setCellValue(jobTitle);
        newRow.createCell(2).setCellValue(jobCount);
        newRow.createCell(3).setCellValue(screenshotCount);

        // Write changes to the file
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
            workbook.write(fileOutputStream);
        }
        workbook.close();
    }
}