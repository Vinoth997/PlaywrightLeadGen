package helpers;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;

public class WindowUtils {
	
	public static Page switchToNewWindow(BrowserContext context, Page currentPage) {
        // Check for null inputs
        if (context == null || currentPage == null) {
            throw new IllegalArgumentException("BrowserContext and current Page cannot be null");
        }

        // Wait for the new page (new tab/window) to be opened
        Page newPage = context.waitForPage(() -> {
//            System.out.println("Waiting for new tab/window to open...");
        });

        // Bring the new tab/window to the front
        newPage.bringToFront();

//        System.out.println("Switched to the new tab/window.");
        
        return newPage;  // Return the new Page object representing the new tab/window
    }

}
