package scripts;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.microsoft.playwright.Page;

import helpers.DataProviderUtils;
import helpers.ExcelUtils;
import helpers.FolderUtils;
import helpers.WaitUtils;
import listeners.TestListener;
import pageObjects.BasePo;
import pageObjects.LinkedInPo;
import retryAnalyser.RetryAnalyzer;

@Listeners(TestListener.class)

public class LinkedInScript {

	private BasePo basePo;
	private LinkedInPo linkedInPo;
	
	private final String portalName = "LinkedIn";

	@BeforeSuite
	public void beforeSuite() throws IOException {
		Path folderToDelete = Paths.get("JobsScreenShots/" + portalName);
		FolderUtils.deleteDirectory(folderToDelete);
	    LoginCookies.generateLoginCookies(portalName);
	}

	@BeforeMethod
	public void startup() {
		basePo = BasePo.getInstance();
		Page page = basePo.setup(portalName);
		linkedInPo = new LinkedInPo(page);
	}

	@AfterMethod
	public void tearDown() {
		basePo.teardown();
	}

	@Test(priority = 0, retryAnalyzer = RetryAnalyzer.class)
	public void Frontend() throws IOException, InterruptedException {
		String category = "Frontend";

		linkedInPo.navigateToLinkedInJobPage();
		WaitUtils.applyRandomTimeout(basePo.getPage());
		linkedInPo.hideMessagingTab();
		// Screenshot directory setup
		Method method = new Object() {
		}.getClass().getEnclosingMethod();
		String testMethodName = method.getName();
		Path screenshotDir = FolderUtils.createScreenshotDirectory(portalName, testMethodName);
		// Data fetching and iteration through technology and location
		List<String> technologiesData = DataProviderUtils.getTechnologyData(category);
		for (int j = 0; j < technologiesData.size(); j++) {
			int count = 0;
			int screenshotCount = 0;
			linkedInPo.enterSearchKeywords(technologiesData.get(j));
			List<String> locationData = DataProviderUtils.getLocationData("Location");
			for (int k = 0; k < locationData.size(); k++) {
				System.out.println(technologiesData.get(j) + " - " + locationData.get(k));
				linkedInPo.enterLocation(locationData.get(k));

				linkedInPo.clickAllFiltersButton();

				linkedInPo.enterFilters();

				if (!linkedInPo.checkNoJobsFoundIsVisible()) {
					count = linkedInPo.clickAllElementandgetlink(screenshotDir, count);
					screenshotCount = linkedInPo.getScreenshotCount();
				} else {
					count = 0;
					screenshotCount = 0;
					System.out.println("Job Count: " + count);
				}
				System.out.println("Screenshot Count: " + screenshotCount);
				ExcelUtils.writeToExcel(portalName, category, technologiesData.get(j), locationData.get(k), count, screenshotCount);
			}
		}
	}

	@Test(priority = 1, retryAnalyzer = RetryAnalyzer.class)
	public void BackendOne() throws IOException, InterruptedException {
		String category = "BackendOne";

		linkedInPo.navigateToLinkedInJobPage();
		WaitUtils.applyRandomTimeout(basePo.getPage());
		linkedInPo.hideMessagingTab();
		// Screenshot directory setup
		Method method = new Object() {
		}.getClass().getEnclosingMethod();
		String testMethodName = method.getName();
		Path screenshotDir = FolderUtils.createScreenshotDirectory(portalName, testMethodName);
		// Data fetching and iteration through technology and location
		List<String> technologiesData = DataProviderUtils.getTechnologyData(category);
		for (int j = 0; j < technologiesData.size(); j++) {
			int count = 0;
			int screenshotCount = 0;
			linkedInPo.enterSearchKeywords(technologiesData.get(j));
			List<String> locationData = DataProviderUtils.getLocationData("Location");
			for (int k = 0; k < locationData.size(); k++) {
				System.out.println(technologiesData.get(j) + " - " + locationData.get(k));
				linkedInPo.enterLocation(locationData.get(k));

				linkedInPo.clickAllFiltersButton();

				linkedInPo.enterFilters();

				if (!linkedInPo.checkNoJobsFoundIsVisible()) {
					count = linkedInPo.clickAllElementandgetlink(screenshotDir, count);
					screenshotCount = linkedInPo.getScreenshotCount();
				} else {
					count = 0;
					screenshotCount = 0;
					System.out.println("Job Count: " + count);
				}
				System.out.println("Screenshot Count: " + screenshotCount);
				ExcelUtils.writeToExcel(portalName, category, technologiesData.get(j), locationData.get(k), count, screenshotCount);
			}
		}
	}

