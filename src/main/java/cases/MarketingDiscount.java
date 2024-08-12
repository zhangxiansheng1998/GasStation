package cases;

import elements.*;
import elements.MarketingDiscountElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tools.Browser;
import tools.MyScreenshot;

import java.io.IOException;

public class MarketingDiscount {
    public static WebDriver driver;
    public static Browser browser;
    private static MyScreenshot screenshotHelper;

    @BeforeClass
    public static void setUp() throws IOException {
        MarketingDiscount.browser = new Browser();
        MarketingDiscount.browser.InitConfigData();
        driver = MarketingDiscount.browser.getBrowser(); // 这一步才开始调用getBrowser()函数，并生成浏览器driver
        screenshotHelper = new MyScreenshot(driver);  // 实例化截图类,调用screenshot()函数进行截图
        MarketingDiscount.browser.implicitlyWait(10);
    }

    @Test(description = "7-1:登录系统")
    public void Login() {
        MarketingDiscount.browser.input(By.xpath(LoginElement.phone_input_box),"15180381485");
        MarketingDiscount.browser.input(By.xpath(LoginElement.code_input_box),"thinkr");
        MarketingDiscount.browser.explicitlyWait(By.xpath(LoginElement.login_button),10);
        MarketingDiscount.browser.click(By.xpath(LoginElement.login_button));
        System.out.println("登录成功!");
    }

    @Test(dependsOnMethods = {"Login"}, description = "7-2:进入折扣券页面")
    public void DiscountCoupon() throws InterruptedException {
        MarketingDiscount.browser.explicitlyWait(By.xpath(ShopElement.my_shop_button), 10);
        MarketingDiscount.browser.click(By.xpath(ShopElement.my_shop_button));
        MarketingDiscount.browser.click(By.xpath(ShopElement.shop_logo));
        MarketingDiscount.browser.click(By.xpath(MarketingDiscountElement.marketing));
        Thread.sleep(2000);
        MarketingDiscount.browser.click(By.xpath(MarketingDiscountElement.discount_coupon));
        MarketingDiscount.browser.explicitlyWait(By.xpath(MarketingDiscountElement.discount_coupon_text), 10);
        MarketingDiscount.browser.Assert(By.xpath(MarketingDiscountElement.discount_coupon_text),"折扣券");
    }

    @Test(dependsOnMethods = {"DiscountCoupon"}, description = "7-3:新增折扣券")
    public void AddDiscountCoupon() throws InterruptedException {
        MarketingDiscount.browser.explicitlyWait(By.xpath(MarketingDiscountElement.add_discount_coupon_button), 10);
        MarketingDiscount.browser.click(By.xpath(MarketingDiscountElement.add_discount_coupon_button));
        MarketingDiscount.browser.input(By.xpath(MarketingDiscountElement.coupon_name), MarketingDiscount.browser.GenerateGasGunName());
        MarketingDiscount.browser.input(By.xpath(MarketingDiscountElement.satisfied_amount), "300");
        MarketingDiscount.browser.input(By.xpath(MarketingDiscountElement.discount_amount), "90");
        MarketingDiscount.browser.click(By.xpath(MarketingDiscountElement.available_disesl));
        MarketingDiscount.browser.input(By.xpath(MarketingDiscountElement.valid_days), "14");
        MarketingDiscount.browser.click(By.xpath(MarketingDiscountElement.confirm_button));
        System.out.println("折扣券新增成功!");
    }

    @Test(dependsOnMethods = {"AddDiscountCoupon"}, description = "7-4:下线折扣券")
    public void OfflineDiscountCoupon() throws InterruptedException {
        MarketingDiscount.browser.explicitlyWait(By.xpath(MarketingDiscountElement.offline_button), 10);
        MarketingDiscount.browser.click(By.xpath(MarketingDiscountElement.offline_button));
        MarketingDiscount.browser.click(By.xpath(MarketingDiscountElement.offline_confirm_button));
        MarketingDiscount.browser.explicitlyWait(By.xpath(MarketingDiscountElement.offline_text), 10);
        MarketingDiscount.browser.Assert(By.xpath(MarketingDiscountElement.offline_text),"优惠券下线成功");
        System.out.println("折扣券下线成功!");
    }

    @Test(dependsOnMethods = {"OfflineDiscountCoupon"}, description = "7-5:删除折扣券")
    public void DeleteDiscountCoupon() throws InterruptedException {
        MarketingDiscount.browser.explicitlyWait(By.xpath(MarketingDiscountElement.delete_button), 10);
        MarketingDiscount.browser.click(By.xpath(MarketingDiscountElement.delete_button));
        MarketingDiscount.browser.click(By.xpath(MarketingDiscountElement.offline_confirm_button));
        MarketingDiscount.browser.explicitlyWait(By.xpath(MarketingDiscountElement.offline_text), 10);
        MarketingDiscount.browser.Assert(By.xpath(MarketingDiscountElement.offline_text),"删除成功");
        System.out.println("折扣券删除成功!");
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }
}