package pageObjects;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import helpers.ActionUtils;
import helpers.DataProviderUtils;
import helpers.ExcelUtils;
import helpers.FolderUtils;
import helpers.WaitUtils;

public class LinkedInPo {
	private Page page;
	int screenshotCount = 0;
	int duplicateScreenshotCount = 0;

	public LinkedInPo(Page page) {
		this.page = page;
	}

	// Actions
	public void navigateToLinkedInHomePage() {
		String linkedinUrl = DataProviderUtils.getUrlData("linkedin");
		page.navigate(linkedinUrl);
	}

	public void clickSignIn() {
		ActionUtils.clickElement(page.locator("[data-tracking-control-name='guest_homepage-basic_nav-header-signin']"));
	}

	public void navigateToLinkedInJobPage() {

		String linkedinJobUrl = DataProviderUtils.getUrlData("linkedinjob");
		// Call navigateToUrl using the instance method from BasePo if necessary.
		// For example, if BasePo was a parameter passed in constructor, use it here.
		// basePo.navigateToUrl(linkedinJobUrl);
		page.navigate(linkedinJobUrl); // Use the page object directly
	}

	public void enterEmailAndPassword(String username) {
		String email = DataProviderUtils.getLinkedInUserData("email", username);
		String password = DataProviderUtils.getLinkedInUserData("password", username);
		WaitUtils.applyRandomTimeout(page);
		ActionUtils.typeText(page.getByLabel("Email or phone"), email, 200);
		WaitUtils.applyRandomTimeout(page);
		ActionUtils.typeText(page.getByLabel("Password"), password, 200);
	}

	public void clickSignInButton() {
		WaitUtils.applyRandomTimeout(page);
//		page.pause(); 
		ActionUtils.clickElement(page.getByLabel("Sign in", new Page.GetByLabelOptions().setExact(true)));
	}

	public void hideMessagingTab() {
		Locator messaging = page.locator(
				"//header[@class='msg-overlay-bubble-header']//*[name()='svg' and @data-test-icon='chevron-down-small']/parent::button");
		WaitUtils.applyRandomTimeout(page);
		ActionUtils.clickElement(messaging);
	}

	public void enterSearchKeywords(String keyword) {
		Locator searchBar = page.getByRole(AriaRole.COMBOBOX,
				new Page.GetByRoleOptions().setName("Search by title, skill, or"));
		WaitUtils.applyRandomTimeout(page);
		ActionUtils.typeText(searchBar, keyword, 300);
		WaitUtils.applyRandomTimeout(page);
		searchBar.press("Enter");
	}

	public void enterLocation(String location) {
		Locator locationInputBox = page.getByRole(AriaRole.COMBOBOX,
				new Page.GetByRoleOptions().setName("City, state, or zip code"));
		WaitUtils.applyRandomTimeout(page);
		ActionUtils.typeText(locationInputBox, location, 200);
		WaitUtils.applyRandomTimeout(page);
		locationInputBox.press("Enter");
	}

	public void clickAllFiltersButton() {
		Locator allFilters = page.getByLabel("Show all filters. Clicking");
		ActionUtils.clickElement(allFilters);
	}

	public boolean checkPast24HoursIsChecked() {
		Locator Past24Hours = page.getByLabel("All filters", new Page.GetByLabelOptions().setExact(true))
				.locator("label").filter(new Locator.FilterOptions().setHasText("Past 24 hours Filter by Past"));

		return Past24Hours.isChecked();
	}