	@Test(priority = 2, retryAnalyzer = RetryAnalyzer.class)
	public void BackendTwo() throws IOException, InterruptedException {
		String category = "BackendTwo";

		linkedInPo.navigateToLinkedInJobPage();
		WaitUtils.applyRandomTimeout(basePo.getPage());
		linkedInPo.hideMessagingTab();
		// Screenshot directory setup
		Method method = new Object() {
		}.getClass().getEnclosingMethod();
		String testMethodName = method.getName();
		Path screenshotDir = FolderUtils.createScreenshotDirectory(portalName, testMethodName);
		// Data fetching and iteration through technology and location
		List<String> technologiesData = DataProviderUtils.getTechnologyData(category);
		for (int j = 0; j < technologiesData.size(); j++) {
			int count = 0;
			int screenshotCount = 0;
			linkedInPo.enterSearchKeywords(technologiesData.get(j));
			List<String> locationData = DataProviderUtils.getLocationData("Location");
			for (int k = 0; k < locationData.size(); k++) {
				System.out.println(technologiesData.get(j) + " - " + locationData.get(k));
				linkedInPo.enterLocation(locationData.get(k));

				linkedInPo.clickAllFiltersButton();

				linkedInPo.enterFilters();

				if (!linkedInPo.checkNoJobsFoundIsVisible()) {
					count = linkedInPo.clickAllElementandgetlink(screenshotDir, count);
					screenshotCount = linkedInPo.getScreenshotCount();
				} else {
					count = 0;
					screenshotCount = 0;
					System.out.println("Job Count: " + count);
				}
				System.out.println("Screenshot Count: " + screenshotCount);
				ExcelUtils.writeToExcel(portalName, category, technologiesData.get(j), locationData.get(k), count, screenshotCount);
			}
		}
	}

	@Test(priority = 3, retryAnalyzer = RetryAnalyzer.class)
	public void BackendThree() throws IOException, InterruptedException {
		String category = "BackendThree";

		linkedInPo.navigateToLinkedInJobPage();
		WaitUtils.applyRandomTimeout(basePo.getPage());
		linkedInPo.hideMessagingTab();
		// Screenshot directory setup
		Method method = new Object() {
		}.getClass().getEnclosingMethod();
		String testMethodName = method.getName();
		Path screenshotDir = FolderUtils.createScreenshotDirectory(portalName, testMethodName);
		// Data fetching and iteration through technology and location
		List<String> technologiesData = DataProviderUtils.getTechnologyData(category);
		for (int j = 0; j < technologiesData.size(); j++) {
			int count = 0;
			int screenshotCount = 0;
			linkedInPo.enterSearchKeywords(technologiesData.get(j));
			List<String> locationData = DataProviderUtils.getLocationData("Location");
			for (int k = 0; k < locationData.size(); k++) {
				System.out.println(technologiesData.get(j) + " - " + locationData.get(k));
				linkedInPo.enterLocation(locationData.get(k));

				linkedInPo.clickAllFiltersButton();

				linkedInPo.enterFilters();

				if (!linkedInPo.checkNoJobsFoundIsVisible()) {
					count = linkedInPo.clickAllElementandgetlink(screenshotDir, count);
					screenshotCount = linkedInPo.getScreenshotCount();
				} else {
					count = 0;
					screenshotCount = 0;
					System.out.println("Job Count: " + count);
				}
				System.out.println("Screenshot Count: " + screenshotCount);
				ExcelUtils.writeToExcel(portalName, category, technologiesData.get(j), locationData.get(k), count, screenshotCount);
			}
		}
	}

