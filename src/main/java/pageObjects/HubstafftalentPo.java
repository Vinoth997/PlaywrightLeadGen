package pageObjects;

import java.io.IOException;
import java.nio.file.Path;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.AriaRole;

import helpers.ActionUtils;
import helpers.DataProviderUtils;
import helpers.DateChecker;
import helpers.ExcelUtils;
import helpers.FolderUtils;
import helpers.WaitUtils;

public class HubstafftalentPo {
	private Page page;
	int screenshotCount = 0;

	public HubstafftalentPo(Page page) {
		this.page = page;
	}

	public void navigateToHubstafftalentHomePage() {
		String hubstafftalentUrl = DataProviderUtils.getUrlData("hubstafftalent");
		WaitUtils.applyRandomTimeout(page);
		page.navigate(hubstafftalentUrl);
		page.reload();
	}

	public void clickSignIn() {
		WaitUtils.applyRandomTimeout(page);
		ActionUtils.forceClick(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Sign in")));
	}

	public void enterEmailAndPasswordAndSignIn(String userNumber) {
		String email = DataProviderUtils.getHubstaffTalentUserData("email", userNumber);
		String password = DataProviderUtils.getHubstaffTalentUserData("password", userNumber);
		WaitUtils.applyRandomTimeout(page);
		ActionUtils.typeText(page.getByPlaceholder("Enter email"), email, 200);
		WaitUtils.applyRandomTimeout(page);
		ActionUtils.typeText(page.getByPlaceholder("Enter password"), password, 200);
		WaitUtils.applyRandomTimeout(page);
		ActionUtils.clickElement(page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign in")));
	}

	public boolean isHomePageVisible() {
		WaitUtils.applyRandomTimeout(page);
		Locator hubstaffLogo = page.locator("//div[@class='navbar-header']//a[contains(@title,'Hubstaff online time')]");
		return hubstaffLogo.isVisible();
	}

	public void clickBrowseJobs() {
		Locator browseJobs = page.locator("//div[@id='navbar-collapse']//a[text()='Browse jobs']");
		WaitUtils.waitForElementVisible(browseJobs, 30);
		WaitUtils.applyRandomTimeout(page);
		ActionUtils.clickElement(browseJobs);
		WaitUtils.applyRandomTimeout(page);
	}
	
	public void clickJobType() {
		Locator fullTime = page.locator("//input[@name='search[job_type][0]']/parent::label");
		Locator hourlyContract = page.locator("//input[@name='search[job_type][1]']/parent::label");
		WaitUtils.waitForElementVisible(fullTime, 30);
		WaitUtils.waitForElementVisible(hourlyContract, 30);
		ActionUtils.clickElement(fullTime);
		WaitUtils.applyRandomTimeout(page);
		ActionUtils.clickElement(hourlyContract);
		WaitUtils.applyRandomTimeout(page);
	}

	public void enterTechnology(String technology) {
		WaitUtils.applyRandomTimeout(page);
		Locator jobSearchTextField = page.locator("#filter_search input[name='search[keywords]']");
		ActionUtils.typeText(jobSearchTextField, technology, 200);
		Locator searchJobsButton = page.locator("//button[text()='Search jobs']");
		ActionUtils.clickElement(searchJobsButton);
	}
	
//	public void sortBy() {
//		Locator sortBy = page.locator("//select[@id='search_sort_by']");
//		Locator options = page.locator("//select[@id='search_sort_by']/option");
//		ActionUtils.clickElement(sortBy);
//		WaitUtils.applyRandomTimeout(page);
//		for (int i = 0; i < options.count(); i++) {
//			Locator optionText = options.nth(i);
//			String value = optionText.getAttribute("value");
//			System.out.println(value);
//			if (value.equals("date_added")) {
//				ActionUtils.hoverAndForceClick(page, optionText);
//				System.out.println("Selected option: " + value);
//				break;
//			}
//		}
//	}
	
	public void sortBy() {
	    Locator sortBy = page.locator("//select[@id='search_sort_by']");
	    ActionUtils.clickElement(sortBy);
	    WaitUtils.applyRandomTimeout(page);
	    try {
	        sortBy.selectOption("date_added");
	        System.out.println("Selected option: date_added");
	    } catch (PlaywrightException e) {
	        System.err.println("Failed to select option: " + e.getMessage());
	    }
	}

	public int selectJobs(Path screenshotDir, int jobCount) throws IOException {
	    int i = 0;
	    int pageNumber = 2;
	    int previousLinkCount = 0;
	    jobCount = 0;
	    screenshotCount = 0;
//	    Locator sortBy = page.getByLabel("Sort by");
//	    sortBy.selectOption("date_added");
	    
//	    sortBy();

	    while (true) {
	        // Apply a random timeout to simulate user interaction
	        WaitUtils.applyRandomTimeout(page);
	        // Locate the 'Sort by' dropdown
	        // Get the job list count
	        int jobList = (int) page.evaluate("document.querySelectorAll('.main-details a.name').length");
//	        System.out.println("Job List: " + jobList);

	        // If no new jobs are found, exit the loop
	        if (jobList == previousLinkCount) {
	            break;
	        }
	        previousLinkCount = jobList;
	        // Iterate through the job list
	        while (i < jobList) {
//	            System.out.println("Processing job at index: " + i);
	            // Locators for posted date, job title, and company name
	            Locator postedDate = page.locator(".main-details span.is-inline-block.text-light-grey:not(.margin-right-20)");
	            Locator jobTitleList = page.locator(".main-details a.name");
	            Locator companyName = page.locator("//div[@class='job-company']//i[@title='Client']//parent::*[self::a or self::span]");
	            // Scroll to the current job element
	            Locator companyNameElement = companyName.nth(i);
	            if (companyNameElement.isVisible()) {
	                Locator postedDateElement = postedDate.nth(i);
	                WaitUtils.waitForElementVisible(postedDateElement, 20);
	                // Check if the job's posted date is within the last two weeks
	                if (DateChecker.isWithinOneWeek(postedDateElement.innerText().trim())) {
	                    Locator jobListElement = jobTitleList.nth(i);
	                    String screenShotName = jobListElement.textContent();
	                    String companyNameText = companyNameElement.textContent();
	                    // Sanitize the file names
	                    String sanitizedCompanyName = companyNameText.replaceAll("[<>:\"/\\|?*]", "_")
	                            .replaceAll("\\s+", " ").replaceAll("[\\u00A0]", "").trim();
	                    String sanitizedFileName = screenShotName.replaceAll("[<>:\"/\\|?*]", "_")
	                            .replaceAll("\\s+", " ").replaceAll("[\\u00A0]", "").trim() + "_" + sanitizedCompanyName + ".png";
	                    WaitUtils.applyRandomTimeout(page);
	                    // Click on the job and take a screenshot
	                    ActionUtils.clickElement(jobListElement);
	                    Locator healthBenefit = page.locator("//p[contains(text(),'Health') or contains(text(),'health')] | //li[contains(text(),'Health') or contains(text(),'health')]");
	                    if(!healthBenefit.isVisible()||!checkNoMatchJobsNotFoundNotExistIsVisible()) {
	                    	boolean screenshotExists = FolderUtils.checkScreenshotExists("HubstaffTalent", sanitizedFileName);

					        // Check if screenshot already exists in any folder
					        if (!screenshotExists) {
	                    	Path screenshotPath = screenshotDir.resolve(sanitizedFileName);
		                    page.screenshot(new Page.ScreenshotOptions().setPath(screenshotPath).setFullPage(true).setTimeout(30000));

		                    // Log the job URL and filename
		                    String jobUrl = page.url();
//		                    System.out.println("Screenshot saved as: " + sanitizedFileName + " - URL: " + jobUrl);
				            ExcelUtils.writeUrlToExcel("HubstaffTalent",sanitizedFileName, jobUrl);
		                    screenshotCount++;
					        }
	                    }else {
//	                    	System.err.println(healthBenefit.innerText());
//	                    	System.err.println("Job has Health Benefits");
	                    }
	                    WaitUtils.applyRandomTimeout(page);
	                    // Go back to the job list
	                    page.goBack();
//	                    WaitUtils.applyRandomTimeout(page);
//	                    sortBy();
	                    WaitUtils.applyRandomTimeout(page);
	                }
	                jobCount++;
	                // Move to the next job in the list
	                i++;

	                // Check if we've processed all jobs on the current page
	                if (i == jobList) {
//	                    System.out.println("End of job list on page. Moving to page number: " + pageNumber);

	                    Locator nextPage = page.locator(
	                            "//a[contains(@class,'is-paginate paginate-link') and @data-page='" + pageNumber + "']");

	                    if (nextPage.isVisible()) {
	                        WaitUtils.applyRandomTimeout(page);
	                        nextPage.click(new Locator.ClickOptions().setForce(true));
	                        pageNumber++;
	                        i = 0;  // Reset job index for the new page
	                        WaitUtils.applyRandomTimeout(page);
	                    } else {
	                        break;  // No more pages, exit the loop
	                    }
	                }
	            } else {
	                break;  // Exit if the company name element is not visible
	            }
	        }
	        System.out.println("Job Count: " + jobCount);
	    }
	    return jobCount;  // Return the total number of jobs processed
	}


	public boolean checkNoMatchJobsFoundIsVisible() {
		Locator noMatchingJobs = page.locator("div.content-section h5.section-subtitle.empty-search");
		WaitUtils.applyRandomTimeout(page);
		return noMatchingJobs.isVisible();
	}
	
	public boolean checkNoMatchJobsNotFoundNotExistIsVisible() {
		Locator jobNotFoundNotExist = page.locator("//div[@class='subhead' and contains(text(),'The job is expired or does not exist.')]");
		WaitUtils.applyRandomTimeout(page);
		return jobNotFoundNotExist.isVisible();
	}

	public int getScreenshotCount() {
		return screenshotCount;
	}

}