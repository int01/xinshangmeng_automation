import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @Author minhongwei
 * @DateTime 2023/5/22 14:02 星期一
 * @Description: TODO
 */
public class sctaxTest {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;
    // 91510107MA62PPPN1M
// 15983377317
// 统一社会信用代码/纳税人识别号
    String Identification = "91510107MA62PPPN1M";
    // 居民身份证号码/手机号码/用户名
    String username = "15983377317";
    // 个人用户密码(初始密码为证件号码后六位)
    String password = "wang1985";

    // @Before
    // public void setUp() {
    //     ChromeOptions option = new ChromeOptions();
    //     option.addArguments("--remote-allow-origins=*");
    //     driver = new ChromeDriver(option);
    //     js = (JavascriptExecutor) driver;
    //     // vars = new HashMap<String, Object>();
    // }
    @Test
    public void login() {
        ChromeOptions option = new ChromeOptions();
        option.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(option);
        driver.get("https://tpass.sichuan.chinatax.gov.cn:8443/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

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
        // 得到验证码
        driver.findElement(By.xpath("//*[@id=\"app\"]/div/div[1]/div[2]/div/div[2]/div/div[3]/div/div[2]/div/form/div[1]/div[2]/div/div/button")).click();

    }

    public static void main(String[] args) {
        WebDriver driver;
        ChromeOptions option = new ChromeOptions();
        option.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(option);
        // js = (JavascriptExecutor) driver;
        // vars = new HashMap<String, Object>();

        // 统一社会信用代码/纳税人识别号
        String Identification = "91510107MA62PPPN1M";
        // 居民身份证号码/手机号码/用户名
        String username = "15983377317";
        // 个人用户密码(初始密码为证件号码后六位)
        String password = "wang1985";

        driver.get("https://tpass.sichuan.chinatax.gov.cn:8443/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

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
        // 得到验证码
        driver.findElement(By.xpath("//*[@id=\"app\"]/div/div[1]/div[2]/div/div[2]/div/div[3]/div/div[2]/div/form/div[1]/div[2]/div/div/button")).click();

        Scanner sc = new Scanner(System.in);
        System.out.println("请输入验证码：");
        String yzm = sc.nextLine();
        System.out.println("验证码为：" + yzm);

    //     //*[@id="app"]/div/div[1]/div[2]/div/div[2]/div/div[3]/div/div[2]/div/form/div[1]/div[2]/div/div/div/input
        driver.findElement(By.xpath("//*[@id=\"app\"]/div/div[1]/div[2]/div/div[2]/div/div[3]/div/div[2]/div/form/div[1]/div[2]/div/div/div/input")).click();
        driver.findElement(By.xpath("//*[@id=\"app\"]/div/div[1]/div[2]/div/div[2]/div/div[3]/div/div[2]/div/form/div[1]/div[2]/div/div/div/input")).sendKeys(yzm);

        driver.findElement(By.linkText("登录")).click();

        driver.findElement(By.linkText("税务数字账户")).click();
    }

    @Test
    public void submitLogin() {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入验证码：");
        String yzm = sc.nextLine();
        System.out.println("验证码为：" + yzm);
        // String yzm = ""; // 动态输入验证码
        // driver.findElement(By.xpath("//*[@id=\"app\"]/div/div[1]/div[2]/div/div[2]/div/div[3]/div/div[2]/div/form/div[1]/div[2]/div/div/div/input")).sendKeys(yzm);

    }

    @After
    public void tearDown() {
        // driver.quit();
    }

}