	@Test(priority = 4, retryAnalyzer = RetryAnalyzer.class)
	public void BackendFour() throws IOException, InterruptedException {
		String category = "BackendFour";

		linkedInPo.navigateToLinkedInJobPage();
		WaitUtils.applyRandomTimeout(basePo.getPage());
		linkedInPo.hideMessagingTab();
		// Screenshot directory setup
		Method method = new Object() {
		}.getClass().getEnclosingMethod();
		String testMethodName = method.getName();
		Path screenshotDir = FolderUtils.createScreenshotDirectory(portalName, testMethodName);
		// Data fetching and iteration through technology and location
		List<String> technologiesData = DataProviderUtils.getTechnologyData(category);
		for (int j = 0; j < technologiesData.size(); j++) {
			int count = 0;
			int screenshotCount = 0;
			linkedInPo.enterSearchKeywords(technologiesData.get(j));
			List<String> locationData = DataProviderUtils.getLocationData("Location");
			for (int k = 0; k < locationData.size(); k++) {
				System.out.println(technologiesData.get(j) + " - " + locationData.get(k));
				linkedInPo.enterLocation(locationData.get(k));

				linkedInPo.clickAllFiltersButton();

				linkedInPo.enterFilters();

				if (!linkedInPo.checkNoJobsFoundIsVisible()) {
					count = linkedInPo.clickAllElementandgetlink(screenshotDir, count);
					screenshotCount = linkedInPo.getScreenshotCount();
				} else {
					count = 0;
					screenshotCount = 0;
					System.out.println("Job Count: " + count);
				}
				System.out.println("Screenshot Count: " + screenshotCount);
				ExcelUtils.writeToExcel(portalName, category, technologiesData.get(j), locationData.get(k), count, screenshotCount);
			}
		}
	}

	@Test(priority = 5, retryAnalyzer = RetryAnalyzer.class)
	public void BackendFive() throws IOException, InterruptedException {
		String category = "BackendFive";

		linkedInPo.navigateToLinkedInJobPage();
		WaitUtils.applyRandomTimeout(basePo.getPage());
		linkedInPo.hideMessagingTab();
		// Screenshot directory setup
		Method method = new Object() {
		}.getClass().getEnclosingMethod();
		String testMethodName = method.getName();
		Path screenshotDir = FolderUtils.createScreenshotDirectory(portalName, testMethodName);
		// Data fetching and iteration through technology and location
		List<String> technologiesData = DataProviderUtils.getTechnologyData(category);
		for (int j = 0; j < technologiesData.size(); j++) {
			int count = 0;
			int screenshotCount = 0;
			linkedInPo.enterSearchKeywords(technologiesData.get(j));
			List<String> locationData = DataProviderUtils.getLocationData("Location");
			for (int k = 0; k < locationData.size(); k++) {
				System.out.println(technologiesData.get(j) + " - " + locationData.get(k));
				linkedInPo.enterLocation(locationData.get(k));

				linkedInPo.clickAllFiltersButton();

				linkedInPo.enterFilters();

				if (!linkedInPo.checkNoJobsFoundIsVisible()) {
					count = linkedInPo.clickAllElementandgetlink(screenshotDir, count);
					screenshotCount = linkedInPo.getScreenshotCount();
				} else {
					count = 0;
					screenshotCount = 0;
					System.out.println("Job Count: " + count);
				}
				System.out.println("Screenshot Count: " + screenshotCount);
				ExcelUtils.writeToExcel(portalName, category, technologiesData.get(j), locationData.get(k), count, screenshotCount);
			}
		}
	}

