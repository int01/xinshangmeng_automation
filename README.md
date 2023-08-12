# iminhw-xinsahngmeng-qtp

新商盟自动化定烟脚本

    需要安装jdk1.8+，并配置环境变量。下载安装谷歌浏览器，
    并保证谷歌浏览器能正常打开网页。下载安装chrome driver，
    具体方法自行按操作系统百度解决。


```java
   private static WebDriver openCharm() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        // options.addArguments("--user-data-dir=/Users/minhongwei/Library/Application Support/Google/Chrome/");
        options.addArguments("disable-infobars");
        options.addArguments("--disable-blink-features");
        options.addArguments("--disable-blink-features=AutomationControlled");
        // 设置ExperimentalOption
        List<String> excludeSwitches = Lists.newArrayList("enable-automation");
        options.setExperimentalOption("excludeSwitches", excludeSwitches);
        options.setExperimentalOption("useAutomationExtension", false);

        ChromeDriver driver = new ChromeDriver(options);
        // 修改window.navigator.webdirver=undefined，防机器人识别机制
        Map<String, Object> command = new HashMap<>();
        command.put("source", "Object.defineProperty(navigator, 'webdriver', {get: () => undefined})");
        // command.put()
        driver.executeCdpCommand("Page.addScriptToEvaluateOnNewDocument", command);
        // driver.executeScript("Object.defineProperty(navigator, 'webdriver', {get: () => undefined})","");
        return driver;
    }
```
