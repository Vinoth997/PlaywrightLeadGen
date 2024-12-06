package scripts;

import java.io.IOException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.microsoft.playwright.Page;

import listeners.TestListener;
import pageObjects.BasePo;
import pageObjects.WellFoundPo;

@Listeners(TestListener.class)
public class WellFoundScript {
	private BasePo basePo;
	private WellFoundPo wellFoundPo;
	
	private final String portalName = "WellFound";

	@BeforeMethod
	public void startup() {
		basePo = BasePo.getInstance();
		Page page = basePo.setup(portalName);
		wellFoundPo = new WellFoundPo(page);
	}

	@AfterMethod
	public void tearDown() {
		basePo.teardown();
	}

	@Test
	public void Frontend() throws IOException {
		wellFoundPo.navigateToLinkedInHomePage();
	}
}