	@Test(priority = 6, retryAnalyzer = RetryAnalyzer.class)
	public void AI_ML() throws IOException, InterruptedException {
		String category = "AI_ML";

		linkedInPo.navigateToLinkedInJobPage();
		WaitUtils.applyRandomTimeout(basePo.getPage());
		linkedInPo.hideMessagingTab();
		// Screenshot directory setup
		Method method = new Object() {
		}.getClass().getEnclosingMethod();
		String testMethodName = method.getName();
		Path screenshotDir = FolderUtils.createScreenshotDirectory(portalName, testMethodName);
		// Data fetching and iteration through technology and location
		List<String> technologiesData = DataProviderUtils.getTechnologyData(category);
		for (int j = 0; j < technologiesData.size(); j++) {
			int count = 0;
			int screenshotCount = 0;
			linkedInPo.enterSearchKeywords(technologiesData.get(j));
			List<String> locationData = DataProviderUtils.getLocationData("Location");
			for (int k = 0; k < locationData.size(); k++) {
				System.out.println(technologiesData.get(j) + " - " + locationData.get(k));
				linkedInPo.enterLocation(locationData.get(k));

				linkedInPo.clickAllFiltersButton();

				linkedInPo.enterFilters();

				if (!linkedInPo.checkNoJobsFoundIsVisible()) {
					count = linkedInPo.clickAllElementandgetlink(screenshotDir, count);
					screenshotCount = linkedInPo.getScreenshotCount();
				} else {
					count = 0;
					screenshotCount = 0;
					System.out.println("Job Count: " + count);
				}
				System.out.println("Screenshot Count: " + screenshotCount);
				ExcelUtils.writeToExcel(portalName, category, technologiesData.get(j), locationData.get(k), count, screenshotCount);
			}
		}
	}

	@Test(priority = 7, retryAnalyzer = RetryAnalyzer.class)
	public void Mobile_App() throws IOException, InterruptedException {
		String category = "Mobile_App";

		linkedInPo.navigateToLinkedInJobPage();
		WaitUtils.applyRandomTimeout(basePo.getPage());
		linkedInPo.hideMessagingTab();
		// Screenshot directory setup
		Method method = new Object() {
		}.getClass().getEnclosingMethod();
		String testMethodName = method.getName();
		Path screenshotDir = FolderUtils.createScreenshotDirectory(portalName, testMethodName);
		// Data fetching and iteration through technology and location
		List<String> technologiesData = DataProviderUtils.getTechnologyData(category);
		for (int j = 0; j < technologiesData.size(); j++) {
			int count = 0;
			int screenshotCount = 0;
			linkedInPo.enterSearchKeywords(technologiesData.get(j));
			List<String> locationData = DataProviderUtils.getLocationData("Location");
			for (int k = 0; k < locationData.size(); k++) {
				System.out.println(technologiesData.get(j) + " - " + locationData.get(k));
				linkedInPo.enterLocation(locationData.get(k));

				linkedInPo.clickAllFiltersButton();

				linkedInPo.enterFilters();

				if (!linkedInPo.checkNoJobsFoundIsVisible()) {
					count = linkedInPo.clickAllElementandgetlink(screenshotDir, count);
					screenshotCount = linkedInPo.getScreenshotCount();
				} else {
					count = 0;
					screenshotCount = 0;
					System.out.println("Job Count: " + count);
				}
				System.out.println("Screenshot Count: " + screenshotCount);
				ExcelUtils.writeToExcel(portalName, category, technologiesData.get(j), locationData.get(k), count, screenshotCount);
			}
		}
	}

