package cases;

import elements.LoginElement;
import elements.ShopElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tools.Browser;
import tools.MyScreenshot;

import java.io.IOException;
import java.util.logging.Logger;

public class Shop {

    public static WebDriver driver;
    public static Browser browser;
    private static MyScreenshot screenshotHelper;
    public static final Logger logger = Logger.getLogger(Browser.class.getName());

    @BeforeClass
    public static void setUp() throws IOException{
        Shop.browser = new Browser();
        Shop.browser.InitConfigData();
        driver = Shop.browser.getBrowser(); // 这一步才开始调用getBrowser()函数，并生成浏览器driver
        screenshotHelper = new MyScreenshot(driver);  // 实例化截图类,调用screenshot()函数进行截图
        Shop.browser.implicitlyWait(10);
    }

    @Test(description = "2-1:登录系统")
    public void Login() {
        Shop.browser.input(By.xpath(LoginElement.phone_input_box),"15180381485");
        Shop.browser.input(By.xpath(LoginElement.code_input_box),"thinkr");
        Shop.browser.explicitlyWait(By.xpath(LoginElement.login_button),10);
        Shop.browser.click(By.xpath(LoginElement.login_button));
        logger.info("登录成功!");
    }

    @Test(dependsOnMethods = {"Login"}, description = "2-2:验证店铺名称")
    public void MyShop() {
        Shop.browser.click(By.xpath(ShopElement.my_shop_button));
        Shop.browser.explicitlyWait(By.xpath(ShopElement.shop_name), 10);
        Shop.browser.Assert(By.xpath(ShopElement.shop_name),"新城加油站");
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }
}

