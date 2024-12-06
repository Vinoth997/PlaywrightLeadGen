package scripts;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.microsoft.playwright.Page;

import helpers.DataProviderUtils;
import helpers.FolderUtils;
import helpers.WaitUtils;
import listeners.TestListener;
import pageObjects.BasePo;
import pageObjects.RemoterocketshipPo;

@Listeners(TestListener.class)
public class RemoteRocketshipScript {
	
	private BasePo basePo;
	private RemoterocketshipPo remoterocketshipPo ;
	
	private final String portalName = "RemoteRocketShip";

	@BeforeMethod
	public void startup() {
		basePo = BasePo.getInstance();
		Page page = basePo.setup(portalName);
		remoterocketshipPo = new RemoterocketshipPo(page);
	}

	@AfterMethod
	public void tearDown() {
		basePo.teardown();
	}
	
	@Test
	public void Frontend() throws IOException {
		remoterocketshipPo.navigateToLinkedInHomePage();
//		remoterocketshipPo.clickSignInWithEmail();
//		remoterocketshipPo.enterEmailAndPassword("1");
//		remoterocketshipPo.clickSignInButton();
//		remoterocketshipPo.navigateToLinkedInJobPage();
//		WaitUtils.applyRandomTimeout(basePo.getPage());
//		remoterocketshipPo.hideMessagingTab();
//
//		// Create the folder only once before the loop starts
//		Method method = new Object() {
//		}.getClass().getEnclosingMethod();
//		String testMethodName = method.getName();
//		// Create the screenshot directory with the test method name
//		Path screenshotDir = FolderUtils.createScreenshotDirectory(testMethodName);
//
//		List<String> technologiesData = DataProviderUtils.getTechnologyData("Frontend");
//		int count = 0;
//		for (int j = 0; j < technologiesData.size(); j++) {
//			
//			remoterocketshipPo.enterSearchKeywords(technologiesData.get(j));
//			List<String> locationData = DataProviderUtils.getLocationData("Location");
//			for (int k = 0; k < locationData.size(); k++) {
//				System.out.println(technologiesData.get(j) +" - "+ locationData.get(k));
//				remoterocketshipPo.enterLocation(locationData.get(k));
//
//				remoterocketshipPo.clickAllFiltersButton();
//
//				if (!remoterocketshipPo.checkPast24HoursIsChecked()) {
//					remoterocketshipPo.enterFilters();
//				} else {
//					remoterocketshipPo.clickCloseFilterButton();
//				}
//
//				if (!remoterocketshipPo.checkNoJobsFoundIsVisible()) {
//					remoterocketshipPo.clickAllElementandgetlink(screenshotDir, count);
//				}
//			}
//		}
	}




}