	public void enterFilters() {

		Locator Past24Hours = page.getByLabel("All filters", new Page.GetByLabelOptions().setExact(true))
				.getByText("Past 24 hours", new Locator.GetByTextOptions().setExact(true));
		Locator contractFilter = page.getByLabel("All filters", new Page.GetByLabelOptions().setExact(true))
				.getByText("Contract", new Locator.GetByTextOptions().setExact(true));
		Locator partTimeFilter = page.locator("input[id=advanced-filter-jobType-P] + label");
		Locator RemoteFilter = page.getByText("Remote", new Page.GetByTextOptions().setExact(true)).nth(1);
		Locator showFilters = page.getByLabel("Apply current filters to show");
		Locator easyApply = page.getByText("Off Toggle Easy Apply filter");

		WaitUtils.applyRandomTimeout(page);

		if (Past24Hours.isVisible()) {
			WaitUtils.applyRandomTimeout(page);
			if(!Past24Hours.isChecked()) {
				ActionUtils.clickElement(Past24Hours);
			}
		}
		if (partTimeFilter.isVisible()) {
			WaitUtils.applyRandomTimeout(page);
			if(!partTimeFilter.isChecked()) {
				ActionUtils.clickElement(partTimeFilter);
			}
		}
		if (contractFilter.isVisible()) {
			WaitUtils.applyRandomTimeout(page);
			if(!contractFilter.isChecked()) {
				ActionUtils.clickElement(contractFilter);
			}
		}
		if (RemoteFilter.isVisible()) {
			WaitUtils.applyRandomTimeout(page);
			if(!RemoteFilter.isChecked()) {
				ActionUtils.clickElement(RemoteFilter);
			}
		}
		if (easyApply.isVisible()) {
			WaitUtils.applyRandomTimeout(page);
			ActionUtils.clickElement(easyApply);
		}
		WaitUtils.applyRandomTimeout(page);
		ActionUtils.clickElement(showFilters);
	}

	public void clickCloseFilterButton() {
		Locator closeFilter = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Dismiss"));
		WaitUtils.applyRandomTimeout(page);
		ActionUtils.clickElement(closeFilter);
	}

	public boolean checkNoJobsFoundIsVisible() {
		Locator noMatchingJobs = page.getByRole(AriaRole.HEADING,
				new Page.GetByRoleOptions().setName("No matching jobs found."));
		WaitUtils.applyRandomTimeout(page);
		return noMatchingJobs.isVisible();
	}

