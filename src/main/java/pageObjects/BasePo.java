package pageObjects;

import java.nio.file.Paths;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
 
public class BasePo {
    private static BasePo instance;

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    private boolean setHeadless = true;

    // Private constructor to prevent instantiation
    private BasePo() {
    }

    // Public method to get the singleton instance
    public static BasePo getInstance() {
        if (instance == null) {
            instance = new BasePo();
        }
        return instance;
    }

    public Page setup(String cookie) {
        if (playwright == null) {
            playwright = Playwright.create();
        }
        if (browser == null) {
            browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(setHeadless));
        }
        context = browser.newContext(new Browser.NewContextOptions().setStorageStatePath(Paths.get(cookie.toLowerCase() + "-cookies.json")));
        page = context.newPage();
        return page;
    }

    public Page getPage() {
        return page;
    }

    public void teardown() {
        if (context != null) {
            context.close();
        }
        if (page != null) {
            page.close();
        }
        if (browser != null) {
            browser.close();
            browser = null; // Avoid reuse of a closed browser
        }
        if (playwright != null) {
            playwright.close();
            playwright = null; // Avoid reuse of a closed playwright instance
        }
    }
}
