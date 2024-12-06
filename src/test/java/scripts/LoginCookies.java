package scripts;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.NoSuchElementException;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import helpers.WaitUtils;
import pageObjects.HubstafftalentPo;
import pageObjects.LinkedInPo;
import pageObjects.TejofiPo;

public class LoginCookies {
	
	private static boolean setHeadless = true;
	
	public static void generateLoginCookies(String name) throws IOException {
		switch (name.toLowerCase()) {
        case "linkedin":
        	generateLinkedInLoginCookies();
            break;
        case "hubstafftalent":
        	generateHubstafftalentLoginCookies();
            break;
        case "tejofi":
        	generateTejofiLoginCookies();
            break;
        default:
            System.out.println("No such method exists: " + name);
            break;
    }
	}

	public static void generateLinkedInLoginCookies() throws IOException {
		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(setHeadless));
			BrowserContext context = browser.newContext();
			Page page = context.newPage();
			LinkedInPo linkedInPo = new LinkedInPo(page);

			// List of user IDs to attempt login
			int[] userIds = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13 };
			boolean loginSuccessful = false;
			int retryCount = 0;

			// Retry login with different user IDs until successful or all attempts fail
			while (!loginSuccessful && retryCount < userIds.length) {
				try {
					linkedInPo.navigateToLinkedInHomePage();
					WaitUtils.applyRandomTimeout(page);
					linkedInPo.clickSignIn();

					// Attempt login with the current user ID
					linkedInPo.enterEmailAndPassword(String.valueOf(userIds[retryCount]));
					linkedInPo.clickSignInButton();
					WaitUtils.applyRandomTimeout(page);

					// Check if verification (or another issue) is preventing login
					if (!linkedInPo.isElementVerificationPresent()) {
						throw new NoSuchElementException(
								"Verification required for this user ID, retrying with the next one.");
					}

					// If login is successful, save cookies and break the loop
					page.waitForSelector(".scaffold-layout");
					context.storageState(
							new BrowserContext.StorageStateOptions().setPath(Paths.get("linkedin-cookies.json")));
					System.out.println("Logged in successfully with User ID: " + userIds[retryCount]);
					loginSuccessful = true;

				} catch (NoSuchElementException e) {
					retryCount++;
					if (retryCount >= userIds.length) {
						throw new IOException("All login attempts failed due to verification or other issues.");
					}
					System.out.println("Login attempt failed, retrying with next User ID...");
				}
			}

			if (loginSuccessful) {
				System.out.println("Cookies saved successfully.");
			} else {
				System.out.println("Failed to log in after multiple attempts.");
			}

		} catch (IOException e) {
			System.err.println("Error during login process: " + e.getMessage());
		}
	}
	
	public static void generateHubstafftalentLoginCookies() throws IOException {
		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(setHeadless));
			BrowserContext context = browser.newContext();
			Page page = context.newPage();
			HubstafftalentPo hubstafftalentPo = new HubstafftalentPo(page);

			// List of user IDs to attempt login
			int[] userIds = { 1 };
			boolean loginSuccessful = false;
			int retryCount = 0;

			// Retry login with different user IDs until successful or all attempts fail
			while (!loginSuccessful && retryCount < userIds.length) {
				try {
					hubstafftalentPo.navigateToHubstafftalentHomePage();
					WaitUtils.applyRandomTimeout(page);
					
					hubstafftalentPo.clickSignIn();
					WaitUtils.applyRandomTimeout(page);

					// Attempt login with the current user ID
					hubstafftalentPo.enterEmailAndPasswordAndSignIn(String.valueOf(userIds[retryCount]));
					WaitUtils.applyRandomTimeout(page);

					// Check if verification (or another issue) is preventing login
					if (!hubstafftalentPo.isHomePageVisible()) {
						throw new NoSuchElementException(
								"Verification required for this user ID, retrying with the next one.");
					}
					context.storageState(
							new BrowserContext.StorageStateOptions().setPath(Paths.get("hubstafftalent-cookies.json")));
					System.out.println("Logged in successfully with User ID: " + userIds[retryCount]);
					loginSuccessful = true;

				} catch (NoSuchElementException e) {
					retryCount++;
					if (retryCount >= userIds.length) {
						throw new IOException("All login attempts failed due to verification or other issues.");
					}
					System.out.println("Login attempt failed, retrying with next User ID...");
				}
			}

			if (loginSuccessful) {
				System.out.println("Cookies saved successfully.");
			} else {
				System.out.println("Failed to log in after multiple attempts.");
			}

		} catch (IOException e) {
			System.err.println("Error during login process: " + e.getMessage());
		}
	}
	
	public static void generateTejofiLoginCookies() {
		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(setHeadless));
			BrowserContext context = browser.newContext();
			Page page = context.newPage();
//			HubstafftalentPo hubstafftalentPo = new HubstafftalentPo(page);
			TejofiPo tejofiPo = new TejofiPo(page, context);

			// List of user IDs to attempt login
			int[] userIds = { 1 };
			boolean loginSuccessful = false;
			int retryCount = 0;

			// Retry login with different user IDs until successful or all attempts fail
			while (!loginSuccessful && retryCount < userIds.length) {
				try {
					tejofiPo.navigateToHubstafftalentHomePage();
					WaitUtils.applyRandomTimeout(page);
					
					tejofiPo.clickLoginIn();
					WaitUtils.applyRandomTimeout(page);
					
					tejofiPo.clickPassword();
					WaitUtils.applyRandomTimeout(page);

					// Attempt login with the current user ID
					tejofiPo.getTejofiUserData(String.valueOf(userIds[retryCount]));
					WaitUtils.applyRandomTimeout(page);

					// Check if verification (or another issue) is preventing login
					if (!tejofiPo.isHomePageVisible()) {
						throw new NoSuchElementException(
								"Verification required for this user ID, retrying with the next one.");
					}
					context.storageState(
							new BrowserContext.StorageStateOptions().setPath(Paths.get("tejofi-cookies.json")));
					System.out.println("Logged in successfully with User ID: " + userIds[retryCount]);
					loginSuccessful = true;

				} catch (NoSuchElementException e) {
					retryCount++;
					if (retryCount >= userIds.length) {
						throw new IOException("All login attempts failed due to verification or other issues.");
					}
					System.out.println("Login attempt failed, retrying with next User ID...");
				}
			}

			if (loginSuccessful) {
				System.out.println("Cookies saved successfully.");
			} else {
				System.out.println("Failed to log in after multiple attempts.");
			}

		} catch (IOException e) {
			System.err.println("Error during login process: " + e.getMessage());
		}
	}

}