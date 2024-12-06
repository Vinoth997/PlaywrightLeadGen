package pageObjects;

import com.microsoft.playwright.Page;

public class WellFoundPo {
	 private Page page;

		public WellFoundPo(Page page) {
			this.page = page;
		}

		// Actions
		public void navigateToLinkedInHomePage() {
//			String linkedinUrl = DataProviderUtils.getUrlData("WellFound");
			page.navigate("https://javascriptjob.xyz/");
			page.pause();
		}

	
}
