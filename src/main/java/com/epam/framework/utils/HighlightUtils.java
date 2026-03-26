package com.epam.framework.utils;

import com.epam.framework.core.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

/**
 * Static highlight utility — kept for backwards compatibility.
 * For new code, prefer injecting the driver directly via HighlightDecorator,
 * which avoids the DIP violation present here (static access to DriverManager).
 */
public class HighlightUtils {

    private static final Logger log = LogManager.getLogger(HighlightUtils.class);
    private static final String HIGHLIGHT_STYLE = "background: yellow; border: 2px solid red;";
    private static final int HIGHLIGHT_DURATION_MS = 300;

    private HighlightUtils() {}

    public static void highlight(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        String originalStyle = element.getAttribute("style");
        log.debug("Highlighting element: [{}]", element.getTagName());
        try {
            js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
                    element, HIGHLIGHT_STYLE);
            Thread.sleep(HIGHLIGHT_DURATION_MS);
            js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
                    element, originalStyle == null ? "" : originalStyle);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("Highlight interrupted for element: [{}]", element.getTagName());
        }
    }
}