	@Test(priority = 8, retryAnalyzer = RetryAnalyzer.class)
	public void Design_Tools() throws IOException {
		String category = "Design_Tools";

		linkedInPo.navigateToLinkedInJobPage();
		WaitUtils.applyRandomTimeout(basePo.getPage());
		linkedInPo.hideMessagingTab();
		// Screenshot directory setup
		Method method = new Object() {
		}.getClass().getEnclosingMethod();
		String testMethodName = method.getName();
		Path screenshotDir = FolderUtils.createScreenshotDirectory(portalName, testMethodName);
		// Data fetching and iteration through technology and location
		List<String> technologiesData = DataProviderUtils.getTechnologyData(category);
		for (int j = 0; j < technologiesData.size(); j++) {
			int count = 0;
			int screenshotCount = 0;
			linkedInPo.enterSearchKeywords(technologiesData.get(j));
			List<String> locationData = DataProviderUtils.getLocationData("Location");
			for (int k = 0; k < locationData.size(); k++) {
				System.out.println(technologiesData.get(j) + " - " + locationData.get(k));
				linkedInPo.enterLocation(locationData.get(k));

				linkedInPo.clickAllFiltersButton();

				linkedInPo.enterFilters();

				if (!linkedInPo.checkNoJobsFoundIsVisible()) {
					count = linkedInPo.clickAllElementandgetlink(screenshotDir, count);
					screenshotCount = linkedInPo.getScreenshotCount();
				} else {
					count = 0;
					screenshotCount = 0;
					System.out.println("Job Count: " + count);
				}
				System.out.println("Screenshot Count: " + screenshotCount);
				ExcelUtils.writeToExcel(portalName, category, technologiesData.get(j), locationData.get(k), count, screenshotCount);
			}
		}
	}

	@Test(priority = 9, retryAnalyzer = RetryAnalyzer.class)
	public void QA() throws IOException, InterruptedException {
		String category = "QA";

		linkedInPo.navigateToLinkedInJobPage();
		WaitUtils.applyRandomTimeout(basePo.getPage());
		linkedInPo.hideMessagingTab();
		// Screenshot directory setup
		Method method = new Object() {
		}.getClass().getEnclosingMethod();
		String testMethodName = method.getName();
		Path screenshotDir = FolderUtils.createScreenshotDirectory(portalName, testMethodName);
		// Data fetching and iteration through technology and location
		List<String> technologiesData = DataProviderUtils.getTechnologyData(category);
		for (int j = 0; j < technologiesData.size(); j++) {
			int count = 0;
			int screenshotCount = 0;
			linkedInPo.enterSearchKeywords(technologiesData.get(j));
			List<String> locationData = DataProviderUtils.getLocationData("Location");
			for (int k = 0; k < locationData.size(); k++) {
				System.out.println(technologiesData.get(j) + " - " + locationData.get(k));
				linkedInPo.enterLocation(locationData.get(k));

				linkedInPo.clickAllFiltersButton();

				linkedInPo.enterFilters();

				if (!linkedInPo.checkNoJobsFoundIsVisible()) {
					count = linkedInPo.clickAllElementandgetlink(screenshotDir, count);
					screenshotCount = linkedInPo.getScreenshotCount();
				} else {
					count = 0;
					screenshotCount = 0;
					System.out.println("Job Count: " + count);
				}
				System.out.println("Screenshot Count: " + screenshotCount);
				ExcelUtils.writeToExcel(portalName, category, technologiesData.get(j), locationData.get(k), count, screenshotCount);
			}
		}
	}

