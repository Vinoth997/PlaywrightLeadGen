package pageObjects;

import com.microsoft.playwright.Page;

import helpers.ActionUtils;
import helpers.DataProviderUtils;

public class RemoterocketshipPo {

	
	 private Page page;

		public RemoterocketshipPo(Page page) {
			this.page = page;
		}

		// Actions
		public void navigateToLinkedInHomePage() {
			String linkedinUrl = DataProviderUtils.getUrlData("remoterocketship");
			page.navigate(linkedinUrl);
			page.pause();
		}

		public void enterJobTitle() {
			ActionUtils.clickElement(page.locator("[data-test-id=\"home-hero-sign-in-cta\"]"));
		}
		
		
//		public void 

}