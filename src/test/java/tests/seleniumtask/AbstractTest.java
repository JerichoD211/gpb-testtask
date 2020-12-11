package tests.seleniumtask;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;

public class AbstractTest {
    private WebDriver driver;
    private static String driverPath = "C:\\drivers\\";
    private static String filePath = new File("").getAbsolutePath() + "\\src\\test\\java\\";

    @BeforeEach
    public void setUp() {
       driver = initChromeDriver();
    }

    private WebDriver initChromeDriver() {
        System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://yandex.ru/");
        return driver;
    }

    public WebDriver getDriver() {
        return driver;
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

}