	@Test(priority = 10, retryAnalyzer = RetryAnalyzer.class)
	public void Amazon_AWS_One() throws IOException, InterruptedException {
		String category = "Amazon_AWS_One";

		linkedInPo.navigateToLinkedInJobPage();
		WaitUtils.applyRandomTimeout(basePo.getPage());
		linkedInPo.hideMessagingTab();
		// Screenshot directory setup
		Method method = new Object() {
		}.getClass().getEnclosingMethod();
		String testMethodName = method.getName();
		Path screenshotDir = FolderUtils.createScreenshotDirectory(portalName, testMethodName);
		// Data fetching and iteration through technology and location
		List<String> technologiesData = DataProviderUtils.getTechnologyData(category);
		for (int j = 0; j < technologiesData.size(); j++) {
			int count = 0;
			int screenshotCount = 0;
			linkedInPo.enterSearchKeywords(technologiesData.get(j));
			List<String> locationData = DataProviderUtils.getLocationData("Location");
			for (int k = 0; k < locationData.size(); k++) {
				System.out.println(technologiesData.get(j) + " - " + locationData.get(k));
				linkedInPo.enterLocation(locationData.get(k));

				linkedInPo.clickAllFiltersButton();

				linkedInPo.enterFilters();

				if (!linkedInPo.checkNoJobsFoundIsVisible()) {
					count = linkedInPo.clickAllElementandgetlink(screenshotDir, count);
					screenshotCount = linkedInPo.getScreenshotCount();
				} else {
					count = 0;
					screenshotCount = 0;
					System.out.println("Job Count: " + count);
				}
				System.out.println("Screenshot Count: " + screenshotCount);
				ExcelUtils.writeToExcel(portalName, category, technologiesData.get(j), locationData.get(k), count, screenshotCount);
			}
		}
	}

	@Test(priority = 11, retryAnalyzer = RetryAnalyzer.class)
	public void Amazon_AWS_Two() throws IOException, InterruptedException {
		String category = "Amazon_AWS_Two";

		linkedInPo.navigateToLinkedInJobPage();
		WaitUtils.applyRandomTimeout(basePo.getPage());
		linkedInPo.hideMessagingTab();
		// Screenshot directory setup
		Method method = new Object() {
		}.getClass().getEnclosingMethod();
		String testMethodName = method.getName();
		Path screenshotDir = FolderUtils.createScreenshotDirectory(portalName, testMethodName);
		// Data fetching and iteration through technology and location
		List<String> technologiesData = DataProviderUtils.getTechnologyData(category);
		for (int j = 0; j < technologiesData.size(); j++) {
			int count = 0;
			int screenshotCount = 0;
			linkedInPo.enterSearchKeywords(technologiesData.get(j));
			List<String> locationData = DataProviderUtils.getLocationData("Location");
			for (int k = 0; k < locationData.size(); k++) {
				System.out.println(technologiesData.get(j) + " - " + locationData.get(k));
				linkedInPo.enterLocation(locationData.get(k));

				linkedInPo.clickAllFiltersButton();

				linkedInPo.enterFilters();

				if (!linkedInPo.checkNoJobsFoundIsVisible()) {
					count = linkedInPo.clickAllElementandgetlink(screenshotDir, count);
					screenshotCount = linkedInPo.getScreenshotCount();
				} else {
					count = 0;
					screenshotCount = 0;
					System.out.println("Job Count: " + count);
				}
				System.out.println("Screenshot Count: " + screenshotCount);
				ExcelUtils.writeToExcel(portalName, category, technologiesData.get(j), locationData.get(k), count, screenshotCount);
			}
		}
	}

	@Test(priority = 12, retryAnalyzer = RetryAnalyzer.class)
	public void GCP_One() throws IOException, InterruptedException {
		String category = "GCP_One";

		linkedInPo.navigateToLinkedInJobPage();
		WaitUtils.applyRandomTimeout(basePo.getPage());
		linkedInPo.hideMessagingTab();
		// Screenshot directory setup
		Method method = new Object() {
		}.getClass().getEnclosingMethod();
		String testMethodName = method.getName();
		Path screenshotDir = FolderUtils.createScreenshotDirectory(portalName, testMethodName);
		// Data fetching and iteration through technology and location
		List<String> technologiesData = DataProviderUtils.getTechnologyData(category);
		for (int j = 0; j < technologiesData.size(); j++) {
			int count = 0;
			int screenshotCount = 0;
			linkedInPo.enterSearchKeywords(technologiesData.get(j));
			List<String> locationData = DataProviderUtils.getLocationData("Location");
			for (int k = 0; k < locationData.size(); k++) {
				System.out.println(technologiesData.get(j) + " - " + locationData.get(k));
				linkedInPo.enterLocation(locationData.get(k));

				linkedInPo.clickAllFiltersButton();

				linkedInPo.enterFilters();

				if (!linkedInPo.checkNoJobsFoundIsVisible()) {
					count = linkedInPo.clickAllElementandgetlink(screenshotDir, count);
					screenshotCount = linkedInPo.getScreenshotCount();
				} else {
					count = 0;
					screenshotCount = 0;
					System.out.println("Job Count: " + count);
				}
				System.out.println("Screenshot Count: " + screenshotCount);
				ExcelUtils.writeToExcel(portalName, category, technologiesData.get(j), locationData.get(k), count, screenshotCount);
			}
		}
	}