	public int clickAllElementandgetlink(Path screenshotDir, int jobCount) throws IOException {
		WaitUtils.applyRandomTimeout(page);
		int i = 0;
		int pageNumber = 2;
		int previousLinkCount = 0;
		jobCount = 0;
		screenshotCount = 0;
		duplicateScreenshotCount = 0;

		while (true) {
			WaitUtils.applyRandomTimeout(page);

			int jobList = (int) page.evaluate(
					"document.querySelectorAll('ul.scaffold-layout__list-container li.ember-view.jobs-search-results__list-item.occludable-update.p0.relative.scaffold-layout__list-item').length");
			// If no new items are loaded, break the loop
			if (jobList == previousLinkCount) {
				break;
			}
//            System.out.println("ScreenSS : "+screenshotCount);
			previousLinkCount = jobList; // Update previous link count

			while (i < jobList) {
				Locator listLocator = page.locator(
						"ul.scaffold-layout__list-container li.ember-view.jobs-search-results__list-item.occludable-update.p0.relative.scaffold-layout__list-item");

				WaitUtils.applyRandomTimeout(page);
				if (listLocator.nth(i).isVisible()) {
					WaitUtils.applyRandomTimeout(page);
					page.evaluate(
							"document.querySelectorAll('ul.scaffold-layout__list-container li.ember-view.jobs-search-results__list-item.occludable-update.p0.relative.scaffold-layout__list-item')["
									+ i + "].scrollIntoView()");
					Locator listItem = listLocator.nth(i);
					WaitUtils.waitForElementVisible(listItem, 40);
//                    WaitUtils.applyRandomTimeout(page);
					ActionUtils.hoverAndForceClick(page, listItem);
					Locator jobTitles = page.locator("div.job-details-jobs-unified-top-card__job-title");
//        			WaitUtils.applyRandomTimeout(page);
					ActionUtils.clickElement(jobTitles);

					WaitUtils.applyRandomTimeout(page);
					if (checkEmployeeCriteriaIsThere() && !checkLocationCriteriaIsThere() && !checkIfFullTimeIsPresent()) {

						if (page.getByLabel("Click to see more description").isVisible()) {
							WaitUtils.applyRandomTimeout(page);
							ActionUtils.clickElement(page.getByLabel("Click to see more description"));
						}

						try {
							// Ensure page stability before taking a screenshot
							WaitUtils.applyRandomTimeout(page);
							page.evaluate("window.scrollTo(0, 0)");
							WaitUtils.applyRandomTimeout(page);
//							Path screenshotPath = screenshotDir.resolve(sanitizedFileName);
//							 System.out.println(jobsScreenShotsDir.toString());
							// Extract the screenshot name and sanitize it
							String screenShotName = jobTitles.textContent();
							String sanitizedFileName = screenShotName.replaceAll("[<>:\"/\\|?*]", "_")
                                    .replaceAll("\\s+", " ")    // Normalize spaces
                                    .replaceAll("[\\u00A0]", "") // Remove non-breaking spaces
                                    .trim() + ".png";

					        boolean screenshotExists = FolderUtils.checkScreenshotExists("LinkedIn", sanitizedFileName);

					        // Check if screenshot already exists in any folder
					        if (!screenshotExists) {
					            // Capture the screenshot
					            Path screenshotPath = screenshotDir.resolve(sanitizedFileName);
					            page.screenshot(new Page.ScreenshotOptions().setPath(screenshotPath).setFullPage(true)
					                    .setTimeout(30000)); // Increase timeout to 30 seconds
					            
					            // Store the job title and URL in Excel
					            String jobUrl = page.url();
					            ExcelUtils.writeUrlToExcel("LinkedIn", screenShotName, jobUrl);
					            screenshotCount++;
					        } else {
//					            System.out.println("Screenshot already exists in one of the folders, skipping: " + sanitizedFileName);
					            duplicateScreenshotCount++;
					        }
						} catch (Exception e) {
							System.err.println("Error capturing screenshot: " + e.getMessage());
							e.printStackTrace();
						}
//        				System.out.println("Screenshot : "+ screenshotCount);
					}
					jobCount++;
					WaitUtils.applyRandomTimeout(page);
					page.goBack();

					if (jobList == (i + 1)) {
//                        System.out.println(page.locator("div.artdeco-pagination__page-state").isVisible());
//                    	page.pause();
						Locator pageNo = page.getByLabel("Page " + pageNumber);

						if (pageNo.isVisible()) {
							WaitUtils.applyRandomTimeout(page);
							page.waitForTimeout(5000);
							pageNo.click(new Locator.ClickOptions().setForce(true));
							pageNumber++;
							i = 0;
							WaitUtils.applyRandomTimeout(page);
							page.reload();
							WaitUtils.applyRandomTimeout(page);
						} else {
							break;
						}
					} else {
						i++;
					}
				} else {
					break;
				}
			}

			System.out.println("Job Count: " + jobCount);

		}
		return jobCount;
	}

	public boolean check100applicantsIsThere() {
		return page.getByText("Over 100 applicants").isVisible();
	}

	public boolean checkLocationCriteriaIsThere() {
		if (page.locator(
				"//section[contains(@class,'job-details-how-you-match-card__header')]//h2[not(@id='how-you-match-card-title')]")
				.isVisible()) {
			String locationCriteria = page.locator(
					"//section[contains(@class,'job-details-how-you-match-card__header')]//h2[not(@id='how-you-match-card-title')]")
					.textContent().trim();
			boolean isMatched = isJobRequirementMatched(locationCriteria);
			return isMatched;
		} else {
			return false;
		}
	}

	public boolean checkEmployeeCriteriaIsThere() {
		Locator employeeCriteria = page
				.locator("div.jobs-company__box span.jobs-company__inline-information:first-of-type");
		if (employeeCriteria.isVisible()) {
			String textContent = employeeCriteria.textContent();
			return isEmployeeRangeWithinLimit(textContent);
		}
		return true;

	}

