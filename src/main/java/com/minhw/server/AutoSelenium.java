package com.minhw.server;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

/**
 * @Author minhongwei
 * @DateTime 2023/5/23 11:48 星期二
 * @Description: TODO
 */
public class AutoSelenium {

    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = openCharm();
        // 统一社会信用代码/纳税人识别号
        String Identification = "91510107MA62PPPN1M";
        // 居民身份证号码/手机号码/用户名
        String username = "15983377317";
        // 个人用户密码(初始密码为证件号码后六位)
        String password = "wang1985";

        driver.get("https://tpass.sichuan.chinatax.gov.cn:8443/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        AutoSelenium autoSelenium = new AutoSelenium();
        autoSelenium.login(driver);

        Thread.sleep(1000 * 20);

        driver.quit();
    }

    private static WebDriver openCharm() {
        ChromeOptions option = new ChromeOptions();
        option.addArguments("--remote-allow-origins=*");
        return new ChromeDriver(option);
    }

    private void login(WebDriver driver) {
        String Identification = "91510107MA62PPPN1M";
        // 居民身份证号码/手机号码/用户名
        String username = "15983377317";
        // 个人用户密码(初始密码为证件号码后六位)
        String password = "wang1985";

        // 企业信息输入
        driver.findElement(By.xpath("//*[@id=\"app\"]/div/div[1]/div[2]/div/div[2]/div[3]/div[2]/div/div[1]/div[1]/div/form/div[1]/div/div/div/div[1]/input")).sendKeys(Identification);
        driver.findElement(By.xpath("//*[@id=\"app\"]/div/div[1]/div[2]/div/div[2]/div[3]/div[2]/div/div[1]/div[1]/div/form/div[2]/div/div[1]/div/input")).sendKeys(username);
        driver.findElement(By.xpath("//*[@id=\"app\"]/div/div[1]/div[2]/div/div[2]/div[3]/div[2]/div/div[1]/div[1]/div/form/div[3]/div[1]/div/div[2]/div/input")).sendKeys(password);
        // 执行滑动条
        WebElement tracker = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div[1]/div[2]/div/div[2]/div[3]/div[2]/div/div[1]/div[1]/div/form/div[4]/div/div/div/div/div[3]"));
        new Actions(driver)
                .dragAndDropBy(tracker, 500, 0)
                .perform();
        // 点击登录
        driver.findElement(By.xpath("//*[@id=\"app\"]/div/div[1]/div[2]/div/div[2]/div[3]/div[2]/div/div[1]/div[1]/div/form/div[5]/div/button")).click();

    }

    private void getSmsCode(WebDriver driver) {

    }
}