	@Test(priority = 13, retryAnalyzer = RetryAnalyzer.class)
	public void GCP_Two() throws IOException, InterruptedException {
		String category = "GCP_Two";

		linkedInPo.navigateToLinkedInJobPage();
		WaitUtils.applyRandomTimeout(basePo.getPage());
		linkedInPo.hideMessagingTab();
		// Screenshot directory setup
		Method method = new Object() {
		}.getClass().getEnclosingMethod();
		String testMethodName = method.getName();
		Path screenshotDir = FolderUtils.createScreenshotDirectory(portalName, testMethodName);
		// Data fetching and iteration through technology and location
		List<String> technologiesData = DataProviderUtils.getTechnologyData(category);
		for (int j = 0; j < technologiesData.size(); j++) {
			int count = 0;
			int screenshotCount = 0;
			linkedInPo.enterSearchKeywords(technologiesData.get(j));
			List<String> locationData = DataProviderUtils.getLocationData("Location");
			for (int k = 0; k < locationData.size(); k++) {
				System.out.println(technologiesData.get(j) + " - " + locationData.get(k));
				linkedInPo.enterLocation(locationData.get(k));

				linkedInPo.clickAllFiltersButton();

				linkedInPo.enterFilters();

				if (!linkedInPo.checkNoJobsFoundIsVisible()) {
					count = linkedInPo.clickAllElementandgetlink(screenshotDir, count);
					screenshotCount = linkedInPo.getScreenshotCount();
				} else {
					count = 0;
					screenshotCount = 0;
					System.out.println("Job Count: " + count);
				}
				System.out.println("Screenshot Count: " + screenshotCount);
				ExcelUtils.writeToExcel(portalName, category, technologiesData.get(j), locationData.get(k), count, screenshotCount);
			}
		}
	}

	@Test(priority = 14, retryAnalyzer = RetryAnalyzer.class)
	public void Azure() throws IOException, InterruptedException {
		String category = "Azure";

		linkedInPo.navigateToLinkedInJobPage();
		WaitUtils.applyRandomTimeout(basePo.getPage());
		linkedInPo.hideMessagingTab();
		// Screenshot directory setup
		Method method = new Object() {
		}.getClass().getEnclosingMethod();
		String testMethodName = method.getName();
		Path screenshotDir = FolderUtils.createScreenshotDirectory(portalName, testMethodName);
		// Data fetching and iteration through technology and location
		List<String> technologiesData = DataProviderUtils.getTechnologyData(category);
		for (int j = 0; j < technologiesData.size(); j++) {
			int count = 0;
			int screenshotCount = 0;
			linkedInPo.enterSearchKeywords(technologiesData.get(j));
			List<String> locationData = DataProviderUtils.getLocationData("Location");
			for (int k = 0; k < locationData.size(); k++) {
				System.out.println(technologiesData.get(j) + " - " + locationData.get(k));
				linkedInPo.enterLocation(locationData.get(k));

				linkedInPo.clickAllFiltersButton();

				linkedInPo.enterFilters();

				if (!linkedInPo.checkNoJobsFoundIsVisible()) {
					count = linkedInPo.clickAllElementandgetlink(screenshotDir, count);
					screenshotCount = linkedInPo.getScreenshotCount();
				} else {
					count = 0;
					screenshotCount = 0;
					System.out.println("Job Count: " + count);
				}
				System.out.println("Screenshot Count: " + screenshotCount);
				ExcelUtils.writeToExcel(portalName, category, technologiesData.get(j), locationData.get(k), count, screenshotCount);
			}
		}
	}

