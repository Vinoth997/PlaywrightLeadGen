package pageObjects;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;

import helpers.ActionUtils;
import helpers.DataProviderUtils;
import helpers.ExcelUtils;
import helpers.FolderUtils;
import helpers.WaitUtils;
import helpers.WindowUtils;

public class TejofiPo {

	private Page page;
	private BrowserContext context ;
	int screenshotCount = 0;

	public TejofiPo(Page page, BrowserContext context) {
		this.page = page;
		this.context = context;
	}

	public void navigateToHubstafftalentHomePage() {
		String tejofiUrl = DataProviderUtils.getUrlData("tejofi");
		WaitUtils.applyRandomTimeout(page);
		page.navigate(tejofiUrl);
	}

	public void clickLoginIn() {
		WaitUtils.applyRandomTimeout(page);
		Locator loginIn = page.locator("//button[text()='Log In']");
		ActionUtils.forceClick(loginIn);
	}

	public void clickPassword() {
		WaitUtils.applyRandomTimeout(page);
		Locator loginIn = page.locator("//button[@data-radix-collection-item and contains(text(),'Password')]");
		ActionUtils.forceClick(loginIn);
//		page.pause();
	}

	public void clickViewJobs() {
		WaitUtils.applyRandomTimeout(page);
		Locator viewJobs = page.locator("//button[contains(text(),'View Jobs')]");
		ActionUtils.forceClick(viewJobs);
	}

