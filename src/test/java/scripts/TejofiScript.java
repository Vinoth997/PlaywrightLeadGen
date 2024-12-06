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

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;

import helpers.DataProviderUtils;
import helpers.ExcelUtils;
import helpers.FolderUtils;
import listeners.TestListener;
import pageObjects.BasePo;
import pageObjects.TejofiPo;
import retryAnalyser.RetryAnalyzer;

@Listeners(TestListener.class)

public class TejofiScript {
	private BasePo basePo;
	private TejofiPo tejofiPo;
	
	private final String portalName = "Tejofi";
	private final String techData = "hubsstafftalent";
	
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
	    BrowserContext context = page.context();
	    tejofiPo = new TejofiPo(page, context);
	}

	@AfterMethod
	public void tearDown() {
		basePo.teardown();
	}
	
	@Test(priority = 0, retryAnalyzer = RetryAnalyzer.class)
	public void FrontEnd() throws IOException {
		String category = "Frontend";
		Method method = new Object() {
		}.getClass().getEnclosingMethod();
		String testMethodName = method.getName();
		Path screenshotDir = FolderUtils.createScreenshotDirectory(portalName, testMethodName);
		tejofiPo.navigateToHubstafftalentHomePage();
		tejofiPo.clickViewJobs();
		tejofiPo.selectPreviousDate();
		List<String> technologiesData = DataProviderUtils.getTechnologyData(techData, category);
		for (int i = 0; i < technologiesData.size(); i++) {
			int count = 0;
			int screenshotCount = 0;
			System.out.println("--" + technologiesData.get(i));
			count = tejofiPo.selectTechnology(technologiesData.get(i), screenshotDir, count);
			screenshotCount = tejofiPo.getScreenshotCount();
			
			System.out.println("Screenshot Count: " + screenshotCount);
			ExcelUtils.writeToExcel(portalName, category, technologiesData.get(i), count, screenshotCount);
		}
	}
	
	@Test(priority = 1, retryAnalyzer = RetryAnalyzer.class)
	public void BackEndOne() throws IOException {
		String category = "BackendOne";
		Method method = new Object() {
		}.getClass().getEnclosingMethod();
		String testMethodName = method.getName();
		Path screenshotDir = FolderUtils.createScreenshotDirectory(portalName, testMethodName);
		tejofiPo.navigateToHubstafftalentHomePage();
		tejofiPo.clickViewJobs();
		tejofiPo.selectPreviousDate();
		List<String> technologiesData = DataProviderUtils.getTechnologyData(techData, category);
		for (int i = 0; i < technologiesData.size(); i++) {
			int count = 0;
			int screenshotCount = 0;
			System.out.println("--" + technologiesData.get(i));
			count = tejofiPo.selectTechnology(technologiesData.get(i), screenshotDir, count);
			screenshotCount = tejofiPo.getScreenshotCount();
			
			System.out.println("Screenshot Count: " + screenshotCount);
			ExcelUtils.writeToExcel(portalName, category, technologiesData.get(i), count, screenshotCount);
		}
	}
	
	@Test(priority = 2, retryAnalyzer = RetryAnalyzer.class)
	public void BackEndTwo() throws IOException {
		String category = "BackendTwo";
		Method method = new Object() {
		}.getClass().getEnclosingMethod();
		String testMethodName = method.getName();
		Path screenshotDir = FolderUtils.createScreenshotDirectory(portalName, testMethodName);
		tejofiPo.navigateToHubstafftalentHomePage();
		tejofiPo.clickViewJobs();
		tejofiPo.selectPreviousDate();
		List<String> technologiesData = DataProviderUtils.getTechnologyData(techData, category);
		for (int i = 0; i < technologiesData.size(); i++) {
			int count = 0;
			int screenshotCount = 0;
			System.out.println("--" + technologiesData.get(i));
			count = tejofiPo.selectTechnology(technologiesData.get(i), screenshotDir, count);
			screenshotCount = tejofiPo.getScreenshotCount();
			
			System.out.println("Screenshot Count: " + screenshotCount);
			ExcelUtils.writeToExcel(portalName, category, technologiesData.get(i), count, screenshotCount);
		}
	}
	
	@Test(priority = 3, retryAnalyzer = RetryAnalyzer.class)
	public void BackEndThree() throws IOException {
		String category = "BackendThree";
		Method method = new Object() {
		}.getClass().getEnclosingMethod();
		String testMethodName = method.getName();
		Path screenshotDir = FolderUtils.createScreenshotDirectory(portalName, testMethodName);
		tejofiPo.navigateToHubstafftalentHomePage();
		tejofiPo.clickViewJobs();
		tejofiPo.selectPreviousDate();
		List<String> technologiesData = DataProviderUtils.getTechnologyData(techData, category);
		for (int i = 0; i < technologiesData.size(); i++) {
			int count = 0;
			int screenshotCount = 0;
			System.out.println("--" + technologiesData.get(i));
			count = tejofiPo.selectTechnology(technologiesData.get(i), screenshotDir, count);
			screenshotCount = tejofiPo.getScreenshotCount();
			System.out.println("Job Count: " + count);
			System.out.println("Screenshot Count: " + screenshotCount);
			ExcelUtils.writeToExcel(portalName, category, technologiesData.get(i), count, screenshotCount);
		}
	}
	
	@Test(priority = 4, retryAnalyzer = RetryAnalyzer.class)
	public void BackEndFour() throws IOException {
		String category = "BackendFour";
		Method method = new Object() {
		}.getClass().getEnclosingMethod();
		String testMethodName = method.getName();
		Path screenshotDir = FolderUtils.createScreenshotDirectory(portalName, testMethodName);
		tejofiPo.navigateToHubstafftalentHomePage();
		tejofiPo.clickViewJobs();
		tejofiPo.selectPreviousDate();
		List<String> technologiesData = DataProviderUtils.getTechnologyData(techData, category);
		for (int i = 0; i < technologiesData.size(); i++) {
			int count = 0;
			int screenshotCount = 0;
			System.out.println("--" + technologiesData.get(i));
			count = tejofiPo.selectTechnology(technologiesData.get(i), screenshotDir, count);
			screenshotCount = tejofiPo.getScreenshotCount();
			System.out.println("Job Count: " + count);
			System.out.println("Screenshot Count: " + screenshotCount);
			ExcelUtils.writeToExcel(portalName, category, technologiesData.get(i), count, screenshotCount);
		}
	}
	
	@Test(priority = 5, retryAnalyzer = RetryAnalyzer.class)
	public void BackEndFive() throws IOException {
		String category = "BackendFive";
		Method method = new Object() {
		}.getClass().getEnclosingMethod();
		String testMethodName = method.getName();
		Path screenshotDir = FolderUtils.createScreenshotDirectory(portalName, testMethodName);
		tejofiPo.navigateToHubstafftalentHomePage();
		tejofiPo.clickViewJobs();
		tejofiPo.selectPreviousDate();
		List<String> technologiesData = DataProviderUtils.getTechnologyData(techData, category);
		for (int i = 0; i < technologiesData.size(); i++) {
			int count = 0;
			int screenshotCount = 0;
			System.out.println("--" + technologiesData.get(i));
			count = tejofiPo.selectTechnology(technologiesData.get(i), screenshotDir, count);
			screenshotCount = tejofiPo.getScreenshotCount();
			System.out.println("Job Count: " + count);
			System.out.println("Job Count: " + count);
			System.out.println("Screenshot Count: " + screenshotCount);
			ExcelUtils.writeToExcel(portalName, category, technologiesData.get(i), count, screenshotCount);
		}
	}
	
	@Test(priority = 6, retryAnalyzer = RetryAnalyzer.class)
	public void AI_ML() throws IOException {
		String category = "AI_ML";
		Method method = new Object() {
		}.getClass().getEnclosingMethod();
		String testMethodName = method.getName();
		Path screenshotDir = FolderUtils.createScreenshotDirectory(portalName, testMethodName);
		tejofiPo.navigateToHubstafftalentHomePage();
		tejofiPo.clickViewJobs();
		tejofiPo.selectPreviousDate();
		List<String> technologiesData = DataProviderUtils.getTechnologyData(techData, category);
		for (int i = 0; i < technologiesData.size(); i++) {
			int count = 0;
			int screenshotCount = 0;
			System.out.println("--" + technologiesData.get(i));
			count = tejofiPo.selectTechnology(technologiesData.get(i), screenshotDir, count);
			screenshotCount = tejofiPo.getScreenshotCount();
			System.out.println("Job Count: " + count);
			System.out.println("Screenshot Count: " + screenshotCount);
			ExcelUtils.writeToExcel(portalName, category, technologiesData.get(i), count, screenshotCount);
		}
	}
	
	@Test(priority = 7, retryAnalyzer = RetryAnalyzer.class)
	public void Mobile_App() throws IOException {
		String category = "Mobile_App";
		Method method = new Object() {
		}.getClass().getEnclosingMethod();
		String testMethodName = method.getName();
		Path screenshotDir = FolderUtils.createScreenshotDirectory(portalName, testMethodName);
		tejofiPo.navigateToHubstafftalentHomePage();
		tejofiPo.clickViewJobs();
		tejofiPo.selectPreviousDate();
		List<String> technologiesData = DataProviderUtils.getTechnologyData(techData, category);
		for (int i = 0; i < technologiesData.size(); i++) {
			int count = 0;
			int screenshotCount = 0;
			System.out.println("--" + technologiesData.get(i));
			count = tejofiPo.selectTechnology(technologiesData.get(i), screenshotDir, count);
			screenshotCount = tejofiPo.getScreenshotCount();
			System.out.println("Job Count: " + count);
			System.out.println("Screenshot Count: " + screenshotCount);
			ExcelUtils.writeToExcel(portalName, category, technologiesData.get(i), count, screenshotCount);
		}
	}
	
	@Test(priority = 8, retryAnalyzer = RetryAnalyzer.class)
	public void Design_Tools() throws IOException {
		String category = "Design_Tools";
		Method method = new Object() {
		}.getClass().getEnclosingMethod();
		String testMethodName = method.getName();
		Path screenshotDir = FolderUtils.createScreenshotDirectory(portalName, testMethodName);
		tejofiPo.navigateToHubstafftalentHomePage();
		tejofiPo.clickViewJobs();
		tejofiPo.selectPreviousDate();
		List<String> technologiesData = DataProviderUtils.getTechnologyData(techData, category);
		for (int i = 0; i < technologiesData.size(); i++) {
			int count = 0;
			int screenshotCount = 0;
			System.out.println("--" + technologiesData.get(i));
			count = tejofiPo.selectTechnology(technologiesData.get(i), screenshotDir, count);
			screenshotCount = tejofiPo.getScreenshotCount();
			System.out.println("Job Count: " + count);
			System.out.println("Screenshot Count: " + screenshotCount);
			ExcelUtils.writeToExcel(portalName, category, technologiesData.get(i), count, screenshotCount);
		}
	}
	
	@Test(priority = 9, retryAnalyzer = RetryAnalyzer.class)
	public void QA() throws IOException {
		String category = "QA";
		Method method = new Object() {
		}.getClass().getEnclosingMethod();
		String testMethodName = method.getName();
		Path screenshotDir = FolderUtils.createScreenshotDirectory(portalName, testMethodName);
		tejofiPo.navigateToHubstafftalentHomePage();
		tejofiPo.clickViewJobs();
		tejofiPo.selectPreviousDate();
		List<String> technologiesData = DataProviderUtils.getTechnologyData(techData, category);
		for (int i = 0; i < technologiesData.size(); i++) {
			int count = 0;
			int screenshotCount = 0;
			System.out.println("--" + technologiesData.get(i));
			count = tejofiPo.selectTechnology(technologiesData.get(i), screenshotDir, count);
			screenshotCount = tejofiPo.getScreenshotCount();
			System.out.println("Job Count: " + count);
			System.out.println("Screenshot Count: " + screenshotCount);
			ExcelUtils.writeToExcel(portalName, category, technologiesData.get(i), count, screenshotCount);
		}
	}
	
	@Test(priority = 10, retryAnalyzer = RetryAnalyzer.class)
	public void Amazon_AWS_One() throws IOException {
		String category = "Amazon_AWS_One";
		Method method = new Object() {
		}.getClass().getEnclosingMethod();
		String testMethodName = method.getName();
		Path screenshotDir = FolderUtils.createScreenshotDirectory(portalName, testMethodName);
		tejofiPo.navigateToHubstafftalentHomePage();
		tejofiPo.clickViewJobs();
		tejofiPo.selectPreviousDate();
		List<String> technologiesData = DataProviderUtils.getTechnologyData(techData, category);
		for (int i = 0; i < technologiesData.size(); i++) {
			int count = 0;
			int screenshotCount = 0;
			System.out.println("--" + technologiesData.get(i));
			count = tejofiPo.selectTechnology(technologiesData.get(i), screenshotDir, count);
			screenshotCount = tejofiPo.getScreenshotCount();
			System.out.println("Job Count: " + count);
			System.out.println("Screenshot Count: " + screenshotCount);
			ExcelUtils.writeToExcel(portalName, category, technologiesData.get(i), count, screenshotCount);
		}
	}
	
	@Test(priority = 11, retryAnalyzer = RetryAnalyzer.class)
	public void Amazon_AWS_Two() throws IOException {
		String category = "Amazon_AWS_Two";
		Method method = new Object() {
		}.getClass().getEnclosingMethod();
		String testMethodName = method.getName();
		Path screenshotDir = FolderUtils.createScreenshotDirectory(portalName, testMethodName);
		tejofiPo.navigateToHubstafftalentHomePage();
		tejofiPo.clickViewJobs();
		tejofiPo.selectPreviousDate();
		List<String> technologiesData = DataProviderUtils.getTechnologyData(techData, category);
		for (int i = 0; i < technologiesData.size(); i++) {
			int count = 0;
			int screenshotCount = 0;
			System.out.println("--" + technologiesData.get(i));
			count = tejofiPo.selectTechnology(technologiesData.get(i), screenshotDir, count);
			screenshotCount = tejofiPo.getScreenshotCount();
			System.out.println("Job Count: " + count);
			System.out.println("Screenshot Count: " + screenshotCount);
			ExcelUtils.writeToExcel(portalName, category, technologiesData.get(i), count, screenshotCount);
		}
	}
	
	@Test(priority = 12, retryAnalyzer = RetryAnalyzer.class)
	public void GCP_One() throws IOException {
		String category = "GCP_One";
		Method method = new Object() {
		}.getClass().getEnclosingMethod();
		String testMethodName = method.getName();
		Path screenshotDir = FolderUtils.createScreenshotDirectory(portalName, testMethodName);
		tejofiPo.navigateToHubstafftalentHomePage();
		tejofiPo.clickViewJobs();
		tejofiPo.selectPreviousDate();
		List<String> technologiesData = DataProviderUtils.getTechnologyData(techData, category);
		for (int i = 0; i < technologiesData.size(); i++) {
			int count = 0;
			int screenshotCount = 0;
			System.out.println("--" + technologiesData.get(i));
			count = tejofiPo.selectTechnology(technologiesData.get(i), screenshotDir, count);
			screenshotCount = tejofiPo.getScreenshotCount();
			System.out.println("Job Count: " + count);
			System.out.println("Screenshot Count: " + screenshotCount);
			ExcelUtils.writeToExcel(portalName, category, technologiesData.get(i), count, screenshotCount);
		}
	}
	
	@Test(priority = 13, retryAnalyzer = RetryAnalyzer.class)
	public void GCP_Two() throws IOException {
		String category = "GCP_Two";
		Method method = new Object() {
		}.getClass().getEnclosingMethod();
		String testMethodName = method.getName();
		Path screenshotDir = FolderUtils.createScreenshotDirectory(portalName, testMethodName);
		tejofiPo.navigateToHubstafftalentHomePage();
		tejofiPo.clickViewJobs();
		tejofiPo.selectPreviousDate();
		List<String> technologiesData = DataProviderUtils.getTechnologyData(techData, category);
		for (int i = 0; i < technologiesData.size(); i++) {
			int count = 0;
			int screenshotCount = 0;
			System.out.println("--" + technologiesData.get(i));
			count = tejofiPo.selectTechnology(technologiesData.get(i), screenshotDir, count);
			screenshotCount = tejofiPo.getScreenshotCount();
			System.out.println("Job Count: " + count);
			System.out.println("Screenshot Count: " + screenshotCount);
			ExcelUtils.writeToExcel(portalName, category, technologiesData.get(i), count, screenshotCount);
		}
	}
	
	@Test(priority = 14, retryAnalyzer = RetryAnalyzer.class)
	public void Azure() throws IOException {
		String category = "Azure";
		Method method = new Object() {
		}.getClass().getEnclosingMethod();
		String testMethodName = method.getName();
		Path screenshotDir = FolderUtils.createScreenshotDirectory(portalName, testMethodName);
		tejofiPo.navigateToHubstafftalentHomePage();
		tejofiPo.clickViewJobs();
		tejofiPo.selectPreviousDate();
		List<String> technologiesData = DataProviderUtils.getTechnologyData(techData, category);
		for (int i = 0; i < technologiesData.size(); i++) {
			int count = 0;
			int screenshotCount = 0;
			System.out.println("--" + technologiesData.get(i));
			count = tejofiPo.selectTechnology(technologiesData.get(i), screenshotDir, count);
			screenshotCount = tejofiPo.getScreenshotCount();
			System.out.println("Job Count: " + count);
			System.out.println("Screenshot Count: " + screenshotCount);
			ExcelUtils.writeToExcel(portalName, category, technologiesData.get(i), count, screenshotCount);
		}
	}
	
	@Test(priority = 15, retryAnalyzer = RetryAnalyzer.class)
	public void Others() throws IOException {
		String category = "Others";
		Method method = new Object() {
		}.getClass().getEnclosingMethod();
		String testMethodName = method.getName();
		Path screenshotDir = FolderUtils.createScreenshotDirectory(portalName, testMethodName);
		tejofiPo.navigateToHubstafftalentHomePage();
		tejofiPo.clickViewJobs();
		tejofiPo.selectPreviousDate();
		List<String> technologiesData = DataProviderUtils.getTechnologyData(techData, category);
		for (int i = 0; i < technologiesData.size(); i++) {
			int count = 0;
			int screenshotCount = 0;
			System.out.println("--" + technologiesData.get(i));
			count = tejofiPo.selectTechnology(technologiesData.get(i), screenshotDir, count);
			screenshotCount = tejofiPo.getScreenshotCount();
			System.out.println("Job Count: " + count);
			System.out.println("Screenshot Count: " + screenshotCount);
			ExcelUtils.writeToExcel(portalName, category, technologiesData.get(i), count, screenshotCount);
		}
	}
	
	@Test(priority = 16, retryAnalyzer = RetryAnalyzer.class)
	public void Pipeline() throws IOException {
		String category = "Pipeline";
		Method method = new Object() {
		}.getClass().getEnclosingMethod();
		String testMethodName = method.getName();
		Path screenshotDir = FolderUtils.createScreenshotDirectory(portalName, testMethodName);
		tejofiPo.navigateToHubstafftalentHomePage();
		tejofiPo.clickViewJobs();
		tejofiPo.selectPreviousDate();
		List<String> technologiesData = DataProviderUtils.getTechnologyData(techData, category);
		for (int i = 0; i < technologiesData.size(); i++) {
			int count = 0;
			int screenshotCount = 0;
			System.out.println("--" + technologiesData.get(i));
			count = tejofiPo.selectTechnology(technologiesData.get(i), screenshotDir, count);
			screenshotCount = tejofiPo.getScreenshotCount();
			System.out.println("Job Count: " + count);
			System.out.println("Screenshot Count: " + screenshotCount);
			ExcelUtils.writeToExcel(portalName, category, technologiesData.get(i), count, screenshotCount);
		}
	}
}
