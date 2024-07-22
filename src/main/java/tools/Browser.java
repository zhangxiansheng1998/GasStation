package tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import java.util.concurrent.TimeUnit;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import org.testng.Assert;

public class Browser {

    public String BrowserName;
    public String Domain;
    public String Headless;
    public static WebDriver driver;

    private static final Logger logger = Logger.getLogger(Browser.class.getName());

    public void InitConfigData() throws IOException{

        Properties p = new Properties();
        // 加载配置文件
        InputStream ips = new FileInputStream("/Users/macbook_air/Desktop/Backup/Automation/GasStation/src/main/java/tools/Browser.properties");
        p.load(ips);

        logger.info("Start to read values from Browser.properties file");

        BrowserName=p.getProperty("BrowserName");

        Domain = p.getProperty("Domain");

        Headless = p.getProperty("Headless");

        ips.close();

    }

    public WebDriver getBrowser(){

        if(BrowserName.equalsIgnoreCase("Firefox")){

            System.setProperty("webdriver.gecko.driver", "");
            driver = CreateFireFoxDriver();
            logger.info("Launching Firefox ...");

        }else if(BrowserName.equalsIgnoreCase("Chrome")){
            if (Headless.equalsIgnoreCase("Yes")){
                ChromeOptions options = new ChromeOptions();
                options.addArguments("headless"); // 添加无头模式参数
                options.addArguments("window-size=1920,1080"); // 设置浏览器窗口大小（可选）
                options.addArguments("disable-gpu"); // 禁用GPU（在某些情况下可能有助于减少资源使用）
                options.addArguments("no-sandbox"); // 禁用沙箱模式（在某些环境中可能需要）
                System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
                driver= new ChromeDriver(options);
                logger.info("Launching Headless Chrome ...");
            }
            else {
                System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
                driver= new ChromeDriver();
                logger.info("Launching Chrome ...");
            }

        }else if(BrowserName.equalsIgnoreCase("Safari")){

            System.setProperty("webdriver.safari.driver", "/usr/bin/safaridriver");
            driver= new SafariDriver();
            logger.info("Launching Safari ...");
            
        }

        driver.get(Domain);
        logger.info("Open Domain: "+ Domain);
        driver.manage().window().maximize();
        logger.info("Maximize browser...");
        return driver;
    }

    /*
     * createFireFox Driver
     * @Param: null
     * @return: WebDriver
     */
    private WebDriver CreateFireFoxDriver() {

        WebDriver driver = null;
        FirefoxProfile firefoxProfile = new FirefoxProfile();

        firefoxProfile.setPreference("prefs.converted-to-utf8", true);
        //set download folder to default folder: TestDownload
        firefoxProfile.setPreference("browser.download.folderList", 2);
        firefoxProfile.setPreference("browser.download.dir", ".\\TestDownload");

        try {
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.setProfile(firefoxProfile);
        } catch (Exception e) {
            logger.info(e.getMessage());
            logger.info("Failed to initialize the Firefox driver");
            Assert.fail("An exception occurred: " + e.getMessage(), e);
        }
        return driver;
    }

    /*
     * 关闭浏览器并退出方法
     */

    public void tearDown() throws InterruptedException{
        logger.info("Closing browser...");
        Thread.sleep(3000);
        driver.quit();

    }

    /*
     * 定位元素
     * @param loc 元素
     */
    public WebElement locator(By loc){
        try{
            return driver.findElement(loc);
        } catch (Exception e) {
            HandleException(e);
            Assert.fail("An exception occurred: " + e.getMessage(), e);
        }
        return null;
    }

    /*
     * 输入操作
     * @param loc 元素
     * @param text 要输入的文本
     */
    public void input(By loc,String text){
        try{
            locator(loc).sendKeys(text);
        } catch (Exception e) {
            HandleException(e);
            Assert.fail("An exception occurred: " + e.getMessage(), e);
        }
    }

    /*
     * 点击操作
     * @param loc 元素
     */
    public void click(By loc){
        try{
            locator(loc).click();
        } catch (Exception e) {
            HandleException(e);
            Assert.fail("An exception occurred: " + e.getMessage(), e);
        }
    }

    /*
     * 隐式等待
     * @param duration 等待时间，单位秒
     */

    public void implicitlyWait(long duration){
        try{
            driver.manage().timeouts().implicitlyWait(duration, TimeUnit.SECONDS);
            logger.info("implicitlyWait for "+duration+" seconds.");
        } catch (Exception e) {
            HandleException(e);
            Assert.fail("An exception occurred: " + e.getMessage(), e);
        }
    }

