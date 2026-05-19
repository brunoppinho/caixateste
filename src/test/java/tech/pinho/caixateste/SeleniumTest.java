package tech.pinho.caixateste;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
https://chromedriver.storage.googleapis.com/index.html

 */
public class SeleniumTest {

    public WebDriver driver;


    @Test
    void test() {
        driver = new ChromeDriver();
        driver.get("https://www.google.com/");

        String title = driver.getTitle();
        assertEquals("Google", title);
        driver.quit();
    }
}