	public static boolean isTextPresent(Locator locator, String text) {
		try {
			String locatorText = locator.textContent();
			return locatorText != null && locatorText.contains(text);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Checks if the upper bound of the given employee range is less than or equal
	 * to 500. The input may contain non-numeric characters, which will be ignored.
	 *
	 * @param employeeRange The employee range as a string (e.g., "11-50 employees",
	 *                      "100-200 people").
	 * @return True if the upper bound of the range is less than or equal to 500,
	 *         otherwise false.
	 */
	public static boolean isEmployeeRangeWithinLimit(String employeeRange) {
		if (employeeRange == null || employeeRange.isEmpty()) {
			return false;
		}

		try {
			// Remove any non-numeric characters except for the hyphen
			String numericRange = employeeRange.replaceAll("[^0-9-]", "");

			// Split the range based on the hyphen (-)
			String[] rangeParts = numericRange.split("-");
			if (rangeParts.length != 2) {
				return false;
			}

			// Parse the upper bound of the range
			int upperBound = Integer.parseInt(rangeParts[1].trim());

			// Check if the upper bound is less than or equal to 500
			return upperBound <= 500;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Checks if the provided text matches any of the predefined strings.
	 *
	 * @param text The text to be checked.
	 * @return true if the text matches any of the predefined strings, false
	 *         otherwise.
	 */
	public static boolean isJobRequirementMatched(String text) {
		// List of strings to match against, declared inside the method
		List<String> matchTexts = Arrays.asList("This job requires a work authorization",
				"Your location does not match country requirements. Hirer is not accepting out of country applications.");

		// Check if the provided text contains any of the predefined strings
		for (String matchText : matchTexts) {
			if (text.trim().toLowerCase().contains(matchText.toLowerCase().trim())) {
				return true; // Return true immediately if a match is found
			}
		}

		// Return false if no match is found
		return false;
	}

	public void scrollToEndOfScrollbar(Page page, String scrollbarSelector) {
		// Locate the scrollbar using the provided selector
		Locator scrollbar = page.locator(scrollbarSelector);

		// Get the bounding box of the scrollbar
		@SuppressWarnings("unchecked")
		Map<String, Object> bounds = (Map<String, Object>) scrollbar.evaluate(
				"element => { const rect = element.getBoundingClientRect(); return { x: rect.x, y: rect.y, width: rect.width, height: rect.height }; }");

		// Calculate the center of the scrollbar to start the drag
		float startX = ((Number) bounds.get("x")).floatValue() + ((Number) bounds.get("width")).floatValue() / 2;
		float startY = ((Number) bounds.get("y")).floatValue() + ((Number) bounds.get("height")).floatValue() / 2;

		// Perform a loop to drag the scrollbar to the end
		float previousScrollTop = -1;
		float currentScrollTop = 0;

		while (currentScrollTop != previousScrollTop) {
			previousScrollTop = currentScrollTop;

			// Move to the current position and drag downwards
			page.mouse().move(startX, startY); // Move to the start position
			page.mouse().down(); // Press the mouse button down
			page.mouse().move(startX, startY + 200); // Drag the mouse downwards
			page.mouse().up(); // Release the mouse button

			// Optional: Wait for content to load or update
			page.waitForTimeout(1000);

			// Update the current scroll position
			currentScrollTop = ((Number) scrollbar.evaluate("element => element.scrollTop")).floatValue();
		}
	}

	public boolean isElementVerificationPresent() {
		return page.locator("div.authentication-outlet").isVisible();

	}

	public int getScreenshotCount() {
		return screenshotCount;
	}
	
	public int getDuplicateScreenshotCount() {
		return duplicateScreenshotCount;
	}

	public boolean checkIfFullTimeIsPresent() {
	    Locator parent = page.locator(
	        "(//li[@class='job-details-jobs-unified-top-card__job-insight job-details-jobs-unified-top-card__job-insight--highlight'])[1]");
	    List<String> childSpansTexts = parent.locator("span").allTextContents();

	    // Combine the text and normalize spaces
	    String combinedText = String.join(" ", childSpansTexts).replaceAll("\\s+", " ").trim();
//	    System.out.println("combinedText: " + combinedText);

	    // Check if "Full-time" is present
	    if (combinedText.toLowerCase().contains("full-time")) {
//	        System.out.println("Full-time is present");
	        return true;
	    } else {
//	        System.out.println("Full-time is not present");
	        return false;
	    }
	}
}
