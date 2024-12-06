package listeners;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.microsoft.playwright.Page;

import pageObjects.BasePo;

public class TestListener implements ITestListener {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // No-argument constructor
    public TestListener() {
    }

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Starting test: " + result.getMethod().getMethodName() + " at " + getCurrentTime());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test passed: " + result.getMethod().getMethodName() + " at " + getCurrentTime());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("Test failed: " + result.getMethod().getMethodName() + " at " + getCurrentTime());

        Path screenshotDir = Paths.get(System.getProperty("user.dir"), "FailuresScreenshot");
        try {
            if (!Files.exists(screenshotDir)) {
                Files.createDirectories(screenshotDir);
            }

            String screenshotFileName = result.getMethod().getMethodName() + "_" + getCurrentTime();
            screenshotFileName = screenshotFileName.replaceAll("[^a-zA-Z0-9_\\-]", "_") + ".png";
            Path screenshotPath = screenshotDir.resolve(screenshotFileName);

            Page page = BasePo.getInstance().getPage(); // Get the singleton instance
            if (page != null) {
                page.screenshot(new Page.ScreenshotOptions().setPath(screenshotPath).setFullPage(true).setTimeout(30000));
                System.out.println("Screenshot saved: " + screenshotPath.toAbsolutePath());
            } else {
                System.err.println("Page is null. Cannot take screenshot.");
            }
        } catch (Exception e) {
            System.err.println("Error while saving screenshot: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("Test skipped: " + result.getMethod().getMethodName() + " at " + getCurrentTime());
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("All tests finished at " + getCurrentTime());
    }

    private String getCurrentTime() {
        return sdf.format(new Date());
    }
}

