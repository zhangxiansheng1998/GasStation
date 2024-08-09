package cases;

import elements.GasGunElement;
import elements.LoginElement;
import elements.ShopElement;
import elements.MarketingReductionElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tools.Browser;
import tools.MyScreenshot;
import java.io.IOException;


public class MarketingReduction {

    public static WebDriver driver;
    public static Browser browser;
    private static MyScreenshot screenshotHelper;

    @BeforeClass
    public static void setUp() throws IOException{
        MarketingReduction.browser = new Browser();
        MarketingReduction.browser.InitConfigData();
        driver = MarketingReduction.browser.getBrowser(); // 这一步才开始调用getBrowser()函数，并生成浏览器driver
        screenshotHelper = new MyScreenshot(driver);  // 实例化截图类,调用screenshot()函数进行截图
        MarketingReduction.browser.implicitlyWait(10);
    }

    @Test(description = "6-1:登录系统")
    public void Login() {
        MarketingReduction.browser.input(By.xpath(LoginElement.phone_input_box),"15180381485");
        MarketingReduction.browser.input(By.xpath(LoginElement.code_input_box),"thinkr");
        MarketingReduction.browser.explicitlyWait(By.xpath(LoginElement.login_button),10);
        MarketingReduction.browser.click(By.xpath(LoginElement.login_button));
        System.out.println("登录成功!");
    }

    @Test(dependsOnMethods = {"Login"}, description = "6-2:进入满减券页面")
    public void ReductionCoupon() throws InterruptedException {
        MarketingReduction.browser.explicitlyWait(By.xpath(ShopElement.my_shop_button), 10);
        MarketingReduction.browser.click(By.xpath(ShopElement.my_shop_button));
        MarketingReduction.browser.click(By.xpath(ShopElement.shop_logo));
        MarketingReduction.browser.click(By.xpath(MarketingReductionElement.marketing));
        Thread.sleep(2000);
        MarketingReduction.browser.click(By.xpath(MarketingReductionElement.reduction_coupon));
        MarketingReduction.browser.explicitlyWait(By.xpath(MarketingReductionElement.reduction_coupon_text), 10);
        MarketingReduction.browser.Assert(By.xpath(MarketingReductionElement.reduction_coupon_text),"满减券");
    }

    @Test(dependsOnMethods = {"ReductionCoupon"}, description = "6-3:新增满减券")
    public void AddReductionCoupon() throws InterruptedException {
        MarketingReduction.browser.explicitlyWait(By.xpath(MarketingReductionElement.add_reduction_coupon_button), 10);
        MarketingReduction.browser.click(By.xpath(MarketingReductionElement.add_reduction_coupon_button));
        MarketingReduction.browser.input(By.xpath(MarketingReductionElement.coupon_name), MarketingReduction.browser.GenerateGasGunName());
        MarketingReduction.browser.input(By.xpath(MarketingReductionElement.satisfied_amount), "200");
        MarketingReduction.browser.input(By.xpath(MarketingReductionElement.reduction_amount), "10");
        MarketingReduction.browser.click(By.xpath(MarketingReductionElement.available_disesl));
        MarketingReduction.browser.input(By.xpath(MarketingReductionElement.valid_days), "7");
        MarketingReduction.browser.click(By.xpath(MarketingReductionElement.confirm_button));
    }

    @Test(dependsOnMethods = {"AddReductionCoupon"}, description = "6-4:下线满减券")
    public void OfflineReductionCoupon() throws InterruptedException {
        MarketingReduction.browser.explicitlyWait(By.xpath(MarketingReductionElement.offline_button), 10);
        MarketingReduction.browser.click(By.xpath(MarketingReductionElement.offline_button));
        MarketingReduction.browser.click(By.xpath(MarketingReductionElement.offline_confirm_button));
        MarketingReduction.browser.explicitlyWait(By.xpath(MarketingReductionElement.offline_text), 10);
        MarketingReduction.browser.Assert(By.xpath(MarketingReductionElement.offline_text),"优惠券下线成功");
    }

    @Test(dependsOnMethods = {"OfflineReductionCoupon"}, description = "6-5:删除满减券")
    public void DeleteReductionCoupon() throws InterruptedException {
        MarketingReduction.browser.explicitlyWait(By.xpath(MarketingReductionElement.delete_button), 10);
        MarketingReduction.browser.click(By.xpath(MarketingReductionElement.delete_button));
        MarketingReduction.browser.click(By.xpath(MarketingReductionElement.offline_confirm_button));
        MarketingReduction.browser.explicitlyWait(By.xpath(MarketingReductionElement.offline_text), 10);
        MarketingReduction.browser.Assert(By.xpath(MarketingReductionElement.offline_text),"删除成功");
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }
}