	public void getTejofiUserData(String userNumber) {
		String email = DataProviderUtils.getTejofiUserData("email", userNumber);
		String password = DataProviderUtils.getTejofiUserData("password", userNumber);
		WaitUtils.applyRandomTimeout(page);
		ActionUtils.typeText(page.locator("//input[@id='email']"), email, 200);
		WaitUtils.applyRandomTimeout(page);
		ActionUtils.typeText(page.locator("//input[@id='password']"), password, 200);
		WaitUtils.applyRandomTimeout(page);
		ActionUtils.clickElement(
				page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign In").setExact(true)));
	}

	public boolean isHomePageVisible() {
		WaitUtils.applyRandomTimeout(page);
		Locator loginIn = page.locator(
				"//div[@class='flex flex-row items-center pt-2 pb-2 px-5 mx-auto max-w-screen-2xl']//button[@aria-haspopup='menu']");
		return loginIn.isVisible();
	}

	public void selectPreviousDate() {
		Locator dateBtn = page
				.locator("(//h2[text()='Date first posted']//parent::div/..//button[@data-state='closed'])[1]");
		ActionUtils.clickElement(dateBtn);
		WaitUtils.applyRandomTimeout(page);
		selectPreviousDay(page);
		selectRemote();
	}

	public void selectRemote() {
		Locator attendanceCheckbox = page.locator("#queryWorkArrangement");
		ActionUtils.clickElement(attendanceCheckbox);
		WaitUtils.applyRandomTimeout(page);

		Locator remoteRadioBtn = page.getByLabel("Generic Filters").getByText("Remote");
		ActionUtils.forceClick(remoteRadioBtn);
	}

	public static void selectPreviousDay(Page page) {
		LocalDate today = LocalDate.now();
		LocalDate previousDay = today.minusDays(1);
//		System.out.println("Date : " + today);
//		System.out.println("Date : " + previousDay);
		String previousDayString = DateTimeFormatter.ofPattern("d").format(previousDay);
		String daySelector = String.format("//td/button[text()='%s']", previousDayString);
		page.locator(daySelector).click();
	}

	public int selectTechnology(String technology, Path screenshotDir, int jobCount) throws IOException {
		Locator searchJobTextField = page.locator("div")
				.filter(new Locator.FilterOptions().setHasText(Pattern.compile("^ListTable(?:Clear)?VI$")))
				.getByPlaceholder("Search..");
		WaitUtils.applyRandomTimeout(page);
		ActionUtils.typeTextAndPressEnter(searchJobTextField, technology, 200);

		int i = 0;
		jobCount = 0;
		screenshotCount = 0;

		while (true) {
			WaitUtils.applyRandomTimeout(page);
			int jobList = (int) page
					.evaluate("document.querySelectorAll('div[data-state=\"active\"] a[href*=\"jobs\"]').length");
			if (i == jobList) {
				break;
			}
			while (i < jobList) {
				Locator jobListLink = page.locator("div[data-state='active'] a[href*='jobs']");
				if (jobListLink.nth(i).isVisible()) {
					WaitUtils.applyRandomTimeout(page);
					page.evaluate("document.querySelectorAll('div[data-state=\"active\"] a[href*=\"jobs\"]')[" + i
							+ "].scrollIntoView()");
					Locator listItem = jobListLink.nth(i);

					WaitUtils.waitForElementVisible(listItem, 40);
					ActionUtils.hoverAndForceClick(page, listItem);
					Page newPage = WindowUtils.switchToNewWindow(context, page);
					WaitUtils.applyRandomTimeout(page);
					String jobName = newPage.locator("//div[contains(@class,'max')]/h1").textContent();
					String companyName = newPage.locator("//div[contains(@class,'max')]/h2").textContent();

					WaitUtils.applyRandomTimeout(page);
					String sanitizedJobName = jobName.replaceAll("[<>:\"/\\|?*]", "_").replaceAll("\\s+", " ")
							.replaceAll("[\\u00A0]", "").trim();
					String sanitizedFileName = sanitizedJobName + "_" + companyName.replaceAll("[<>:\"/\\|?*]", "_")
							.replaceAll("\\s+", " ").replaceAll("[\\u00A0]", "").trim() + ".png";

					boolean screenshotExists = FolderUtils.checkScreenshotExists("Tejofi", sanitizedFileName);

					if (!screenshotExists) {
						Locator applyBtn = newPage.locator("//button[text()='Apply']");

						if (applyBtn.isVisible()) {
							WaitUtils.applyRandomTimeout(page);
							ActionUtils.forceClick(applyBtn);
							Page childpage = WindowUtils.switchToNewWindow(context, page);
							WaitUtils.applyRandomTimeout(page);
							childpage.reload();
							WaitUtils.applyRandomTimeout(page);
							childpage.waitForLoadState(LoadState.LOAD,
									new Page.WaitForLoadStateOptions().setTimeout(60000));
							handlePopup(childpage);

							childpage.evaluate("window.scrollTo(0, 0)");
							WaitUtils.applyRandomTimeout(page);

							childpage.evaluate("window.scrollTo(0, document.body.scrollHeight/20)");
							WaitUtils.applyRandomTimeout(page);
							childpage.evaluate("window.scrollTo(0, document.body.scrollHeight/40)");
							WaitUtils.applyRandomTimeout(page);
							childpage.evaluate("window.scrollTo(0, document.body.scrollHeight/60)");
							WaitUtils.applyRandomTimeout(page);
							childpage.evaluate("window.scrollTo(0, document.body.scrollHeight)");
							WaitUtils.applyRandomTimeout(page);
							childpage.evaluate("window.scrollTo(0, 0)");
							WaitUtils.applyRandomTimeout(page);
							childpage.evaluate("window.scrollTo(0, 0)");
							WaitUtils.applyRandomTimeout(page);
							
							if (!checkWordsOnPage(childpage)) {
								WaitUtils.applyRandomTimeout(page);
								Path screenshotPath = screenshotDir.resolve(sanitizedFileName);
								String jobUrl = childpage.url();

//								System.out.println(sanitizedFileName + " - " + childpage.url());
								childpage.screenshot(new Page.ScreenshotOptions().setPath(screenshotPath)
										.setFullPage(true).setTimeout(40000));
								ExcelUtils.writeUrlToExcel("Tejofi", sanitizedFileName, jobUrl);
								WaitUtils.applyRandomTimeout(page);
//								page.pause();
								childpage.close();
								screenshotCount++;
							} else {
//								System.err.println("Page contains text");
								WaitUtils.applyRandomTimeout(page);
								childpage.close();
							}
						}
					}
					WaitUtils.applyRandomTimeout(page);
					newPage.close();
					page.bringToFront();
					jobCount++;
					i++;
					break;
				}
			}
		}
		return jobCount;
	}

	public int getScreenshotCount() {
		return screenshotCount;
	}
	
	public void handlePopup(Page page) {
		Locator popUp = page.locator("//div[contains(@class,'onesignal-slidedown-dialog')]");
		Locator popUpCancel = page.locator("//div[contains(@class,'onesignal-slidedown-dialog')]//button[contains(text(),'Cancel')]");
		if(popUp.isVisible()) {
			ActionUtils.clickElement(popUpCancel);
		}
		
	}
	
	public boolean checkWordsOnPage(Page pageCon) {
	    List<String> wordsToCheck = Arrays.asList(
	        "This is an all-remote team",
	        "Remote - U",
	        "Remote USA", 
	        "Remote, United States",
	        "within the United States", 
	        "Health Insurance",  
	        "Life Insurance", 
	        "Page not found", 
	        "no longer active", 
	        "health support", 
	        "Homeoffice", 
	        "Dental Care", 
	        "Health Care",
	        "Sorry, we couldn't find",
	        "Sorry",
	        "Remote, United States",
	        "Healthcare",
	        "Remote, USA",
	        "Remote EMEA"
	    );

	    String pageText = pageCon.textContent("body").toLowerCase();
	    boolean wordFound = false;

	    // Iterate through the list and check if any word matches
	    for (String word : wordsToCheck) {
	        if (pageText.contains(word.toLowerCase())) {
//	            System.out.println("Match found: " + word);
	            wordFound = true;
	        }
	    }

//	    if (!wordFound) {
////	        System.out.println("No matching words found.");
//	    }

	    return wordFound;
	}
}
