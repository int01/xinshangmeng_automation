import com.beust.jcommander.internal.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author minhongwei
 * @DateTime 2023/6/6 15:51 星期二
 * @Description: TODO
 */
public class ttt {

    private static WebDriver openCharm() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        // options.addArguments("--user-data-dir=/Users/minhongwei/Library/Application Support/Google/Chrome/");
        options.addArguments("disable-infobars");
        options.addArguments("--disable-blink-features");
        options.addArguments("--disable-blink-features=AutomationControlled");
        //设置ExperimentalOption
        List<String> excludeSwitches = Lists.newArrayList("enable-automation");
        options.setExperimentalOption("excludeSwitches", excludeSwitches);
        options.setExperimentalOption("useAutomationExtension", false);

        ChromeDriver driver = new ChromeDriver(options);
        //修改window.navigator.webdirver=undefined，防机器人识别机制
        Map<String, Object> command = new HashMap<>();
        command.put("source", "Object.defineProperty(navigator, 'webdriver', {get: () => undefined})");
        // command.put()
        driver.executeCdpCommand("Page.addScriptToEvaluateOnNewDocument", command);
        // driver.executeScript("Object.defineProperty(navigator, 'webdriver', {get: () => undefined})","");
        return driver;
    }
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;
    @Before
    public void setUp() {
        driver = openCharm();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
    }
    @After
    public void tearDown() {
        // driver.quit();
    }
    public String waitForWindow(int timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Set<String> whNow = driver.getWindowHandles();
        Set<String> whThen = (Set<String>) vars.get("window_handles");
        if (whNow.size() > whThen.size()) {
            whNow.removeAll(whThen);
        }
        return whNow.iterator().next();
    }
    @Test
    public void qqqqq() {
        driver.get("https://www.baidu.com/");
        vars.put("window_handles", driver.getWindowHandles());
        driver.findElement(By.linkText("地图")).click();
        vars.put("win3373", waitForWindow(2000));
        vars.put("root", driver.getWindowHandle());
        driver.switchTo().window(vars.get("win3373").toString());
        driver.switchTo().window(vars.get("root").toString());
        vars.put("window_handles", driver.getWindowHandles());
        driver.findElement(By.cssSelector(".hotsearch-item:nth-child(3) .title-content-title")).click();
        vars.put("win625", waitForWindow(2000));
        driver.switchTo().window(vars.get("win625").toString());
    }
}
