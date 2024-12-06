package helpers;


import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.time.Duration;
import java.util.Random;

public class WaitUtils {

    /**
     * Waits until an element specified by the Locator is visible on the page.
     *
     * @param locator The Locator of the element.
     * @param timeoutInSeconds The maximum time to wait in seconds.
     */
    public static void waitForElementVisible(Locator locator, int timeoutInSeconds) {
        locator.waitFor(new Locator.WaitForOptions().setTimeout(Duration.ofSeconds(timeoutInSeconds).toMillis()).setState(WaitForSelectorState.VISIBLE));
    }

    /**
     * Waits until an element specified by the Locator is hidden or removed from the page.
     *
     * @param locator The Locator of the element.
     * @param timeoutInSeconds The maximum time to wait in seconds.
     */
    public void waitForElementHidden(Locator locator, int timeoutInSeconds) {
        locator.waitFor(new Locator.WaitForOptions().setTimeout(Duration.ofSeconds(timeoutInSeconds).toMillis()).setState(WaitForSelectorState.HIDDEN));
    }

    /**
     * Applies a random timeout from a given set of timeouts.
     *
     * @param page The Playwright Page object to apply the timeout on.
     */
    public static void applyRandomTimeout(Page page) {
        int[] timeouts = {2000, 3000, 4000, 5000};
        int randomTimeout = getRandomTimeout(timeouts);
//        System.out.println("Applying timeout: " + randomTimeout + " ms");
        page.waitForTimeout(randomTimeout);
    }

    /**
     * Selects a random timeout from the given array of timeouts.
     *
     * @param timeouts The array of timeouts to choose from.
     * @return A randomly selected timeout value.
     */
    private static int getRandomTimeout(int[] timeouts) {
        Random random = new Random();
        int index = random.nextInt(timeouts.length);
        return timeouts[index];
    }

}
