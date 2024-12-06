package helpers;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator.PressSequentiallyOptions;

public class ActionUtils {


    /**
     * Clicks on an element specified by the Locator.
     *
     * @param locator The Locator of the element to click.
     */
    public static void clickElement(Locator locator) {
        locator.click();
    }

    /**
     * Double-clicks on an element specified by the Locator.
     *
     * @param locator The Locator of the element to double-click.
     */
    public  static void doubleClickElement(Locator locator) {
        locator.dblclick();
    }

    /**
     * Types text into a text input field specified by the Locator.
     *
     * @param locator The Locator of the input element.
     * @param text The text to enter into the input field.
     */
    public static void typeText(Locator locator, String text) {
        locator.fill(text);
    }

    /**
     * Hovers over the element specified by the Locator.
     *
     * @param locator The Locator of the element to hover over.
     */
    public static void hoverOverElement(Locator locator) {
        locator.hover();
    }

    /**
     * Drags an element from the source Locator to the target Locator.
     *
     * @param sourceLocator The Locator of the element to drag.
     * @param targetLocator The Locator of the element to drop onto.
     */
    public static void dragAndDrop(Locator sourceLocator, Locator targetLocator) {
        sourceLocator.dragTo(targetLocator);
    }

    /**
     * Selects an option from a dropdown specified by the Locator.
     *
     * @param locator The Locator of the dropdown element.
     * @param value The value of the option to select.
     */
    public static void selectOption(Locator locator, String value) {
        locator.selectOption(value);
    }

    /**
     * Retrieves text content from an element specified by the Locator.
     *
     * @param locator The Locator of the element.
     * @return The text content of the element.
     */
    public String getText(Locator locator) {
        return locator.textContent();
    }

    /**
     * Checks a checkbox or radio button specified by the Locator.
     *
     * @param locator The Locator of the checkbox or radio button.
     */
    public static void checkElement(Locator locator) {
        locator.check();
    }

    /**
     * Unchecks a checkbox specified by the Locator.
     *
     * @param locator The Locator of the checkbox to uncheck.
     */
    public static void uncheckElement(Locator locator) {
        locator.uncheck();
    }
    
    /**
     * Types the given text into the specified locator, simulating human typing with a pause between each character.
     *
     * @param locator The Locator object where the text should be typed.
     * @param text    The text to type into the locator.
     * @param delay   The delay in milliseconds between typing each character.
     */
    public static void typeText(Locator locator, String text, int delay) {
    	locator.clear();
    	locator.pressSequentially(text, new PressSequentiallyOptions().setDelay(delay));
    }
    
    public static void hoverAndForceClick(Page page, Locator element) {	        
        // Perform mouse hover
        element.hover();
        
        // Force click the element
        element.click(new Locator.ClickOptions().setForce(true));
    }
    
    public static void forceClick(Locator element) {	
        // Force click the element
        element.click(new Locator.ClickOptions().setForce(true));
    }
    
    /**
     * Presses the Enter key after typing the text
     */
    public static void typeTextAndPressEnter(Locator locator, String text, int delay) {
        locator.clear();
        locator.pressSequentially(text, new Locator.PressSequentiallyOptions().setDelay(delay));
        locator.press("Enter");
    }

    
}
