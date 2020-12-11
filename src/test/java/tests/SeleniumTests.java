package tests;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import tests.seleniumtask.AbstractTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class SeleniumTests extends AbstractTest {
    private static final String XPATH_TO_ELEMENTS_LIST = "//div[@data-apiary-widget-name='@MarketNode/SearchResults']/div/div/div/*";

    private static WebDriver driver;

    private static void waitForLoading(long milliseconds) {
        synchronized (driver) {
            try {
                driver.wait(milliseconds);
            } catch (InterruptedException ignored) {
            }
        }
    }

    @Test @Step("Тест для Ноутбуков")
    public void notebookSearch() {
        driver = getDriver();
        Actions actions = new Actions(driver);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //Переходим в нужный раздел
        driver.findElement(By.linkText("Маркет")).click();
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        //Меняем на свежеоткрытую вкладку маркета
        driver.switchTo().window(tabs.get(1));
        driver.findElement(By.linkText("Компьютеры")).click();
        driver.findElement(By.linkText("Ноутбуки")).click();

        Assertions.assertTrue(driver.getTitle().contains("Ноутбуки"), "Заголовок не содержит \"Ноутбуки\"");

        actions.moveToElement(driver.findElement(By.id("7893318_152722"))).click().perform();
        actions.moveToElement(driver.findElement(By.id("7893318_152981"))).click().perform();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        WebElement element = driver.findElement(By.className("vLDMfabyVq"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click()", element);

        element = driver.findElement(By.xpath("//button[contains(.,'Показывать по 12')]"));
        actions.moveToElement(element).perform();
        executor.executeScript("arguments[0].click()", element);

        //не смог найти индикатор прогрузки результатов поиска
        waitForLoading(3000);

        List<WebElement> searchResultsList = driver.findElements(By.xpath(XPATH_TO_ELEMENTS_LIST));
        Assertions.assertEquals(12, searchResultsList.size(), "Количество отображаемых элементов не равно 12");

        WebElement rememberedElement = searchResultsList.get(0);
        String nameOfRememberedElement = rememberedElement.findElement(By.xpath(".//descendant::h3/a[@title]")).getText();
        String costOfRememberedElement = rememberedElement.findElement(By.xpath(".//descendant::a/div/span/span[1]")).getText();

        driver.findElement(By.id("header-search")).sendKeys(nameOfRememberedElement);
        element = driver.findElement(By.xpath("//div[text() = 'Найти']"));
        executor.executeScript("arguments[0].click()", element);

        //не смог найти индикатор прогрузки результатов поиска
        waitForLoading(3000);

        Assertions.assertEquals(
                nameOfRememberedElement,
                driver.findElements(By.xpath(XPATH_TO_ELEMENTS_LIST)).get(0).findElement(By.xpath(".//descendant::h3/a[@title]")).getText(),
                "Имена не совпадают");
        Assertions.assertEquals(
                costOfRememberedElement,driver.findElements(By.xpath(XPATH_TO_ELEMENTS_LIST)).get(0).findElement(By.xpath(".//descendant::a/div/span/span[1]")).getText(),
                "Сумма не совпадает");
    }

    @Test @Step("Тест для Планшетов")
    public void tabletSearch() {
        driver = getDriver();
        Actions actions = new Actions(driver);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //Переходим в нужный раздел
        driver.findElement(By.linkText("Маркет")).click();
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        //Меняем на свежеоткрытую вкладку маркета
        driver.switchTo().window(tabs.get(1));
        driver.findElement(By.linkText("Компьютеры")).click();
        driver.findElement(By.linkText("Планшеты")).click();

        Assertions.assertTrue(driver.getTitle().contains("Планшеты"), "Заголовок не содержит \"Планшеты\"");

        WebElement price_min = driver.findElement(By.id("glpricefrom"));
        price_min.sendKeys("20000");

        waitForLoading(3000);

        WebElement price_max = driver.findElement(By.id("glpriceto"));
        price_max.sendKeys("25000");

        waitForLoading(3000);

        actions.moveToElement(driver.findElement(By.id("7893318_153043"))).click().perform();
        actions.moveToElement(driver.findElement(By.id("7893318_459710"))).click().perform();

        waitForLoading(3000);

        List<WebElement> searchResultsList = driver.findElements(By.xpath(XPATH_TO_ELEMENTS_LIST));

        for (WebElement webElement : searchResultsList) {
            int intValue = Integer.parseInt(webElement.findElement(By.xpath(".//descendant::a/div/span/span[1]")).getText().replaceAll(" ", ""));
            System.out.println(intValue);
            Assertions.assertTrue(intValue >= 20000 && intValue <= 25000, "Одна из цен не совпала с ожидаемой: " + intValue);
        }
    }
}