    /*
     * 显示等待
     * @param locator 元素
     * @param duration 等待时间，单位秒
     */
    public void explicitlyWait(By locator, long duration) {
        try{
            WebDriverWait wait = new WebDriverWait(driver, duration);
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            logger.info("explicitlyWait element【"+ locator +"】for "+ duration +" seconds.");
        } catch (Exception e) {
            HandleException(e);
            Assert.fail("An exception occurred: " + e.getMessage(), e);
        }
    }

    /*
     * 选中元素对应的内容，然后删除
     * @param locator 元素
     */
    public void select_and_delete(By locator) {
        try{
            driver.findElement(locator).click();
            Actions actions = new Actions(driver);
            actions.keyDown(Keys.COMMAND).sendKeys("a").keyUp(Keys.COMMAND).perform();
            actions.sendKeys(Keys.BACK_SPACE).perform();
        } catch (Exception e) {
            HandleException(e);
            Assert.fail("An exception occurred: " + e.getMessage(), e);
        }
    }

    /*
     * 按当前时间的小时和分钟，生成一个带小数位的随机数，格式为12.36
     */
    public double GenerateGasPrice() {
        try{
            LocalTime currentTime = LocalTime.now();
            int hour = currentTime.getHour();
            int minute = currentTime.getMinute();
            return hour + minute / 100.0;
        } catch (Exception e) {
            HandleException(e);
            Assert.fail("An exception occurred: " + e.getMessage(), e);
            return 0;
        }
    }

    /*
     * 断言文本内容是否相同
     * @param locator 元素
     * @param txt 要断言的值
     */
    public void Assert(By locator, String txt) {
        try{
            assert Objects.equals(driver.findElement(locator).getText(), txt);
            System.out.println("Assert element【"+ locator +"】" + "与文本" + "【" + txt + "】相同，断言成功！" );
        } catch (Exception e) {
            HandleException(e);
            Assert.fail("An exception occurred: " + e.getMessage(), e);
        }
    }

    /*
     * 获取当前发生异常的函数
     */
    private void HandleException(Exception e) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        if (stackTraceElements.length > 2) {
            String callingFunctionName = stackTraceElements[2].getMethodName();
            System.err.println("【" + callingFunctionName + "】函数发生异常：" + e.getMessage());
        } else {
            System.err.println("函数发生异常：" + e.getMessage());
        }
        e.printStackTrace();
    }

    /*
     * 按当前时间的年、月、日、小时和分钟，生成十位数的一个随机数，格式为2406190949
     */
    public String GenerateGasGunName() {
        try{
            LocalDate currentDate = LocalDate.now();

            // 获取当前时间
            LocalTime currentTime = LocalTime.now();

            // 格式化日期为"yyMMdd"格式，比如2024年6月19日会被格式化为"240619"
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyMMdd");
            String formattedDate = currentDate.format(dateFormatter);

            // 提取小时和分钟，并格式化为两位数
            int hour = currentTime.get(ChronoField.HOUR_OF_DAY);
            int minute = currentTime.get(ChronoField.MINUTE_OF_HOUR);
            String formattedTime = String.format("%02d%02d", hour, minute);

            return formattedDate + formattedTime;
        } catch (Exception e) {
            HandleException(e);
            Assert.fail("An exception occurred: " + e.getMessage(), e);
            return "Exception";
        }
    }

    /*
     * 点击下拉框中某个特定的值
     * @param ulClass ul元素的class值
     * @param value 要查询的值
     */
    public void ClickDropdown(String ulClass, String value){
        try{
            String xpath = String.format("//ul[@class='%s']/li/span[contains(text(),'%s')]", ulClass, value);
            driver.findElement(By.xpath(xpath)).click();
        } catch (Exception e) {
            HandleException(e);
            Assert.fail("An exception occurred: " + e.getMessage(), e);
        }
    }

    /*
     * 判断元素是否存在于页面上
     * @param loc 元素
     */
    public String tell_elemnt(By loc){
        try{
            List<WebElement> elements = driver.findElements(loc);
            if (elements.isEmpty()) {
                return "元素不存在";
            } else {
                return "元素存在";
            }
        } catch (Exception e) {
            HandleException(e);
            Assert.fail("An exception occurred: " + e.getMessage(), e);
        }
        return null;
    }

    /*
     * 刷新页面
     */
    public void refresh(){
        try{
            driver.navigate().refresh();
        } catch (Exception e) {
            HandleException(e);
            Assert.fail("An exception occurred: " + e.getMessage(), e);
        }
    }
}