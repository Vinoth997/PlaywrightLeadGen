package helpers;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

public class FolderUtils {
	
	/**
     * Creates a directory for screenshots with a name based on the provided method name and timestamp.
     *
     * @param testMethodName The name of the test method.
     * @return The path to the created directory.
     * @throws IOException If an I/O error occurs when creating the directory.
     */
	public static Path createScreenshotDirectory(String JobPortalName, String testMethodName) throws IOException {
        // Create the timestamp
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy_HH-mm-ss"));

        // Create the folder name using the extracted method name and timestamp
        String folderName = "JobsScreenshots/"+ JobPortalName+ "/" + testMethodName + "_" + timestamp;

        // Define the path and create the directory
        Path screenshotDir = Paths.get(System.getProperty("user.dir"), folderName);
        Files.createDirectories(screenshotDir);

        return screenshotDir;
    }
	
	public static void deleteDirectory(Path directory) throws IOException {
        if (Files.exists(directory)) {
            // Use walkFileTree to delete the folder and its contents
            Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    // Delete each file
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    // Delete the directory after all its files are deleted
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
            System.out.println("Deleted directory: " + directory.toString());
        } else {
            System.out.println("Directory does not exist: " + directory.toString());
        }
    }
	
	public static boolean checkScreenshotExists(String folderName, String sanitizedFileName) throws IOException {
		Path jobsScreenShotsDir = Paths.get("JobsScreenShots/" + folderName);
		try (Stream<Path> paths = Files.walk(jobsScreenShotsDir)) {
			return paths.filter(Files::isRegularFile)
					.filter(path -> path.getFileName().toString().equals(sanitizedFileName)).findAny().isPresent();
		}
	}

}