	@Test(priority = 15, retryAnalyzer = RetryAnalyzer.class)
	public void Others() throws IOException, InterruptedException {
		String category = "Others";

		linkedInPo.navigateToLinkedInJobPage();
		WaitUtils.applyRandomTimeout(basePo.getPage());
		linkedInPo.hideMessagingTab();
		// Screenshot directory setup
		Method method = new Object() {
		}.getClass().getEnclosingMethod();
		String testMethodName = method.getName();
		Path screenshotDir = FolderUtils.createScreenshotDirectory(portalName, testMethodName);
		// Data fetching and iteration through technology and location
		List<String> technologiesData = DataProviderUtils.getTechnologyData(category);
		for (int j = 0; j < technologiesData.size(); j++) {
			int count = 0;
			int screenshotCount = 0;
			linkedInPo.enterSearchKeywords(technologiesData.get(j));
			List<String> locationData = DataProviderUtils.getLocationData("Location");
			for (int k = 0; k < locationData.size(); k++) {
				System.out.println(technologiesData.get(j) + " - " + locationData.get(k));
				linkedInPo.enterLocation(locationData.get(k));

				linkedInPo.clickAllFiltersButton();

				linkedInPo.enterFilters();

				if (!linkedInPo.checkNoJobsFoundIsVisible()) {
					count = linkedInPo.clickAllElementandgetlink(screenshotDir, count);
					screenshotCount = linkedInPo.getScreenshotCount();
				} else {
					count = 0;
					screenshotCount = 0;
					System.out.println("Job Count: " + count);
				}
				System.out.println("Screenshot Count: " + screenshotCount);
				ExcelUtils.writeToExcel(portalName, category, technologiesData.get(j), locationData.get(k), count, screenshotCount);
			}
		}
	}

	@Test(priority = 16, retryAnalyzer = RetryAnalyzer.class)
	public void Pipeline() throws IOException, InterruptedException {
		String category = "Pipeline";

		linkedInPo.navigateToLinkedInJobPage();
		WaitUtils.applyRandomTimeout(basePo.getPage());
		linkedInPo.hideMessagingTab();
		// Screenshot directory setup
		Method method = new Object() {
		}.getClass().getEnclosingMethod();
		String testMethodName = method.getName();
		Path screenshotDir = FolderUtils.createScreenshotDirectory(portalName, testMethodName);
		// Data fetching and iteration through technology and location
		List<String> technologiesData = DataProviderUtils.getTechnologyData(category);
		for (int j = 0; j < technologiesData.size(); j++) {
			int count = 0;
			int screenshotCount = 0;
			linkedInPo.enterSearchKeywords(technologiesData.get(j));
			List<String> locationData = DataProviderUtils.getLocationData("Location");
			for (int k = 0; k < locationData.size(); k++) {
				System.out.println(technologiesData.get(j) + " - " + locationData.get(k));
				linkedInPo.enterLocation(locationData.get(k));

				linkedInPo.clickAllFiltersButton();

				linkedInPo.enterFilters();

				if (!linkedInPo.checkNoJobsFoundIsVisible()) {
					count = linkedInPo.clickAllElementandgetlink(screenshotDir, count);
					screenshotCount = linkedInPo.getScreenshotCount();
				} else {
					count = 0;
					screenshotCount = 0;
					System.out.println("Job Count: " + count);
				}
				System.out.println("Screenshot Count: " + screenshotCount);
				ExcelUtils.writeToExcel(portalName, category, technologiesData.get(j), locationData.get(k), count, screenshotCount);
			}
		}
	}
}